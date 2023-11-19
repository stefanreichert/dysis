/**
 * ActivityDAOImpl.java
 *
 * Copyright (c) Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planing.core.integration;

import java.util.Date;
import java.util.List;

import net.sf.dysis.planing.core.domain.ActivityImpl;

/**
 * <b>ATTENTION</b> The class {@link ActivityDAOImpl} is a generated class.
 * Changes to this file will <b>NOT</b> be overwritten by the next build. Your
 * changes are safe.
 * 
 * @author Stefan Reichert
 */
public class ActivityDAOImpl extends ActivityDAO {

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	protected List<ActivityImpl> internalFindByPerson(Long personId) {
		String queryString = "select activity from ActivityImpl activity join activity.members person where person.id = ?";
		return (List<ActivityImpl>) getHibernateTemplate().find(queryString,
				personId);
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	protected List<ActivityImpl> internalFindByProject(Long projectId) {
		String queryString = "select activity from ActivityImpl activity where activity.projectReference.id = ?";
		return (List<ActivityImpl>) getHibernateTemplate().find(queryString,
				projectId);
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	protected List<ActivityImpl> internalFindByDateAndPerson(Date day, Long personId) {
		String queryString = "select activity from ActivityImpl activity join activity.projectReference as project join activity.members as person where activity.active = true and project.startDate <= ? and project.endDate >= ? and person.id = ?";
		return (List<ActivityImpl>) getHibernateTemplate().find(queryString,
				new Object[] { day, day, personId });
	}
}
