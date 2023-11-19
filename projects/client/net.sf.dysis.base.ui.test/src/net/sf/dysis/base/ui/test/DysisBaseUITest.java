package net.sf.dysis.base.ui.test;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.sf.dysis.planning.ui.view.ProjectNavigator;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Tests für das UI.
 * 
 * @author Stefan Reichert
 */
public class DysisBaseUITest extends TestCase {

	/**
	 * Testet den Start der Applikation und schaut, ob nach dem Start der
	 * {@link ProjectNavigator} View sichtbar ist.
	 */
	public void testApplicationStartup() {
		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		IViewPart viewPart = workbenchWindow.getActivePage().findView(
				ProjectNavigator.ID);
		Assert.assertNotNull(viewPart);
	}
}
