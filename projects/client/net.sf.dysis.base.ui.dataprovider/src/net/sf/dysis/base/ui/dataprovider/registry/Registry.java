/**
 * Registry.java created on 29.02.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.registry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.dysis.base.ui.dataprovider.IDataProvider;
import net.sf.dysis.base.ui.dataprovider.cache.ICachingProperty;
import net.sf.dysis.base.ui.dataprovider.cache.ICachingStrategy;
import net.sf.dysis.base.ui.dataprovider.cache.IDataProviderAware;
import net.sf.dysis.base.ui.dataprovider.internal.cache.CachingProperty;
import net.sf.dysis.base.ui.dataprovider.internal.cache.DataProviderCacheAdapter;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.dynamichelpers.ExtensionTracker;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.ui.PlatformUI;

/**
 * This class represents the registry for <code>IDataProvider</code>s registered
 * via extension.
 * 
 * @author Stefan Reichert
 */
public class Registry implements IExtensionChangeHandler {

	/** This class' Logger instance. */
	private static Logger logger = Logger.getLogger(Registry.class);

	/** Registry ID for . */
	private static final String REGISTRY_ID_EXTENSION_POINT_ID = "net.sf.dysis.base.ui.dataprovider"; //$NON-NLS-1$

	/** Registry ID for . */
	private static final String REGISTRY_ID_ATTRIBUTE_ID = "id"; //$NON-NLS-1$

	/** Registry ID for . */
	private static final String REGISTRY_ID_ATTRIBUTE_CLASS = "class"; //$NON-NLS-1$

	/** Registry ID for . */
	private static final String REGISTRY_ID_ATTRIBUTE_PARAMETERS = "cachingParameter"; //$NON-NLS-1$

	/** Registry ID for . */
	private static final String REGISTRY_ID_ATTRIBUTE_ETERNAL = "eternalEntry"; //$NON-NLS-1$

	/** Registry ID for . */
	private static final String REGISTRY_ID_ATTRIBUTE_TIMEOUT = "entryTimeOut"; //$NON-NLS-1$

	/** Registry ID for . */
	private static final String REGISTRY_ID_ATTRIBUTE_TIMETOLIVE = "entryTimeToLive"; //$NON-NLS-1$

	/** Registry ID for . */
	private static final String REGISTRY_ID_ATTRIBUTE_CACHING_STRATEGY = "cachingStrategy"; //$NON-NLS-1$

	/** Default time out. */
	private static final Long DEFAULT_TIMEOUT = 30000l;

	/** Default time to live. */
	private static final Long DEFAULT_TIMETOLIVE = 60000l;

	/** Flag whether caching is disabled globally. **/
	private static boolean ignoreCacheDefinition = false;

	/** The singleton instance of <code>Registry</code>. */
	private static Registry registryInstance;

	/**
	 * @return the singleton instance of <code>Registry</code>
	 */
	public static Registry getRegistry() {
		if (registryInstance == null) {
			registryInstance = new Registry();
			registryInstance.initialize();
		}
		return registryInstance;
	}

	/**
	 * @param ignoreCacheDefinition
	 *            the ignoreCacheDefinition to set
	 */
	public static void ignoreCacheDefinition() {
		if (!ignoreCacheDefinition && registryInstance != null) {
			throw new RegistryException(
					"Cache definition cannot not be ignored anymore as the registry was already initialized. This method must be called prior to the registry intialisation."); //$NON-NLS-1$
		}
		ignoreCacheDefinition = true;
	}

	/**
	 * @return the ignoreCacheDefinition
	 */
	public static boolean isCacheDefinitionIgnored() {
		return ignoreCacheDefinition;
	}

	/**
	 * <code>Map</code> which contains all registered
	 * <code>IDataProviders</code> mapped by it's type.
	 */
	private Map<String, IDataProvider> registeredProvidersByType;

	/**
	 * <code>Map</code> which contains all registered
	 * <code>IDataProviders</code> mapped by it's id form the extension.
	 */
	private Map<String, IDataProvider> registeredProvidersById;

	/**
	 * Constructor for <class>Registry</class>.
	 */
	private Registry() {
		// private constructor to avoid creation
		super();
		registeredProvidersByType = new HashMap<String, IDataProvider>();
		registeredProvidersById = new HashMap<String, IDataProvider>();
	}

	/**
	 * @return the {@link IDataProvider} extension point
	 */
	private IExtensionPoint getExtensionPointFilter() {
		return Platform.getExtensionRegistry().getExtensionPoint(
				REGISTRY_ID_EXTENSION_POINT_ID);
	}

	/** {@inheritDoc} */
	public void addExtension(IExtensionTracker tracker, IExtension extension) {
		logger.info("DataProvider extension handling detected"); //$NON-NLS-1$
		if (isCacheDefinitionIgnored()) {
			logger.info("--- ignoring defined cache definition"); //$NON-NLS-1$
		}
		for (IConfigurationElement providerElement : extension
				.getConfigurationElements()) {
			try {
				String dataProviderId = providerElement
						.getAttribute(REGISTRY_ID_ATTRIBUTE_ID);
				IDataProvider dataProvider = (IDataProvider) providerElement
						.createExecutableExtension(REGISTRY_ID_ATTRIBUTE_CLASS);
				logger
						.info("--- register provider type [" + dataProvider.getType() //$NON-NLS-1$
								+ "] with id [" + dataProviderId + "]"); //$NON-NLS-1$ //$NON-NLS-2$
				IConfigurationElement[] cachingStragtegyElements = providerElement
						.getChildren(REGISTRY_ID_ATTRIBUTE_CACHING_STRATEGY);
				// Evaluate whether caching is enabled
				if (!isCacheDefinitionIgnored()
						&& cachingStragtegyElements.length == 1) {
					// If so, instantiate the caching strategy...
					ICachingStrategy cachingStrategy = (ICachingStrategy) cachingStragtegyElements[0]
							.createExecutableExtension(REGISTRY_ID_ATTRIBUTE_CLASS);
					logger.info("--- - caching strategy found [" //$NON-NLS-1$
							+ cachingStrategy.getClass().getName() + "]"); //$NON-NLS-1$
					// ... set the host IDataProvider if requested ...
					if (cachingStrategy instanceof IDataProviderAware) {
						logger.info("--- - caching strategy is provider aware"); //$NON-NLS-1$
						IDataProviderAware dataProviderAware = (IDataProviderAware) cachingStrategy;
						dataProviderAware.setDataProvider(dataProvider);
					}
					// ... fetch the properties ...
					cachingStrategy
							.initialize(getCachingProperties(cachingStragtegyElements[0]));
					// ... and register an adapted dataprovider
					IDataProvider adapter = new DataProviderCacheAdapter(
							dataProvider, cachingStrategy);
					registeredProvidersByType.put(dataProvider.getType(),
							adapter);
					registeredProvidersById.put(dataProviderId, adapter);
					logger.info("--- - caching activated for [" //$NON-NLS-1$
							+ dataProvider.getType() + "]"); //$NON-NLS-1$
				}
				else {
					// If not, register the original dataprovider
					registeredProvidersByType.put(dataProvider.getType(),
							dataProvider);
					registeredProvidersById.put(dataProviderId, dataProvider);
				}
			}
			catch (CoreException exception) {
				logger.error(exception.getMessage(), exception);
			}
		}
	}

	/** {@inheritDoc} */
	public void removeExtension(IExtension extension, Object[] objects) {
		logger.info("DataProvider extension handling detected"); //$NON-NLS-1$
		for (IConfigurationElement providerElement : extension
				.getConfigurationElements()) {
			String dataProviderId = providerElement
					.getAttribute(REGISTRY_ID_ATTRIBUTE_ID);
			IDataProvider dataProvider = registeredProvidersById
					.get(dataProviderId);
			logger.info("--- remove provider type [" + dataProvider.getType() //$NON-NLS-1$
					+ "] with id [" + dataProviderId + "]"); //$NON-NLS-1$ //$NON-NLS-2$
			// unregister the dataprovider
			registeredProvidersById.remove(dataProviderId);
			registeredProvidersByType.remove(dataProvider.getType());
			// destroy caching stragegy if necessary
			if (dataProvider instanceof DataProviderCacheAdapter) {
				DataProviderCacheAdapter adapter = (DataProviderCacheAdapter) dataProvider;
				adapter.getCachingStrategy().destroy();
			}
		}
	}

	/**
	 * Initializes the <code>Regsitry</code> by loading the declared extensions.
	 * All <code>IDataProvider</code>s found are listed in the <code>Map</code>
	 * to allow a lookup later on.
	 */
	private void initialize() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = registry
				.getExtensionPoint(REGISTRY_ID_EXTENSION_POINT_ID);
		// activate dynamic tracking
		logger.info("Activate dynamic DataProvider extension tracking"); //$NON-NLS-1$
		IExtensionTracker tracker = PlatformUI.getWorkbench()
				.getExtensionTracker();
		tracker.registerHandler(this, ExtensionTracker
				.createExtensionPointFilter(getExtensionPointFilter()));
		logger.info("Initializing dataprovider registry"); //$NON-NLS-1$
		// initialize registry with currently active dataproviders
		for (IExtension providerExtension : extensionPoint.getExtensions()) {
			addExtension(PlatformUI.getWorkbench().getExtensionTracker(),
					providerExtension);
		}
		logger.info("Initializing dataprovider registry finished"); //$NON-NLS-1$
	}

	/**
	 * @param configurationElement
	 * @return
	 */
	private Set<ICachingProperty> getCachingProperties(
			IConfigurationElement cachingStragtegyElement) {
		Boolean eternalEntry = Boolean.parseBoolean(cachingStragtegyElement
				.getAttribute(REGISTRY_ID_ATTRIBUTE_ETERNAL));
		logger.info("--- - eternal [" + eternalEntry + "]"); //$NON-NLS-1$ //$NON-NLS-2$
		Long entryTimeOut = DEFAULT_TIMEOUT;
		try {
			entryTimeOut = Long.parseLong(cachingStragtegyElement
					.getAttribute(REGISTRY_ID_ATTRIBUTE_TIMEOUT));
			logger.info("--- - time out [" + entryTimeOut + "]"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		catch (Throwable throwable) {
			logger.info("--- - time out undefined; set to default [" //$NON-NLS-1$
					+ entryTimeOut + "]"); //$NON-NLS-1$
		}
		Long entryTimeToLive = DEFAULT_TIMETOLIVE;
		try {
			entryTimeToLive = Long.parseLong(cachingStragtegyElement
					.getAttribute(REGISTRY_ID_ATTRIBUTE_TIMETOLIVE));
			logger.info("--- - time to live [" + entryTimeToLive + "]"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		catch (Throwable throwable) {
			logger.info("--- - time to live undefined; set to default [" //$NON-NLS-1$
					+ entryTimeToLive + "]"); //$NON-NLS-1$
		}
		Set<ICachingProperty> cachingProperties = new HashSet<ICachingProperty>();
		cachingProperties.add(new CachingProperty(ICachingStrategy.ETERNAL_KEY,
				"eternal", eternalEntry.toString())); //$NON-NLS-1$
		cachingProperties.add(new CachingProperty(
				ICachingStrategy.TIME_OUT_KEY, "time out", entryTimeOut //$NON-NLS-1$
						.toString()));
		cachingProperties.add(new CachingProperty(
				ICachingStrategy.TIME_TO_LIVE_KEY, "time to live", //$NON-NLS-1$
				entryTimeToLive.toString()));
		IConfigurationElement[] cachingPropertyElements = cachingStragtegyElement
				.getChildren(REGISTRY_ID_ATTRIBUTE_PARAMETERS);
		for (IConfigurationElement cachingPropertyElement : cachingPropertyElements) {
			logger.info("--- - " //$NON-NLS-1$
					+ cachingPropertyElement.getAttribute("name") //$NON-NLS-1$
					+ " [" //$NON-NLS-1$
					+ cachingPropertyElement.getAttribute("value") //$NON-NLS-1$
					+ "]"); //$NON-NLS-1$
			cachingProperties.add(new CachingProperty(cachingPropertyElement
					.getAttribute("key"), cachingPropertyElement //$NON-NLS-1$
					.getAttribute("name"), cachingPropertyElement //$NON-NLS-1$
					.getAttribute("value"))); //$NON-NLS-1$
		}
		return cachingProperties;
	}

	/**
	 * @param providerType
	 *            The type of <code>IDataProvider</code> to look up
	 * @return The matching <code>IDataProvider</code>
	 * @throws UnknownDataProviderException
	 *             if the given <code>providerType</code> is not available.
	 *             Ckeck existence with {@link #isDataProviderAvailable(String)}
	 *             first.
	 */
	public IDataProvider lookupDataProvider(String providerType) {
		if (!registeredProvidersByType.containsKey(providerType)) {
			throw new UnknownDataProviderException(providerType);
		}
		return registeredProvidersByType.get(providerType);
	}

	/**
	 * @param providerType
	 *            The type of <code>IDataProvider</code> to look up
	 * @return Whether a matching <code>IDataProvider</code> exists
	 */
	public boolean isDataProviderAvailable(String providerType) {
		return registeredProvidersByType.containsKey(providerType);
	}
}
