/**
 * ActivationAction.java created on 15.12.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.cache.internal.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;

/**
 * @author Stefan Reichert
 */
public class ActivationAction extends Action {

	/**
	 * Constructor for <class>ActivationAction</class>.
	 */
	public ActivationAction(String label) {
		super(label, IAction.AS_CHECK_BOX);
		setChecked(false);
	}

	/** {@inheritDoc} */
	@Override
	public void run() {
		// do nothing
	}

}
