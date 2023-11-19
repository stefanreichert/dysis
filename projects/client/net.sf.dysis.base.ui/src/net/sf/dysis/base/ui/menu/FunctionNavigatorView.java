package net.sf.dysis.base.ui.menu;

import java.util.ArrayList;
import java.util.List;

import net.sf.dysis.base.ui.internal.Activator;
import net.sf.dysis.base.ui.internal.Messages;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.nebula.widgets.pshelf.PShelf;
import org.eclipse.nebula.widgets.pshelf.PShelfItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import com.swtdesigner.ResourceManager;

public class FunctionNavigatorView extends ViewPart {

	public static final String ID = "net.sf.dysis.base.ui.menu.FunctionNavigatorView"; //$NON-NLS-1$

	private PShelf navigationShelf;

	/**
	 * Create contents of the view part
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		navigationShelf = new PShelf(parent, SWT.NONE);
		TableViewer tableViewerShelfItemPlaning = initPShelfItem(
				new PShelfItem(navigationShelf, SWT.NONE),
				Messages.getString("FunctionNavigatorView.group.projects.label")); //$NON-NLS-1$
		tableViewerShelfItemPlaning.setInput(getPlaningActions());

		TableViewer tableViewerShelfItemResource = initPShelfItem(
				new PShelfItem(navigationShelf, SWT.NONE), Messages.getString("FunctionNavigatorView.group.resource.label")); //$NON-NLS-1$
		tableViewerShelfItemResource.setInput(getResourceActions());
		//
		createActions();
		initializeToolBar();
		initializeMenu();
	}

	/**
	 * Initializes a {@link PShelfItem} with the given text.
	 * 
	 * @param shelfItem
	 *            The {@link PShelfItem} to initialize
	 * @param text
	 *            The text
	 * @return the content {@link TableViewer}
	 */
	private TableViewer initPShelfItem(PShelfItem shelfItem, String text) {
		shelfItem.setText(text);
		shelfItem.getBody().setLayout(new FillLayout());
		TableViewer tableViewerShelfItem = new TableViewer(shelfItem.getBody(),
				SWT.VIRTUAL);
		tableViewerShelfItem.setContentProvider(new ArrayContentProvider());
		tableViewerShelfItem.setLabelProvider(new ActionLabelProvider());
		tableViewerShelfItem.addDoubleClickListener(new ActionListener());
		return tableViewerShelfItem;
	}

	private List<IAction> getPlaningActions() {
		List<IAction> actionList = new ArrayList<IAction>();
		actionList.add(new OpenViewAction(Messages.getString("FunctionNavigatorView.element.projectnavigator.open"), //$NON-NLS-1$
				ResourceManager.getPluginImageDescriptor(
						Activator.getDefault(), "img/project_view.gif"), //$NON-NLS-1$
				"net.sf.dysis.planning.ui.view.ProjectNavigator")); //$NON-NLS-1$
		actionList.add(new OpenViewAction(Messages.getString("FunctionNavigatorView.element.projectsearch.open"), //$NON-NLS-1$
				ResourceManager.getPluginImageDescriptor(
						Activator.getDefault(), "img/search.gif"), //$NON-NLS-1$
				"net.sf.dysis.planning.ui.search.view.ProjectSearchView")); //$NON-NLS-1$
		actionList.add(new OpenViewAction(Messages.getString("FunctionNavigatorView.element.project.create"), //$NON-NLS-1$
				ResourceManager.getPluginImageDescriptor(
						Activator.getDefault(), "img/project.gif"), //$NON-NLS-1$
				"net.sf.dysis.planning.ui.view.ProjectNavigator")); //$NON-NLS-1$
		actionList.add(new OpenViewAction(Messages.getString("FunctionNavigatorView.element.activity.create"), //$NON-NLS-1$
				ResourceManager.getPluginImageDescriptor(
						Activator.getDefault(), "img/activity.gif"), //$NON-NLS-1$
				"net.sf.dysis.planning.ui.view.ProjectNavigator")); //$NON-NLS-1$
		return actionList;
	}

	private List<IAction> getResourceActions() {
		List<IAction> actionList = new ArrayList<IAction>();
		actionList.add(new OpenViewAction(Messages.getString("FunctionNavigatorView.element.resourcenavigator.open"), //$NON-NLS-1$
				ResourceManager.getPluginImageDescriptor(
						Activator.getDefault(), "img/resource_view.gif"), //$NON-NLS-1$
				"net.sf.dysis.resource.ui.view.ResourceNavigator")); //$NON-NLS-1$
		actionList.add(new OpenViewAction(Messages.getString("FunctionNavigatorView.element.resource.create"), //$NON-NLS-1$
				ResourceManager.getPluginImageDescriptor(
						Activator.getDefault(), "img/resource.gif"), //$NON-NLS-1$
				"net.sf.dysis.resource.ui.view.ResourceNavigator")); //$NON-NLS-1$
		return actionList;
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
		// IMenuManager menuManager = getViewSite().getActionBars()
		// .getMenuManager();
	}

	@Override
	public void setFocus() {
		navigationShelf.setFocus();
	}

	private class ActionLabelProvider extends LabelProvider {
		/** {@inheritDoc} */
		@Override
		public String getText(Object element) {
			IAction action = (IAction) element;
			return action.getText();
		}

		/** {@inheritDoc} */
		@Override
		public Image getImage(Object element) {
			IAction action = (IAction) element;
			return ResourceManager.getImage(action.getImageDescriptor());
		}
	}

	private class ActionListener implements IDoubleClickListener {
		/** {@inheritDoc} */
		public void doubleClick(DoubleClickEvent event) {
			if (!event.getSelection().isEmpty()) {
				IStructuredSelection selection = (IStructuredSelection) event
						.getSelection();
				IAction action = (IAction) selection.getFirstElement();
				action.run();
			}

		}
	}

	private class OpenViewAction extends Action {

		private String viewId;

		/**
		 * Constructor for <class>OpenViewAction</class>.
		 */
		public OpenViewAction(String text, ImageDescriptor imageDescriptor,
				String viewId) {
			super(text, imageDescriptor);
			this.viewId = viewId;
		}

		/** {@inheritDoc} */
		@Override
		public void run() {
			try {
				getSite().getPage().showView(viewId);
			}
			catch (PartInitException exception) {
				exception.printStackTrace();
			}
		}
	}
}
