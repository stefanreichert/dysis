/**
 * ICachingStrategy.java created on 29.02.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.cache;

import java.util.Collection;
import java.util.Set;

import net.sf.dysis.base.ui.dataprovider.IDataProvider;
import net.sf.dysis.base.ui.dataprovider.key.IKey;

/**
 * Interface for a caching strategy.
 * 
 * @author Stefan Reichert
 */
public interface ICachingStrategy {

	/** The eternal caching property key. */
	final String ETERNAL_KEY = "net.sf.dysis.caching.eternal"; //$NON-NLS-1$

	/** The eternal caching property key. */
	final String TIME_OUT_KEY = "net.sf.dysis.caching.timeout"; //$NON-NLS-1$

	/** The eternal caching property key. */
	final String TIME_TO_LIVE_KEY = "net.sf.dysis.caching.timetolive"; //$NON-NLS-1$

	/**
	 * Evaluates whether the data with the given <code>IKey</code> expired yet.
	 * This method should ensure, that the given entry exists by calling
	 * <code>exists(IKey)</code> beore evaluating the given
	 * <code>ICacheEntryInformation</code>.
	 * 
	 * @param key
	 *            The <code>IKey</code> of the investigated data
	 * @param cacheEntryInformation
	 *            The <code>ICacheEntryInformation</code> of the investigated
	 *            data
	 * @return whether the data has expired
	 */
	boolean expired(IKey key, ICacheEntryInformation cacheEntryInformation);

	/**
	 * Evaluates whether the data with the given <code>IKey</code> exists. If
	 * <code>evictCachedData(IKey)</code> was called for this <code>Key</code>
	 * before, this method should return <code>false</code>.
	 * 
	 * @param key
	 *            The <code>IKey</code> of the investigated data
	 * @see #expired(IKey, ICacheEntryInformation)
	 */
	boolean exists(IKey key);

	/**
	 * @return the data with the given <code>IKey</code>
	 */
	Object getCachedData(IKey key);

	/**
	 * Evict the entry for the data with the given <code>IKey</code>.
	 * 
	 * @param data
	 *            The updated data
	 */
	void evictCachedData(IKey key);

	/**
	 * Registers an entry for the data with the given <code>IKey</code>.
	 * 
	 * @param key
	 *            The <code>IKey</code> of the data to register
	 * @param data
	 *            The data to rigister for the given <code>IKey</code>
	 * @param cacheEntryInformation
	 *            The corresponding <code>ICacheEntryInformation</code>
	 */
	void registerCachedData(IKey key, Object data,
			ICacheEntryInformation cacheEntryInformation);

	/**
	 * Clears the whole cache.
	 */
	void clearCache();

	/**
	 * Initializes this strategy passing the properties defined.
	 */
	void initialize(Set<ICachingProperty> cachingProperties);

	/**
	 * Destroys this strategy. This method gets invoked when the corresponding
	 * {@link IDataProvider} gets removed from the registry. It's pupose is to
	 * clean up everything e.g. unregistering the stragegy from some service.
	 */
	void destroy();

	/**
	 * @return the {@link ICachingProperty} for the given key
	 */
	ICachingProperty getCachingProperty(String key);

	/**
	 * @return the {@link Collection} of {@link ICachingProperty}
	 */
	Collection<ICachingProperty> getCachingProperties();
}
