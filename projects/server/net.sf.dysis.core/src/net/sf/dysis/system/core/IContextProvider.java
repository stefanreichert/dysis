/**
 * IRequestContextProvider.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.system.core;

/**
 * @author Stefan Reichert
 */
public interface IContextProvider {

	final String USER_ID = "dysis.user.id";

	final String SESSION_ID = "dysis.session.id";

	final String VERSION = "dysis.version";

	String getContextUserId();

	String getContextSessionId();

	String getContextVersion();

}
