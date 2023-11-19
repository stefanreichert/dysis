/**
 * ActivityRemoteServiceImpl.java
 *
 * Copyright (c) Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planing.core.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.dysis.planing.core.domain.ActivityImpl;
import net.sf.dysis.planing.core.domain.TimeEntryImpl;
import net.sf.dysis.planing.core.dto.ActivityDTO;
import net.sf.dysis.planing.core.dto.ActivityTimeEntryDTO;
import net.sf.dysis.planing.core.dto.TimeEntryDTO;

/**
 * <b>ATTENTION</b> The class {@link ActivityRemoteServiceImpl} is a generated
 * class. Changes to this file will <b>NOT</b> be overwritten by the next build.
 * Your changes are safe.
 * 
 * @author Stefan Reichert
 */
public class ActivityRemoteServiceImpl extends ActivityRemoteService {

	/** {@inheritDoc} */
	@Override
	protected List<ActivityDTO> internalLoadAll() {
		List<ActivityDTO> activities = new ArrayList<ActivityDTO>();
		for (ActivityImpl project : getActivityService().loadAll()) {
			activities.add(getTransformationService().transform(project, true));
		}
		return activities;
	}

	/** {@inheritDoc} */
	@Override
	protected ActivityDTO internalLoad(Long id) {
		return getTransformationService().transform(
				getActivityService().load(id), true);
	}

	/** {@inheritDoc} */
	@Override
	protected ActivityDTO internalSave(ActivityDTO activity) {
		return getTransformationService().transform(
				getActivityService().save(
						getTransformationService().transform(activity, true)),
				true);
	}

	/** {@inheritDoc} */
	@Override
	protected List<ActivityDTO> internalFindByPerson(Long personId) {
		List<ActivityDTO> activities = new ArrayList<ActivityDTO>();
		for (ActivityImpl project : getActivityService().findByPerson(personId)) {
			activities.add(getTransformationService().transform(project, true));
		}
		return activities;
	}

	/** {@inheritDoc} */
	@Override
	protected List<ActivityDTO> internalFindByProject(Long projectId) {
		List<ActivityDTO> activities = new ArrayList<ActivityDTO>();
		for (ActivityImpl project : getActivityService().findByProject(
				projectId)) {
			activities.add(getTransformationService().transform(project, true));
		}
		return activities;
	}

	/** {@inheritDoc} */
	@Override
	protected List<ActivityTimeEntryDTO> internalFindActivityTimeEntries(
			Long personId) {
		return getActivityService().findActivityTimeEntries(personId);
	}

	/** {@inheritDoc} */
	@Override
	protected void internalSaveTimeEntries(List<TimeEntryDTO> timeEntryDTOs) {
		List<TimeEntryImpl> timeEntries = new ArrayList<TimeEntryImpl>();
		for (TimeEntryDTO timeEntryDTO : timeEntryDTOs) {
			timeEntries.add(getTransformationService().transform(timeEntryDTO));
		}
		getActivityService().saveTimeEntries(timeEntries);
	}
}
