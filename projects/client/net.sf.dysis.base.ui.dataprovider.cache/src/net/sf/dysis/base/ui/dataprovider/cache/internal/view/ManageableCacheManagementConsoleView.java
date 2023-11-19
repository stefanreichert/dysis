/**
 * ManageableCacheManagementConsoleView.java created on 11.12.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.cache.internal.view;

import net.sf.dysis.base.ui.dataprovider.cache.IManageableCacheEntry;
import net.sf.dysis.base.ui.dataprovider.cache.IManageableCacheEntryStatistics;
import net.sf.dysis.base.ui.dataprovider.cache.IManageableCachingStrategy;
import net.sf.dysis.base.ui.dataprovider.cache.IManageableCachingStrategyListener;
import net.sf.dysis.base.ui.dataprovider.cache.ManageableCachingStrategyRegistry;
import net.sf.dysis.base.ui.dataprovider.cache.internal.Activator;
import net.sf.dysis.base.ui.dataprovider.cache.internal.Messages;
import net.sf.dysis.base.ui.dataprovider.cache.internal.action.ActivationAction;
import net.sf.dysis.base.ui.dataprovider.cache.internal.action.ExportCacheDataToCSVAction;
import net.sf.dysis.base.ui.dataprovider.cache.internal.view.provider.ManageableCacheEntryContentProvider;
import net.sf.dysis.base.ui.dataprovider.cache.internal.view.provider.ManageableCacheEntryLabelProvider;
import net.sf.dysis.base.ui.dataprovider.cache.internal.view.provider.ManageableCacheEntryContentProvider.Category;
import net.sf.dysis.base.ui.dataprovider.cache.internal.view.provider.ManageableCacheEntryContentProvider.ElementCategory;
import net.sf.dysis.base.ui.dataprovider.key.IKey;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;

import com.swtdesigner.ResourceManager;

public class ManageableCacheManagementConsoleView extends ViewPart {

	/** The i of this plugin. */
	public static final String ID = "net.sf.dysis.base.ui.dataprovider.cache.internal.view.ManageableCacheManagementConsoleView"; //$NON-NLS-1$

	/** Widget. */
	private TreeViewer treeViewerCacheInstances;

	/** The listener for cache changes. */
	private IManageableCachingStrategyListener manageableCachingStrategyListener;

	/** Hide 50 plus cache entries. */
	private ActivationAction hide50PlusEntriesAction;

	/** Hide 33 plus cache entries. */
	private ActivationAction hide33PlusEntriesAction;

	/** Hide 20 plus cache entries. */
	private ActivationAction hide20PlusEntriesAction;

	/** Hide 20 minus cache entries. */
	private ActivationAction hide20MinusEntriesAction;

	/** Hide other cache entries. */
	private ActivationAction hideOtherEntriesAction;

	/** Hide instance properties. */
	private ActivationAction hideInstancePropertiesAction;

	/** Hide instance properties. */
	private ActivationAction autoRefreshAction;

	/** The active filters available action. */
	private Action activeFiltersAvailableAction;

	/** The export cache dump action. */
	private ExportCacheDataToCSVAction exportCacheDumpAction;

	/** The viewer filter. */
	private ViewerFilter viewerFilter;

	/**
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout());
		treeViewerCacheInstances = new TreeViewer(container, SWT.FULL_SELECTION
				| SWT.BORDER | SWT.VIRTUAL);
		treeViewerCacheInstances
				.setLabelProvider(new ManageableCacheEntryLabelProvider());
		treeViewerCacheInstances
				.setContentProvider(new ManageableCacheEntryContentProvider());

		viewerFilter = new ViewerFilter() {
			/** {@inheritDoc} */
			@Override
			public boolean select(Viewer viewer, Object parentElement,
					Object element) {
				if (element instanceof IManageableCacheEntry) {
					IManageableCacheEntry cacheEntry = (IManageableCacheEntry) element;
					IManageableCacheEntryStatistics cacheEntryStatistics = cacheEntry
							.getHostStrategy().getCacheEntryStatistics(
									cacheEntry);
					if (cacheEntryStatistics.getAccessCount() == 0) {
						return !hideOtherEntriesAction.isChecked();
					}
					double requestCount = cacheEntryStatistics.getAccessCount();
					double cacheHits = cacheEntryStatistics.getCacheHits();
					double cacheHitRate = cacheHits / requestCount * 100;
					if (cacheHitRate >= 50) {
						return !hide50PlusEntriesAction.isChecked();
					}
					if (cacheHitRate >= 33.33) {
						return !hide33PlusEntriesAction.isChecked();
					}
					if (cacheHitRate >= 20) {
						return !hide20PlusEntriesAction.isChecked();
					}
					return !hide20MinusEntriesAction.isChecked();
				}
				else if (element instanceof ElementCategory) {
					ElementCategory elementCategory = (ElementCategory) element;
					return elementCategory.getCategory() == Category.ENTRIES
							|| !hideInstancePropertiesAction.isChecked();
				}
				return true;
			}
		};

		treeViewerCacheInstances.addFilter(viewerFilter);
		Tree tree = treeViewerCacheInstances.getTree();
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);

		final TreeColumn cacheHostnameColumn = new TreeColumn(tree, SWT.NONE);
		cacheHostnameColumn.setWidth(300);

		final TreeColumn initialCreationColumn = new TreeColumn(tree, SWT.NONE);
		initialCreationColumn.setWidth(125);
		initialCreationColumn
				.setText(Messages
						.getString("ManageableCacheManagementConsoleView.column.initial.entry.creation")); //$NON-NLS-1$

		final TreeColumn entryRequestColumn = new TreeColumn(tree, SWT.NONE);
		entryRequestColumn.setWidth(100);
		entryRequestColumn
				.setText(Messages
						.getString("ManageableCacheManagementConsoleView.column.access.count")); //$NON-NLS-1$

		final TreeColumn entryCacheHitColumn = new TreeColumn(tree, SWT.NONE);
		entryCacheHitColumn.setWidth(100);
		entryCacheHitColumn
				.setText(Messages
						.getString("ManageableCacheManagementConsoleView.column.cache.hit.count"));//$NON-NLS-1$

		final TreeColumn entryCacheHitRateColumn = new TreeColumn(tree,
				SWT.NONE);
		entryCacheHitRateColumn.setWidth(100);
		entryCacheHitRateColumn
				.setText(Messages
						.getString("ManageableCacheManagementConsoleView.column.cache.hit.rate")); //$NON-NLS-1$

		final TreeColumn entryCreationColumn = new TreeColumn(tree, SWT.NONE);
		entryCreationColumn.setWidth(125);
		entryCreationColumn
				.setText(Messages
						.getString("ManageableCacheManagementConsoleView.column.current.entry.creation")); //$NON-NLS-1$

		final TreeColumn entryLastAccessColumn = new TreeColumn(tree, SWT.NONE);
		entryLastAccessColumn.setWidth(125);
		entryLastAccessColumn
				.setText(Messages
						.getString("ManageableCacheManagementConsoleView.column.last.access")); //$NON-NLS-1$

		// register listener
		manageableCachingStrategyListener = new IManageableCachingStrategyListener() {
			/** {@inheritDoc} */
			public void cacheChanged(
					final IManageableCachingStrategy cachingStrategy, IKey key) {
				if (autoRefreshAction.isChecked()) {
					refreshViewer();
				}
			}

			/** {@inheritDoc} */
			public void cacheAdded(
					final IManageableCachingStrategy cachingStrategy) {
				if (autoRefreshAction.isChecked()) {
					reloadViewer();
				}
			}

			/** {@inheritDoc} */
			public void cacheRemoved(IManageableCachingStrategy cachingStrategy) {
				if (autoRefreshAction.isChecked()) {
					reloadViewer();
				}
			}

		};
		ManageableCachingStrategyRegistry
				.addManageableStrategyListener(manageableCachingStrategyListener);
		// remove listener on dispose
		container.addDisposeListener(new DisposeListener() {
			/** {@inheritDoc} */
			public void widgetDisposed(DisposeEvent event) {
				ManageableCachingStrategyRegistry
						.removeManageableStrategyListener(manageableCachingStrategyListener);
			}
		});

		// set content initially
		treeViewerCacheInstances.setInput(ManageableCachingStrategyRegistry
				.getManageableStrategies());
		getViewSite().setSelectionProvider(treeViewerCacheInstances);
		// label view
		labelView();
		// create actions
		createActions();
		// setup menus
		initializeToolBar();
		initializeMenu();
		initializeContextMenu();
	}

	/**
	 * Relabels the view.
	 */
	private void labelView() {
		setContentDescription(Messages
				.getString("ManageableCacheManagementConsoleView.view.content.description.prefix") //$NON-NLS-1$
				+ ManageableCachingStrategyRegistry.getManageableStrategies()
						.size()
				+ Messages
						.getString("ManageableCacheManagementConsoleView.view.content.description.suffix")); //$NON-NLS-1$
		firePropertyChange(PROP_TITLE);
	}

	/**
	 * Create actions.
	 */
	private void createActions() {
		// export action
		exportCacheDumpAction = new ExportCacheDataToCSVAction();
		// specific filter actions
		hideInstancePropertiesAction = new ActivationAction(
				Messages
						.getString("ManageableCacheManagementConsoleView.action.filter.properties")); //$NON-NLS-1$
		hide50PlusEntriesAction = new ActivationAction(
				Messages
						.getString("ManageableCacheManagementConsoleView.action.filter.well")); //$NON-NLS-1$
		hide33PlusEntriesAction = new ActivationAction(
				Messages
						.getString("ManageableCacheManagementConsoleView.action.filter.acceptable")); //$NON-NLS-1$
		hide20PlusEntriesAction = new ActivationAction(
				Messages
						.getString("ManageableCacheManagementConsoleView.action.filter.poor")); //$NON-NLS-1$
		hide20MinusEntriesAction = new ActivationAction(
				Messages
						.getString("ManageableCacheManagementConsoleView.action.filter.bad")); //$NON-NLS-1$
		hideOtherEntriesAction = new ActivationAction(
				Messages
						.getString("ManageableCacheManagementConsoleView.action.filter.not.yet.rated")); //$NON-NLS-1$
		// auto refresh action
		autoRefreshAction = new ActivationAction(
				Messages
						.getString("ManageableCacheManagementConsoleView.action.automatic.refresh")); //$NON-NLS-1$
		autoRefreshAction.setImageDescriptor(ResourceManager
				.getPluginImageDescriptor(Activator.getDefault(),
						"/img/cacheConsole_autoRefresh.gif")); //$NON-NLS-1$
		autoRefreshAction.setChecked(true);
		// general filter action
		activeFiltersAvailableAction = new Action("", Action.AS_CHECK_BOX) { //$NON-NLS-1$
			@Override
			public void run() {
				hideInstancePropertiesAction.setChecked(false);
				hide50PlusEntriesAction.setChecked(false);
				hide33PlusEntriesAction.setChecked(false);
				hide20PlusEntriesAction.setChecked(false);
				hide20MinusEntriesAction.setChecked(false);
				hideOtherEntriesAction.setChecked(false);
				setChecked(false);
				setEnabled(false);
				getViewSite().getActionBars().updateActionBars();
				treeViewerCacheInstances.refresh();
			}
		};
		activeFiltersAvailableAction.setImageDescriptor(ResourceManager
				.getPluginImageDescriptor(Activator.getDefault(),
						"img/cacheConsole_filterActive.gif")); //$NON-NLS-1$
		activeFiltersAvailableAction.setDisabledImageDescriptor(ResourceManager
				.getPluginImageDescriptor(Activator.getDefault(),
						"img/cacheConsole_filterInactive.gif")); //$NON-NLS-1$
		activeFiltersAvailableAction.setText(Messages
				.getString("ManageableCacheManagementConsoleView.18")); //$NON-NLS-1$
		activeFiltersAvailableAction.setChecked(false);
		activeFiltersAvailableAction.setEnabled(false);
		// register listnere for update
		IPropertyChangeListener propertyChangeListener = new IPropertyChangeListener() {
			/** {@inheritDoc} */
			public void propertyChange(PropertyChangeEvent event) {
				if (hideInstancePropertiesAction.isChecked()
						|| hide50PlusEntriesAction.isChecked()
						|| hide33PlusEntriesAction.isChecked()
						|| hide20PlusEntriesAction.isChecked()
						|| hide20MinusEntriesAction.isChecked()
						|| hideOtherEntriesAction.isChecked()) {
					activeFiltersAvailableAction.setChecked(true);
					activeFiltersAvailableAction.setEnabled(true);
				}
				else {
					activeFiltersAvailableAction.setChecked(false);
					activeFiltersAvailableAction.setEnabled(false);
				}
				getViewSite().getActionBars().updateActionBars();
				treeViewerCacheInstances.refresh();
			}
		};

		hideInstancePropertiesAction
				.addPropertyChangeListener(propertyChangeListener);
		hide50PlusEntriesAction
				.addPropertyChangeListener(propertyChangeListener);
		hide33PlusEntriesAction
				.addPropertyChangeListener(propertyChangeListener);
		hide20PlusEntriesAction
				.addPropertyChangeListener(propertyChangeListener);
		hide20MinusEntriesAction
				.addPropertyChangeListener(propertyChangeListener);
		hideOtherEntriesAction
				.addPropertyChangeListener(propertyChangeListener);
	}

	/**
	 * Initialize the toolbar
	 */
	private void initializeContextMenu() {
		MenuManager menuManager = new MenuManager();
		menuManager
				.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		getViewSite()
				.registerContextMenu(menuManager, treeViewerCacheInstances);
		Menu contextMenu = menuManager
				.createContextMenu(treeViewerCacheInstances.getTree());
		treeViewerCacheInstances.getTree().setMenu(contextMenu);
	}

	/**
	 * Initialize the toolbar
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
		toolbarManager
				.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		toolbarManager.add(new Separator("dysis.cache.actions")); //$NON-NLS-1$
		toolbarManager.add(activeFiltersAvailableAction);
	}

	/**
	 * Initialize the menu
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
		// add to menu bar
		menuManager.add(new Separator("dysis.cache.filter.instance.actions")); //$NON-NLS-1$
		menuManager.add(hideInstancePropertiesAction);
		menuManager.add(new Separator("dysis.cache.filter.entry.actions")); //$NON-NLS-1$
		menuManager.add(hide50PlusEntriesAction);
		menuManager.add(hide33PlusEntriesAction);
		menuManager.add(hide20PlusEntriesAction);
		menuManager.add(hide20MinusEntriesAction);
		menuManager.add(hideOtherEntriesAction);
		menuManager.add(new Separator("dysis.cache.filter.general.actions")); //$NON-NLS-1$
		menuManager.add(activeFiltersAvailableAction);
		menuManager.add(autoRefreshAction);
		menuManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		menuManager.add(exportCacheDumpAction);
		getViewSite().getActionBars().updateActionBars();

	}

	/** {@inheritDoc} */
	@Override
	public void setFocus() {
		treeViewerCacheInstances.getTree().setFocus();
	}

	/**
	 * Proceeds an async refresh of the viewer's content.
	 */
	private void refreshViewer() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				TreePath[] expandedPaths = treeViewerCacheInstances
						.getExpandedTreePaths();
				ISelection selection = treeViewerCacheInstances.getSelection();
				treeViewerCacheInstances.refresh();
				treeViewerCacheInstances.setExpandedTreePaths(expandedPaths);
				treeViewerCacheInstances.setSelection(selection);
			}
		});
	}

	/**
	 * Proceeds an async reload of the viewer's content.
	 */
	private void reloadViewer() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				TreePath[] expandedPaths = treeViewerCacheInstances
						.getExpandedTreePaths();
				ISelection selection = treeViewerCacheInstances.getSelection();
				treeViewerCacheInstances
						.setInput(ManageableCachingStrategyRegistry
								.getManageableStrategies());
				treeViewerCacheInstances.setExpandedTreePaths(expandedPaths);
				treeViewerCacheInstances.setSelection(selection);
				labelView();
			}
		});
	}
}
