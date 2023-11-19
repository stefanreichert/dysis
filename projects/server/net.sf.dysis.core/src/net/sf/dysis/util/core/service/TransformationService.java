/**
 * TransformationService.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.util.core.service;

import java.lang.Boolean;

import net.sf.dysis.planing.core.domain.ActivityImpl;
import net.sf.dysis.planing.core.domain.ProjectImpl;
import net.sf.dysis.planing.core.domain.TimeEntryImpl;
import net.sf.dysis.planing.core.dto.ActivityDTO;
import net.sf.dysis.planing.core.dto.ProjectDTO;
import net.sf.dysis.planing.core.dto.TimeEntryDTO;
import net.sf.dysis.resource.core.domain.PersonImpl;
import net.sf.dysis.resource.core.dto.PersonDTO;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link TransformationService} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public abstract class TransformationService implements ITransformationService {

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract ActivityDTO internalTransform(ActivityImpl activity,
        Boolean tranformProject);

    /** {@inheritDoc} */
    public final ActivityDTO transform(ActivityImpl activity,
        Boolean tranformProject) {
        return internalTransform(activity, tranformProject);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract ActivityImpl internalTransform(ActivityDTO activityDTO,
        Boolean tranformProject);

    /** {@inheritDoc} */
    public final ActivityImpl transform(ActivityDTO activityDTO,
        Boolean tranformProject) {
        return internalTransform(activityDTO, tranformProject);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract ProjectDTO internalTransform(ProjectImpl theProject,
        Boolean tranformActivities);

    /** {@inheritDoc} */
    public final ProjectDTO transform(ProjectImpl theProject,
        Boolean tranformActivities) {
        return internalTransform(theProject, tranformActivities);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract ProjectImpl internalTransform(ProjectDTO theProjectDTO,
        Boolean tranformActivities);

    /** {@inheritDoc} */
    public final ProjectImpl transform(ProjectDTO theProjectDTO,
        Boolean tranformActivities) {
        return internalTransform(theProjectDTO, tranformActivities);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract PersonDTO internalTransform(PersonImpl person);

    /** {@inheritDoc} */
    public final PersonDTO transform(PersonImpl person) {
        return internalTransform(person);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract PersonImpl internalTransform(PersonDTO personDTO);

    /** {@inheritDoc} */
    public final PersonImpl transform(PersonDTO personDTO) {
        return internalTransform(personDTO);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract TimeEntryImpl internalTransform(
        TimeEntryDTO timeEntryDTO);

    /** {@inheritDoc} */
    public final TimeEntryImpl transform(TimeEntryDTO timeEntryDTO) {
        return internalTransform(timeEntryDTO);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract TimeEntryDTO internalTransform(TimeEntryImpl timeEntry);

    /** {@inheritDoc} */
    public final TimeEntryDTO transform(TimeEntryImpl timeEntry) {
        return internalTransform(timeEntry);

    }
}
