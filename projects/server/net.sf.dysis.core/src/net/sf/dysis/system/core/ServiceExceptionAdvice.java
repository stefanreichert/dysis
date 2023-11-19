/**
 * ServiceExceptionAdvice.java created on 27.02.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.system.core;

import org.apache.log4j.Logger;
import org.springframework.aop.ThrowsAdvice;

/**
 * An Advice that catches all {@link Throwable}s execpt the
 * {@link SessionExpiredException} and converts the to a
 * {@link ServiceException} instance.
 * 
 * @author Stefan Reichert
 */
public class ServiceExceptionAdvice implements ThrowsAdvice {

	private static Logger logger = Logger.getLogger(ServiceExceptionAdvice.class);

	/**
	 * @param throwable
	 *            The {@link Throwable} cause
	 * @throws Throwable
	 */
	public void afterThrowing(Throwable throwable) throws Throwable {
		logger.info("Exception of type <" + throwable.getClass().getName() + " traced");
		if (throwable instanceof ServiceException) {
			throw throwable;
		}
		if (throwable instanceof SessionExpiredException) {
			throw throwable;
		}
		throw new ServiceException(throwable);
	}
}
