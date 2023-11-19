/**
 * ProjectDAO.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planing.core.integration;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.dysis.planing.core.domain.ProjectImpl;
import net.sf.dysis.planing.core.dto.ProjectSearchCriteriaDTO;
import net.sf.dysis.resource.core.domain.PersonImpl;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link ProjectDAO} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public abstract class ProjectDAO extends HibernateDaoSupport
    implements IProjectDAO {

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public Set<ProjectImpl> loadAll() {
        return new HashSet<ProjectImpl>(getHibernateTemplate()
                                            .loadAll(ProjectImpl.class));
    }

    /** {@inheritDoc} */
    public ProjectImpl loadProject(Long id) {
        return (ProjectImpl) getHibernateTemplate().load(ProjectImpl.class, id);
    }

    /** {@inheritDoc} */
    public ProjectImpl saveProject(ProjectImpl project) {
        if (project.getId() == null) {
            Serializable id = getHibernateTemplate()
                                  .save(project);
            return (ProjectImpl) getHibernateTemplate()
                                     .load(ProjectImpl.class, id);
        } else {
            return (ProjectImpl) getHibernateTemplate().merge(project);
        }
    }

    /** {@inheritDoc} */
    public void deleteProject(ProjectImpl project) {
        getHibernateTemplate().delete(project);
    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract List<ProjectImpl> internalFindByPerson(
        PersonImpl thePerson);

    /** {@inheritDoc} */
    public final List<ProjectImpl> findByPerson(PersonImpl thePerson) {
        return internalFindByPerson(thePerson);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract List<ProjectImpl> internalFindByCriteria(
        ProjectSearchCriteriaDTO searchCriteria);

    /** {@inheritDoc} */
    public final List<ProjectImpl> findByCriteria(
        ProjectSearchCriteriaDTO searchCriteria) {
        return internalFindByCriteria(searchCriteria);

    }
}
