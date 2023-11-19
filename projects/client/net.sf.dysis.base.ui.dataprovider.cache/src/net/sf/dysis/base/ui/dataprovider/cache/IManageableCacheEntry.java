/**
 * IManageableCacheEntry.java created on 13.12.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.cache;

import net.sf.dysis.base.ui.dataprovider.cache.ICacheEntryInformation;
import net.sf.dysis.base.ui.dataprovider.key.IKey;

/**
 * An entry of an {@link IManageableCachingStrategy}.
 * 
 * @author Stefan Reichert
 */
public interface IManageableCacheEntry {

	/**
	 * @return the {@link ICacheEntryInformation} of this entry
	 */
	ICacheEntryInformation getCacheEntryInformation();

	/**
	 * @return the hosting {@link IManageableCachingStrategy}
	 */
	IManageableCachingStrategy getHostStrategy();

	/**
	 * @return the {@link IKey} of the entry
	 */
	IKey getKey();
}
