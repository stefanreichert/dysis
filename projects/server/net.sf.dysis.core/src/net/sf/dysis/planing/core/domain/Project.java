/**
 * Project.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planing.core.domain;

import java.io.Serializable;

import java.lang.Long;
import java.lang.String;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link Project} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
@MappedSuperclass
public abstract class Project implements Serializable {

    /** The serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The private field for {@link java.lang.Long}. */
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** The private field for {@link java.lang.String}. */
    @Column(nullable = false, unique = true)
    private String name;

    /** The private field for {@link java.lang.String}. */
    @Column(nullable = false, unique = false)
    private String description;

    /** The private field for {@link java.util.Date}. */
    @Column(nullable = false, unique = false)
    private Date startDate;

    /** The private field for {@link java.util.Date}. */
    @Column(nullable = false, unique = false)
    private Date endDate;

    /** The private field for {@link List<ActivityImpl> getActivities()}. */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectReference", fetch = FetchType.EAGER)
    private List<ActivityImpl> activities;

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
     * @return the {@link List<ActivityImpl> getActivities()} to get
     */
    public List<ActivityImpl> getActivities() {
        return activities;
    }

    /**
     *  @param activities The {@link List<ActivityImpl>} to set
     */
    public void setActivities(List<ActivityImpl> activities) {
        this.activities = activities;
    }
}
