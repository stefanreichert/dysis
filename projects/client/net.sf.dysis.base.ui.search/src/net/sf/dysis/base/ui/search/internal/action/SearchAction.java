/**
 * SearchAction.java created on 20.02.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.search.internal.action;

import net.sf.dysis.base.ui.search.ISearchCriteriaDialog;
import net.sf.dysis.base.ui.search.ISearchExecutable;
import net.sf.dysis.base.ui.search.internal.Activator;
import net.sf.dysis.base.ui.search.internal.Messages;
import net.sf.dysis.base.ui.search.internal.job.SearchJob;
import net.sf.dysis.base.ui.search.internal.registry.Search;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Shell;

import com.swtdesigner.ResourceManager;

/**
 * An {@link IAction} that executes the serach defined by it's
 * {@link ISearchExecutable}.
 * 
 * @author Stefan Reichert
 */
public class SearchAction extends Action {

	/** The search descriptor. */
	private Search search;

	/** The Job that executes the search. */
	private SearchJob searchJob;

	/**
	 * Constructor for <class>SearchAction</class>.
	 */
	public SearchAction(Shell parentShell, Search search) {
		super(Messages.getString("SearchAction.name"), ResourceManager.getPluginImageDescriptor(Activator //$NON-NLS-1$
				.getDefault(), "img/search.gif")); //$NON-NLS-1$
		this.search = search;
		searchJob = new SearchJob(search.getName(), search.getSearch());
	}

	/** {@inheritDoc} */
	@Override
	public void run() {
		ISearchCriteriaDialog searchCriteriaDialog = search.getCriteriaDialog();
		if (searchCriteriaDialog.open() == IDialogConstants.OK_ID) {
			searchJob.setCriteria(searchCriteriaDialog.getSearchCriteria());
			searchJob.schedule();
		}
	}

	/**
	 * @return the searchJob
	 */
	public SearchJob getSearchJob() {
		return searchJob;
	}

}
