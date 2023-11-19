/**
 * TimesheetOoenAction.java created on 16.02.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.timesheet.ui.action;

import java.util.List;

import net.sf.dysis.application.DysisApplication;
import net.sf.dysis.base.ui.dataprovider.key.ForeignKey;
import net.sf.dysis.base.ui.editor.input.DataProviderEditorInput;
import net.sf.dysis.base.ui.editor.input.DataProviderInputDetails;
import net.sf.dysis.planing.core.dto.ActivityTimeEntryDTO;
import net.sf.dysis.resource.core.dto.PersonDTO;
import net.sf.dysis.timesheet.ui.Messages;
import net.sf.dysis.timesheet.ui.dataprovider.ActivityTimeEntryDataProvider;
import net.sf.dysis.timesheet.ui.editor.Timesheet;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.WorkbenchException;

/**
 * Opens the {@link Timesheet}.
 * 
 * @author Stefan Reichert
 */
public class TimesheetOpenAction implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;

	/** {@inheritDoc} */
	public void dispose() {

	}

	/** {@inheritDoc} */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	/** {@inheritDoc} */
	public void run(IAction action) {
		try {
			window
					.getWorkbench()
					.showPerspective(
							"net.sf.dysis.timesheet.ui.perspective.TimesheetPerspective", //$NON-NLS-1$
							window);
			DataProviderInputDetails details = new DataProviderInputDetails(
					Messages.getString("TimesheetOpenAction.input.name"), Messages.getString("TimesheetOpenAction.input.tooltip"), //$NON-NLS-1$ //$NON-NLS-2$
					null);
			PersonDTO principal = DysisApplication.getDefault()
					.getAuthenticationToken().getPrincipal();
			window
					.getActivePage()
					.openEditor(
							new DataProviderEditorInput<List<ActivityTimeEntryDTO>>(
									new ForeignKey(
											principal.getId(),
											ActivityTimeEntryDataProvider.FOREIGN_KEY_PERSON),
									ActivityTimeEntryDataProvider.TYPE, details),
							"net.sf.dysis.timesheet.ui.editor.Timesheet"); //$NON-NLS-1$
		}
		catch (PartInitException partInitException) {
			partInitException.printStackTrace();
		}
		catch (WorkbenchException partInitException) {
			partInitException.printStackTrace();
		}
	}

	/** {@inheritDoc} */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
