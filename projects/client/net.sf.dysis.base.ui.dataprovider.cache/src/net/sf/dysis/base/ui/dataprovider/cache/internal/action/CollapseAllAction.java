/**
 * CollapseAllAction.java created on 15.12.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.cache.internal.action;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

/**
 * Collapses all elements in the viewer.
 * 
 * @author Stefan Reichert
 */
public class CollapseAllAction implements IViewActionDelegate {

	/** The parent view. */
	private IViewPart view;

	/** {@inheritDoc} */
	public void init(IViewPart view) {
		this.view = view;
	}

	/** {@inheritDoc} */
	public void run(IAction action) {
		TreeViewer viewer = (TreeViewer) view.getSite().getSelectionProvider();
		viewer.collapseAll();
	}

	/** {@inheritDoc} */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
