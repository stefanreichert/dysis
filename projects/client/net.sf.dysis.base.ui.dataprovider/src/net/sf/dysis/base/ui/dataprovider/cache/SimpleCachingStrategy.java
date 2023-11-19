/**
 * SimpleCachingStrategy.java created on 01.03.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.cache;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.sf.dysis.base.ui.dataprovider.key.IKey;

/**
 * Implements a simple <code>ICachingStrategy</code>.
 * 
 * @author Stefan Reichert
 */
public class SimpleCachingStrategy implements ICachingStrategy {

	/** The in memory cache. */
	private Map<IKey, Object> cache;

	/** The caching properties. */
	private Map<String, ICachingProperty> cachingPropertyMap;

	/**
	 * Constructor for <class>SimpleCachingStrategy</class>.
	 */
	public SimpleCachingStrategy() {
		super();
		cache = new HashMap<IKey, Object>();
		cachingPropertyMap = new HashMap<String, ICachingProperty>();
	}

	/** {@inheritDoc} */
	public void initialize(Set<ICachingProperty> cachingProperties) {
		for (ICachingProperty cachingProperty : cachingProperties) {
			cachingPropertyMap.put(cachingProperty.getKey(), cachingProperty);
		}
	}

	/** {@inheritDoc} */
	public void evictCachedData(IKey key) {
		cache.remove(key);
	}

	/** {@inheritDoc} */
	public boolean expired(IKey key,
			ICacheEntryInformation cacheEntryInformation) {
		if (!exists(key)) {
			// if entry was evicted, it automatically has expired ;)
			return true;
		}
		else if (Boolean.valueOf(cachingPropertyMap.get(ETERNAL_KEY).getValue())) {
			// if it is an eternal entry, it never expires
			return false;
		}
		else {
			// if it is not an eternal entry, we have to check the entry
			// information
			Calendar now = Calendar.getInstance();
			Calendar lifeTime = Calendar.getInstance();
			lifeTime.setTime(cacheEntryInformation.getCreation());
			lifeTime.add(Calendar.MILLISECOND,
					Integer.valueOf(cachingPropertyMap.get(TIME_TO_LIVE_KEY)
							.getValue()));
			Calendar timeOut = Calendar.getInstance();
			timeOut.setTime(cacheEntryInformation.getLastAccess());
			timeOut.add(Calendar.MILLISECOND, Integer.valueOf(cachingPropertyMap
					.get(TIME_OUT_KEY).getValue()));
			// Check whether time out was reached
			boolean timeOutReached = now.after(timeOut);
			// Check whether life time exceeded
			boolean lifeTimeExceeded = now.after(lifeTime);
			return (timeOutReached || lifeTimeExceeded);
		}
	}

	/** {@inheritDoc} */
	public Object getCachedData(IKey key) {
		return cache.get(key);
	}

	/** {@inheritDoc} */
	public void registerCachedData(IKey key, Object data,
			ICacheEntryInformation cacheEntryInformation) {
		cache.put(key, data);
	}

	/** {@inheritDoc} */
	public void clearCache() {
		cache.clear();
	}

	/** {@inheritDoc} */
	public boolean exists(IKey key) {
		return cache.containsKey(key);
	}

	/** {@inheritDoc} */
	public ICachingProperty getCachingProperty(String key) {
		return cachingPropertyMap.get(key);
	}

	/** {@inheritDoc} */
	public Collection<ICachingProperty> getCachingProperties() {
		return Collections.unmodifiableCollection(cachingPropertyMap.values());
	}

	/** {@inheritDoc} */
	public void destroy() {
		clearCache();
	}
}
