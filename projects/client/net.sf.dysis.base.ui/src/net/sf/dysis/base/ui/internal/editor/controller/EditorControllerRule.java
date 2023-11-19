/**
 * EditorControllerRule.java created on 26.04.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.internal.editor.controller;

import net.sf.dysis.base.ui.editor.controller.EditorSaveController;

import org.eclipse.core.runtime.jobs.ISchedulingRule;

/**
 * An {@link ISchedulingRule} for the {@link EditorSaveController}.
 * 
 * @author Stefan Reichert
 */
public class EditorControllerRule implements ISchedulingRule {

	/** The context {@link Class}. */
	private Class<?> contextClass;

	/**
	 * Constructor for <class>EditorControllerRule</class>.
	 */
	public EditorControllerRule(Class<?> contextClass) {
		this.contextClass = contextClass;
	}
	
	/** {@inheritDoc} */
	public boolean contains(ISchedulingRule rule) {
		if (rule instanceof EditorControllerRule) {
			EditorControllerRule otherRule = (EditorControllerRule) rule;
			return otherRule.getContextClass().equals(contextClass);
		}
		return false;
	}

	/** {@inheritDoc} */
	public boolean isConflicting(ISchedulingRule rule) {
		if (rule instanceof EditorControllerRule) {
			EditorControllerRule otherRule = (EditorControllerRule) rule;
			return otherRule.getContextClass().equals(contextClass);
		}
		return false;
	}

	/**
	 * @return the contextClass
	 */
	protected Class<?> getContextClass() {
		return contextClass;
	}

}
