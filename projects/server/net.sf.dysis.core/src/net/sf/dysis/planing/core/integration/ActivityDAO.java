/**
 * ActivityDAO.java
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

import java.lang.Long;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.dysis.planing.core.domain.ActivityImpl;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link ActivityDAO} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public abstract class ActivityDAO extends HibernateDaoSupport
    implements IActivityDAO {

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public Set<ActivityImpl> loadAll() {
        return new HashSet<ActivityImpl>(getHibernateTemplate()
                                             .loadAll(ActivityImpl.class));
    }

    /** {@inheritDoc} */
    public ActivityImpl loadActivity(Long id) {
        return (ActivityImpl) getHibernateTemplate().load(ActivityImpl.class, id);
    }

    /** {@inheritDoc} */
    public ActivityImpl saveActivity(ActivityImpl activity) {
        if (activity.getId() == null) {
            Serializable id = getHibernateTemplate()
                                  .save(activity);
            return (ActivityImpl) getHibernateTemplate()
                                      .load(ActivityImpl.class, id);
        } else {
            return (ActivityImpl) getHibernateTemplate().merge(activity);
        }
    }

    /** {@inheritDoc} */
    public void deleteActivity(ActivityImpl activity) {
        getHibernateTemplate().delete(activity);
    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract List<ActivityImpl> internalFindByPerson(Long personId);

    /** {@inheritDoc} */
    public final List<ActivityImpl> findByPerson(Long personId) {
        return internalFindByPerson(personId);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract List<ActivityImpl> internalFindByDateAndPerson(
        Date day, Long personId);

    /** {@inheritDoc} */
    public final List<ActivityImpl> findByDateAndPerson(Date day, Long personId) {
        return internalFindByDateAndPerson(day, personId);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract List<ActivityImpl> internalFindByProject(Long projectId);

    /** {@inheritDoc} */
    public final List<ActivityImpl> findByProject(Long projectId) {
        return internalFindByProject(projectId);

    }
}
