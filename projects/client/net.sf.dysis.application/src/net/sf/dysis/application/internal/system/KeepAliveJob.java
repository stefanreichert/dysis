/**
 * KeepAliveJob.java created on 27.02.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.application.internal.system;

import net.sf.dysis.application.internal.Activator;
import net.sf.dysis.core.client.spring.HttpInvokerRequestExecutor;
import net.sf.dysis.util.core.service.ISessionRemoteService;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

/**
 * A {@link Job} that sends a <i>keep alive</i> signal periodicly.
 * 
 * @author Stefan Reichert
 */
public class KeepAliveJob extends Job {

	/**
	 * Constructor for <class>KeepAliveJob</class>.
	 */
	public KeepAliveJob() {
		super("Keep Alive"); //$NON-NLS-1$
	}

	/** {@inheritDoc} */
	@Override
	public IStatus run(IProgressMonitor monitor) {
		monitor.beginTask("Sending keep alive signal", -1); //$NON-NLS-1$
		Activator.getDefault().getService(ISessionRemoteService.class)
				.keepAlive(HttpInvokerRequestExecutor.getSessionId());
		schedule(285000);
		return Status.OK_STATUS;
	}

}
