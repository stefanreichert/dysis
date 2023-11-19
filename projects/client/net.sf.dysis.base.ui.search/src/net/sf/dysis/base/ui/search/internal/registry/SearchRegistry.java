/**
 * SearchRegistry.java created on 20.02.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.search.internal.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.dysis.base.ui.search.ISearchCriteriaDialog;
import net.sf.dysis.base.ui.search.ISearchExecutable;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ITableLabelProvider;

/**
 * Registry for searches defined by a search extension.
 * 
 * @author Stefan Reichert
 */
public class SearchRegistry {

	/** The static logger instance. */
	private static Logger logger = Logger.getLogger(SearchRegistry.class);

	/** The singleton instance. */
	private static SearchRegistry instance;

	/**
	 * Returns the singleton instance of the {@link SearchRegistry}.
	 * 
	 * @return the singleton instance
	 */
	private static SearchRegistry getInstance() {
		if (instance == null) {
			instance = new SearchRegistry();
			instance.initialize();
		}
		return instance;
	}

	/**
	 * Returns the registered search from the registry or <code>null</code> if
	 * none exists.
	 * 
	 * @param searchId
	 *            The id of the search to get
	 * @return The search descriptor
	 */
	public static Search getSearch(String searchId) {
		return getInstance().searchMap.get(searchId);
	}

	/** The {@link Map} of registered searches. */
	private Map<String, Search> searchMap;

	/**
	 * Constructor for <class>SearchRegistry</class>.
	 */
	private SearchRegistry() {
		// private constructor to avoid external use
	}

	/**
	 * Initializes the ristry by gathering all registered searches.
	 */
	private void initialize() {
		// initialize map
		searchMap = new HashMap<String, Search>();
		// get the seraches
		IConfigurationElement[] searchDescriptors = Platform
				.getExtensionRegistry().getConfigurationElementsFor(
						"net.sf.dysis.base.ui.search.descriptor"); //$NON-NLS-1$
		for (IConfigurationElement configurationElementSearch : searchDescriptors) {
			try {
				// gather serach config
				String id = configurationElementSearch.getAttribute("id"); //$NON-NLS-1$
				String name = configurationElementSearch.getAttribute("name"); //$NON-NLS-1$
				ISearchExecutable searchExecutable;
				searchExecutable = (ISearchExecutable) configurationElementSearch
						.createExecutableExtension("executable"); //$NON-NLS-1$
				ISearchCriteriaDialog searchCriteriaDialog = (ISearchCriteriaDialog) configurationElementSearch
						.createExecutableExtension("criteriaDialog"); //$NON-NLS-1$
				ITableLabelProvider tableLabelProvider = (ITableLabelProvider) configurationElementSearch
						.createExecutableExtension("labelProvider"); //$NON-NLS-1$
				// gather result list config
				List<ResultListColumn> resultListColumns = new ArrayList<ResultListColumn>();
				IConfigurationElement[] configurationElementResultListColumns = configurationElementSearch
						.getChildren("column"); //$NON-NLS-1$
				for (IConfigurationElement configurationElementResultListColumn : configurationElementResultListColumns) {
					String coulmnLabel = configurationElementResultListColumn
							.getAttribute("label"); //$NON-NLS-1$
					int coulmnWidth = Integer
							.valueOf(configurationElementResultListColumn
									.getAttribute("width")); //$NON-NLS-1$
					resultListColumns.add(new ResultListColumn(coulmnLabel,
							coulmnWidth));
				}
				// build the descriptor
				Search search = new Search(id, name, searchExecutable,
						searchCriteriaDialog, tableLabelProvider,
						resultListColumns);
				searchMap.put(id, search);
			}
			catch (CoreException exception) {
				logger.error(exception);
			}
		}

	}
}
