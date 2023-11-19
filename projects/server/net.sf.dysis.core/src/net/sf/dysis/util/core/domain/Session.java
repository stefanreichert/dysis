/**
 * Session.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.util.core.domain;

import java.io.Serializable;

import java.lang.Long;
import java.lang.String;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link Session} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
@MappedSuperclass
public abstract class Session implements Serializable {

    /** The serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The private field for {@link java.lang.Long}. */
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** The private field for {@link java.lang.String}. */
    @Column(nullable = false, unique = false)
    private String userId;

    /** The private field for {@link java.lang.String}. */
    @Column(nullable = false, unique = false)
    private String sessionId;

    /** The private field for {@link java.util.Date}. */
    @Column(nullable = false, unique = false)
    private Date lastAccess;

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
    public String getSessionId() {
        return sessionId;
    }

    /**
     *  @param sessionId The {@link java.lang.String} to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return the {@link java.util.Date} to get
     */
    public Date getLastAccess() {
        return lastAccess;
    }

    /**
     *  @param lastAccess The {@link java.util.Date} to set
     */
    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }
}
