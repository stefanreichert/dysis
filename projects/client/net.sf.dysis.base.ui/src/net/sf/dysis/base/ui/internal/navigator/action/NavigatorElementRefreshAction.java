/**
 * NavigatorElementRefreshAction.java created on 21.01.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.internal.navigator.action;

import net.sf.dysis.base.ui.navigator.INavigatorView;
import net.sf.dysis.base.ui.navigator.NavigatorElement;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Refresh action for {@link NavigatorElement}s.
 * 
 * @author Stefan Reichert
 */
public class NavigatorElementRefreshAction implements IObjectActionDelegate {

	/** The selected {@link NavigatorElement}. */
	private NavigatorElement selectedNavigatorElement;

	/** The hosting {@link INavigatorView}. */
	private INavigatorView navigatorView;

	/** {@inheritDoc} */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		if (targetPart instanceof INavigatorView) {
			navigatorView = (INavigatorView) targetPart;
		}
	}

	/** {@inheritDoc} */
	public void run(IAction action) {
		if (navigatorView != null && selectedNavigatorElement != null) {
			navigatorView.getNavigatorTreeViewer().refresh(
					selectedNavigatorElement, true);
		}
	}

	/** {@inheritDoc} */
	public void selectionChanged(IAction action, ISelection selection) {
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		selectedNavigatorElement = null;
		action.setEnabled(false);
		if (navigatorView != null && !structuredSelection.isEmpty()) {
			Object selectedObject = structuredSelection.getFirstElement();
			if (selectedObject instanceof NavigatorElement) {
				selectedNavigatorElement = (NavigatorElement) selectedObject;
				action.setEnabled(true);
			}
		}
	}

}
