/**
 * ProjectDTO.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planing.core.dto;

import java.io.Serializable;

import java.lang.Long;
import java.lang.String;

import java.util.Date;
import java.util.List;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link ProjectDTO} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public abstract class ProjectDTO implements Serializable {

    /** The serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The private field for {@link java.lang.Long}. */
    private Long id;

    /** The private field for {@link java.lang.String}. */
    private String name;

    /** The private field for {@link java.lang.String}. */
    private String description;

    /** The private field for {@link java.util.Date}. */
    private Date startDate;

    /** The private field for {@link java.util.Date}. */
    private Date endDate;

    /** The private field for {@link ActivityDTO}. */
    private List<ActivityDTO> activities;

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
    public Date getStartDate() {
        return startDate;
    }

    /**
     *  @param startDate The {@link java.util.Date} to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the {@link java.util.Date} to get
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     *  @param endDate The {@link java.util.Date} to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the {@link ActivityDTO} to get
     */
    public List<ActivityDTO> getActivities() {
        return activities;
    }

    /**
     *  @param activities The {@link ActivityDTO} to set
     */
    public void setActivities(List<ActivityDTO> activities) {
        this.activities = activities;
    }
}
