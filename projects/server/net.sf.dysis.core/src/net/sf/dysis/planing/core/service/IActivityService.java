/**
 * IActivityService.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planing.core.service;

import java.lang.Long;

import java.util.List;

import net.sf.dysis.planing.core.domain.ActivityImpl;
import net.sf.dysis.planing.core.domain.TimeEntryImpl;
import net.sf.dysis.planing.core.dto.ActivityTimeEntryDTO;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link IActivityService} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public interface IActivityService {
    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    List<ActivityImpl> loadAll();

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    ActivityImpl load(Long id);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    ActivityImpl save(ActivityImpl activity);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    List<ActivityImpl> findByPerson(Long personId);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    List<ActivityImpl> findByProject(Long projectId);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    List<ActivityTimeEntryDTO> findActivityTimeEntries(Long personId);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    void saveTimeEntries(List<TimeEntryImpl> timeEntryDTOs);
}
