/**
 * ISearchCriteriaDialog.java created on 20.02.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.search;

/**
 * Represents a dialog for search criteria.
 * 
 * @author Stefan Reichert
 */
public interface ISearchCriteriaDialog {
	/**
	 * Sets the <code>Object</code> containing the entered criteria.
	 * 
	 * @param searchCriteria
	 *            The given searchCriteria
	 */
	void setSearchCriteria(Object searchCriteria);

	/**
	 * Returns an <code>Object</code> containing the entered criteria.
	 * 
	 * @return the entered searchCriteria
	 */
	Object getSearchCriteria();

	/**
	 * Opens this dialog, creating it first if it has not yet been created.
	 * <p>
	 * This dialog's return codes are dialog-specific, although two standard
	 * return codes are predefined:<br>
	 * <code>OK</code> and <code>CANCEL</code>.
	 * </p>
	 * 
	 * @return the return code
	 */
	int open();
}
