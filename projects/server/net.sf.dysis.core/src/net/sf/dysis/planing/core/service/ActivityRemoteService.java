/**
 * ActivityRemoteService.java
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
import net.sf.dysis.util.core.service.ITransformationService;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link ActivityRemoteService} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public abstract class ActivityRemoteService implements IActivityRemoteService {

    /** The private field for {@link net.sf.dysis.planing.core.service.IActivityService}. */
    private IActivityService activityService;

    /** The private field for {@link net.sf.dysis.util.core.service.ITransformationService}. */
    private ITransformationService transformationService;

    /**
     * @return the {@link net.sf.dysis.planing.core.service.IActivityService} to get
     */
    public IActivityService getActivityService() {
        return activityService;
    }

    /**
     *  @param activityService The {@link net.sf.dysis.planing.core.service.IActivityService} to set
     */
    public void setActivityService(IActivityService activityService) {
        this.activityService = activityService;
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
    protected abstract List<ActivityDTO> internalLoadAll();

    /** {@inheritDoc} */
    public final List<ActivityDTO> loadAll() {
        return internalLoadAll();

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract ActivityDTO internalLoad(Long id);

    /** {@inheritDoc} */
    public final ActivityDTO load(Long id) {
        return internalLoad(id);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract ActivityDTO internalSave(ActivityDTO activity);

    /** {@inheritDoc} */
    public final ActivityDTO save(ActivityDTO activity) {
        return internalSave(activity);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract List<ActivityDTO> internalFindByPerson(Long personId);

    /** {@inheritDoc} */
    public final List<ActivityDTO> findByPerson(Long personId) {
        return internalFindByPerson(personId);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract List<ActivityDTO> internalFindByProject(Long projectId);

    /** {@inheritDoc} */
    public final List<ActivityDTO> findByProject(Long projectId) {
        return internalFindByProject(projectId);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract List<ActivityTimeEntryDTO> internalFindActivityTimeEntries(
        Long personId);

    /** {@inheritDoc} */
    public final List<ActivityTimeEntryDTO> findActivityTimeEntries(
        Long personId) {
        return internalFindActivityTimeEntries(personId);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract void internalSaveTimeEntries(
        List<TimeEntryDTO> timeEntryDTOs);

    /** {@inheritDoc} */
    public final void saveTimeEntries(List<TimeEntryDTO> timeEntryDTOs) {
        internalSaveTimeEntries(timeEntryDTOs);

    }
}
