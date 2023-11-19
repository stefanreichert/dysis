/**
 * SessionServiceImpl.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.util.core.service;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import net.sf.dysis.resource.core.domain.PersonImpl;
import net.sf.dysis.system.core.SessionExpiredException;
import net.sf.dysis.util.core.domain.SessionImpl;

/**
 * <b>ATTENTION</b> The class {@link SessionServiceImpl} is a generated class.
 * Changes to this file will <b>NOT</b> be overwritten by the next build. Your
 * changes are safe.
 * 
 * @author <i>Stefan Reichert</i>
 */
public class SessionServiceImpl extends SessionService {

	private static final int SESSION_EXPIRATION = 300;

	/** The logger instance */
	private static final Logger logger = Logger
			.getLogger(SessionServiceImpl.class);

	/** {@inheritDoc} */
	@Override
	protected String internalLogin(String userid, String password) {
		String sessionid = null;
		PersonImpl personImpl = getPersonService().findByUserIdAndPassword(
				userid, password);
		if (personImpl != null) {
			SessionImpl sessionImpl = new SessionImpl();
			sessionImpl.setUserId(userid);
			sessionImpl.setSessionId(UUID.randomUUID().toString());
			sessionImpl.setLastAccess(new Date());
			sessionImpl = getSessionDAO().saveSession(sessionImpl);
			sessionid = sessionImpl.getSessionId();
		}
		return sessionid;
	}

	/** {@inheritDoc} */
	@Override
	protected void internalKeepAlive(String sessionId) {
		logger.info("processing keep alive for session <" + sessionId + ">");
		SessionImpl sessionImpl = getSessionDAO().findBySessionId(sessionId);
		if (sessionImpl != null) {
			Calendar now = Calendar.getInstance();
			Calendar expireDate = Calendar.getInstance();
			expireDate.setTime(sessionImpl.getLastAccess());
			expireDate.add(Calendar.SECOND, SESSION_EXPIRATION);
			logger.info(" * date is <"
					+ DateFormat.getDateTimeInstance(DateFormat.LONG,
							DateFormat.MEDIUM).format(now.getTime()) + ">");
			logger.info(" * expire date is <"
					+ DateFormat.getDateTimeInstance(DateFormat.LONG,
							DateFormat.MEDIUM).format(expireDate.getTime())
					+ ">");
			if (expireDate.after(now)) {
				logger.info(" * expiration updated");
				sessionImpl.setLastAccess(now.getTime());
				getSessionDAO().saveSession(sessionImpl);
				return;
			}
		}
		throw new SessionExpiredException("Session <" + sessionId
				+ "> expired.");
	}

	/** {@inheritDoc} */
	@Override
	protected Boolean internalIsAlive(String sessionId) {
		logger.info("processing is alive for session <" + sessionId + ">");
		SessionImpl sessionImpl = getSessionDAO().findBySessionId(sessionId);
		if (sessionImpl != null) {
			Calendar now = Calendar.getInstance();
			Calendar expireDate = Calendar.getInstance();
			expireDate.setTime(sessionImpl.getLastAccess());
			expireDate.add(Calendar.SECOND, SESSION_EXPIRATION);
			logger.info(" * date is <"
					+ DateFormat.getDateTimeInstance(DateFormat.LONG,
							DateFormat.MEDIUM).format(now.getTime()) + ">");
			logger.info(" * expire date is <"
					+ DateFormat.getDateTimeInstance(DateFormat.LONG,
							DateFormat.MEDIUM).format(expireDate.getTime())
					+ ">");
			if (expireDate.after(now)) {
				logger.info(" * valid");
				return true;
			}
			else {
				logger.info(" * expired");
				return false;
			}
		}
		logger.info(" * unknown session id");
		return Boolean.FALSE;
	}
}
