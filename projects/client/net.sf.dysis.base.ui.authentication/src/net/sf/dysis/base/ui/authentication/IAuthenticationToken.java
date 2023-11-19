/**
 * IAuthenticationToken.java created on 02.03.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.authentication;

/**
 * A <i>token</i> which is granted on a successful authentication.
 * 
 * @author Stefan Reichert
 */
public interface IAuthenticationToken<TokenClass, PrincipalClass> {

	/**
	 * @return the username {@link String}.
	 */
	String getUsername();

	/**
	 * @return the password {@link String}.
	 */
	String getPassword();

	/**
	 * @return the token {@link TokenClass}.
	 */
	TokenClass getToken();

	/**
	 * @return the user object provided by the authentication counterpart.
	 */
	PrincipalClass getPrincipal();

	/**
	 * Factory for {@link IAuthenticationToken}s.
	 * 
	 * @author Stefan Reichert
	 */
	class Factory {

		/**
		 * Creates an {@link IAuthenticationToken} with the given parameters.
		 * 
		 * @param <PrincipalClass>
		 *            The type of the user object
		 * @param token
		 *            The object that represents the token
		 * @param user
		 *            The user object
		 * @return the {@link IAuthenticationToken} with the given parameters
		 */
		static <TokenClass, PrincipalClass> IAuthenticationToken<TokenClass, PrincipalClass> createToken(
				String username, String password, TokenClass token,
				PrincipalClass user) {
			return new AuthenticationToken<TokenClass, PrincipalClass>(username, password,
					token, user);
		}

		/**
		 * Internal implementation of the parent interface
		 * {@link IAuthenticationToken}.
		 * 
		 * @author Stefan Reichert
		 */
		private static class AuthenticationToken<TokenClass, PrincipalClass> implements
				IAuthenticationToken<TokenClass, PrincipalClass> {
			/** The username. */
			private String username;

			/** The encrypted password. */
			private String password;

			/** The token {@link TokenClass}. */
			private TokenClass token;

			/** The user provided by the authentication counterpart. */
			private PrincipalClass principal;

			/**
			 * @param token
			 *            the token {@link String}
			 */
			public AuthenticationToken(String username, String password,
					TokenClass token, PrincipalClass principal) {
				super();
				this.username = username;
				this.password = password;
				this.token = token;
				this.principal = principal;
			}

			/** {@inheritDoc} */
			public TokenClass getToken() {
				return token;
			}

			/** {@inheritDoc} */
			public PrincipalClass getPrincipal() {
				return principal;
			}

			/** {@inheritDoc} */
			public String getPassword() {
				return password;
			}

			/** {@inheritDoc} */
			public String getUsername() {
				return username;
			}

		}
	}

}
