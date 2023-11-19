/**
 * ActivityServiceImpl.java
 *
 * Copyright (c) Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planing.core.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.dysis.planing.core.domain.ActivityImpl;
import net.sf.dysis.planing.core.domain.TimeEntryImpl;
import net.sf.dysis.planing.core.dto.ActivityDTO;
import net.sf.dysis.planing.core.dto.ActivityTimeEntryDTO;
import net.sf.dysis.planing.core.dto.ActivityTimeEntryDTOImpl;
import net.sf.dysis.planing.core.dto.TimeEntryDTO;

/**
 * <b>ATTENTION</b> The class {@link ActivityServiceImpl} is a generated class.
 * Changes to this file will <b>NOT</b> be overwritten by the next build. Your
 * changes are safe.
 * 
 * @author Stefan Reichert
 */
public class ActivityServiceImpl extends ActivityService {

	/** {@inheritDoc} */
	@Override
	protected List<ActivityImpl> internalLoadAll() {
		return new ArrayList<ActivityImpl>(getActivityDAO().loadAll());
	}

	/** {@inheritDoc} */
	@Override
	protected ActivityImpl internalLoad(Long id) {
		return getActivityDAO().loadActivity(id);
	}

	/** {@inheritDoc} */
	@Override
	protected ActivityImpl internalSave(ActivityImpl activity) {
		return getActivityDAO().saveActivity(activity);
	}

	/** {@inheritDoc} */
	@Override
	protected List<ActivityImpl> internalFindByPerson(Long personId) {
		return getActivityDAO().findByPerson(personId);
	}

	/** {@inheritDoc} */
	@Override
	protected List<ActivityImpl> internalFindByProject(Long projectId) {
		return getActivityDAO().findByProject(projectId);
	}

	/**
	 * Extracts the year, month and date information from the given {@link Date}
	 * and returns an instance containing only these values.
	 * 
	 * @param date
	 *            The {@link Date} to filter
	 * @return the filtered {@link Date}
	 */
	private Date dateOnly(Date date) {
		Calendar calendarOriginal = Calendar.getInstance();
		calendarOriginal.setTime(date);
		Calendar calendarModified = Calendar.getInstance();
		calendarModified.clear();
		calendarModified.set(calendarOriginal.get(Calendar.YEAR),
				calendarOriginal.get(Calendar.MONTH), calendarOriginal
						.get(Calendar.DATE));
		return calendarModified.getTime();
	}

	/** {@inheritDoc} */
	@Override
	protected List<ActivityTimeEntryDTO> internalFindActivityTimeEntries(
			Long personId) {
		List<ActivityTimeEntryDTO> activityTimeEntries = new ArrayList<ActivityTimeEntryDTO>();
		for (ActivityImpl activity : getActivityDAO().findByDateAndPerson(
				dateOnly(new Date()), personId)) {
			ActivityDTO activityDTO = getTransformationService().transform(
					activity, false);
			ActivityTimeEntryDTOImpl activityTimeEntryDTOImpl = new ActivityTimeEntryDTOImpl();
			activityTimeEntryDTOImpl.setBookableFromDate(dateOnly(activity
					.getProjectReference().getStartDate()));
			activityTimeEntryDTOImpl.setBookableToDate(dateOnly(activity
					.getProjectReference().getEndDate()));
			activityTimeEntryDTOImpl.setActivity(activityDTO);
			activityTimeEntryDTOImpl.setPerson(getTransformationService()
					.transform(getPersonService().load(personId)));
			activityTimeEntryDTOImpl
					.setTimeEntries(new ArrayList<TimeEntryDTO>());
			for (TimeEntryImpl timeEntryImpl : getTimeEntryDAO()
					.findByActivityAndPerson(activityDTO.getId(), personId)) {
				activityTimeEntryDTOImpl.getTimeEntries().add(
						getTransformationService().transform(timeEntryImpl));
			}
			activityTimeEntries.add(activityTimeEntryDTOImpl);
		}
		return activityTimeEntries;
	}

	/** {@inheritDoc} */
	@Override
	protected void internalSaveTimeEntries(List<TimeEntryImpl> timeEntries) {
		for (TimeEntryImpl timeEntry : timeEntries) {
			getTimeEntryDAO().saveTimeEntry(timeEntry);
		}
	}
}
