/**
 * ProjectSearchCriteriaDialog.java created on 20.02.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planning.ui.search.dialog;

import net.sf.dysis.base.ui.search.SearchCriteriaDialogBase;
import net.sf.dysis.planing.core.dto.ProjectSearchCriteriaDTOImpl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * TODO Please describe what this class is for
 * 
 * @author Stefan Reichert
 */
public class ProjectSearchCriteriaDialog extends SearchCriteriaDialogBase {

	/** Widget. */
	private CriteriaPanel criteriaPanel;

	/** The search criteria to use. */
	private ProjectSearchCriteriaDTOImpl searchCriteria;

	/**
	 * Constructor for <class>ProjectSearchCriteriaDialog</class>.
	 */
	public ProjectSearchCriteriaDialog() {
		super(Display.getDefault().getActiveShell());
		searchCriteria = new ProjectSearchCriteriaDTOImpl();
	}

	/** {@inheritDoc} */
	@Override
	protected void createCriteriaArea(Composite parent) {
		criteriaPanel = new CriteriaPanel(parent, SWT.NONE);
	}

	/** {@inheritDoc} */
	@Override
	protected Point getInitialSize() {
		return new Point(415, 250);
	}

	/** {@inheritDoc} */
	@Override
	protected void okPressed() {
		searchCriteria.setName(criteriaPanel.getTextName().getText());
		searchCriteria.setDescription(criteriaPanel.getTextBeschreibung()
				.getText());
		searchCriteria.setFromStartDate(criteriaPanel.getDateStartdatumVon()
				.getValue());
		searchCriteria.setToStartDate(criteriaPanel.getDateStartdatumBis()
				.getValue());
		searchCriteria.setFromEndDate(criteriaPanel.getDateEnddatumVon()
				.getValue());
		searchCriteria.setToEndDate(criteriaPanel.getDateEnddatumBis()
				.getValue());
		super.okPressed();
	}

	/** {@inheritDoc} */
	public Object getSearchCriteria() {
		return searchCriteria;
	}

	/** {@inheritDoc} */
	public void setSearchCriteria(Object searchCriteria) {

	}

}
