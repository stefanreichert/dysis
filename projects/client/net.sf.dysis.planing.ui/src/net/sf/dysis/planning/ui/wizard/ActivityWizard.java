/**
 * ActivityWizard.java created on 22.02.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planning.ui.wizard;

import net.sf.dysis.planning.ui.Messages;
import net.sf.dysis.planning.ui.wizard.activity.ActivityWizardActivityPage;
import net.sf.dysis.planning.ui.wizard.activity.ActivityWizardSaveHandler;
import net.sf.dysis.planning.ui.wizard.activity.IActivityWizardHandler;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

/**
 * The <code>IWizard</code> for adding a new project.
 * 
 * @author Stefan Reichert
 */
public class ActivityWizard extends Wizard implements INewWizard,
		IExecutableExtension {

	/** This wizards handler for the perform event. */
	private IActivityWizardHandler activityWizardHandler;

	/** The final perspective id. */
	private String finalPerspectiveId;

	/**
	 * Constructor for <class>ActivityWizard</class>.
	 */
	public ActivityWizard() {
		this(new ActivityWizardSaveHandler());
	}

	/**
	 * Constructor for <class>ActivityWizard</class>.
	 */
	public ActivityWizard(IActivityWizardHandler activityWizardHandler) {
		super();
		this.activityWizardHandler = activityWizardHandler;
	}

	/**
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		boolean returnValue = activityWizardHandler.performFinish(this);
		if (finalPerspectiveId != null) {
			// try to switch to final perspective
			try {
				PlatformUI.getWorkbench().showPerspective(finalPerspectiveId,
						PlatformUI.getWorkbench().getActiveWorkbenchWindow());
			}
			catch (WorkbenchException workbenchException) {
				workbenchException.printStackTrace();
			}
		}
		return returnValue;
	}

	/**
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		addPage(new ActivityWizardActivityPage());
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	/**
	 * @see org.eclipse.jface.wizard.Wizard#getWindowTitle()
	 */
	public String getWindowTitle() {
		return Messages.getString("ActivityWizard.window.title"); //$NON-NLS-1$
	}

	/**
	 * @return the ActivityWizardHandler
	 */
	public IActivityWizardHandler getActivityWizardHandler() {
		return activityWizardHandler;
	}

	/** {@inheritDoc} */
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		finalPerspectiveId = config.getAttribute("finalPerspective"); //$NON-NLS-1$
	}

}
