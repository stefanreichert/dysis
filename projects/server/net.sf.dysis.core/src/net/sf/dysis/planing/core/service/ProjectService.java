/**
 * ProjectService.java
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
import net.sf.dysis.planing.core.integration.IProjectDAO;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link ProjectService} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public abstract class ProjectService implements IProjectService {

    /** The private field for {@link net.sf.dysis.planing.core.integration.IProjectDAO}. */
    private IProjectDAO projectDAO;

    /**
     * @return the {@link net.sf.dysis.planing.core.integration.IProjectDAO} to get
     */
    public IProjectDAO getProjectDAO() {
        return projectDAO;
    }

    /**
     *  @param projectDAO The {@link net.sf.dysis.planing.core.integration.IProjectDAO} to set
     */
    public void setProjectDAO(IProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract List<ProjectImpl> internalLoadAll();

    /** {@inheritDoc} */
    public final List<ProjectImpl> loadAll() {
        return internalLoadAll();

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract ProjectImpl internalLoad(Long id);

    /** {@inheritDoc} */
    public final ProjectImpl load(Long id) {
        return internalLoad(id);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract ProjectImpl internalSave(ProjectImpl theProject);

    /** {@inheritDoc} */
    public final ProjectImpl save(ProjectImpl theProject) {
        return internalSave(theProject);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract List<ProjectImpl> internalFindByCriteria(
        ProjectSearchCriteriaDTO searchCriteria);

    /** {@inheritDoc} */
    public final List<ProjectImpl> findByCriteria(
        ProjectSearchCriteriaDTO searchCriteria) {
        return internalFindByCriteria(searchCriteria);

    }
}
