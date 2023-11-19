/**
 * NavigatorViewRefreshAction.java created on 12.03.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.navigator.action;

import net.sf.dysis.base.ui.navigator.INavigatorView;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

/**
 * Refresh action for {@link INavigatorView}s.
 * 
 * @author Stefan Reichert
 */
public class NavigatorViewRefreshAction implements IViewActionDelegate {

	/** The hosting {@link INavigatorView}. */
	private INavigatorView navigatorView;

	/** {@inheritDoc} */
	public void init(IViewPart view) {
		if (view instanceof INavigatorView) {
			navigatorView = (INavigatorView) view;
		}
	}

	/** {@inheritDoc} */
	public void run(IAction action) {
		if (navigatorView != null) {
			navigatorView.getNavigatorTreeViewer().refresh();
		}
	}

	/** {@inheritDoc} */
	public void selectionChanged(IAction action, ISelection selection) {
		// intentionally left blank
	}

}
