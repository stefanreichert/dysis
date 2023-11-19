/**
 * UnknownDataProviderException.java created on 22.04.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.registry;

/**
 * Exception that is thrown if a requested <code>IDSataProvider</code> is not available.
 *
 * @author Stefan Reichert
 *
 */
public class UnknownDataProviderException extends RuntimeException {

	/** Default serial version UID. */
	private static final long serialVersionUID = 2509074437935880385L;

	/**
	 * Constructor for <class>UnknownDataProviderException</class>.
	 */
	public UnknownDataProviderException(String providerType) {
		super("Provider <" + providerType + "> is unknown to the registry."); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
