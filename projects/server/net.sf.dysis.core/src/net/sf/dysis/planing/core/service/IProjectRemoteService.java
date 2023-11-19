/**
 * IProjectRemoteService.java
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

import net.sf.dysis.planing.core.dto.ProjectDTO;
import net.sf.dysis.planing.core.dto.ProjectSearchCriteriaDTO;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link IProjectRemoteService} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public interface IProjectRemoteService {
    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    List<ProjectDTO> loadAll();

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    ProjectDTO load(Long id);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    ProjectDTO save(ProjectDTO theProject);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    List<ProjectDTO> findByCriteria(ProjectSearchCriteriaDTO searchCriteria);
}
