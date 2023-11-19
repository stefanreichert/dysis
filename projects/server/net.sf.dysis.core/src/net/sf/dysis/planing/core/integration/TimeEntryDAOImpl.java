/**
 * TimeEntryDAOImpl.java
 *
 * Copyright (c) Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planing.core.integration;

import java.util.List;

import net.sf.dysis.planing.core.domain.TimeEntryImpl;

/**
 * <b>ATTENTION</b> The class {@link TimeEntryDAOImpl} is a generated class.
 * Changes to this file will <b>NOT</b> be overwritten by the next build. Your
 * changes are safe.
 * 
 * @author Stefan Reichert
 */
public class TimeEntryDAOImpl extends TimeEntryDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected List<TimeEntryImpl> internalFindByActivityAndPerson(
			Long activityId, Long personId) {
		String queryString = "select timeEntry from TimeEntryImpl timeEntry where timeEntry.activity.id = ? and timeEntry.person.id = ?";
		return (List<TimeEntryImpl>) getHibernateTemplate().find(queryString,
				new Object[] { activityId, personId });
	}
}
