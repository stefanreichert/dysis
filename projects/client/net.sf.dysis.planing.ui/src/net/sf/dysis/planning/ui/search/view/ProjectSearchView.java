/**
 * ProjectSearchView.java created on 20.02.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planning.ui.search.view;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.dysis.base.ui.search.view.SearchViewPart;
import net.sf.dysis.planing.core.dto.ProjectSearchCriteriaDTOImpl;
import net.sf.dysis.planning.ui.Messages;

/**
 * The search view for projects.
 * 
 * @author Stefan Reichert
 */
public class ProjectSearchView extends SearchViewPart {

	public static final String ID = "net.sf.dysis.planning.ui.search.view.ProjectSearchView"; //$NON-NLS-1$

	/** {@inheritDoc} */
	@Override
	protected String getSearchId() {
		return "net.sf.dysis.planning.ui.search.project"; //$NON-NLS-1$
	}

	/** {@inheritDoc} */
	@Override
	protected String getSearchCriteriaString(Object searchCriteria) {
		ProjectSearchCriteriaDTOImpl projectSearchCriteria = (ProjectSearchCriteriaDTOImpl) searchCriteria;
		List<String> filledCriteria = new ArrayList<String>();
		if (projectSearchCriteria.getName().length() > 0) {
			filledCriteria.add(Messages.getString("ProjectSearchView.project.name") + projectSearchCriteria.getName()); //$NON-NLS-1$
		}
		if (projectSearchCriteria.getDescription().length() > 0) {
			filledCriteria.add(Messages.getString("ProjectSearchView.project.description") //$NON-NLS-1$
					+ projectSearchCriteria.getDescription());
		}
		if (projectSearchCriteria.getFromStartDate() != null) {
			filledCriteria.add(Messages.getString("ProjectSearchView.project.start.from") //$NON-NLS-1$
					+ DateFormat.getDateInstance().format(
							projectSearchCriteria.getFromStartDate()));
		}
		if (projectSearchCriteria.getToStartDate() != null) {
			filledCriteria.add(Messages.getString("ProjectSearchView.project.start.to") //$NON-NLS-1$
					+ DateFormat.getDateInstance().format(
							projectSearchCriteria.getToStartDate()));
		}
		if (projectSearchCriteria.getFromEndDate() != null) {
			filledCriteria.add(Messages.getString("ProjectSearchView.project.end.from") //$NON-NLS-1$
					+ DateFormat.getDateInstance().format(
							projectSearchCriteria.getFromEndDate()));
		}
		if (projectSearchCriteria.getToEndDate() != null) {
			filledCriteria.add(Messages.getString("ProjectSearchView.project.end.to") //$NON-NLS-1$
					+ DateFormat.getDateInstance().format(
							projectSearchCriteria.getToEndDate()));
		}
		StringBuffer buffer = new StringBuffer();
		for (Iterator<String> iterator = filledCriteria.iterator(); iterator.hasNext();) {
			buffer.append(iterator.next());
			if (iterator.hasNext()) {
				buffer.append("; "); //$NON-NLS-1$
			}
			
		}
		return buffer.toString();
	}
}
