/**
 * ClearCacheAction.java created on 08.06.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.cache.internal.action;

import net.sf.dysis.base.ui.dataprovider.cache.IManageableCachingStrategy;
import net.sf.dysis.base.ui.dataprovider.cache.ManageableCachingStrategyRegistry;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

/**
 * Clears all registered cache instances.
 * 
 * @author Stefan Reichert
 */
public class ClearCacheAction implements IViewActionDelegate {

	/** {@inheritDoc} */
	public void init(IViewPart view) {
	}

	/** {@inheritDoc} */
	public void run(IAction action) {
		for (IManageableCachingStrategy cachingStrategy : ManageableCachingStrategyRegistry
				.getManageableStrategies()) {
			cachingStrategy.clearCache();
		}
	}

	/** {@inheritDoc} */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
