/**
 * ResultListColumn.java created on 20.02.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.search.internal.registry;

/**
 * Represents a result list column.
 * 
 * @author Stefan Reichert
 */
public class ResultListColumn {

	/** The name of the result list column. */
	private String name;

	/** The width of the result list column. */
	private int width;

	/**
	 * Constructor for <class>ResultListColumn</class>.
	 */
	public ResultListColumn(String name, int width) {
		super();
		this.name = name;
		this.width = width;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
