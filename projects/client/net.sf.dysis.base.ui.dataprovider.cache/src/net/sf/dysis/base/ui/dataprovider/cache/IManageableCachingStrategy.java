/**
 * IManageableCachingStrategy.java created on 13.12.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.cache;

import java.util.Collection;

/**
 * Interface for an manageable {@link ICachingStrategy} implementation.
 * 
 * @author Stefan Reichert
 */
public interface IManageableCachingStrategy extends ICachingStrategy {

	/**
	 * @return the {@link Collection} of {@link IManageableCacheEntry} elements
	 *         currently cached
	 */
	Collection<IManageableCacheEntry> getCacheEntries();

	/**
	 * @return the number of {@link IManageableCacheEntry} elements currently cached
	 */
	int getCacheEntryCount();
	
	/**
	 * @return whether the cache currently has entries
	 */
	boolean hasCacheEntries();

	/**
	 * @return the name of the cache host
	 */
	String getCacheHostname();

	/**
	 * @param listener
	 *            The {@link IManageableCachingStrategyListener} to add
	 */
	void addListener(IManageableCachingStrategyListener listener);

	/**
	 * @param listener
	 *            The {@link IManageableCachingStrategyListener} to remove
	 */
	void removeListener(IManageableCachingStrategyListener listener);

	/**
	 * @param cacheEntry
	 *            The {@link IManageableCacheEntry} to get the statistics for
	 * @return the {@link IManageableCacheEntryStatistics} for the given entry
	 */
	IManageableCacheEntryStatistics getCacheEntryStatistics(
			IManageableCacheEntry cacheEntry);
}
