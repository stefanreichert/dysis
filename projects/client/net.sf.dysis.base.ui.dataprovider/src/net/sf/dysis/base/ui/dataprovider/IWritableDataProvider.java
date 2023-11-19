/**
 * IWritableDataProvider.java created on 29.02.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider;

import net.sf.dysis.base.ui.dataprovider.key.IKey;

/**
 * Extension for <code>IDataProvider</code> supporting a write acces.
 * 
 * @author Stefan Reichert
 */
public interface IWritableDataProvider extends IDataProvider {

	/**
	 * Puts a piece of data back to the persistance layer.
	 * 
	 * @param key
	 *            The <code>IKey</code> of the data to persist
	 * @param data
	 *            The data to persist
	 * @return savedData the data in saved state
	 */
	Object putData(IKey key, Object data);

	/**
	 * Removes a piece of data from the persistance layer.
	 * 
	 * @param key
	 *            The <code>IKey</code> of the data to remove
	 */
	void removeData(IKey key);
}
