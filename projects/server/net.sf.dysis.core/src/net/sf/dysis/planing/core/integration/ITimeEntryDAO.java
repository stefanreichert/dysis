/**
 * ITimeEntryDAO.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planing.core.integration;

import java.lang.Long;

import java.util.List;
import java.util.Set;

import net.sf.dysis.planing.core.domain.TimeEntryImpl;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link ITimeEntryDAO} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public interface ITimeEntryDAO {
    /**
     * Loads all existing {@link net.sf.dysis.planing.core.domain.TimeEntryImpl} entities from the database.
     */
    Set<TimeEntryImpl> loadAll();

    /**
     * Loads the {@link net.sf.dysis.planing.core.domain.TimeEntryImpl} with the given key from the database.
     */
    TimeEntryImpl loadTimeEntry(Long id);

    /**
     * Creates or updates the given {@link net.sf.dysis.planing.core.domain.TimeEntryImpl}.
     */
    TimeEntryImpl saveTimeEntry(TimeEntryImpl timeEntry);

    /**
     * Deletes the given {@link net.sf.dysis.planing.core.domain.TimeEntryImpl}.
     */
    void deleteTimeEntry(TimeEntryImpl timeEntry);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    List<TimeEntryImpl> findByActivityAndPerson(Long activityId, Long personId);
}
