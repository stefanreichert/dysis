package net.sf.dysis.base.ui.dataprovider.cache.internal.handler;

import net.sf.dysis.base.ui.dataprovider.cache.internal.view.ManageableCacheManagementConsoleView;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Handler that open the {@link ManageableCacheManagementConsoleView}.
 */
public class ManageableCacheManagementConsoleHandler extends AbstractHandler {

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		try {
			window.getActivePage().showView(
					ManageableCacheManagementConsoleView.ID);
		}
		catch (PartInitException ePartInitException) {
			ePartInitException.printStackTrace();
		}
		return null;
	}
}
