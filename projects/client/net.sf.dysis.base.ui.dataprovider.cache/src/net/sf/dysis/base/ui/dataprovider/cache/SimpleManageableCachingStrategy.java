/**
 * SimpleManageableCachingStrategy.java created on 11.12.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.cache;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import net.sf.dysis.base.ui.dataprovider.IDataProvider;
import net.sf.dysis.base.ui.dataprovider.key.IKey;

/**
 * An {@link IManageableCachingStrategy} implementation allowing external
 * management.
 * 
 * @author Stefan Reichert
 */
public class SimpleManageableCachingStrategy implements
		IManageableCachingStrategy, IDataProviderAware {

	/** The in memory cache. */
	private Map<IKey, Object> cache;

	/** The information for the registered entries. */
	private Map<IKey, ICacheEntryInformation> cacheInformation;

	/** The hosting {@link IDataProvider}. */
	private IDataProvider dataProvider;

	/** The {@link Collection} of {@link IManageableCachingStrategyListener}. */
	private Collection<IManageableCachingStrategyListener> listeners;

	/** The caching properties. */
	private Map<String, ICachingProperty> cachingPropertyMap;

	/** The caching statistics. */
	private Map<IKey, IManageableCacheEntryStatistics> cacheEntryStatisticsMap;

	/**
	 * Constructor for <class>SimpleManageableCachingStrategy</class>.
	 */
	public SimpleManageableCachingStrategy() {
		super();
		listeners = new ArrayList<IManageableCachingStrategyListener>();
		cache = new HashMap<IKey, Object>();
		cacheInformation = new HashMap<IKey, ICacheEntryInformation>();
		cachingPropertyMap = new HashMap<String, ICachingProperty>();
		cacheEntryStatisticsMap = new HashMap<IKey, IManageableCacheEntryStatistics>();
		ManageableCachingStrategyRegistry.registerManageableStrategy(this);
	}

	/** {@inheritDoc} */
	public void evictCachedData(IKey key) {
		cache.remove(key);
		cacheInformation.remove(key);
		fireCacheChanged(key);
	}

	/** {@inheritDoc} */
	public void initialize(Set<ICachingProperty> cachingProperties) {
		for (ICachingProperty cachingProperty : cachingProperties) {
			cachingPropertyMap.put(cachingProperty.getKey(), cachingProperty);
		}
	}

	/** {@inheritDoc} */
	public void destroy() {
		clearCache();
		ManageableCachingStrategyRegistry.deregisterManageableStrategy(this);
	}

	/** {@inheritDoc} */
	public boolean expired(IKey key,
			ICacheEntryInformation cacheEntryInformation) {
		if (!exists(key)) {
			// if entry was evicted, it automatically has expired ;)
			registerCacheMiss(key);
			fireCacheChanged(key);
			return true;
		}
		else if (Boolean
				.valueOf(cachingPropertyMap.get(ETERNAL_KEY).getValue())) {
			cacheInformation.put(key, cacheEntryInformation);
			registerCacheHit(key);
			fireCacheChanged(key);
			// if it is an eternal entry, it never expires
			return false;
		}
		else {
			// if it is not an eternal entry, we have to check the entry
			// information
			Calendar now = Calendar.getInstance();
			Calendar lifeTime = Calendar.getInstance();
			lifeTime.setTime(cacheEntryInformation.getCreation());
			lifeTime.add(Calendar.MILLISECOND, Integer
					.valueOf(cachingPropertyMap.get(TIME_TO_LIVE_KEY)
							.getValue()));
			Calendar timeOut = Calendar.getInstance();
			timeOut.setTime(cacheEntryInformation.getLastAccess());
			timeOut.add(Calendar.MILLISECOND, Integer
					.valueOf(cachingPropertyMap.get(TIME_OUT_KEY).getValue()));
			// Check whether time out was reached
			boolean timeOutReached = now.after(timeOut);
			// Check whether life time exceeded
			boolean lifeTimeExceeded = now.after(lifeTime);
			if (timeOutReached || lifeTimeExceeded) {
				registerCacheMiss(key);
			}
			else {
				registerCacheHit(key);
			}
			fireCacheChanged(key);
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
		cacheInformation.put(key, cacheEntryInformation);
		if (!cacheEntryStatisticsMap.containsKey(key)) {
			cacheEntryStatisticsMap.put(key,
					new ManageableCacheEntryStatistics());
		}
		fireCacheChanged(key);
	}

	/** {@inheritDoc} */
	public void clearCache() {
		// get the keys
		List<IKey> keys = new ArrayList<IKey>(cache.keySet());
		cache.clear();
		cacheInformation.clear();
		for (IKey key : keys) {
			fireCacheChanged(key);
		}
	}

	/** {@inheritDoc} */
	public boolean exists(IKey key) {
		return cache.containsKey(key);
	}

	/** {@inheritDoc} */
	public void setDataProvider(IDataProvider dataProvider) {
		this.dataProvider = dataProvider;
		fireCacheAdded();
	}

	/** {@inheritDoc} */
	public String getCacheHostname() {
		return dataProvider.getType();
	}

	/**
	 * Promotes a cache change to the list of
	 * {@link IManageableCachingStrategyListener}.
	 */
	private void fireCacheChanged(IKey key) {
		for (IManageableCachingStrategyListener strategyListener : listeners) {
			strategyListener.cacheChanged(this, key);
		}
	}

	/**
	 * Promotes a cache add to the list of
	 * {@link IManageableCachingStrategyListener}.
	 */
	private void fireCacheAdded() {
		for (IManageableCachingStrategyListener strategyListener : listeners) {
			strategyListener.cacheAdded(this);
		}
	}

	/**
	 * @param key
	 *            The key which was missed in cache
	 */
	private void registerCacheMiss(IKey key) {
		ManageableCacheEntryStatistics cacheEntryStatistics = (ManageableCacheEntryStatistics) cacheEntryStatisticsMap
				.get(key);
		if (cacheEntryStatistics == null) {
			cacheEntryStatistics = new ManageableCacheEntryStatistics();
			cacheEntryStatisticsMap.put(key, cacheEntryStatistics);
		}
		cacheEntryStatistics.registerCacheMiss();
	}

	/**
	 * @param key
	 *            The key which was hit in cache
	 */
	private void registerCacheHit(IKey key) {
		ManageableCacheEntryStatistics cacheEntryStatistics = (ManageableCacheEntryStatistics) cacheEntryStatisticsMap
				.get(key);
		if (cacheEntryStatistics == null) {
			cacheEntryStatistics = new ManageableCacheEntryStatistics();
			cacheEntryStatisticsMap.put(key, cacheEntryStatistics);
		}
		cacheEntryStatistics.registerCacheHit();
	}

	/** {@inheritDoc} */
	public void addListener(IManageableCachingStrategyListener listener) {
		listeners.add(listener);
	}

	/** {@inheritDoc} */
	public void removeListener(IManageableCachingStrategyListener listener) {
		listeners.add(listener);
	}

	/** {@inheritDoc} */
	public Collection<IManageableCacheEntry> getCacheEntries() {
		Collection<IManageableCacheEntry> cacheEntries = new ArrayList<IManageableCacheEntry>();
		for (Entry<IKey, ICacheEntryInformation> cacheInformationEntry : cacheInformation
				.entrySet()) {
			cacheEntries.add(new CacheEntry(cacheInformationEntry.getKey(),
					cacheInformationEntry.getValue(), this));
		}
		return cacheEntries;
	}

	/** {@inheritDoc} */
	public int getCacheEntryCount() {
		return cacheInformation.size();
	}

	/** {@inheritDoc} */
	public IManageableCacheEntryStatistics getCacheEntryStatistics(
			IManageableCacheEntry cacheEntry) {
		return cacheEntryStatisticsMap.get(cacheEntry.getKey());
	}

	/** {@inheritDoc} */
	public boolean hasCacheEntries() {
		return !cacheInformation.isEmpty();
	}

	/** {@inheritDoc} */
	public ICachingProperty getCachingProperty(String key) {
		return cachingPropertyMap.get(key);
	}

	/** {@inheritDoc} */
	public Collection<ICachingProperty> getCachingProperties() {
		return Collections.unmodifiableCollection(cachingPropertyMap.values());
	}

	/**
	 * An implementation for the {@link IManageableCacheEntry} interface.
	 * 
	 * @author Stefan Reichert
	 */
	private class CacheEntry implements IManageableCacheEntry {

		/** The {@link IKey} of this entry. */
		private IKey key;

		/** The hosting strategy. */
		private IManageableCachingStrategy cachingStrategy;

		/** The entry's {@link ICacheEntryInformation}. */
		private ICacheEntryInformation cacheEntryInformation;

		/**
		 * Constructor for <class>CacheEntry</class>.
		 */
		public CacheEntry(IKey key,
				ICacheEntryInformation cacheEntryInformation,
				IManageableCachingStrategy cachingStrategy) {
			super();
			this.cacheEntryInformation = cacheEntryInformation;
			this.cachingStrategy = cachingStrategy;
			this.key = key;
		}

		/** {@inheritDoc} */
		public ICacheEntryInformation getCacheEntryInformation() {
			return cacheEntryInformation;
		}

		/** {@inheritDoc} */
		public IManageableCachingStrategy getHostStrategy() {
			return cachingStrategy;
		}

		/** {@inheritDoc} */
		public IKey getKey() {
			return key;
		}

		/** {@inheritDoc} */
		@Override
		public boolean equals(Object object) {
			if (object instanceof CacheEntry) {
				CacheEntry otherCacheEntry = (CacheEntry) object;
				return otherCacheEntry.getKey().equals(key);
			}
			return super.equals(object);
		}

		/** {@inheritDoc} */
		@Override
		public int hashCode() {
			return key.hashCode();
		}
	}

	private class ManageableCacheEntryStatistics implements
			IManageableCacheEntryStatistics {

		/** The count of requests. */
		private int accessCount;

		/** The count of cacheHits. */
		private int cacheHits;

		/** the initial creation {@link Date}. */
		private Date initialCreationDate;

		/**
		 * Constructor for <class>ManageableCacheEntryStatistics</class>.
		 */
		public ManageableCacheEntryStatistics() {
			super();
			initialCreationDate = new Date();
			accessCount = 0;
			cacheHits = 0;
		}

		/** {@inheritDoc} */
		public int getCacheHits() {
			return cacheHits;
		}

		/** {@inheritDoc} */
		public Date getInitialCreation() {
			return initialCreationDate;
		}

		/** {@inheritDoc} */
		public int getAccessCount() {
			return accessCount;
		}

		/**
		 * Increases the number of requests and cache hits.
		 */
		public void registerCacheHit() {
			accessCount++;
			cacheHits++;
		}

		/**
		 * Increases the number of requests.
		 */
		public void registerCacheMiss() {
			accessCount++;
		}

	}
}
