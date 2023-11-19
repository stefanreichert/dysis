/**
 * ProjectNavigator created on 10.01.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planning.ui.view;

import net.sf.dysis.base.ui.menu.StandardMenuManager;
import net.sf.dysis.base.ui.navigator.INavigatorView;
import net.sf.dysis.base.ui.navigator.NavigatorElement;
import net.sf.dysis.planning.ui.Messages;
import net.sf.dysis.planning.ui.provider.ProjectNavigatorContentProvider;
import net.sf.dysis.planning.ui.provider.ProjectNavigatorLabelProvider;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

/**
 * Represents the navigator tree for all existing <code>Projects</code> and
 * child <code>Activities</code>.
 * 
 * @author Stefan Reichert
 */
public class ProjectNavigator extends ViewPart implements INavigatorView {

	private TreeViewer treeViewerProjects;

	public static final String ID = "net.sf.dysis.planning.ui.view.ProjectNavigator"; //$NON-NLS-1$

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

		treeViewerProjects = new TreeViewer(container, SWT.BORDER);
		treeViewerProjects
				.setContentProvider(new ProjectNavigatorContentProvider(
						getSite()));
		treeViewerProjects
				.setLabelProvider(new ProjectNavigatorLabelProvider());
		treeViewerProjects.setInput(NavigatorElement.adapt(null,
				Messages.getString("ProjectNavigator.input.name"), treeViewerProjects)); //$NON-NLS-1$

		Tree tree = treeViewerProjects.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		//
		createActions();
		initializeToolBar();
		initializeMenu();
		initializeHelp();
	}

	private void initializeHelp() {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(
				treeViewerProjects.getTree(),
				"net.sf.dysis.resource.ui.ProjectNavigator"); //$NON-NLS-1$
	}

	/**
	 * Create the actions
	 */
	private void createActions() {
		// Create the actions
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
		getSite().registerContextMenu(contextMenuManager, treeViewerProjects);
		// Create the actual menu and setup the widget
		Menu contextmenu = contextMenuManager
				.createContextMenu(treeViewerProjects.getTree());
		treeViewerProjects.getTree().setMenu(contextmenu);
		getSite().setSelectionProvider(treeViewerProjects);

	}

	@Override
	public void setFocus() {
		// Set the focus
		treeViewerProjects.getTree().setFocus();
	}

	/** {@inheritDoc} */
	public TreeViewer getNavigatorTreeViewer() {
		return treeViewerProjects;
	}

}
