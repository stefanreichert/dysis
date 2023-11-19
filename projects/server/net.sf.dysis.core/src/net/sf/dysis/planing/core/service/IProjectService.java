/**
 * IProjectService.java
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

import net.sf.dysis.planing.core.domain.ProjectImpl;
import net.sf.dysis.planing.core.dto.ProjectSearchCriteriaDTO;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link IProjectService} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public interface IProjectService {
    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    List<ProjectImpl> loadAll();

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    ProjectImpl load(Long id);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    ProjectImpl save(ProjectImpl theProject);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    List<ProjectImpl> findByCriteria(ProjectSearchCriteriaDTO searchCriteria);
}
