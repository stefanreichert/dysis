/**
 * ForeignKey.java created on 23.04.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.key;

/**
 * A simple foreign key.
 * 
 * @author Stefan Reichert
 */
public class ForeignKey implements ICollectionKey, Comparable<ForeignKey> {
	/** The foreign key. */
	private Long foreignKey;

	/** The hosting entity. */
	private String hostingEntity;

	/**
	 * Constructor for <class>PrimaryKey</class>.
	 */
	public ForeignKey(Long foreignKey, String hostingEntity) {
		super();
		this.foreignKey = foreignKey;
		this.hostingEntity = hostingEntity;
	}

	/**
	 * @return the foreignKey
	 */
	public Long getForeignKey() {
		return foreignKey;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return foreignKey.intValue();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (object instanceof ForeignKey) {
			ForeignKey otherForeignKey = (ForeignKey) object;
			boolean sameKey = getForeignKey().equals(
					otherForeignKey.getForeignKey());
			boolean sameHostingEntity = getHostingEntity().equals(
					otherForeignKey.getHostingEntity());
			return sameKey && sameHostingEntity;
		}
		return false;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "foreign key in " + hostingEntity + " " + foreignKey.toString(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @return the hostingEntity
	 */
	public String getHostingEntity() {
		return hostingEntity;
	}

	/** {@inheritDoc} */
	public int compareTo(ForeignKey otherForeignKey) {
		return (int) (foreignKey - otherForeignKey.getForeignKey());
	}
}
