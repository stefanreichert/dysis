/**
 * PeoplePeterDataProvider.java created on 07.03.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.test.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.dysis.base.ui.dataprovider.IDataProvider;
import net.sf.dysis.base.ui.dataprovider.key.ICollectionKey;
import net.sf.dysis.base.ui.dataprovider.key.IKey;
import net.sf.dysis.base.ui.dataprovider.key.PrimaryKey;

/**
 * A <code>IDataProvider</code> for testing reasons providing
 * <code>PersonVO</code>s.
 * 
 * @author Stefan Reichert
 */
public class PeoplePeterDataProvider implements IDataProvider {

	/** The group this provider belongs to. */
	public static final String DATA_PROVIDER_GROUP = "test.person";

	/** The type of this provider. */
	public static final String DATA_PROVIDER_TYPE = "test.person.peter";

	/**
	 * @see net.sf.dysis.base.ui.dataprovider.IDataProvider#getData(net.sf.dysis.base.ui.dataprovider.key.IKey)
	 */
	public Object getData(IKey key) {
		assert key instanceof PrimaryKey;
		PrimaryKey primaryKey = (PrimaryKey) key;
		switch (primaryKey.getPrimaryKey().intValue()) {
			case 3:
				return new PersonVO(3l, "P3", "Peter Friese");
			case 4:
				return new PersonVO(4l, "P4", "Anne Friese");
			default:
				return null;
		}
	}

	/**
	 * @see net.sf.dysis.base.ui.dataprovider.IDataProvider#getProviderGroup()
	 */
	public String getProviderGroup() {
		return DATA_PROVIDER_GROUP;
	}

	/**
	 * @see net.sf.dysis.base.ui.dataprovider.IDataProvider#getType()
	 */
	public String getType() {
		return DATA_PROVIDER_TYPE;
	}

	/**
	 * @see net.sf.dysis.base.ui.dataprovider.IDataProvider#getDataCollection(net.sf.dysis.base.ui.dataprovider.key.ICollectionKey)
	 */
	@SuppressWarnings("unchecked")
	public Collection getDataCollection(ICollectionKey collectionKey) {
		List<PersonVO> people = new ArrayList<PersonVO>();
		people.add(new PersonVO(3l, "P3", "Peter Friese"));
		people.add(new PersonVO(4l, "P4", "Anne Friese"));
		return people;
	}

	/**
	 * @see net.sf.dysis.base.ui.dataprovider.IDataProvider#getKey(java.lang.Object)
	 */
	public IKey getKey(Object data) {
		return new PrimaryKey(((PersonVO) data).getKey());
	}

}
