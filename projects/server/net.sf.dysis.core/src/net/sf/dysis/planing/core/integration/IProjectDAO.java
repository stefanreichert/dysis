/**
 * IProjectDAO.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planing.core.integration;

import java.util.List;
import java.util.Set;

import net.sf.dysis.planing.core.domain.ProjectImpl;
import net.sf.dysis.planing.core.dto.ProjectSearchCriteriaDTO;
import net.sf.dysis.resource.core.domain.PersonImpl;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link IProjectDAO} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public interface IProjectDAO {
    /**
     * Loads all existing {@link net.sf.dysis.planing.core.domain.ProjectImpl} entities from the database.
     */
    Set<ProjectImpl> loadAll();

    /**
     * Loads the {@link net.sf.dysis.planing.core.domain.ProjectImpl} with the given key from the database.
     */
    ProjectImpl loadProject(Long id);

    /**
     * Creates or updates the given {@link net.sf.dysis.planing.core.domain.ProjectImpl}.
     */
    ProjectImpl saveProject(ProjectImpl project);

    /**
     * Deletes the given {@link net.sf.dysis.planing.core.domain.ProjectImpl}.
     */
    void deleteProject(ProjectImpl project);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    List<ProjectImpl> findByPerson(PersonImpl thePerson);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    List<ProjectImpl> findByCriteria(ProjectSearchCriteriaDTO searchCriteria);
}
