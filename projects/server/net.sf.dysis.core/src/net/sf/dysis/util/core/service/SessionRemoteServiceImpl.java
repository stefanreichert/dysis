/**
 * SessionRemoteServiceImpl.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.util.core.service;

/**
 * 
 * 
 * <b>ATTENTION</b> The class {@link SessionRemoteServiceImpl} is a generated
 * class. Changes to this file will <b>NOT</b> be overwritten by the next build.
 * Your changes are safe.
 * 
 * @author <i>Stefan Reichert</i>
 */
public class SessionRemoteServiceImpl extends SessionRemoteService {

	/** {@inheritDoc} */
	@Override
	protected String internalLogin(String userid, String password) {
		return getSessionService().login(userid, password);

	}

	/** {@inheritDoc} */
	@Override
	protected void internalKeepAlive(String sessionId) {
		getSessionService().keepAlive(sessionId);
	}
}
