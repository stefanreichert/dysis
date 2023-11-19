/**
 * ProjectRemoteService.java
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
import net.sf.dysis.util.core.service.ITransformationService;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link ProjectRemoteService} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public abstract class ProjectRemoteService implements IProjectRemoteService {

    /** The private field for {@link net.sf.dysis.planing.core.service.IProjectService}. */
    private IProjectService projectService;

    /** The private field for {@link net.sf.dysis.util.core.service.ITransformationService}. */
    private ITransformationService transformationService;

    /**
     * @return the {@link net.sf.dysis.planing.core.service.IProjectService} to get
     */
    public IProjectService getProjectService() {
        return projectService;
    }

    /**
     *  @param projectService The {@link net.sf.dysis.planing.core.service.IProjectService} to set
     */
    public void setProjectService(IProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * @return the {@link net.sf.dysis.util.core.service.ITransformationService} to get
     */
    public ITransformationService getTransformationService() {
        return transformationService;
    }

    /**
     *  @param transformationService The {@link net.sf.dysis.util.core.service.ITransformationService} to set
     */
    public void setTransformationService(
        ITransformationService transformationService) {
        this.transformationService = transformationService;
    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract List<ProjectDTO> internalLoadAll();

    /** {@inheritDoc} */
    public final List<ProjectDTO> loadAll() {
        return internalLoadAll();

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract ProjectDTO internalLoad(Long id);

    /** {@inheritDoc} */
    public final ProjectDTO load(Long id) {
        return internalLoad(id);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract ProjectDTO internalSave(ProjectDTO theProject);

    /** {@inheritDoc} */
    public final ProjectDTO save(ProjectDTO theProject) {
        return internalSave(theProject);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract List<ProjectDTO> internalFindByCriteria(
        ProjectSearchCriteriaDTO searchCriteria);

    /** {@inheritDoc} */
    public final List<ProjectDTO> findByCriteria(
        ProjectSearchCriteriaDTO searchCriteria) {
        return internalFindByCriteria(searchCriteria);

    }
}
