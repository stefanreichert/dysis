/**
 * ISessionService.java
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

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link ISessionService} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public interface ISessionService {

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    String login(String userId, String password);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    Boolean isAlive(String sessionId);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    void keepAlive(String sessionId);
}
