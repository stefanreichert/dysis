/**
 * Activity.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planing.core.domain;

import java.io.Serializable;

import java.lang.Boolean;
import java.lang.Long;
import java.lang.String;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import net.sf.dysis.resource.core.domain.PersonImpl;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link Activity} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
@MappedSuperclass
public abstract class Activity implements Serializable {

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

    /** The private field for {@link java.lang.Boolean}. */
    @Column(nullable = false, unique = false)
    private Boolean active;

    /** The private field for {@link ProjectImpl getProjectReference()}. */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private ProjectImpl projectReference;

    /** The private field for {@link List<PersonImpl> getMembers()}. */
    @ManyToMany(mappedBy = "activities")
    private List<PersonImpl> members;

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
     * @return the {@link ProjectImpl getProjectReference()} to get
     */
    public ProjectImpl getProjectReference() {
        return projectReference;
    }

    /**
     *  @param projectReference The {@link net.sf.dysis.planing.core.domain.ProjectImpl} to set
     */
    public void setProjectReference(ProjectImpl projectReference) {
        this.projectReference = projectReference;
    }

    /**
     * @return the {@link List<PersonImpl> getMembers()} to get
     */
    public List<PersonImpl> getMembers() {
        return members;
    }

    /**
     *  @param members The {@link List<PersonImpl>} to set
     */
    public void setMembers(List<PersonImpl> members) {
        this.members = members;
    }
}
