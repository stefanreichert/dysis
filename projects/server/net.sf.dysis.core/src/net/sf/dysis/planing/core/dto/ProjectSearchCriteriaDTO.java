/**
 * ProjectSearchCriteriaDTO.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planing.core.dto;

import java.io.Serializable;

import java.lang.String;

import java.util.Date;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link ProjectSearchCriteriaDTO} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public abstract class ProjectSearchCriteriaDTO implements Serializable {

    /** The serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The private field for {@link java.lang.String}. */
    private String name;

    /** The private field for {@link java.lang.String}. */
    private String description;

    /** The private field for {@link java.util.Date}. */
    private Date fromStartDate;

    /** The private field for {@link java.util.Date}. */
    private Date toStartDate;

    /** The private field for {@link java.util.Date}. */
    private Date fromEndDate;

    /** The private field for {@link java.util.Date}. */
    private Date toEndDate;

    /**
     * @return the {@link java.lang.String} to get
     */
    public String getName() {
        return name;
    }

    /**
     *  @param name The {@link java.lang.String} to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the {@link java.lang.String} to get
     */
    public String getDescription() {
        return description;
    }

    /**
     *  @param description The {@link java.lang.String} to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the {@link java.util.Date} to get
     */
    public Date getFromStartDate() {
        return fromStartDate;
    }

    /**
     *  @param fromStartDate The {@link java.util.Date} to set
     */
    public void setFromStartDate(Date fromStartDate) {
        this.fromStartDate = fromStartDate;
    }

    /**
     * @return the {@link java.util.Date} to get
     */
    public Date getToStartDate() {
        return toStartDate;
    }

    /**
     *  @param toStartDate The {@link java.util.Date} to set
     */
    public void setToStartDate(Date toStartDate) {
        this.toStartDate = toStartDate;
    }

    /**
     * @return the {@link java.util.Date} to get
     */
    public Date getFromEndDate() {
        return fromEndDate;
    }

    /**
     *  @param fromEndDate The {@link java.util.Date} to set
     */
    public void setFromEndDate(Date fromEndDate) {
        this.fromEndDate = fromEndDate;
    }

    /**
     * @return the {@link java.util.Date} to get
     */
    public Date getToEndDate() {
        return toEndDate;
    }

    /**
     *  @param toEndDate The {@link java.util.Date} to set
     */
    public void setToEndDate(Date toEndDate) {
        this.toEndDate = toEndDate;
    }
}
