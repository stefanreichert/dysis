/**
 * ActivityLabelProvider.java created on 21.01.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.resource.ui.provider;

import net.sf.dysis.planing.core.dto.ActivityDTO;

import org.eclipse.jface.viewers.LabelProvider;

/**
 * LabelProvider for {@link ActivityDTO} objects
 * 
 * @author Stefan Reichert
 */
public class ActivityLabelProvider extends LabelProvider {
	/**
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		ActivityDTO task = (ActivityDTO) element;
		return task.getName();
	}
}
