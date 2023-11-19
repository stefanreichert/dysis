/**
 * PluginLogger.java created on Apr 12, 2008
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
import org.eclipse.core.runtime.Status;

/**
 * Utility class for logging.
 * 
 * @author Peter Friese
 * @author Stefan Reichert
 */
public final class PluginLogger implements IPluginLogger {

	/** The hosting runtime {@link Plugin}. */
	private Plugin runtimePlugin;

	/** Flag whether logging is enabled due to debug options. */
	private boolean debugEnabled;

	/**
	 * Creates an {@link IPluginLogger} instance.
	 * 
	 * @param runtimePlugin
	 *            The hosting runtime {@link Plugin}
	 * @return The {@link IPluginLogger} instance
	 */
	public static IPluginLogger getLogger(Plugin runtimePlugin) {
		return new PluginLogger(runtimePlugin);
	}

	/**
	 * Constructor for <class>PluginLogger</class>.
	 */
	private PluginLogger(Plugin runtimePlugin) {
		super();
		this.runtimePlugin = runtimePlugin;
		debugEnabled = runtimePlugin.isDebugging();
	}

	/** {@inheritDoc} */
	public final void info(String message, Throwable throwable) {
		log(IStatus.INFO, IStatus.OK, message, throwable);
	}

	/** {@inheritDoc} */
	public final void info(String message) {
		log(IStatus.INFO, IStatus.OK, message, null);
	}

	/** {@inheritDoc} */
	public final void error(String message) {
		log(IStatus.ERROR, IStatus.OK, message, null);
	}

	/** {@inheritDoc} */
	public final void error(Throwable throwable) {
		error(throwable.getMessage(), throwable);
	}

	/** {@inheritDoc} */
	public final void error(String message, Throwable throwable) {
		log(IStatus.ERROR, IStatus.OK, message, throwable);
	}

	/**
	 * Logs the <code>message</code> with the <code>throwable</code> with the
	 * given <code>severity</code>.
	 * 
	 * @param severity
	 *            The severity of the log entry
	 * @param code
	 *            The code of the log entry
	 * @param message
	 *            The message to log
	 * @param throwable
	 *            The corresponding {@link Throwable}
	 */
	private final void log(int severity, int code, String message,
			Throwable exception) {
		if (severity == IStatus.ERROR || (debugEnabled)) {
			log(createStatus(severity, code, message, exception));
		}
	}

	/**
	 * Creates an {@link IStatus} object for the <code>message</code> with the
	 * <code>throwable</code> with the given <code>severity</code>.
	 * 
	 * @param severity
	 *            The severity of the log entry
	 * @param code
	 *            The code of the log entry
	 * @param message
	 *            The message to log
	 * @param throwable
	 *            The corresponding {@link Throwable}
	 * @return an {@link IStatus} containing the passed information
	 */
	private final IStatus createStatus(int severity, int code, String message,
			Throwable exception) {
		String symbolicName = runtimePlugin.getBundle().getSymbolicName();
		return new Status(severity, symbolicName, code,
				message != null ? message : "", exception); //$NON-NLS-1$
	}

	/**
	 * Uses the <code>runtimePlugin</code>'s {@link ILog} to log the message.
	 * 
	 * @param status
	 *            The {@link IStatus} to log
	 */
	private final void log(IStatus status) {
		runtimePlugin.getLog().log(status);
	}

}
