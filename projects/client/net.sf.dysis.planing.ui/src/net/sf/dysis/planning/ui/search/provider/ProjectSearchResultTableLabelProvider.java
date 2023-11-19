/**
 * ProjectSearchResultTableLabelProvider.java created on 20.02.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planning.ui.search.provider;

import java.text.DateFormat;

import net.sf.dysis.planing.core.dto.ProjectDTO;
import net.sf.dysis.planning.ui.search.ProjectSearch;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * {@link ITableLabelProvider} implementation for the {@link ProjectSearch}.
 * 
 * @author Stefan Reichert
 */
public class ProjectSearchResultTableLabelProvider extends LabelProvider
		implements ITableLabelProvider {

	/** {@inheritDoc} */
	public Image getColumnImage(Object element, int columnIndex) {

		return null;
	}

	/** {@inheritDoc} */
	public String getColumnText(Object element, int columnIndex) {
		ProjectDTO projectDTO = (ProjectDTO) element;
		switch (columnIndex) {
			case 0:
				return projectDTO.getName();
			case 1:
				return projectDTO.getDescription();
			case 2:
				return DateFormat.getDateInstance().format(
						projectDTO.getStartDate());
			case 3:
				return DateFormat.getDateInstance().format(
						projectDTO.getEndDate());
			default:
				return "unknown column"; //$NON-NLS-1$
		}
	}

}
