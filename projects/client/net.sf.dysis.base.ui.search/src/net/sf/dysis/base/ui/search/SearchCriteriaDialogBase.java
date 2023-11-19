/**
 * SearchCriteriaDialogBase.java created on 20.02.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.search;

import net.sf.dysis.base.ui.search.internal.Activator;
import net.sf.dysis.base.ui.search.internal.Messages;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.swtdesigner.ResourceManager;

/**
 * Abstract implementation of an {@link ISearchCriteriaDialog} as
 * {@link TitleAreaDialog};
 * 
 * @author Stefan Reichert
 */
public abstract class SearchCriteriaDialogBase extends TitleAreaDialog
		implements ISearchCriteriaDialog {

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public SearchCriteriaDialogBase(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		container.setLayout(new FillLayout());
		createCriteriaArea(container);

		setTitleImage(ResourceManager.getPluginImage(Activator.getDefault(),
				"img/criteriaDialog.gif")); //$NON-NLS-1$
		setTitle(Messages.getString("SearchCriteriaDialogBase.title")); //$NON-NLS-1$
		setMessage(null);
		setMessage(Messages.getString("SearchCriteriaDialogBase.message")); //$NON-NLS-1$
		//
		return area;
	}

	/** {@inheritDoc} */
	@Override
	public int open() {
		return super.open();
	}

	/**
	 * @param parent
	 *            Creates the criteria area
	 */
	protected abstract void createCriteriaArea(Composite parent);

	/**
	 * Create contents of the button bar
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, Messages.getString("SearchCriteriaDialogBase.button.search.label"), true); //$NON-NLS-1$
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected abstract Point getInitialSize();

	/** {@inheritDoc} */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.getString("SearchCriteriaDialogBase.shell.text")); //$NON-NLS-1$
		newShell.setImage(ResourceManager.getPluginImage(
				Activator.getDefault(), "img/search.gif")); //$NON-NLS-1$
	}

}
