/**
 * SessionRemoteService.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.util.core.service;

import java.lang.String;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link SessionRemoteService} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public abstract class SessionRemoteService implements ISessionRemoteService {

    /** The private field for {@link net.sf.dysis.util.core.service.ISessionService}. */
    private ISessionService sessionService;

    /**
     * @return the {@link net.sf.dysis.util.core.service.ISessionService} to get
     */
    public ISessionService getSessionService() {
        return sessionService;
    }

    /**
     *  @param sessionService The {@link net.sf.dysis.util.core.service.ISessionService} to set
     */
    public void setSessionService(ISessionService sessionService) {
        this.sessionService = sessionService;
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
    protected abstract void internalKeepAlive(String sessionId);

    /** {@inheritDoc} */
    public final void keepAlive(String sessionId) {
        internalKeepAlive(sessionId);

    }
}
