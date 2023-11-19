/**
 * TransformationServiceImpl.java
 *
 * Copyright (c) Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.util.core.service;

import java.util.ArrayList;

import net.sf.dysis.planing.core.domain.ActivityImpl;
import net.sf.dysis.planing.core.domain.ProjectImpl;
import net.sf.dysis.planing.core.domain.TimeEntryImpl;
import net.sf.dysis.planing.core.dto.ActivityDTO;
import net.sf.dysis.planing.core.dto.ActivityDTOImpl;
import net.sf.dysis.planing.core.dto.ProjectDTO;
import net.sf.dysis.planing.core.dto.ProjectDTOImpl;
import net.sf.dysis.planing.core.dto.TimeEntryDTO;
import net.sf.dysis.planing.core.dto.TimeEntryDTOImpl;
import net.sf.dysis.resource.core.domain.PersonImpl;
import net.sf.dysis.resource.core.dto.PersonDTO;
import net.sf.dysis.resource.core.dto.PersonDTOImpl;

/**
 * <b>ATTENTION</b> The class {@link TransformationServiceImpl} is a generated
 * class. Changes to this file will <b>NOT</b> be overwritten by the next build.
 * Your changes are safe.
 * 
 * @author Stefan Reichert
 */
public class TransformationServiceImpl extends TransformationService {
	/** {@inheritDoc} */
	@Override
	protected ActivityImpl internalTransform(ActivityDTO activityDTO,
			Boolean transformProject) {
		ActivityImpl activity = new ActivityImpl();
		activity.setId(activityDTO.getId());
		activity.setName(activityDTO.getName());
		activity.setDescription(activityDTO.getDescription());
		activity.setActive(activityDTO.getActive());
		if (transformProject && activityDTO.getProjectReference() != null) {
			activity.setProjectReference(transform(activityDTO
					.getProjectReference(), false));
		}
		return activity;
	}

	/** {@inheritDoc} */
	@Override
	protected ActivityDTO internalTransform(ActivityImpl activity,
			Boolean transformProject) {
		ActivityDTO activityDTO = new ActivityDTOImpl();
		activityDTO.setId(activity.getId());
		activityDTO.setName(activity.getName());
		activityDTO.setDescription(activity.getDescription());
		activityDTO.setActive(activity.getActive());
		if (transformProject && activity.getProjectReference() != null) {
			activityDTO.setProjectReference(transform(activity
					.getProjectReference(), false));
		}
		return activityDTO;
	}

	/** {@inheritDoc} */
	@Override
	protected ProjectDTO internalTransform(ProjectImpl project,
			Boolean transformActivities) {
		ProjectDTO projectDTO = new ProjectDTOImpl();
		projectDTO.setId(project.getId());
		projectDTO.setName(project.getName());
		projectDTO.setDescription(project.getDescription());
		projectDTO.setStartDate(project.getStartDate());
		projectDTO.setEndDate(project.getEndDate());
		projectDTO.setActivities(new ArrayList<ActivityDTO>());
		if (transformActivities && projectDTO.getActivities() != null) {
			for (ActivityImpl activityImpl : project.getActivities()) {
				ActivityDTO activityDTO = transform(activityImpl, false);
				activityDTO.setProjectReference(projectDTO);
				projectDTO.getActivities().add(activityDTO);
			}
		}
		return projectDTO;
	}

	/** {@inheritDoc} */
	@Override
	protected ProjectImpl internalTransform(ProjectDTO projectDTO,
			Boolean transformActivities) {
		ProjectImpl project = new ProjectImpl();
		project.setId(projectDTO.getId());
		project.setName(projectDTO.getName());
		project.setDescription(projectDTO.getDescription());
		project.setStartDate(projectDTO.getStartDate());
		project.setEndDate(projectDTO.getEndDate());
		project.setActivities(new ArrayList<ActivityImpl>());
		if (transformActivities && projectDTO.getActivities() != null) {
			for (ActivityDTO activityDTO : projectDTO.getActivities()) {
				ActivityImpl activityImpl = transform(activityDTO, false);
				activityImpl.setProjectReference(project);
				project.getActivities().add(activityImpl);
			}
		}
		return project;
	}

	/** {@inheritDoc} */
	@Override
	protected PersonDTO internalTransform(PersonImpl person) {
		PersonDTO personDTO = new PersonDTOImpl();
		personDTO.setId(person.getId());
		personDTO.setUserId(person.getUserId());
		personDTO.setFirstname(person.getFirstname());
		personDTO.setLastname(person.getLastname());
		personDTO.setPassword(person.getPassword());
		personDTO.setEmploymentDate(person.getEmploymentDate());
		personDTO.setActive(person.getActive());
		personDTO.setWeekHours(person.getWeekHours());
		personDTO.setActivities(new ArrayList<ActivityDTO>());
		for (ActivityImpl activity : person.getActivities()) {
			personDTO.getActivities().add(transform(activity, false));
		}
		return personDTO;
	}

	/** {@inheritDoc} */
	@Override
	protected PersonImpl internalTransform(PersonDTO personDTO) {
		PersonImpl person = new PersonImpl();
		person.setId(personDTO.getId());
		person.setUserId(personDTO.getUserId());
		person.setFirstname(personDTO.getFirstname());
		person.setLastname(personDTO.getLastname());
		person.setPassword(personDTO.getPassword());
		person.setEmploymentDate(personDTO.getEmploymentDate());
		person.setActive(personDTO.getActive());
		person.setWeekHours(personDTO.getWeekHours());
		person.setActivities(new ArrayList<ActivityImpl>());
		for (ActivityDTO activityDTO : personDTO.getActivities()) {
			person.getActivities().add(transform(activityDTO, false));
		}
		return person;
	}

	/** {@inheritDoc} */
	@Override
	protected TimeEntryImpl internalTransform(TimeEntryDTO timeEntryDTO) {
		TimeEntryImpl timeEntry = new TimeEntryImpl();
		timeEntry.setId(timeEntryDTO.getId());
		timeEntry.setDate(timeEntryDTO.getDate());
		timeEntry.setHours(timeEntryDTO.getHours());
		timeEntry.setActivity(transform(timeEntryDTO.getActivity(), false));
		timeEntry.setPerson(transform(timeEntryDTO.getPerson()));
		return timeEntry;
	}

	/** {@inheritDoc} */
	@Override
	protected TimeEntryDTO internalTransform(TimeEntryImpl timeEntry) {
		TimeEntryDTO timeEntryDTO = new TimeEntryDTOImpl();
		timeEntryDTO.setId(timeEntry.getId());
		timeEntryDTO.setDate(timeEntry.getDate());
		timeEntryDTO.setHours(timeEntry.getHours());
		timeEntryDTO.setActivity(transform(timeEntry.getActivity(), false));
		timeEntryDTO.setPerson(transform(timeEntry.getPerson()));
		return timeEntryDTO;
	}
}
