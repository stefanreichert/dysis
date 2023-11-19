/**
 * ISearchExecutable.java created on 20.02.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.search;

import java.util.Collection;

/**
 * Interface for a search.
 * 
 * @author Stefan Reichert
 */
public interface ISearchExecutable {

	/**
	 * Proceeds a search witch given criteria.
	 * 
	 * @param searchCriteria
	 *            The criteria to use
	 * @return returns the result collection
	 */
	@SuppressWarnings("unchecked")
	Collection perform(Object searchCriteria);
}
