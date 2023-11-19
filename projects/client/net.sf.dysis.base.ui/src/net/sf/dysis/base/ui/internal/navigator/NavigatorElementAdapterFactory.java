/**
 * NavigatorNodeAdapterFactory.java created on 10.01.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.internal.navigator;

import net.sf.dysis.base.ui.navigator.NavigatorElement;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.progress.IDeferredWorkbenchAdapter;

/**
 * The <code>IAdapterFactory</code> for <code>NavigatorElement</code>s.
 * 
 * @author Stefan Reichert
 */
public class NavigatorElementAdapterFactory implements IAdapterFactory {

	/** Adapter for nodes. */
	private NavigatorElementAdapter nodeAdapter = new NavigatorNodeAdapter();

	/** Adapter for leafs. */
	private NavigatorElementAdapter leafAdapter = new NavigatorLeafAdapter();

	/**
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object,
	 *      java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adaptableObject instanceof NavigatorElement) {
			if (adapterType == NavigatorElement.class
					|| adapterType == IDeferredWorkbenchAdapter.class
					|| adapterType == IWorkbenchAdapter.class) {
				NavigatorElement element = (NavigatorElement) adaptableObject;
				if (element.isContainer()) {
					return nodeAdapter;
				}
				else {
					return leafAdapter;
				}
			}
		}
		return null;
	}

	/**
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	@SuppressWarnings("unchecked")
	public Class[] getAdapterList() {
		return new Class[] { NavigatorElement.class,
				IDeferredWorkbenchAdapter.class, IWorkbenchAdapter.class };
	}

}
