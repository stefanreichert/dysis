/**
 * Search.java created on 20.02.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.search.internal.registry;

import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;

import net.sf.dysis.base.ui.search.ISearchExecutable;
import net.sf.dysis.base.ui.search.ISearchCriteriaDialog;

/**
 * Represents a registered searchExecutable.
 * 
 * @author Stefan Reichert
 */
public class Search {

	/** The id of this search. */
	private String id;

	/** The name of this search. */
	private String name;

	/** The executable of this search. */
	private ISearchExecutable searchExecutable;

	/** The criteria dialog of this search. */
	private ISearchCriteriaDialog criteriaDialog;

	/** The label provider of the result list. */
	private ITableLabelProvider labelProvider;

	/** The list of columns. */
	private List<ResultListColumn> columns;

	/**
	 * Constructor for <class>Search</class>.
	 */
	public Search(String id, String name, ISearchExecutable searchExecutable,
			ISearchCriteriaDialog criteriaDialog,
			ITableLabelProvider labelProvider, List<ResultListColumn> columns) {
		super();
		this.id = id;
		this.name = name;
		this.searchExecutable = searchExecutable;
		this.criteriaDialog = criteriaDialog;
		this.labelProvider = labelProvider;
		this.columns = columns;
		
	}

	/**
	 * @return the labelProvider
	 */
	public ITableLabelProvider getLabelProvider() {
		return labelProvider;
	}

	/**
	 * @return the columns
	 */
	public List<ResultListColumn> getColumns() {
		return columns;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the searchExecutable
	 */
	public ISearchExecutable getSearch() {
		return searchExecutable;
	}

	/**
	 * @return the criteriaDialog
	 */
	public ISearchCriteriaDialog getCriteriaDialog() {
		return criteriaDialog;
	}

}
