/**
 * ResourceNavigatorElement.java created on 02.03.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.resource.ui.navigator;

import net.sf.dysis.base.ui.navigator.NavigatorElement;

import org.eclipse.jface.viewers.AbstractTreeViewer;

/**
 * <code>NavigatorElement</code> for a <code>Resource</code>.
 * 
 * @author Stefan Reichert
 */
public class ResourceNavigatorElement extends NavigatorElement {

	/**
	 * Constructor for <class>ResourceNavigatorElement</class>.
	 */
	public ResourceNavigatorElement(NavigatorElement parent, Object element,
			AbstractTreeViewer treeViewer) {
		super(parent, element, treeViewer);
	}

}
