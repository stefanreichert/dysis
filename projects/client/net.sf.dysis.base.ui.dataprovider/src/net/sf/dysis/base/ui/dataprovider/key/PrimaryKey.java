/**
 * PrimaryKey.java created on 29.02.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.key;

/**
 * A simple primary key.
 * 
 * @author Stefan Reichert
 */
public class PrimaryKey implements IKey, Comparable<PrimaryKey> {

	/** The primary key. * */
	private Long primaryKey;

	/**
	 * Constructor for <class>PrimaryKey</class>.
	 */
	public PrimaryKey(Long primaryKey) {
		super();
		this.primaryKey = primaryKey;
	}

	/**
	 * @return the primaryKey
	 */
	public Long getPrimaryKey() {
		return primaryKey;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return primaryKey.intValue();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (object instanceof PrimaryKey) {
			PrimaryKey otherPrimaryKey = (PrimaryKey) object;
			return getPrimaryKey().equals(otherPrimaryKey.getPrimaryKey());
		}
		return false;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return primaryKey.toString();
	}

	/** {@inheritDoc} */
	public int compareTo(PrimaryKey otherPrimaryKey) {
		return (int) (primaryKey - otherPrimaryKey.getPrimaryKey());
	}
}
