/**
 * RemoveCacheEntryAction.java created on 14.12.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.cache.internal.action;

import net.sf.dysis.base.ui.dataprovider.cache.IManageableCacheEntry;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author Stefan Reichert
 */
public class RemoveCacheEntryAction implements IObjectActionDelegate {

	/** The selected {@link IManageableCacheEntry}. */
	private IManageableCacheEntry cacheEntry;

	/** {@inheritDoc} */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	/** {@inheritDoc} */
	public void run(IAction action) {
		if (cacheEntry != null) {
			cacheEntry.getHostStrategy().evictCachedData(cacheEntry.getKey());
		}
	}

	/** {@inheritDoc} */
	public void selectionChanged(IAction action, ISelection selection) {
		boolean enabled = false;
		IManageableCacheEntry newCacheEntry = null;
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		if (!structuredSelection.isEmpty()) {
			Object selectedElement = structuredSelection.getFirstElement();
			if (selectedElement instanceof IManageableCacheEntry) {
				enabled = true;
				newCacheEntry = (IManageableCacheEntry) selectedElement;
			}
		}
		cacheEntry = newCacheEntry;
		action.setEnabled(enabled);
	}

}
