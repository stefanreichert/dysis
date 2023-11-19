/**
 * AuthenticationProvider.java created on 02.03.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.application.internal.authentication;

import net.sf.dysis.application.internal.Activator;
import net.sf.dysis.base.ui.authentication.AuthenticationException;
import net.sf.dysis.base.ui.authentication.IAuthenticationProvider;
import net.sf.dysis.core.client.spring.HttpInvokerRequestExecutor;
import net.sf.dysis.resource.core.dto.PersonDTO;
import net.sf.dysis.resource.core.service.IPersonRemoteService;
import net.sf.dysis.util.core.service.ISessionRemoteService;

/**
 * {@link IAuthenticationProvider} to be used until authentication service is
 * available from the server.<br>
 * 
 * @author Stefan Reichert
 */
public class AuthenticationProvider implements
		IAuthenticationProvider<String, PersonDTO> {
	/** {@inheritDoc} */
	public String authenticate(String username, String password)
			throws AuthenticationException {
		ISessionRemoteService sessionService = Activator.getDefault()
				.getService(ISessionRemoteService.class);
		String sessionId = sessionService.login(username, password);
		if (sessionId != null) {
			// set the context information
			HttpInvokerRequestExecutor.setSessionId(sessionId);
			HttpInvokerRequestExecutor.setUserId(username);
			return sessionId;
		}
		throw new AuthenticationException(username);
	}

	/** {@inheritDoc} */
	public PersonDTO getPrincipal(String username) {
		return Activator.getDefault().getService(IPersonRemoteService.class)
				.findByUserId(username);
	}
}
