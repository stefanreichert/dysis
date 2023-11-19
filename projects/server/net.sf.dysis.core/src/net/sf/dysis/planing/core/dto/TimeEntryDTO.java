/**
 * TimeEntryDTO.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planing.core.dto;

import java.io.Serializable;

import java.lang.Integer;
import java.lang.Long;

import java.util.Date;

import net.sf.dysis.resource.core.dto.PersonDTO;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link TimeEntryDTO} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public abstract class TimeEntryDTO implements Serializable {

    /** The serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The private field for {@link java.lang.Long}. */
    private Long id;

    /** The private field for {@link java.util.Date}. */
    private Date date;

    /** The private field for {@link java.lang.Integer}. */
    private Integer hours;

    /** The private field for {@link PersonDTO}. */
    private PersonDTO person;

    /** The private field for {@link ActivityDTO}. */
    private ActivityDTO activity;

    /**
     * @return the {@link java.lang.Long} to get
     */
    public Long getId() {
        return id;
    }

    /**
     *  @param id The {@link java.lang.Long} to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the {@link java.util.Date} to get
     */
    public Date getDate() {
        return date;
    }

    /**
     *  @param date The {@link java.util.Date} to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the {@link java.lang.Integer} to get
     */
    public Integer getHours() {
        return hours;
    }

    /**
     *  @param hours The {@link java.lang.Integer} to set
     */
    public void setHours(Integer hours) {
        this.hours = hours;
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
}
