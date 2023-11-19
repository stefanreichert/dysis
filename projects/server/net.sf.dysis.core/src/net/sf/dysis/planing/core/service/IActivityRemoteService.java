/**
 * IActivityRemoteService.java
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

import net.sf.dysis.planing.core.dto.ActivityDTO;
import net.sf.dysis.planing.core.dto.ActivityTimeEntryDTO;
import net.sf.dysis.planing.core.dto.TimeEntryDTO;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link IActivityRemoteService} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public interface IActivityRemoteService {
    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    List<ActivityDTO> loadAll();

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    ActivityDTO load(Long id);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    ActivityDTO save(ActivityDTO activity);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    List<ActivityDTO> findByPerson(Long personId);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    List<ActivityDTO> findByProject(Long projectId);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    List<ActivityTimeEntryDTO> findActivityTimeEntries(Long personId);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    void saveTimeEntries(List<TimeEntryDTO> timeEntryDTOs);
}
