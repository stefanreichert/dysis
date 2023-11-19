/**
 * ISessionDAO.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.util.core.integration;

import java.lang.String;

import java.util.Set;

import net.sf.dysis.util.core.domain.SessionImpl;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link ISessionDAO} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public interface ISessionDAO {
    /**
     * Loads all existing {@link net.sf.dysis.util.core.domain.SessionImpl} entities from the database.
     */
    Set<SessionImpl> loadAll();

    /**
     * Loads the {@link net.sf.dysis.util.core.domain.SessionImpl} with the given key from the database.
     */
    SessionImpl loadSession(Long id);

    /**
     * Creates or updates the given {@link net.sf.dysis.util.core.domain.SessionImpl}.
     */
    SessionImpl saveSession(SessionImpl session);

    /**
     * Deletes the given {@link net.sf.dysis.util.core.domain.SessionImpl}.
     */
    void deleteSession(SessionImpl session);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    SessionImpl findBySessionId(String sessionId);
}
