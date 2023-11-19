/**
 * ServiceExceptionHandlingAdvice.java created on 27.02.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.core.client.internal.spring;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import net.sf.dysis.core.client.internal.Activator;
import net.sf.dysis.system.core.ServiceException;
import net.sf.dysis.system.core.SessionExpiredException;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.remoting.RemoteAccessException;

/**
 * An Advice that catches all {@link Throwable}s execpt the
 * {@link SessionExpiredException} and converts the to a
 * {@link ServiceException} instance.
 * 
 * @author Stefan Reichert
 */
public class ServiceExceptionHandlingAdvice implements ThrowsAdvice {

	/** The private {@link Logger} instance. */
	private static final Logger logger = Logger
			.getLogger(ServiceExceptionHandlingAdvice.class);

	/**
	 * This method will be invoked each time a {@link java.lang.Throwable} gets
	 * thrown by any service method.
	 * 
	 * @param method
	 *            method to be invoked
	 * @param args
	 *            arguments
	 * @param target
	 *            target object
	 * @param exception
	 *            The exception that has been thrown by the service.
	 * @throws Throwable
	 */
	public void afterThrowing(final Method method, Object[] args,
			Object target, final Throwable throwable) throws Throwable {
		logger.error("Bei Aufruf des Servers ist ein Fehler aufgetreten.",
				throwable);
		if (throwable instanceof SessionExpiredException) {
			Display.getDefault().syncExec(new Runnable() {
				/** {@inheritDoc} */
				public void run() {
					// close client
					MessageDialog
							.openError(
									Display.getDefault().getActiveShell(),
									"Dysis",
									"Ihre Session ist ausgelaufen. Dysis wird daher beendet. Bitte melden Sie sich erneut an.");
					PlatformUI.getWorkbench().close();
				}
			});

		}
		if (throwable instanceof ServiceException) {
			Display.getDefault().syncExec(new Runnable() {
				/** {@inheritDoc} */
				public void run() {
					// show dialog
					ErrorDialog
							.openError(
									Display.getDefault().getActiveShell(),
									"Dysis",
									"Beim Aufruf des Servers ist ein Fehler aufgetreten.",
									createExceptionStatus(throwable, method,
											throwable.getMessage()));
				}
			});
		}
		if (throwable instanceof RemoteAccessException) {
			Display.getDefault().syncExec(new Runnable() {
				/** {@inheritDoc} */
				public void run() {
					// show dialog
					ErrorDialog
							.openError(
									Display.getDefault().getActiveShell(),
									"Dysis",
									"Der Zugriff auf den Server ist zur Zeit nicht möglich. Bitte überprüfen Sie Ihre Verbindungseinstellungen.",
									createExceptionStatus(throwable, method,
											throwable.getMessage()));
				}
			});
		}
	}

	/**
	 * Produces an {@link IStatus} object containing error information.
	 */
	private IStatus createExceptionStatus(Throwable throwable,
			Method contextMethod, String message) {
		MultiStatus exceptionStatus = createBaseStatus(throwable);

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(contextMethod.getDeclaringClass().getName());
		stringBuffer.append(".");
		stringBuffer.append(contextMethod.getName());
		stringBuffer.append("(");
		Iterator<Class<?>> parameterIterator = Arrays.asList(
				contextMethod.getParameterTypes()).iterator();
		while (parameterIterator.hasNext()) {
			stringBuffer.append(parameterIterator.next().getName());
			if (parameterIterator.hasNext()) {
				stringBuffer.append(", ");
			}
		}
		stringBuffer.append(")");
		// context method
		exceptionStatus.add(createStatus(IStatus.ERROR, "Service Context: "
				+ stringBuffer.toString()));
		if (throwable != null) {
			// stacktrace details
			StackTraceElement[] stackTrace = throwable.getStackTrace();
			if (stackTrace.length > 0) {
				StackTraceElement stackFrame = stackTrace[0];
				exceptionStatus.add(createStatus(IStatus.ERROR, DateFormat
						.getDateTimeInstance().format(new Date())));
				exceptionStatus.add(createStatus(IStatus.ERROR, "Classname: "
						+ stackFrame.getClassName()));
				exceptionStatus.add(createStatus(IStatus.ERROR, "Method: "
						+ stackFrame.getMethodName()));
				exceptionStatus.add(createStatus(IStatus.ERROR, "Line number: "
						+ stackFrame.getLineNumber()));
			}
		}
		return exceptionStatus;
	}

	/**
	 * Creates a base {@link IStatus} as {@link MultiStatus} object.
	 */
	private MultiStatus createBaseStatus(Throwable throwable) {
		Bundle bundle = Activator.getDefault().getBundle();
		String symbolicName = bundle.getSymbolicName();
		String bundleName = bundle.getHeaders().get("Bundle-Name").toString();
		String bundleVendor = bundle.getHeaders().get("Bundle-Vendor")
				.toString();
		String bundleVersion = bundle.getHeaders().get("Bundle-Version")
				.toString();

		// the message
		String topLevelMessage = throwable.getMessage();
		MultiStatus baseStatus = new MultiStatus(symbolicName, IStatus.ERROR,
				topLevelMessage, null);

		// Vendor name
		baseStatus.add(createStatus(IStatus.ERROR, "Plug-in Vendor: "
				+ bundleVendor));
		// Plug-in name (user friendly name)
		baseStatus.add(createStatus(IStatus.ERROR, "Plug-in Name: "
				+ bundleName));
		// Plug-in ID
		baseStatus.add(createStatus(IStatus.ERROR, "Plug-in ID: "
				+ symbolicName));
		// Version
		baseStatus
				.add(createStatus(IStatus.ERROR, "Version: " + bundleVersion));
		return baseStatus;
	}

	/**
	 * A helper method that creates an {@link IStatus} object.
	 */
	private IStatus createStatus(int severity, String message) {
		Status status = new Status(severity, Activator.getDefault().getBundle()
				.getSymbolicName(), IStatus.OK, message, null);
		return status;

	}

}
