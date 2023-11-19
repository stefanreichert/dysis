/**
 * ServerContextProviderFilter.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.system.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * @author Stefan Reichert
 */
public class ServerContextProviderFilter implements Filter {

	private static Logger logger = Logger.getLogger(SecurityAdvice.class);

	/** {@inheritDoc} */
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		if (servletRequest instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

			// retrieve context information from the http header
			String userId = httpServletRequest
					.getHeader(IContextProvider.USER_ID);
			String sessionId = httpServletRequest
					.getHeader(IContextProvider.SESSION_ID);
			String version = httpServletRequest
					.getHeader(IContextProvider.VERSION);

			// set the context information for the current thread
			ServerContextProvider.setServerContext(userId, sessionId, version);

			logger.info("incoming request for user <" + userId
					+ "> with session id <" + sessionId
					+ "> - client version is <" + version + ">");
		}
		// delegate call
		filterChain.doFilter(servletRequest, servletResponse);
	}

	/** {@inheritDoc} */
	public void destroy() {
		// intentionally do nothing
	}

	/** {@inheritDoc} */
	public void init(FilterConfig filterConfig) throws ServletException {
		// intentionally do nothing
	}

}
