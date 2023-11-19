/**
 * NavigatorLeafAdapter.java created on 10.01.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.internal.navigator;

import org.eclipse.ui.progress.IDeferredWorkbenchAdapter;

/**
 * The <code>NavigatorElementAdapter</code> for leafs.
 *
 * @author Stefan Reichert
 *
 */
public class NavigatorLeafAdapter extends NavigatorElementAdapter implements IDeferredWorkbenchAdapter{

	/**
	 * @see org.eclipse.ui.progress.IDeferredWorkbenchAdapter#isContainer()
	 */
	public boolean isContainer() {
		return false;
	}
	
}
