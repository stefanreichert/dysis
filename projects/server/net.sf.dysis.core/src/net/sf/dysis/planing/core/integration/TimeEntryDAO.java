/**
 * TimeEntryDAO.java
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.dysis.planing.core.domain.TimeEntryImpl;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link TimeEntryDAO} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public abstract class TimeEntryDAO extends HibernateDaoSupport
    implements ITimeEntryDAO {

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public Set<TimeEntryImpl> loadAll() {
        return new HashSet<TimeEntryImpl>(getHibernateTemplate()
                                              .loadAll(TimeEntryImpl.class));
    }

    /** {@inheritDoc} */
    public TimeEntryImpl loadTimeEntry(Long id) {
        return (TimeEntryImpl) getHibernateTemplate()
                                   .load(TimeEntryImpl.class, id);
    }

    /** {@inheritDoc} */
    public TimeEntryImpl saveTimeEntry(TimeEntryImpl timeEntry) {
        if (timeEntry.getId() == null) {
            Serializable id = getHibernateTemplate()
                                  .save(timeEntry);
            return (TimeEntryImpl) getHibernateTemplate()
                                       .load(TimeEntryImpl.class, id);
        } else {
            return (TimeEntryImpl) getHibernateTemplate().merge(timeEntry);
        }
    }

    /** {@inheritDoc} */
    public void deleteTimeEntry(TimeEntryImpl timeEntry) {
        getHibernateTemplate().delete(timeEntry);
    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract List<TimeEntryImpl> internalFindByActivityAndPerson(
        Long activityId, Long personId);

    /** {@inheritDoc} */
    public final List<TimeEntryImpl> findByActivityAndPerson(Long activityId,
        Long personId) {
        return internalFindByActivityAndPerson(activityId, personId);

    }
}
