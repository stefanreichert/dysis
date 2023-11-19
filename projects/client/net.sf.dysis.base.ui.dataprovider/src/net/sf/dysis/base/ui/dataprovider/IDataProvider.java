/**
 * IDataProvider.java created on 29.02.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider;

import java.util.Collection;

import net.sf.dysis.base.ui.dataprovider.key.ICollectionKey;
import net.sf.dysis.base.ui.dataprovider.key.IKey;

/**
 * Interface for a provider of a specific kind of data.
 * 
 * @author Stefan Reichert
 */
public interface IDataProvider {

	/**
	 * @return the type of this <code>IDataProvider</code>
	 */
	String getType();

	/**
	 * @return the group this <code>IDataProvider</code> belongs to.
	 */
	String getProviderGroup();

	/**
	 * @return the data with the given <code>IKey</code>
	 */
	Object getData(IKey key);

	/**
	 * @return the <code>Collection</code> of data with the given
	 *         <code>ICollectionKey</code>
	 */
	@SuppressWarnings("unchecked")
	Collection getDataCollection(ICollectionKey collectionKey);

	/**
	 * @return the <code>IKey</code> for a given piece of data
	 */
	IKey getKey(Object data);

}
