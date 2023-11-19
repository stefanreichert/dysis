/**
 * ActivityTimeEntryDTO.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planing.core.dto;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import net.sf.dysis.resource.core.dto.PersonDTO;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link ActivityTimeEntryDTO} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public abstract class ActivityTimeEntryDTO implements Serializable {

    /** The serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The private field for {@link java.util.Date}. */
    private Date bookableFromDate;

    /** The private field for {@link java.util.Date}. */
    private Date bookableToDate;

    /** The private field for {@link ActivityDTO}. */
    private ActivityDTO activity;

    /** The private field for {@link PersonDTO}. */
    private PersonDTO person;

    /** The private field for {@link TimeEntryDTO}. */
    private List<TimeEntryDTO> timeEntries;

    /**
     * @return the {@link java.util.Date} to get
     */
    public Date getBookableFromDate() {
        return bookableFromDate;
    }

    /**
     *  @param bookableFromDate The {@link java.util.Date} to set
     */
    public void setBookableFromDate(Date bookableFromDate) {
        this.bookableFromDate = bookableFromDate;
    }

    /**
     * @return the {@link java.util.Date} to get
     */
    public Date getBookableToDate() {
        return bookableToDate;
    }

    /**
     *  @param bookableToDate The {@link java.util.Date} to set
     */
    public void setBookableToDate(Date bookableToDate) {
        this.bookableToDate = bookableToDate;
    }

    /**
     * @return the {@link ActivityDTO} to get
     */
    public ActivityDTO getActivity() {
        return activity;
    }

    /**
     *  @param activity The {@link ActivityDTO} to set
     */
    public void setActivity(ActivityDTO activity) {
        this.activity = activity;
    }

    /**
     * @return the {@link PersonDTO} to get
     */
    public PersonDTO getPerson() {
        return person;
    }

    /**
     *  @param person The {@link PersonDTO} to set
     */
    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    /**
     * @return the {@link TimeEntryDTO} to get
     */
    public List<TimeEntryDTO> getTimeEntries() {
        return timeEntries;
    }

    /**
     *  @param timeEntries The {@link TimeEntryDTO} to set
     */
    public void setTimeEntries(List<TimeEntryDTO> timeEntries) {
        this.timeEntries = timeEntries;
    }
}
