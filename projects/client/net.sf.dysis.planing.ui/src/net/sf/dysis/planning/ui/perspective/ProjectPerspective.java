package net.sf.dysis.planning.ui.perspective;

import net.sf.dysis.base.ui.menu.FunctionNavigatorView;
import net.sf.dysis.planning.ui.search.view.ProjectSearchView;
import net.sf.dysis.planning.ui.view.ProjectNavigator;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class ProjectPerspective implements IPerspectiveFactory {

	/**
	 * Creates the initial layout for a page.
	 */
	public void createInitialLayout(IPageLayout layout) {
		layout.addView(ProjectNavigator.ID, IPageLayout.LEFT, 0.4f, layout
				.getEditorArea());
		layout.addView(FunctionNavigatorView.ID, IPageLayout.BOTTOM, 0.8f,
				ProjectNavigator.ID);
		layout.getViewLayout(FunctionNavigatorView.ID).setCloseable(false);
		layout.getViewLayout(FunctionNavigatorView.ID).setMoveable(false);
		layout.addView(ProjectSearchView.ID, IPageLayout.BOTTOM, 0.7f, layout
				.getEditorArea());
	}

}
