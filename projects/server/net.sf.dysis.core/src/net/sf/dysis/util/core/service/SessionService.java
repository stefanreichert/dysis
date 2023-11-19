/**
 * SessionService.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.util.core.service;

import java.lang.Boolean;
import java.lang.String;

import net.sf.dysis.resource.core.service.IPersonService;
import net.sf.dysis.util.core.integration.ISessionDAO;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link SessionService} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public abstract class SessionService implements ISessionService {

    /** The private field for {@link net.sf.dysis.resource.core.service.IPersonService}. */
    private IPersonService personService;

    /** The private field for {@link net.sf.dysis.util.core.integration.ISessionDAO}. */
    private ISessionDAO sessionDAO;

    /**
     * @return the {@link net.sf.dysis.resource.core.service.IPersonService} to get
     */
    public IPersonService getPersonService() {
        return personService;
    }

    /**
     *  @param personService The {@link net.sf.dysis.resource.core.service.IPersonService} to set
     */
    public void setPersonService(IPersonService personService) {
        this.personService = personService;
    }

    /**
     * @return the {@link net.sf.dysis.util.core.integration.ISessionDAO} to get
     */
    public ISessionDAO getSessionDAO() {
        return sessionDAO;
    }

    /**
     *  @param sessionDAO The {@link net.sf.dysis.util.core.integration.ISessionDAO} to set
     */
    public void setSessionDAO(ISessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract String internalLogin(String userId, String password);

    /** {@inheritDoc} */
    public final String login(String userId, String password) {
        return internalLogin(userId, password);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract Boolean internalIsAlive(String sessionId);

    /** {@inheritDoc} */
    public final Boolean isAlive(String sessionId) {
        return internalIsAlive(sessionId);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract void internalKeepAlive(String sessionId);

    /** {@inheritDoc} */
    public final void keepAlive(String sessionId) {
        internalKeepAlive(sessionId);

    }
}
