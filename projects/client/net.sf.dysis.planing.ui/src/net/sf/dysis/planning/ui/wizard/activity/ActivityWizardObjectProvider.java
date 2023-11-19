/**
 * ActivityWizardObjectProvider.java created on 23.04.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planning.ui.wizard.activity;

import net.sf.dysis.planing.core.dto.ActivityDTO;
import net.sf.dysis.planing.core.dto.ActivityDTOImpl;
import net.sf.dysis.planing.core.dto.ProjectDTO;

import org.eclipse.jface.wizard.Wizard;

/**
 * A <code>IActivityWizardHandler</code> for using the wizard as provider for a
 * new task object.
 * 
 * @author Stefan Reichert
 */
public class ActivityWizardObjectProvider implements IActivityWizardHandler {

	/** The <code>ActivityDTO</code> that is created by this wizard. */
	private ActivityDTO activityDTO;

	/** The predefined project. */
	private ProjectDTO projectDTO;

	public ActivityWizardObjectProvider(ProjectDTO projectDTO) {
		super();
		this.projectDTO = projectDTO;
	}

	/**
	 * @see net.sf.dysis.planning.ui.wizard.activity.IActivityWizardHandler#performFinish(org.eclipse.jface.wizard.Wizard)
	 */
	public boolean performFinish(Wizard wizard) {
		ActivityWizardActivityPage wizardTaskPage = (ActivityWizardActivityPage) wizard
				.getStartingPage();
		activityDTO = new ActivityDTOImpl();
		activityDTO.setId(null);
		activityDTO.setActive(Boolean.TRUE);
		activityDTO.setName(wizardTaskPage.getName());
		activityDTO.setDescription(wizardTaskPage.getDescription());
		activityDTO.setProjectReference(wizardTaskPage.getProject());
		return true;
	}

	/**
	 * @return the task
	 */
	public ActivityDTO getActivity() {
		return activityDTO;
	}

	/**
	 * @see net.sf.dysis.planning.ui.wizard.activity.IActivityWizardHandler#getTaskProject()
	 */
	public ProjectDTO getActivityProject() {
		return projectDTO;
	}
}
