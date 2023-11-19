/**
 * Person.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.resource.core.domain;

import java.io.Serializable;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;

import net.sf.dysis.planing.core.domain.ActivityImpl;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link Person} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
@MappedSuperclass
public abstract class Person implements Serializable {

    /** The serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The private field for {@link java.lang.Long}. */
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** The private field for {@link java.lang.String}. */
    @Column(nullable = false, unique = true)
    private String userId;

    /** The private field for {@link java.lang.String}. */
    @Column(nullable = false, unique = false)
    private String firstname;

    /** The private field for {@link java.lang.String}. */
    @Column(nullable = false, unique = false)
    private String lastname;

    /** The private field for {@link java.lang.String}. */
    @Column(nullable = false, unique = false)
    private String password;

    /** The private field for {@link java.util.Date}. */
    @Column(nullable = false, unique = false)
    private Date employmentDate;

    /** The private field for {@link java.lang.Boolean}. */
    @Column(nullable = false, unique = false)
    private Boolean active;

    /** The private field for {@link java.lang.Integer}. */
    @Column(nullable = false, unique = false)
    private Integer weekHours;

    /** The private field for {@link List<ActivityImpl> getActivities()}. */
    @ManyToMany(fetch = FetchType.EAGER)
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
    public String getUserId() {
        return userId;
    }

    /**
     *  @param userId The {@link java.lang.String} to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the {@link java.lang.String} to get
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     *  @param firstname The {@link java.lang.String} to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the {@link java.lang.String} to get
     */
    public String getLastname() {
        return lastname;
    }

    /**
     *  @param lastname The {@link java.lang.String} to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the {@link java.lang.String} to get
     */
    public String getPassword() {
        return password;
    }

    /**
     *  @param password The {@link java.lang.String} to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the {@link java.util.Date} to get
     */
    public Date getEmploymentDate() {
        return employmentDate;
    }

    /**
     *  @param employmentDate The {@link java.util.Date} to set
     */
    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
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
     * @return the {@link java.lang.Integer} to get
     */
    public Integer getWeekHours() {
        return weekHours;
    }

    /**
     *  @param weekHours The {@link java.lang.Integer} to set
     */
    public void setWeekHours(Integer weekHours) {
        this.weekHours = weekHours;
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
