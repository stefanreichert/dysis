/**
 * SessionDAOImpl.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.util.core.integration;

import java.util.List;

import net.sf.dysis.util.core.domain.SessionImpl;

/**
 * 
 * 
 * <b>ATTENTION</b> The class {@link SessionDAOImpl} is a generated class.
 * Changes to this file will <b>NOT</b> be overwritten by the next build. Your
 * changes are safe.
 * 
 * @author <i>Stefan Reichert</i>
 */
public class SessionDAOImpl extends SessionDAO {

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	protected SessionImpl internalFindBySessionId(String sessionId) {
		String queryString = "select session from SessionImpl session where session.sessionId = ?";
		List<SessionImpl> result = (List<SessionImpl>) getHibernateTemplate()
				.find(queryString, sessionId);
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result.iterator().next();
	}
}
