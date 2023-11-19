/**
 * ProjectWizard.java created on 22.02.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.resource.ui.wizard;

import net.sf.dysis.resource.core.dto.PersonDTO;
import net.sf.dysis.resource.core.dto.PersonDTOImpl;
import net.sf.dysis.resource.core.service.IPersonRemoteService;
import net.sf.dysis.resource.ui.Activator;
import net.sf.dysis.resource.ui.Messages;
import net.sf.dysis.resource.ui.wizard.resource.ResourceWizardActivityPage;
import net.sf.dysis.resource.ui.wizard.resource.ResourceWizardResourcePage;

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
 * The <code>IWizard</code> for adding a new resource.
 * 
 * @author Stefan Reichert
 */
public class ResourceWizard extends Wizard implements INewWizard, IExecutableExtension {

	/** Page. */
	private ResourceWizardResourcePage resourceWizardResourcePage;

	/** Page. */
	private ResourceWizardActivityPage resourceWizardActivityPage;

	/** The final perspective id. */
	private String finalPerspectiveId;

	/**
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		PersonDTO personDTO = new PersonDTOImpl();
		personDTO.setUserId(resourceWizardResourcePage.getID());
		personDTO.setFirstname(resourceWizardResourcePage.getName());
		personDTO.setLastname(resourceWizardResourcePage.getSurame());
		personDTO.setEmploymentDate(resourceWizardResourcePage
				.getEmploymentDate());
		personDTO.setWeekHours(resourceWizardResourcePage.getHoursPerWeek());
		personDTO.setActive(resourceWizardResourcePage.getActive());
		personDTO.setActivities(resourceWizardActivityPage
				.getSelectedActivities());
		// set initial password
		personDTO.setPassword(resourceWizardResourcePage.getID().toLowerCase());
		Activator.getDefault().getService(IPersonRemoteService.class).save(
				personDTO);
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
		resourceWizardResourcePage = new ResourceWizardResourcePage();
		addPage(resourceWizardResourcePage);
		resourceWizardActivityPage = new ResourceWizardActivityPage();
		addPage(resourceWizardActivityPage);
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
		return Messages.getString("ResourceWizard.window.title"); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	@Override
	public boolean canFinish() {
		return getContainer().getCurrentPage() == resourceWizardActivityPage;
	}

	/** {@inheritDoc} */
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		finalPerspectiveId = config.getAttribute("finalPerspective"); //$NON-NLS-1$
	}
}
