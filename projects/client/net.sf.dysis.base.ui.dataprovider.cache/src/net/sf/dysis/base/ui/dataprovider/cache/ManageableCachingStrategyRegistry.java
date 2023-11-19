/**
 * ManageableCachingStrategyRegistry.java created on 13.12.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.cache;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.sf.dysis.base.ui.dataprovider.key.IKey;

/**
 * A registry for {@link IManageableCachingStrategy} objects.
 * {@link IManageableCachingStrategy} registered here will appear in the
 * corresponding console.
 * 
 * @author Stefan Reichert
 */
public final class ManageableCachingStrategyRegistry {

	/**
	 * The {@link Set} of {@link SimpleManageableCachingStrategy} instances.
	 */
	private static Set<IManageableCachingStrategy> manageableStrategies = new HashSet<IManageableCachingStrategy>();

	/**
	 * The {@link Set} of {@link IManageableCachingStrategyListener}s.
	 */
	private static Set<IManageableCachingStrategyListener> manageableStrategyListeners = new HashSet<IManageableCachingStrategyListener>();

	/** The delegate {@link IManageableCachingStrategyListener}. */
	private static IManageableCachingStrategyListener delegateStrategyListener = new IManageableCachingStrategyListener() {
		/** {@inheritDoc} */
		public void cacheAdded(final IManageableCachingStrategy cachingStrategy) {
			for (IManageableCachingStrategyListener strategyListener : manageableStrategyListeners) {
				strategyListener.cacheAdded(cachingStrategy);
			}
		}

		/** {@inheritDoc} */
		public void cacheChanged(
				final IManageableCachingStrategy cachingStrategy, IKey key) {
			for (IManageableCachingStrategyListener strategyListener : manageableStrategyListeners) {
				strategyListener.cacheChanged(cachingStrategy, key);
			}
		}

		/** {@inheritDoc} */
		public void cacheRemoved(IManageableCachingStrategy cachingStrategy) {
			for (IManageableCachingStrategyListener strategyListener : manageableStrategyListeners) {
				strategyListener.cacheRemoved(cachingStrategy);
			}
		}
	};

	/**
	 * @return the {@link Set} of {@link SimpleManageableCachingStrategy}
	 *         instances
	 */
	public static Set<IManageableCachingStrategy> getManageableStrategies() {
		return Collections.unmodifiableSet(manageableStrategies);
	}

	/**
	 * @param listener
	 *            The {@link IManageableCachingStrategy} listener to add
	 */
	public static void addManageableStrategyListener(
			IManageableCachingStrategyListener listener) {
		manageableStrategyListeners.add(listener);
	}

	/**
	 * @param listener
	 *            The {@link IManageableCachingStrategy} listener to add
	 */
	public static void removeManageableStrategyListener(
			IManageableCachingStrategyListener listener) {
		manageableStrategyListeners.remove(listener);
	}

	/**
	 * @param cachingStrategy
	 *            The {@link IManageableCachingStrategy} to register
	 */
	public static void registerManageableStrategy(
			IManageableCachingStrategy cachingStrategy) {
		manageableStrategies.add(cachingStrategy);
		cachingStrategy.addListener(delegateStrategyListener);
		// notify listener
		for (IManageableCachingStrategyListener strategyListener : manageableStrategyListeners) {
			strategyListener.cacheAdded(cachingStrategy);
		}
	}

	/**
	 * @param cachingStrategy
	 *            The {@link IManageableCachingStrategy} to deregister
	 */
	public static void deregisterManageableStrategy(
			IManageableCachingStrategy cachingStrategy) {
		manageableStrategies.remove(cachingStrategy);
		cachingStrategy.removeListener(delegateStrategyListener);
		// notify listener
		for (IManageableCachingStrategyListener strategyListener : manageableStrategyListeners) {
			strategyListener.cacheRemoved(cachingStrategy);
		}
	}
}
