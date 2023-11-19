/**
 * PersonDAO.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.resource.core.integration;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;

import java.lang.String;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.dysis.planing.core.domain.ProjectImpl;
import net.sf.dysis.resource.core.domain.PersonImpl;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link PersonDAO} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public abstract class PersonDAO extends HibernateDaoSupport
    implements IPersonDAO {

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public Set<PersonImpl> loadAll() {
        return new HashSet<PersonImpl>(getHibernateTemplate()
                                           .loadAll(PersonImpl.class));
    }

    /** {@inheritDoc} */
    public PersonImpl loadPerson(Long id) {
        return (PersonImpl) getHibernateTemplate().load(PersonImpl.class, id);
    }

    /** {@inheritDoc} */
    public PersonImpl savePerson(PersonImpl person) {
        if (person.getId() == null) {
            Serializable id = getHibernateTemplate().save(person);
            return (PersonImpl) getHibernateTemplate().load(PersonImpl.class, id);
        } else {
            return (PersonImpl) getHibernateTemplate().merge(person);
        }
    }

    /** {@inheritDoc} */
    public void deletePerson(PersonImpl person) {
        getHibernateTemplate().delete(person);
    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract List<PersonImpl> internalFindByProject(
        ProjectImpl theProject);

    /** {@inheritDoc} */
    public final List<PersonImpl> findByProject(ProjectImpl theProject) {
        return internalFindByProject(theProject);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract PersonImpl internalFindByUserIdAndPassword(
        String userid, String password);

    /** {@inheritDoc} */
    public final PersonImpl findByUserIdAndPassword(String userid,
        String password) {
        return internalFindByUserIdAndPassword(userid, password);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract PersonImpl internalFindByUserId(String userid);

    /** {@inheritDoc} */
    public final PersonImpl findByUserId(String userid) {
        return internalFindByUserId(userid);

    }
}
