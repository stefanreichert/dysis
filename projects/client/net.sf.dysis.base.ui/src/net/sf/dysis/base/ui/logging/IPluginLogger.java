/**
 * IPluginLogger.java created on 21.02.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.logging;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;

/**
 * Interface for a logger that uses a {@link Plugin}'s {@link ILog}.
 * 
 * @author Stefan Reichert
 */
public interface IPluginLogger {

	/**
	 * Logs the <code>message</code> with the <code>throwable</code> with an
	 * {@link IStatus#INFO} status
	 * 
	 * @param message
	 *            The message to log
	 * @param throwable
	 *            The corresponding {@link Throwable}
	 */
	void info(String message, Throwable throwable);

	/**
	 * Logs the <code>message</code> with an {@link IStatus#INFO} status
	 * 
	 * @param message
	 *            The message to log
	 */
	void info(String message);

	/**
	 * Logs the <code>message</code> with an {@link IStatus#ERROR} status
	 * 
	 * @param message
	 *            The message to log
	 */
	void error(String message);

	/**
	 * Logs the <code>throwable</code> with an {@link IStatus#ERROR} status
	 * 
	 * @param throwable
	 *            The corresponding {@link Throwable}
	 */
	void error(Throwable throwable);

	/**
	 * Logs the <code>message</code> with the <code>throwable</code> with an
	 * {@link IStatus#ERROR} status
	 * 
	 * @param message
	 *            The message to log
	 * @param throwable
	 *            The corresponding {@link Throwable}
	 */
	void error(String message, Throwable throwable);

}
