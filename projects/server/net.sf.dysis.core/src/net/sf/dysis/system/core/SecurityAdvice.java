/**
 * SecurityAdvice.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.system.core;

import java.lang.reflect.Method;

import net.sf.dysis.util.core.service.ISessionService;

import org.apache.log4j.Logger;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Stefan Reichert
 */
public class SecurityAdvice implements MethodBeforeAdvice,
		ApplicationContextAware {

	private static Logger logger = Logger.getLogger(SecurityAdvice.class);

	private ApplicationContext applicationContext;

	private IContextProvider contextProvider;

	public SecurityAdvice() {
		logger.info("security advice created.");
	}

	/** {@inheritDoc} */
	public void before(Method method, Object[] arguments, Object returnType)
			throws Throwable {
		ISessionService sessionService = (ISessionService) applicationContext
				.getBean("sessionService");
		logger.info("session verification for user <"
				+ contextProvider.getContextUserId() + "> with session <"
				+ contextProvider.getContextSessionId() + ">");
		if (!sessionService.isAlive(contextProvider.getContextSessionId())) {
			logger.info("- failed");
			throw new SessionExpiredException("Session <"
					+ contextProvider.getContextSessionId() + "> expired.");
		}
		logger.info("- succeeded");
	}

	/** {@inheritDoc} */
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public IContextProvider getContextProvider() {
		return contextProvider;
	}

	public void setContextProvider(IContextProvider contextProvider) {
		this.contextProvider = contextProvider;
	}

}
