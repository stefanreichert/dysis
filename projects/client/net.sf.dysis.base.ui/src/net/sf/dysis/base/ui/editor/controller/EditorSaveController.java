/**
 * EditorSaveController.java created on 26.04.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.editor.controller;

import net.sf.dysis.base.ui.controller.ServerAccessControllerBase;
import net.sf.dysis.base.ui.dataprovider.IWritableDataProvider;
import net.sf.dysis.base.ui.dataprovider.key.IKey;
import net.sf.dysis.base.ui.internal.Messages;
import net.sf.dysis.base.ui.internal.editor.controller.EditorControllerRule;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;

/**
 * A controller that takes care of saving an editor asynchronously using a
 * {@link Job}.
 * 
 * @author Stefan Reichert
 */
public abstract class EditorSaveController<Type> extends
		ServerAccessControllerBase<Type> {

	/** The {@link IWritableDataProvider} to use. */
	private IWritableDataProvider dataProvider;

	/** The key of the data {@link Object} to save. */
	private IKey key;

	/**
	 * Constructor for <class>EditorSaveController</class>.
	 */
	public EditorSaveController(IEditorPart editorPart, Class<Type> contextClass) {
		super(editorPart.getTitle());
		// allow only one save per time
		getServerAccessJob().setRule(new EditorControllerRule(contextClass));
	}

	/** {@inheritDoc} */
	@Override
	protected boolean prepareAccess() {
		if (getServerAccessJob().getState() != Job.NONE) {
			MessageDialog
					.openInformation(
							Display.getDefault().getActiveShell(),
							Messages
									.getString("EditorSaveController.information.title"), //$NON-NLS-1$
							Messages
									.getString("EditorSaveController.information.message")); //$NON-NLS-1$
			return false;
		}
		dataProvider = getDataProvider();
		key = getKey(getData());
		return true;
	}

	/**
	 * @param data
	 *            The data {@link Object} to get the {@link IKey} for
	 * @return the {@link IKey} for the given data
	 */
	protected abstract IKey getKey(Type data);

	/**
	 * @return the {@link IWritableDataProvider} to use for saving
	 */
	protected abstract IWritableDataProvider getDataProvider();

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	protected IStatus internalProceedServerAccess(IProgressMonitor monitor) {
		setData((Type) dataProvider.putData(key, getData()));
		return Status.OK_STATUS;
	}
}
