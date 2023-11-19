/**
 * ITransformationService.java
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
 * The class {@link ITransformationService} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public interface ITransformationService {

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    ActivityDTO transform(ActivityImpl activity, Boolean tranformProject);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    ActivityImpl transform(ActivityDTO activityDTO, Boolean tranformProject);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    ProjectDTO transform(ProjectImpl theProject, Boolean tranformActivities);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    ProjectImpl transform(ProjectDTO theProjectDTO, Boolean tranformActivities);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    PersonDTO transform(PersonImpl person);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    PersonImpl transform(PersonDTO personDTO);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    TimeEntryImpl transform(TimeEntryDTO timeEntryDTO);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    TimeEntryDTO transform(TimeEntryImpl timeEntry);
}
