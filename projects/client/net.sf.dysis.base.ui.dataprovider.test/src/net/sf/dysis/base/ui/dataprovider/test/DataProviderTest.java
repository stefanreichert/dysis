/**
 * DataProviderTest.java created on 07.03.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.test;

import java.util.Collection;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.sf.dysis.base.ui.dataprovider.IDataProvider;
import net.sf.dysis.base.ui.dataprovider.cache.ICachingStrategy;
import net.sf.dysis.base.ui.dataprovider.internal.cache.CacheEntryInformation;
import net.sf.dysis.base.ui.dataprovider.internal.cache.DataProviderCacheAdapter;
import net.sf.dysis.base.ui.dataprovider.key.ICollectionKey;
import net.sf.dysis.base.ui.dataprovider.key.IKey;
import net.sf.dysis.base.ui.dataprovider.key.PrimaryKey;
import net.sf.dysis.base.ui.dataprovider.registry.Registry;
import net.sf.dysis.base.ui.dataprovider.test.util.PeoplePeterDataProvider;
import net.sf.dysis.base.ui.dataprovider.test.util.PeopleStefanDataProvider;

/**
 * Tests for the <code>DataProvider</code>.
 * 
 * @author Stefan Reichert
 */
public class DataProviderTest extends TestCase {

	/** Key for the <code>Person</code> <i>Peter</i>. */
	private static final IKey KEY_PETER = new PrimaryKey(3L);

	/** Key for the <code>Person</code> <i>Anne</i>. */
	private static final IKey KEY_ANNE = new PrimaryKey(4L);

	/**
	 * Retrieves a provider from the <code>Registry</code>.
	 * 
	 * @throws Throwable
	 */
	public void testRegistry() throws Throwable {
		IDataProvider dataProviderPeter = Registry.getRegistry()
				.lookupDataProvider(PeoplePeterDataProvider.DATA_PROVIDER_TYPE);
		Assert.assertTrue("Returned provider type should be "
				+ PeoplePeterDataProvider.DATA_PROVIDER_TYPE,
				(dataProviderPeter.getType()
						.equals(PeoplePeterDataProvider.DATA_PROVIDER_TYPE)));
		Assert.assertTrue("Returned provider type should adapted for caching",
				dataProviderPeter instanceof DataProviderCacheAdapter);
		IDataProvider dataProviderStefan = Registry
				.getRegistry()
				.lookupDataProvider(PeopleStefanDataProvider.DATA_PROVIDER_TYPE);
		Assert.assertTrue("Returned provider type should be "
				+ PeopleStefanDataProvider.DATA_PROVIDER_TYPE,
				(dataProviderStefan.getType()
						.equals(PeopleStefanDataProvider.DATA_PROVIDER_TYPE)));
	}

	/**
	 * Tests the evicting of entries and clear of the whole cache.
	 * 
	 * @throws Throwable
	 */
	public void testCacheEvictAndClear() throws Throwable {
		// get provider
		DataProviderCacheAdapter dataProvider = (DataProviderCacheAdapter) Registry
				.getRegistry().lookupDataProvider(
						PeoplePeterDataProvider.DATA_PROVIDER_TYPE);
		// assert cache to be empty
		assertNull("No information expected for cache entry", dataProvider
				.getCacheInformation(KEY_PETER));
		// retrieve peter
		dataProvider.getData(KEY_PETER);
		// assert cache not to be empty
		assertNotNull("Information expected for cache entry", dataProvider
				.getCacheInformation(KEY_PETER));
		// evict peter
		dataProvider.getCachingStrategy().evictCachedData(KEY_PETER);
		// assert cache to be empty
		assertNull("No information expected for cache entry", dataProvider
				.getCacheInformation(KEY_PETER));
		assertNull("No information expected for cache entry", dataProvider
				.getCacheInformation(KEY_ANNE));
		// retrieve peter and anne
		dataProvider.getData(KEY_PETER);
		dataProvider.getData(KEY_ANNE);
		// assert cache to be filled
		assertNotNull("Information expected for cache entry", dataProvider
				.getCacheInformation(KEY_PETER));
		assertNotNull("Information expected for cache entry", dataProvider
				.getCacheInformation(KEY_ANNE));
		// clear the cache
		dataProvider.getCachingStrategy().clearCache();
		// assert cache to be empty
		assertNull("No information expected for cache entry", dataProvider
				.getCacheInformation(KEY_PETER));
		assertNull("No information expected for cache entry", dataProvider
				.getCacheInformation(KEY_ANNE));
	}

	/**
	 * Tests whether the the underlaying wrapper for the
	 * <code>IDataProvider</code> implements <code>exists(IKey)</code> and
	 * <code>expires(IKey, ICacheEntryInformation)</code> in the right manner.
	 * 
	 * @throws Throwable
	 */
	public void testExistsExpired() throws Throwable {
		// get provider
		DataProviderCacheAdapter dataProvider = (DataProviderCacheAdapter) Registry
				.getRegistry().lookupDataProvider(
						PeoplePeterDataProvider.DATA_PROVIDER_TYPE);
		// assert cache to be empty
		assertNull("No information expected for cache entry", dataProvider
				.getCacheInformation(KEY_PETER));
		// retrieve peter
		dataProvider.getData(KEY_PETER);
		// assert cache to state the entry as existing and not expired
		CacheEntryInformation cacheEntryInformationPeter = dataProvider
				.getCacheInformation(KEY_PETER);
		assertTrue("Expected valid cache entry", dataProvider
				.getCachingStrategy().exists(KEY_PETER));
		assertFalse("Expected valid cache entry", dataProvider
				.getCachingStrategy().expired(KEY_PETER,
						cacheEntryInformationPeter));
		// evict peter
		dataProvider.getCachingStrategy().evictCachedData(KEY_PETER);
		// assert cache to be empty
		assertFalse("Expected invalid cache entry", dataProvider
				.getCachingStrategy().exists(KEY_PETER));
		assertTrue("Expected invalid cache entry", dataProvider
				.getCachingStrategy().expired(KEY_PETER,
						cacheEntryInformationPeter));
		assertNull("No information expected for cache entry", dataProvider
				.getCacheInformation(KEY_PETER));
	}

	/**
	 * Tests the retrieval of a single entry of the cache.
	 * 
	 * @throws Throwable
	 */
	public void testCacheSingleEntry() throws Throwable {
		// get provider
		DataProviderCacheAdapter dataProvider = (DataProviderCacheAdapter) Registry
				.getRegistry().lookupDataProvider(
						PeoplePeterDataProvider.DATA_PROVIDER_TYPE);
		// assert cache to be empty
		assertNull("No information expected for cache entry", dataProvider
				.getCacheInformation(KEY_PETER));
		assertNull("No information expected for cache entry", dataProvider
				.getCacheInformation(KEY_ANNE));
		// retrieve peter and anne
		dataProvider.getData(KEY_PETER);
		CacheEntryInformation cacheEntryInformationPeter = dataProvider
				.getCacheInformation(KEY_PETER);
		dataProvider.getData(KEY_ANNE);
		CacheEntryInformation cacheEntryInformationAnne = dataProvider
				.getCacheInformation(KEY_ANNE);
		// assume that cache entries are valid
		assertNotNull("Information expected for cache entry",
				cacheEntryInformationPeter);
		assertNotNull("Information expected for cache entry",
				cacheEntryInformationAnne);
		assertFalse("Expected valid cache entry", dataProvider
				.getCachingStrategy().expired(KEY_PETER,
						cacheEntryInformationPeter));
		assertFalse("Expected valid cache entry", dataProvider
				.getCachingStrategy().expired(KEY_ANNE,
						cacheEntryInformationAnne));
		// wait a little...
		int entryTimeOut = Integer.valueOf(dataProvider.getCachingStrategy().getCachingProperty(ICachingStrategy.TIME_OUT_KEY).getValue()); 
		int entryTimeToLive = Integer.valueOf(dataProvider.getCachingStrategy().getCachingProperty(ICachingStrategy.TIME_TO_LIVE_KEY).getValue()); 
		Thread.sleep(entryTimeOut / 2);
		// ... 'touch' one entry to avoid time out ...
		dataProvider.getData(KEY_PETER);
		// ..wait another little ...
		Thread.sleep(entryTimeOut / 2);
		// ... check whether one entry timed out and the other not
		assertTrue("Expected invalid cache entry", dataProvider
				.getCachingStrategy().expired(KEY_ANNE,
						cacheEntryInformationAnne));
		assertFalse("Expected valid cache entry", dataProvider
				.getCachingStrategy().expired(KEY_PETER,
						cacheEntryInformationPeter));
		// wait to reache time to live ...
		Thread.sleep(entryTimeToLive);
		// ... check whether that works
		assertTrue("Expected invalid cache entry", dataProvider
				.getCachingStrategy().expired(KEY_PETER,
						cacheEntryInformationPeter));
		// clear the cache
		dataProvider.getCachingStrategy().clearCache();
	}

	/**
	 * Tests the retrieval of a list entry of the cache.
	 * 
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	public void testCacheListEntry() throws Throwable {
		// get provider
		DataProviderCacheAdapter dataProvider = (DataProviderCacheAdapter) Registry
				.getRegistry().lookupDataProvider(
						PeoplePeterDataProvider.DATA_PROVIDER_TYPE);
		assertNull("No information expected for cache entry", dataProvider
				.getCacheInformation(KEY_PETER));
		assertNull("No information expected for cache entry", dataProvider
				.getCacheInformation(KEY_ANNE));
		assertNull("No information expected for cache list entry", dataProvider
				.getCacheInformation(ICollectionKey.ALL));
		// retrieve list
		Collection people = dataProvider.getDataCollection(ICollectionKey.ALL);
		assertEquals("Two elements expected in cache entry list", 2, people
				.size());
		assertNotNull("Information expected for cache entry", dataProvider
				.getCacheInformation(KEY_PETER));
		assertNotNull("Information expected for cache entry", dataProvider
				.getCacheInformation(KEY_ANNE));
		// clear the cache
		dataProvider.getCachingStrategy().clearCache();
	}
}
