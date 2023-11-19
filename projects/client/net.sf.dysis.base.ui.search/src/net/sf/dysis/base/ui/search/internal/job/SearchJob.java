/**
 * SearchJob.java created on 20.02.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.search.internal.job;

import java.util.Collection;
import java.util.Collections;

import net.sf.dysis.base.ui.search.ISearchExecutable;
import net.sf.dysis.base.ui.search.internal.Messages;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

/**
 * A {@link Job} that executes an {@link ISearchExecutable}.
 * 
 * @author Stefan Reichert
 */
public class SearchJob extends Job {

	/** The result of the serach. */
	@SuppressWarnings( { "unchecked" })
	private Collection searchResult;

	/** The {@link ISearchExecutable} to use. */
	private ISearchExecutable executable;

	/** The criteria to use. */
	private Object criteria;

	/**
	 * Constructor for <class>SearchJob</class>.
	 */
	public SearchJob(String name, ISearchExecutable executable) {
		super(name);
		this.executable = executable;
	}

	/** {@inheritDoc} */
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		monitor.beginTask(Messages.getString("SearchJob.task.name"), -1); //$NON-NLS-1$
		searchResult = executable.perform(criteria);
		return Status.OK_STATUS;
	}

	/**
	 * @return the searchResult
	 */
	@SuppressWarnings("unchecked")
	public Collection getSearchResult() {
		if (searchResult != null) {
			return Collections.unmodifiableCollection(searchResult);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @param criteria
	 *            the criteria to set
	 */
	public void setCriteria(Object criteria) {
		this.criteria = criteria;
	}

	/**
	 * @return the criteria
	 */
	public Object getCriteria() {
		return criteria;
	}

}
