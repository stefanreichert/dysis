/**
 * IAuthenticator.java created on 02.03.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.authentication;

/**
 * Class that proceeds authentication and provides the
 * {@link IAuthenticationToken} after successful authentication.
 * 
 * @author Stefan Reichert
 */
public interface IAuthenticator {

	/**
	 * Authenticates the given username and password and returns an
	 * {@link IAuthenticationToken}.
	 * 
	 * @param username
	 *            The username to authenticate
	 * @param password
	 *            The password to use for authentication
	 * @param authenticationProvider
	 *            The {@link IAuthenticationProvider} to use
	 * @return the granted {@link IAuthenticationToken}
	 * @throws AuthenticationException
	 *             An {@link AuthenticationException} is thrown if the given
	 *             combination of username and password is not valid or the
	 *             authentication could not be proceeded due to other
	 *             circumstances
	 */
	<TokenClass, PrincipalClass> IAuthenticationToken<TokenClass, PrincipalClass> authenticate(
			String username,
			String password,
			IAuthenticationProvider<TokenClass, PrincipalClass> authenticationProvider)
			throws AuthenticationException;

	/**
	 * Factory for {@link IAuthenticationToken}s.
	 * 
	 * @author Stefan Reichert
	 */
	class Factory {

		/**
		 * Creates an {@link IAuthenticator}.
		 * 
		 * @return the {@link IAuthenticator}
		 */
		public static IAuthenticator getAuthenticator() {
			return new Authenticator();
		}

		/**
		 * Internal implementation of the parent interface
		 * {@link IAuthenticationToken}.
		 * 
		 * @author Stefan Reichert
		 */
		private static class Authenticator implements IAuthenticator {
			/** {@inheritDoc} */
			public <TokenClass, PrincipalClass> IAuthenticationToken<TokenClass, PrincipalClass> authenticate(
					String username,
					String password,
					IAuthenticationProvider<TokenClass, PrincipalClass> authenticationProvider)
					throws AuthenticationException {
				try {
					TokenClass token = authenticationProvider.authenticate(
							username, password);
					// load the user object from the server
					PrincipalClass principal = authenticationProvider
							.getPrincipal(username);
					return IAuthenticationToken.Factory.createToken(username,
							password, token, principal);
				}
				catch (Throwable throwable) {
					if (throwable instanceof AuthenticationException) {
						throw (AuthenticationException) throwable;
					}
					throw new AuthenticationException(throwable.getMessage(), throwable);
				}
			}
		}
	}
}
