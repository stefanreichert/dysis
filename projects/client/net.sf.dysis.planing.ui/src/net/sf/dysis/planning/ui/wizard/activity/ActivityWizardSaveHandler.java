/**
 * ActivityWizardSaveHandler.java created on 23.04.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planning.ui.wizard.activity;

import net.sf.dysis.base.ui.dataprovider.IWritableDataProvider;
import net.sf.dysis.base.ui.dataprovider.key.PrimaryKey;
import net.sf.dysis.base.ui.dataprovider.registry.Registry;
import net.sf.dysis.planing.core.dto.ActivityDTO;
import net.sf.dysis.planing.core.dto.ActivityDTOImpl;
import net.sf.dysis.planing.core.dto.ProjectDTO;
import net.sf.dysis.planning.ui.dataprovider.ProjectDataProvider;
import net.sf.dysis.planning.ui.wizard.ActivityWizard;

import org.eclipse.jface.wizard.Wizard;

/**
 * Default handler for the {@link ActivityWizard}. Proceeds a save of the
 * {@link ActivityDTO} created.
 * 
 * @author Stefan Reichert
 */
public class ActivityWizardSaveHandler implements IActivityWizardHandler {

	/** {@inheritDoc} */
	public boolean performFinish(Wizard wizard) {
		ActivityWizardActivityPage wizardTaskPage = (ActivityWizardActivityPage) wizard
				.getStartingPage();
		// get the data
		ProjectDTO projectDTO = wizardTaskPage.getProject();
		ActivityDTO activityDTO = new ActivityDTOImpl();
		activityDTO.setId(null);
		activityDTO.setActive(true);
		activityDTO.setName(wizardTaskPage.getName());
		activityDTO.setDescription(wizardTaskPage.getDescription());
		activityDTO.setProjectReference(projectDTO);
		// add activity to project
		projectDTO.getActivities().add(activityDTO);
		// save it by saving the project
		IWritableDataProvider dataProvider = (IWritableDataProvider) Registry
				.getRegistry().lookupDataProvider(ProjectDataProvider.TYPE);
		dataProvider.putData(new PrimaryKey(projectDTO.getId()), projectDTO);
		return true;
	}

	/** {@inheritDoc} */
	public ProjectDTO getActivityProject() {
		return null;
	}

}
