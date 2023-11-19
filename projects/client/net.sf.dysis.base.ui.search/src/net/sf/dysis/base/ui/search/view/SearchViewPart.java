/**
 * SearchViewPart.java created on 20.02.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.search.view;

import java.util.Collection;

import net.sf.dysis.base.ui.menu.StandardMenuManager;
import net.sf.dysis.base.ui.search.internal.Messages;
import net.sf.dysis.base.ui.search.internal.action.SearchAction;
import net.sf.dysis.base.ui.search.internal.job.SearchJob;
import net.sf.dysis.base.ui.search.internal.registry.ResultListColumn;
import net.sf.dysis.base.ui.search.internal.registry.Search;
import net.sf.dysis.base.ui.search.internal.registry.SearchRegistry;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

/**
 * A {@link ViewPart} that provides a generic search based on a
 * <i>net.sf.dysis.base.ui.search.Instance</i> extension.
 * 
 * @author Stefan Reichert
 */
public abstract class SearchViewPart extends ViewPart {

	/** Widget. */
	private TableViewer tableViewerSearch;

	/** the underlaying search descriptor. */
	private Search search;

	/** {@inheritDoc} */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.BORDER);
		FillLayout fillLayout = new FillLayout();
		fillLayout.spacing = 10;
		container.setLayout(fillLayout);

		// first of all we need our descriptor
		search = SearchRegistry.getSearch(getSearchId());
		tableViewerSearch = new TableViewer(container, SWT.FULL_SELECTION
				| SWT.VIRTUAL);
		tableViewerSearch.setContentProvider(new ArrayContentProvider());
		tableViewerSearch.setLabelProvider(search.getLabelProvider());
		Table tableSearch = tableViewerSearch.getTable();
		// create the columns using the descriptor
		for (ResultListColumn resultListColumn : search.getColumns()) {
			TableColumn tableColumn = new TableColumn(tableSearch, SWT.NONE);
			tableColumn.setText(resultListColumn.getName());
			tableColumn.setWidth(resultListColumn.getWidth());
		}
		tableSearch.setLinesVisible(true);
		tableSearch.setHeaderVisible(true);

		initializeToolBar();
		initializeContextMenu();
		setContentDescription(Messages.getString("SearchViewPart.result.empty.message")); //$NON-NLS-1$
	}

	/**
	 * Initializes the {@link TableViewer}'s context menu
	 */
	private void initializeContextMenu() {
		getViewSite().setSelectionProvider(tableViewerSearch);
		MenuManager menuManager = new StandardMenuManager();
		getViewSite().registerContextMenu(getSearchId(), menuManager,
				tableViewerSearch);
		Menu contextMenu = menuManager.createContextMenu(tableViewerSearch
				.getTable());
		tableViewerSearch.getTable().setMenu(contextMenu);
	}

	/** {@inheritDoc} */
	@Override
	public void setFocus() {
		tableViewerSearch.getTable().setFocus();
	}

	/**
	 * Provides the id of the underlaying search extension.
	 */
	protected abstract String getSearchId();

	/**
	 * Provides the id of the underlaying search extension.
	 */
	protected String getSearchCriteriaString(Object searchCriteria) {
		return null;
	}

	/**
	 * Initializes the toolbar of this search.
	 */
	private void initializeToolBar() {
		IToolBarManager toolBarManager = getViewSite().getActionBars()
				.getToolBarManager();
		StandardMenuManager.applyStandard(toolBarManager);
		SearchAction searchAction = new SearchAction(getSite().getShell(),
				search);
		searchAction.getSearchJob().addJobChangeListener(
				new JobChangeAdapter() {
					/** {@inheritDoc} */
					@Override
					public void done(final IJobChangeEvent event) {
						getSite().getShell().getDisplay().asyncExec(
								new Runnable() {
									/** {@inheritDoc} */
									@SuppressWarnings("unchecked")
									public void run() {
										SearchJob searchJob = (SearchJob) event
												.getJob();
										Collection searchResult = searchJob
												.getSearchResult();
										tableViewerSearch
												.setInput(searchResult);
										updateContentescription(searchResult
												.size(), searchJob
												.getCriteria());

									}
								});
						super.done(event);
					}
				});
		toolBarManager.appendToGroup(StandardMenuManager.GROUP_SPECIAL,
				searchAction);
	}

	/**
	 * Creates the content description after a search was run.
	 */
	private void updateContentescription(int resultCount, Object searchCriteria) {
		StringBuffer contentDescription = new StringBuffer();
		contentDescription.append(Messages.getString("SearchViewPart.result.message.prefix")); //$NON-NLS-1$
		contentDescription.append(resultCount);
		String searchCriteriaString = getSearchCriteriaString(searchCriteria);
		if (searchCriteriaString != null) {
			contentDescription.append(" - ["); //$NON-NLS-1$
			contentDescription.append(searchCriteriaString);
			contentDescription.append("]"); //$NON-NLS-1$
		}
		setContentDescription(contentDescription.toString());
	}
}
