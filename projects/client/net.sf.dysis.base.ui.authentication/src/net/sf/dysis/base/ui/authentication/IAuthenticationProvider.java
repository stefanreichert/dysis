/**
 * IAuthenticationProvider.java created on 02.03.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.authentication;

/**
 * An provider for authenticating a specific user object.
 * 
 * @author Stefan Reichert
 */
public interface IAuthenticationProvider<TokenClass, PrincipalClass> {

	/**
	 * Retrieves the principal object for the given unique username.
	 * 
	 * @param username The username to get the object for
	 * @return the principal object for the given unique username
	 */
	PrincipalClass getPrincipal(String username);

	/**
	 * Requests a <i>token</i> for the given username password combination.
	 * 
	 * @param username The username to authenticate
	 * @param password The password to use for authentication
	 * @return the <i>token</i> {@link TokenClass}
	 * @throws AuthenticationException An {@link AuthenticationException} must be thrown if the given
	 *         combination of username and password is not valid or the authentication could not be
	 *         proceeded due to other circumstances
	 */
	TokenClass authenticate(String username, String password) throws AuthenticationException;
}
