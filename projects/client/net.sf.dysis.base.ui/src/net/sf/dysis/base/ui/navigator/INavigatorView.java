/**
 * INavigatorView.java created on 21.01.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.navigator;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.part.ViewPart;

/**
 * Interface for a {@link ViewPart} hosting a navigation tree.
 *
 * @author Stefan Reichert
 *
 */
public interface INavigatorView {
	
	/**
	 * @return the {@link TreeViewer} which represents the navigator
	 */
	TreeViewer getNavigatorTreeViewer();

}
