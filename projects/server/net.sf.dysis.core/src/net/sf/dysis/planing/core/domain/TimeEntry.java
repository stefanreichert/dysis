/**
 * TimeEntry.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planing.core.domain;

import java.io.Serializable;

import java.lang.Integer;
import java.lang.Long;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import net.sf.dysis.resource.core.domain.PersonImpl;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link TimeEntry} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
@MappedSuperclass
public abstract class TimeEntry implements Serializable {

    /** The serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The private field for {@link java.lang.Long}. */
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** The private field for {@link java.util.Date}. */
    @Column(nullable = false, unique = false)
    private Date date;

    /** The private field for {@link java.lang.Integer}. */
    @Column(nullable = false, unique = false)
    private Integer hours;

    /** The private field for {@link PersonImpl getPerson()}. */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private PersonImpl person;

    /** The private field for {@link ActivityImpl getActivity()}. */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private ActivityImpl activity;

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
     * @return the {@link PersonImpl getPerson()} to get
     */
    public PersonImpl getPerson() {
        return person;
    }

    /**
     *  @param person The {@link net.sf.dysis.resource.core.domain.PersonImpl} to set
     */
    public void setPerson(PersonImpl person) {
        this.person = person;
    }

    /**
     * @return the {@link ActivityImpl getActivity()} to get
     */
    public ActivityImpl getActivity() {
        return activity;
    }

    /**
     *  @param activity The {@link net.sf.dysis.planing.core.domain.ActivityImpl} to set
     */
    public void setActivity(ActivityImpl activity) {
        this.activity = activity;
    }
}
