/**
 * ProjectDAOImpl.java
 *
 * Copyright (c) Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planing.core.integration;

import java.util.List;

import net.sf.dysis.planing.core.domain.ProjectImpl;
import net.sf.dysis.planing.core.dto.ProjectSearchCriteriaDTO;
import net.sf.dysis.resource.core.domain.PersonImpl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * <b>ATTENTION</b> The class {@link ProjectDAOImpl} is a generated class.
 * Changes to this file will <b>NOT</b> be overwritten by the next build. Your
 * changes are safe.
 * 
 * @author Stefan Reichert
 */
public class ProjectDAOImpl extends ProjectDAO {

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	protected List<ProjectImpl> internalFindByPerson(PersonImpl thePerson) {
		String queryString = "select project from ProjectImpl project join project.projectMemberships membership where membership.member = ?";
		return (List<ProjectImpl>) getHibernateTemplate().find(queryString,
				thePerson);
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	protected List<ProjectImpl> internalFindByCriteria(
			ProjectSearchCriteriaDTO searchCriteria) {
		Criteria hibernateCriteria = getHibernateTemplate().getSessionFactory()
				.openSession().createCriteria(ProjectImpl.class);
		if (searchCriteria.getName() != null
				&& searchCriteria.getName().length() > 0) {
			hibernateCriteria.add(Restrictions.like("name", searchCriteria
					.getName()));
		}
		if (searchCriteria.getDescription() != null
				&& searchCriteria.getDescription().length() > 0) {
			hibernateCriteria.add(Restrictions.like("description",
					searchCriteria.getDescription()));
		}
		if (searchCriteria.getFromStartDate() != null) {
			hibernateCriteria.add(Restrictions.ge("startDate", searchCriteria
					.getFromStartDate()));
		}
		if (searchCriteria.getToStartDate() != null) {
			hibernateCriteria.add(Restrictions.le("startDate", searchCriteria
					.getToStartDate()));
		}
		if (searchCriteria.getFromEndDate() != null) {
			hibernateCriteria.add(Restrictions.ge("endDate", searchCriteria
					.getFromEndDate()));
		}
		if (searchCriteria.getToEndDate() != null) {
			hibernateCriteria.add(Restrictions.le("endDate", searchCriteria
					.getToEndDate()));
		}
		return Criteria.DISTINCT_ROOT_ENTITY.transformList(hibernateCriteria.list());
	}
}
