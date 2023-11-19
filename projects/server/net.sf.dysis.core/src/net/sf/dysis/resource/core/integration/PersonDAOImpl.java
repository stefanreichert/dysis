/**
 * PersonDAOImpl.java
 *
 * Copyright (c) Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.resource.core.integration;

import java.util.List;

import net.sf.dysis.planing.core.domain.ProjectImpl;
import net.sf.dysis.resource.core.domain.PersonImpl;

/**
 * <b>ATTENTION</b> The class {@link PersonDAOImpl} is a generated class.
 * Changes to this file will <b>NOT</b> be overwritten by the next build. Your
 * changes are safe.
 * 
 * @author Stefan Reichert
 */
public class PersonDAOImpl extends PersonDAO {

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	protected List<PersonImpl> internalFindByProject(ProjectImpl theProject) {
		String queryString = "select person from PersonImpl person join person.projectMemberships membership where membership.membershipProject = ?";
		return (List<PersonImpl>) getHibernateTemplate().find(queryString,
				theProject);
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	protected PersonImpl internalFindByUserIdAndPassword(String userid,
			String password) {
		String queryString = "select person from PersonImpl person where person.userId = ? and person.password = ?";
		List<PersonImpl> result = (List<PersonImpl>) getHibernateTemplate()
				.find(queryString, new Object[] { userid, password });
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result.iterator().next();
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	protected PersonImpl internalFindByUserId(String userid) {
		String queryString = "select person from PersonImpl person where person.userId = ?";
		List<PersonImpl> result = (List<PersonImpl>) getHibernateTemplate()
				.find(queryString, userid);
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result.iterator().next();
	}
}
