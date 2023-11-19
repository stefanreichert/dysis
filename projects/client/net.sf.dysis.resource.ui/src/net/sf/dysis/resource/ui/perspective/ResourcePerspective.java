package net.sf.dysis.resource.ui.perspective;

import net.sf.dysis.base.ui.menu.FunctionNavigatorView;
import net.sf.dysis.resource.ui.view.ResourceNavigator;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class ResourcePerspective implements IPerspectiveFactory {

	/**
	 * Creates the initial layout for a page.
	 */
	public void createInitialLayout(IPageLayout layout) {
		layout.addView(ResourceNavigator.ID, IPageLayout.LEFT, 0.4f, layout
				.getEditorArea());
		layout.addView(FunctionNavigatorView.ID, IPageLayout.BOTTOM, 0.8f,
				ResourceNavigator.ID);
		layout.getViewLayout(FunctionNavigatorView.ID).setCloseable(false);
		layout.getViewLayout(FunctionNavigatorView.ID).setMoveable(false);
	}

}
