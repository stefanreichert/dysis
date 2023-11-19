/**
 * ResourceNavigator created on 10.01.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.resource.ui.view;

import net.sf.dysis.base.ui.menu.StandardMenuManager;
import net.sf.dysis.base.ui.navigator.INavigatorView;
import net.sf.dysis.base.ui.navigator.NavigatorElement;
import net.sf.dysis.resource.ui.Messages;
import net.sf.dysis.resource.ui.provider.ResourceNavigatorContentProvider;
import net.sf.dysis.resource.ui.provider.ResourceNavigatorLabelProvider;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;

/**
 * Represents the navigator tree for all existing <code>Resources</code> and
 * child <code>Activities</code>.
 * 
 * @author Stefan Reichert
 */
public class ResourceNavigator extends ViewPart implements INavigatorView {

	private TreeViewer treeViewerResources;

	public static final String ID = "net.sf.dysis.resource.ui.view.ResourceNavigator"; //$NON-NLS-1$

	/**
	 * Create contents of the view part
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		container.setLayout(gridLayout);

		treeViewerResources = new TreeViewer(container, SWT.BORDER);
		treeViewerResources
				.setContentProvider(new ResourceNavigatorContentProvider(
						getSite()));
		treeViewerResources
				.setLabelProvider(new ResourceNavigatorLabelProvider());
		treeViewerResources.setInput(NavigatorElement.adapt(null, Messages.getString("ResourceNavigator.input.name"), //$NON-NLS-1$
				treeViewerResources));

		Tree tree = treeViewerResources.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		//
		initializeToolBar();
		initializeMenu();
	}

	/**
	 * Initialize the toolbar
	 */
	private void initializeToolBar() {
		// IToolBarManager toolbarManager = getViewSite().getActionBars()
		// .getToolBarManager();
	}

	/**
	 * Initialize the menu
	 */
	private void initializeMenu() {
		// Create and register the tree's context menu manager
		MenuManager contextMenuManager = new StandardMenuManager();
		getSite().registerContextMenu(contextMenuManager, treeViewerResources);
		// Create the actual menu and setup the widget
		Menu contextmenu = contextMenuManager
				.createContextMenu(treeViewerResources.getTree());
		treeViewerResources.getTree().setMenu(contextmenu);
		getSite().setSelectionProvider(treeViewerResources);

	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	/** {@inheritDoc} */
	public TreeViewer getNavigatorTreeViewer() {
		return treeViewerResources;
	}

}
