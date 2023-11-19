/**
 * LogTransfer.java created on 28.03.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.internal.logging;


import net.sf.dysis.base.ui.logging.PluginLogger;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;

/**
 * {@link ILogListener} that transfers the <i>Eclipse Log</i> to the
 * corresponding <i>Log4J Log</i>.
 * 
 * @author Stefan Reichert
 */
public class LogTransfer implements ILogListener {

	/** This class' private {@link PluginLogger}. */
	private Logger logger = Logger.getLogger(LogTransfer.class);

	/** {@inheritDoc} */
	public void logging(IStatus status, String plugin) {
		String message = "[" + plugin + "] " + status.getMessage(); //$NON-NLS-1$ //$NON-NLS-2$
		Throwable exception = status.getException();
		switch (status.getSeverity()) {
			case IStatus.CANCEL:
				logger.error(message, exception);
				break;
			case IStatus.ERROR:
				logger.error(message, exception);
				break;
			case IStatus.INFO:
				logger.info(message, exception);
				break;
			case IStatus.OK:
				logger.info(message, exception);
				break;
			case IStatus.WARNING:
				logger.warn(message, exception);
				break;
			default:
				logger.info(message, exception);
				break;
		}

	}
}