/**
 * ClearCacheInstanceAction.java created on 14.12.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.cache.internal.action;

import net.sf.dysis.base.ui.dataprovider.cache.IManageableCacheEntry;
import net.sf.dysis.base.ui.dataprovider.cache.IManageableCachingStrategy;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author Stefan Reichert
 */
public class ClearCacheInstanceAction implements IObjectActionDelegate {

	/** The selected {@link IManageableCacheEntry}. */
	private IManageableCachingStrategy cacheInstance;

	/** {@inheritDoc} */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	public void run(IAction action) {
		if (cacheInstance != null) {
			cacheInstance.clearCache();
		}
	}

	/** {@inheritDoc} */
	public void selectionChanged(IAction action, ISelection selection) {
		boolean enabled = false;
		IManageableCachingStrategy newCacheInstance = null;
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		if (!structuredSelection.isEmpty()) {
			Object selectedElement = structuredSelection.getFirstElement();
			if (selectedElement instanceof IManageableCachingStrategy) {
				newCacheInstance = (IManageableCachingStrategy) selectedElement;
				enabled = newCacheInstance.hasCacheEntries();
			}
		}
		cacheInstance = newCacheInstance;
		action.setEnabled(enabled);
	}

}
