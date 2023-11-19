/**
 * ActivityService.java
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
import net.sf.dysis.planing.core.integration.IActivityDAO;
import net.sf.dysis.planing.core.integration.ITimeEntryDAO;
import net.sf.dysis.resource.core.service.IPersonService;
import net.sf.dysis.util.core.service.ITransformationService;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link ActivityService} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public abstract class ActivityService implements IActivityService {

    /** The private field for {@link net.sf.dysis.planing.core.integration.IActivityDAO}. */
    private IActivityDAO activityDAO;

    /** The private field for {@link net.sf.dysis.planing.core.integration.ITimeEntryDAO}. */
    private ITimeEntryDAO timeEntryDAO;

    /** The private field for {@link net.sf.dysis.util.core.service.ITransformationService}. */
    private ITransformationService transformationService;

    /** The private field for {@link net.sf.dysis.resource.core.service.IPersonService}. */
    private IPersonService personService;

    /**
     * @return the {@link net.sf.dysis.planing.core.integration.IActivityDAO} to get
     */
    public IActivityDAO getActivityDAO() {
        return activityDAO;
    }

    /**
     *  @param activityDAO The {@link net.sf.dysis.planing.core.integration.IActivityDAO} to set
     */
    public void setActivityDAO(IActivityDAO activityDAO) {
        this.activityDAO = activityDAO;
    }

    /**
     * @return the {@link net.sf.dysis.planing.core.integration.ITimeEntryDAO} to get
     */
    public ITimeEntryDAO getTimeEntryDAO() {
        return timeEntryDAO;
    }

    /**
     *  @param timeEntryDAO The {@link net.sf.dysis.planing.core.integration.ITimeEntryDAO} to set
     */
    public void setTimeEntryDAO(ITimeEntryDAO timeEntryDAO) {
        this.timeEntryDAO = timeEntryDAO;
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
     * @return the {@link net.sf.dysis.resource.core.service.IPersonService} to get
     */
    public IPersonService getPersonService() {
        return personService;
    }

    /**
     *  @param personService The {@link net.sf.dysis.resource.core.service.IPersonService} to set
     */
    public void setPersonService(IPersonService personService) {
        this.personService = personService;
    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract List<ActivityImpl> internalLoadAll();

    /** {@inheritDoc} */
    public final List<ActivityImpl> loadAll() {
        return internalLoadAll();

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract ActivityImpl internalLoad(Long id);

    /** {@inheritDoc} */
    public final ActivityImpl load(Long id) {
        return internalLoad(id);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract ActivityImpl internalSave(ActivityImpl activity);

    /** {@inheritDoc} */
    public final ActivityImpl save(ActivityImpl activity) {
        return internalSave(activity);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract List<ActivityImpl> internalFindByPerson(Long personId);

    /** {@inheritDoc} */
    public final List<ActivityImpl> findByPerson(Long personId) {
        return internalFindByPerson(personId);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract List<ActivityImpl> internalFindByProject(Long projectId);

    /** {@inheritDoc} */
    public final List<ActivityImpl> findByProject(Long projectId) {
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
        List<TimeEntryImpl> timeEntryDTOs);

    /** {@inheritDoc} */
    public final void saveTimeEntries(List<TimeEntryImpl> timeEntryDTOs) {
        internalSaveTimeEntries(timeEntryDTOs);

    }
}
