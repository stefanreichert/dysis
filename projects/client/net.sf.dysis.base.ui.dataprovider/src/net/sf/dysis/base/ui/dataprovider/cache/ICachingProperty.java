/**
 * ICachingProperty.java created on 15.12.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.cache;

/**
 * Property for a cache.
 *
 * @author Stefan Reichert
 *
 */
public interface ICachingProperty {

	/**
	 * @return the name of the property
	 */
	String getName();
	
	/**
	 * @return the key of the property
	 */
	String getKey();
	
	/**
	 * @return the value of the property
	 */
	String getValue();
}
