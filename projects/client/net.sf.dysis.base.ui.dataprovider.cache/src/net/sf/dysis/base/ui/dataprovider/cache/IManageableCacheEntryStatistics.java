/**
 * IManageableCacheEntryStatistics.java created on 15.12.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.cache;

import java.util.Date;

/**
 * Statistic information for an {@link IManageableCacheEntry}.
 *
 * @author Stefan Reichert
 *
 */
public interface IManageableCacheEntryStatistics {
	
	/**
	 * @return the count of accesses for the {@link IManageableCacheEntry}
	 */
	int getAccessCount();

	/**
	 * @return the number of hits for the {@link IManageableCacheEntry}
	 */
	int getCacheHits();
	
	/**
	 * @return the initial creation {@link Date}
	 */
	Date getInitialCreation();
	
}
