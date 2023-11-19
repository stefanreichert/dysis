package net.sf.dysis.application.internal;

import java.text.DateFormat;
import java.util.Date;

import net.sf.dysis.application.DysisApplication;
import net.sf.dysis.base.ui.menu.StandardMenuManager;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.progress.WorkbenchJob;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IWorkbenchAction quitAction;

	private IWorkbenchAction aboutAction;

	private IWorkbenchAction newAction;

	private IWorkbenchAction lockToolBarAction;

	private IWorkbenchAction editActionSetAction;

	private IWorkbenchAction newWizardDropDownAction;

	private IWorkbenchAction saveAction;

	private IWorkbenchAction showHelpAction;

	private IWorkbenchAction searchHelpAction;

	private IWorkbenchAction dynamicHelpAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	/**
	 * @see org.eclipse.ui.application.ActionBarAdvisor#makeActions(org.eclipse.ui
	 *      .IWorkbenchWindow)
	 */
	protected void makeActions(IWorkbenchWindow window) {
		register(ActionFactory.SAVE.create(window));
		newAction = ActionFactory.NEW.create(window);
		newAction.setText(Messages
				.getString("ApplicationActionBarAdvisor.action.new.label")); //$NON-NLS-1$
		register(newAction);
		quitAction = ActionFactory.QUIT.create(window);
		quitAction.setText(Messages
				.getString("ApplicationActionBarAdvisor.action.exit.label")); //$NON-NLS-1$
		register(quitAction);
		aboutAction = ActionFactory.ABOUT.create(window);
		aboutAction.setText(Messages
				.getString("ApplicationActionBarAdvisor.action.about.label")); //$NON-NLS-1$
		register(aboutAction);

		editActionSetAction = ActionFactory.EDIT_ACTION_SETS.create(window);
		register(editActionSetAction);
		lockToolBarAction = ActionFactory.LOCK_TOOL_BAR.create(window);
		register(lockToolBarAction);

		newWizardDropDownAction = ActionFactory.NEW_WIZARD_DROP_DOWN
				.create(window);
		register(newWizardDropDownAction);
		saveAction = ActionFactory.SAVE.create(window);
		register(saveAction);

		showHelpAction = ActionFactory.HELP_CONTENTS.create(window);
		register(showHelpAction);
		searchHelpAction = ActionFactory.HELP_SEARCH.create(window);
		register(searchHelpAction);
		dynamicHelpAction = ActionFactory.DYNAMIC_HELP.create(window);
		register(dynamicHelpAction);

	}

	/**
	 * @see org.eclipse.ui.application.ActionBarAdvisor#fillMenuBar(org.eclipse.
	 *      jface.action.IMenuManager)
	 */
	protected void fillMenuBar(IMenuManager menuBar) {
		// Add file menu
		MenuManager menuManagerFile = new StandardMenuManager(Messages
				.getString("ApplicationActionBarAdvisor.menu.file.label"), //$NON-NLS-1$
				IWorkbenchActionConstants.M_FILE);
		menuManagerFile.insertBefore(StandardMenuManager.GROUP_CREATE,
				newAction);
		menuManagerFile.add(quitAction);
		menuBar.add(menuManagerFile);
		// Add help menu
		MenuManager menuManagerHelp = new StandardMenuManager(Messages
				.getString("ApplicationActionBarAdvisor.menu.help.label"), //$NON-NLS-1$
				IWorkbenchActionConstants.M_HELP);
		menuManagerHelp.appendToGroup(StandardMenuManager.GROUP_HELP,
				showHelpAction);
		menuManagerHelp.appendToGroup(StandardMenuManager.GROUP_HELP,
				searchHelpAction);
		menuManagerHelp.appendToGroup(StandardMenuManager.GROUP_HELP,
				dynamicHelpAction);
		menuManagerHelp.appendToGroup(StandardMenuManager.GROUP_STATE,
				aboutAction);
		menuBar.add(menuManagerHelp);

	}

	/**
	 * @see org.eclipse.ui.application.ActionBarAdvisor#fillCoolBar(org.eclipse.
	 *      jface.action.ICoolBarManager)
	 */
	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {
		// Set up the context Menu
		coolBar.add(new GroupMarker(IWorkbenchActionConstants.M_FILE));

		// File Group
		IToolBarManager fileToolBar = new ToolBarManager(SWT.FLAT);
		fileToolBar.add(new Separator(IWorkbenchActionConstants.NEW_GROUP));
		fileToolBar.add(newWizardDropDownAction);
		fileToolBar.add(new GroupMarker(IWorkbenchActionConstants.SAVE_GROUP));
		fileToolBar.add(saveAction);
		fileToolBar.add(new GroupMarker(IWorkbenchActionConstants.PRINT_EXT));
		fileToolBar.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

		// Add to the cool bar manager
		coolBar.add(fileToolBar);

		coolBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));

	}

	/**
	 * @see org.eclipse.ui.application.ActionBarAdvisor#fillStatusLine(org.eclipse
	 *      .jface.action.IStatusLineManager)
	 */
	protected void fillStatusLine(IStatusLineManager statusLine) {
		// user name and id
		StatusLineContributionItem statusLineContributionItemUser = new StatusLineContributionItem(
				"USER", 50); //$NON-NLS-1$
		statusLineContributionItemUser.setText(Messages
				.getString("ApplicationActionBarAdvisor.status.user.label") //$NON-NLS-1$
				+ DysisApplication.getDefault().getAuthenticationToken()
						.getPrincipal().getUserId());
		statusLine.add(statusLineContributionItemUser);

		// timestamp
		final StatusLineContributionItem statusLineContributionItemTimeStamp = new StatusLineContributionItem(
				"TIMESTAMP", 30); //$NON-NLS-1$
		statusLineContributionItemTimeStamp.setText("TIMESTAMP"); //$NON-NLS-1$
		statusLine.add(statusLineContributionItemTimeStamp);
		Job timestampJob = new WorkbenchJob("Timestamp") { //$NON-NLS-1$
			public IStatus runInUIThread(IProgressMonitor monitor) {
				try {
					Date now = new Date();
					String formattedTime = DateFormat.getDateTimeInstance(
							DateFormat.MEDIUM, DateFormat.SHORT).format(now);
					statusLineContributionItemTimeStamp
							.setText(Messages
									.getString("ApplicationActionBarAdvisor.status.time.label") //$NON-NLS-1$
									+ formattedTime);
				}
				catch (RuntimeException exception) {
					exception.printStackTrace();
				}
				schedule(3000);
				return Status.OK_STATUS;
			}
		};
		timestampJob.setSystem(true);
		timestampJob.schedule();
		statusLine.setMessage("Dysis TimeTracking"); //$NON-NLS-1$
	}
}
