/**
 * SessionExpiredException.java
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
 *
 */
public class SessionExpiredException extends RuntimeException {

	/** The serial version uid. */
	private static final long serialVersionUID = 5621642522087540445L;

	/**
	 * Constructor for <class>SessionExpiredException</class>.
	 */
	public SessionExpiredException() {
		super();
	}

	/**
	 * Constructor for <class>SessionExpiredException</class>.
	 */
	public SessionExpiredException(String message) {
		super(message);
	}
	
	

}
