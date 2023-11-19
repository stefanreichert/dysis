/**
 * CachingProperty.java created on 15.12.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.internal.cache;

import net.sf.dysis.base.ui.dataprovider.cache.ICachingProperty;

/**
 * Implementation for the {@link ICachingProperty} interface
 * 
 * @author Stefan Reichert
 */
public class CachingProperty implements ICachingProperty {

	/** The value of the property. */
	private String value;

	/** The name of the property. */
	private String name;

	/** The key of the property. */
	private String key;

	/**
	 * Constructor for <class>CachingProperty</class>.
	 */
	public CachingProperty(String key, String name, String value) {
		super();
		this.key = key;
		this.name = name;
		this.value = value;
	}

	/** {@inheritDoc} */
	public String getKey() {
		return key;
	}

	/** {@inheritDoc} */
	public String getName() {
		return name;
	}

	/** {@inheritDoc} */
	public String getValue() {
		return value;
	}

}
