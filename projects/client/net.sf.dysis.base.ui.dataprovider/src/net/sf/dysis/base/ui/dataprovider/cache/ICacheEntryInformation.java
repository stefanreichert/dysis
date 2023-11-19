/**
 * ICacheEntryInformation.java created on 01.03.2008
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
 * Cache information for cahced data.
 * 
 * @author Stefan Reichert
 */
public interface ICacheEntryInformation {

	/**
	 * @return the creation <code>Date</code>
	 */
	Date getCreation();

	/**
	 * @return the last access <code>Date</code>
	 */
	Date getLastAccess();

}
