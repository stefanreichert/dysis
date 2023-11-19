/**
 * ICollectionKey.java created on 11.04.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.key;

/**
 * Interface used as a key for <code>IDataProvider</code>.
 * 
 * @author Stefan Reichert
 */
public interface ICollectionKey extends IKey {
	/**
	 * The static <code>ICollectionKey</code> for proving all available data.
	 */
	final static ICollectionKey ALL = new ICollectionKey() {
		// implementation for null keys

		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "all"; //$NON-NLS-1$
		}
	};
}
