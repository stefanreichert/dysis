/**
 * ClearCacheHandler.java created on 30.06.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.cache.internal.handler;

import net.sf.dysis.base.ui.dataprovider.cache.IManageableCachingStrategy;
import net.sf.dysis.base.ui.dataprovider.cache.ManageableCachingStrategyRegistry;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

/**
 * Clears all registered cache instances.
 * 
 * @author Stefan Reichert
 */
public class ClearCacheHandler extends AbstractHandler {

	/** {@inheritDoc} */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		for (IManageableCachingStrategy cachingStrategy : ManageableCachingStrategyRegistry
				.getManageableStrategies()) {
			cachingStrategy.clearCache();
		}
		return null;
	}

}
