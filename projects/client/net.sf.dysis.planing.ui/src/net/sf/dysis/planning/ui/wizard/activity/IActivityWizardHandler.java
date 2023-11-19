/**
 * IActivityWizardHandler.java created on 23.04.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planning.ui.wizard.activity;

import net.sf.dysis.planing.core.dto.ProjectDTO;

import org.eclipse.jface.wizard.Wizard;

/**
 * Interface for a handler of a wizards <code>performFinish</code> method.
 * 
 * @author Stefan Reichert
 */
public interface IActivityWizardHandler {

	/**
	 * Gets invoked by the wizard to perform the finish method.
	 * 
	 * @param wizard
	 *            The invoking wizard
	 * @return whether finishing succeeded
	 */
	boolean performFinish(Wizard wizard);

	/**
	 * Returns the given parent <code>Project</code> or null if none exists.
	 * 
	 * @return the given parent <code>Project</code>
	 */
	ProjectDTO getActivityProject();
}
