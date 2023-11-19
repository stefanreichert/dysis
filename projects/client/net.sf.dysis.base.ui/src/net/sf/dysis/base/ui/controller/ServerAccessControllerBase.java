/**
 * ServerAccessControllerBase.java created on 27.04.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.controller;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.swt.widgets.Display;

/**
 * A base class for server acces from the UI.
 * 
 * @author Stefan Reichert
 */
public abstract class ServerAccessControllerBase<Type> {

	/** The {@link Job} that procceeds the server access. */
	private final Job serverAccessJob;

	/**
	 * The data {@link Object} resulting from the server access. This may be
	 * used for initialisation <b>and</b> result handling.
	 */
	private Type data;

	/**
	 * Constructor for <class>ServerAccessControllerBase</class>.
	 */
	public ServerAccessControllerBase(String label) {
		super();
		serverAccessJob = new Job(label) {
			/** {@inheritDoc} */
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				return internalProceedServerAccess(monitor);
			}
		};
		serverAccessJob.addJobChangeListener(new JobChangeAdapter() {
			/** {@inheritDoc} */
			public void done(final IJobChangeEvent event) {
				Display.getDefault().asyncExec(new Runnable() {
					/** {@inheritDoc} */
					public void run() {
						handleResult(event.getResult());
					}
				});
			}
		});
	}

	/**
	 * Proceeds the server access in a seperate {@link Thread} provided by a
	 * {@link Job}. This method should fill the {@link #data} field with an
	 * appropriate value.<br>
	 * <br>
	 * <b>ATTENTION</b><br>
	 * This method runs <b>not</b> in the UI-Thread and may therefor <b>not</b>
	 * directly access UI elements.
	 * 
	 * @param monitor
	 *            The monitor that may be used for status display.
	 * @return the {@link IStatus} as the result of the access
	 */
	protected abstract IStatus internalProceedServerAccess(
			IProgressMonitor monitor);

	/**
	 * @return the serverAccessJob
	 */
	protected Job getServerAccessJob() {
		return serverAccessJob;
	}

	/**
	 * Initiates the server access. This method calls {@link #prepareAccess()}
	 * before scheduling the internal {@link Job}.
	 */
	public void initiateServerAccess() {
		if (prepareAccess()) {
			serverAccessJob.schedule();
		}
	}

	/**
	 * This method may be used for preparing the server access.<br>
	 * <br>
	 * <b>ATTENTION</b><br>
	 * This method is executed in the UI-Thread and must therefor not access the
	 * server.
	 * 
	 * @return whether the access should still be processed
	 */
	protected abstract boolean prepareAccess();

	/**
	 * This method may be used for handling the result of the server access.<br>
	 * <br>
	 * <b>ATTENTION</b><br>
	 * This method is executed in the UI-Thread and must therefor not access the
	 * server.
	 */
	protected abstract void handleResult(IStatus status);

	/**
	 * @return the data
	 */
	public Type getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Type data) {
		this.data = data;
	}

}
