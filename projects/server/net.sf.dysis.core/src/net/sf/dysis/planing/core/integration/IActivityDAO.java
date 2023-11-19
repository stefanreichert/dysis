/**
 * IActivityDAO.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planing.core.integration;

import java.lang.Long;

import java.util.Date;
import java.util.List;
import java.util.Set;

import net.sf.dysis.planing.core.domain.ActivityImpl;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link IActivityDAO} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public interface IActivityDAO {
    /**
     * Loads all existing {@link net.sf.dysis.planing.core.domain.ActivityImpl} entities from the database.
     */
    Set<ActivityImpl> loadAll();

    /**
     * Loads the {@link net.sf.dysis.planing.core.domain.ActivityImpl} with the given key from the database.
     */
    ActivityImpl loadActivity(Long id);

    /**
     * Creates or updates the given {@link net.sf.dysis.planing.core.domain.ActivityImpl}.
     */
    ActivityImpl saveActivity(ActivityImpl activity);

    /**
     * Deletes the given {@link net.sf.dysis.planing.core.domain.ActivityImpl}.
     */
    void deleteActivity(ActivityImpl activity);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    List<ActivityImpl> findByPerson(Long personId);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    List<ActivityImpl> findByDateAndPerson(Date day, Long personId);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    List<ActivityImpl> findByProject(Long projectId);
}
