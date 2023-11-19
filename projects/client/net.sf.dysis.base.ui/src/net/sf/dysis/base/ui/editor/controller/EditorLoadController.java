/**
 * EditorLoadController.java created on 26.04.2009
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
import net.sf.dysis.base.ui.editor.input.DataProviderEditorInput;
import net.sf.dysis.base.ui.internal.Messages;

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
public abstract class EditorLoadController<Type> extends
		ServerAccessControllerBase<Type> {

	/** The {@link IWritableDataProvider} to use. */
	private DataProviderEditorInput<Type> dataProviderEditorInput;

	/** The context {@link IEditorPart}. */
	private IEditorPart editorPart;

	/**
	 * Constructor for <class>EditorSaveController</class>.
	 */
	public EditorLoadController(IEditorPart editorPart) {
		super(editorPart.getTitle());
		this.editorPart = editorPart;
	}

	/** {@inheritDoc} */
	@Override
	protected IStatus internalProceedServerAccess(IProgressMonitor monitor) {
		setData(dataProviderEditorInput.getInputData());
		return Status.OK_STATUS;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	protected boolean prepareAccess() {
		if (getServerAccessJob().getState() != Job.NONE) {
			MessageDialog
					.openInformation(
							Display.getDefault().getActiveShell(),
							Messages
									.getString("EditorLoadController.information.title"), //$NON-NLS-1$
							Messages
									.getString("EditorLoadController.information.message")); //$NON-NLS-1$
			return false;
		}
		dataProviderEditorInput = (DataProviderEditorInput<Type>) editorPart
				.getEditorInput();
		return true;
	}
}
