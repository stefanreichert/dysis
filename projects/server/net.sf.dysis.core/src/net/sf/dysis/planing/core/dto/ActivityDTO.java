/**
 * ActivityDTO.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planing.core.dto;

import java.io.Serializable;

import java.lang.Boolean;
import java.lang.Long;
import java.lang.String;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link ActivityDTO} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public abstract class ActivityDTO implements Serializable {

    /** The serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The private field for {@link java.lang.Long}. */
    private Long id;

    /** The private field for {@link java.lang.String}. */
    private String name;

    /** The private field for {@link java.lang.String}. */
    private String description;

    /** The private field for {@link java.lang.Boolean}. */
    private Boolean active;

    /** The private field for {@link ProjectDTO}. */
    private ProjectDTO projectReference;

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
     * @return the {@link java.lang.Boolean} to get
     */
    public Boolean getActive() {
        return active;
    }

    /**
     *  @param active The {@link java.lang.Boolean} to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * @return the {@link ProjectDTO} to get
     */
    public ProjectDTO getProjectReference() {
        return projectReference;
    }

    /**
     *  @param projectReference The {@link ProjectDTO} to set
     */
    public void setProjectReference(ProjectDTO projectReference) {
        this.projectReference = projectReference;
    }
}
