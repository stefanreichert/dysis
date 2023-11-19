/**
 * AuthenticationException.java created on 02.03.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.authentication;

/**
 * {@link Exception} that indicates a failed authentication.
 * 
 * @author Stefan Reichert
 */
public class AuthenticationException extends Exception {

	/** The serrial version UID */
	private static final long serialVersionUID = -5223676827082107605L;

	/**
	 * Constructor for {@link AuthenticationException}.
	 * 
	 * @param message The message
	 */
	public AuthenticationException(String message) {
		super(message);
	}

	/**
	 * Constructor for {@link AuthenticationException}.
	 * 
	 * @param message The message
	 * @param cause The cause
	 */
	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor for {@link AuthenticationException}.
	 * 
	 * @param cause The cause
	 */
	public AuthenticationException(Throwable cause) {
		super(cause);
	}

}
