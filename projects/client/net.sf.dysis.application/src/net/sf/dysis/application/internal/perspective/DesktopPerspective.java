package net.sf.dysis.application.internal.perspective;

import net.sf.dysis.base.ui.menu.FunctionNavigatorView;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class DesktopPerspective implements IPerspectiveFactory {

	public static final String ID = "net.sf.dysis.application.internal.perspective.DesktopPerspective"; //$NON-NLS-1$

	/**
	 * Creates the initial layout for a page.
	 */
	public void createInitialLayout(IPageLayout layout) {
		IFolderLayout folderLayout = layout.createFolder("navigator.folder", //$NON-NLS-1$
				IPageLayout.LEFT, 0.3f, layout.getEditorArea());
		folderLayout.addView("net.sf.dysis.planning.ui.view.ProjectNavigator"); //$NON-NLS-1$
		folderLayout.addView("net.sf.dysis.resource.ui.view.ResourceNavigator"); //$NON-NLS-1$
		layout.getViewLayout("navigator.folder").setMoveable(false); //$NON-NLS-1$
		layout.addView(FunctionNavigatorView.ID, IPageLayout.BOTTOM, 0.7f,
				"navigator.folder"); //$NON-NLS-1$
		layout.getViewLayout(FunctionNavigatorView.ID).setCloseable(false);
		layout.getViewLayout(FunctionNavigatorView.ID).setMoveable(false);
		layout.addView("net.sf.dysis.planning.ui.search.view.ProjectSearchView", //$NON-NLS-1$
				IPageLayout.BOTTOM, 0.7f, layout.getEditorArea());
	}

}
