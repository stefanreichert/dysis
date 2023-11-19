/**
 * ProjectWizard.java created on 22.02.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planning.ui.wizard;

import java.util.ArrayList;

import net.sf.dysis.planing.core.dto.ActivityDTO;
import net.sf.dysis.planing.core.dto.ProjectDTO;
import net.sf.dysis.planing.core.dto.ProjectDTOImpl;
import net.sf.dysis.planing.core.service.IProjectRemoteService;
import net.sf.dysis.planning.ui.Activator;
import net.sf.dysis.planning.ui.Messages;
import net.sf.dysis.planning.ui.wizard.project.ProjectWizardProjectPage;

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
public class ProjectWizard extends Wizard implements INewWizard, IExecutableExtension {

	/** The project details page. */
	private ProjectWizardProjectPage projectWizardProjectPage;
	/** The final perspective id. */
	private String finalPerspectiveId;

	/**
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		// get the data
		ProjectDTO projectDTO = new ProjectDTOImpl();
		projectDTO.setId(null);
		projectDTO.setName(projectWizardProjectPage.getName());
		projectDTO.setDescription(projectWizardProjectPage.getDescription());
		projectDTO.setStartDate(projectWizardProjectPage.getStartDate());
		projectDTO.setEndDate(projectWizardProjectPage.getEndDate());
		// add activity to project
		projectDTO.setActivities(new ArrayList<ActivityDTO>());
		Activator.getDefault().getService(IProjectRemoteService.class).save(
				projectDTO);
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
		return true;
	}

	/**
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		addPage(projectWizardProjectPage);
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		projectWizardProjectPage = new ProjectWizardProjectPage();
	}

	/**
	 * @see org.eclipse.jface.wizard.Wizard#getWindowTitle()
	 */
	public String getWindowTitle() {
		return Messages.getString("ProjectWizard.window.title"); //$NON-NLS-1$
	}

	/** {@inheritDoc} */
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		finalPerspectiveId = config.getAttribute("finalPerspective"); //$NON-NLS-1$
	}

}
