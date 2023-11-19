/**
 * RegistryException.java created on 22.04.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.registry;

import net.sf.dysis.base.ui.dataprovider.IDataProvider;

/**
 * Exception that is thrown if something went wrong with the
 * {@link IDataProvider} {@link Registry}.
 * 
 * @author Stefan Reichert
 */
public class RegistryException extends RuntimeException {

	/** Default serial version UID. */
	private static final long serialVersionUID = 2509074437935880385L;

	/**
	 * Constructor for <class>RegistryException</class>.
	 */
	public RegistryException(String message) {
		super(message);
	}
}
