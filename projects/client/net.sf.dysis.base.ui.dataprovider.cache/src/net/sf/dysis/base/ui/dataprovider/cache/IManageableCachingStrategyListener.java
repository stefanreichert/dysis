/**
 * IManageableCachingStrategyListener.java created on 11.12.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.cache;

import net.sf.dysis.base.ui.dataprovider.key.IKey;

/**
 * A listener interface providing events on registered
 * {@link IManageableCachingStrategy}.
 * 
 * @author Stefan Reichert
 */
public interface IManageableCachingStrategyListener {

	/**
	 * @param cachingStrategy
	 *            The hosting strategy of the cache which changed
	 * @param key
	 *            The key of the changed entry
	 */
	void cacheChanged(IManageableCachingStrategy cachingStrategy, IKey key);

	/**
	 * @param cachingStrategy
	 *            The hosting strategy of the added cache
	 */
	void cacheAdded(IManageableCachingStrategy cachingStrategy);

	/**
	 * @param cachingStrategy
	 *            The hosting strategy of the added cache
	 */
	void cacheRemoved(IManageableCachingStrategy cachingStrategy);
}
