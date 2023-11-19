/**
 * ProjectEditAction.java created on 02.03.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planning.ui.action;

import net.sf.dysis.base.ui.dataprovider.key.PrimaryKey;
import net.sf.dysis.base.ui.editor.IEditorProperties;
import net.sf.dysis.base.ui.editor.input.DataProviderEditorInput;
import net.sf.dysis.base.ui.editor.input.DataProviderInputDetails;
import net.sf.dysis.base.ui.logging.IPluginLogger;
import net.sf.dysis.base.ui.logging.PluginLogger;
import net.sf.dysis.base.ui.navigator.INavigatorView;
import net.sf.dysis.planing.core.dto.ProjectDTO;
import net.sf.dysis.planning.ui.Activator;
import net.sf.dysis.planning.ui.Messages;
import net.sf.dysis.planning.ui.dataprovider.ProjectDataProvider;
import net.sf.dysis.planning.ui.editor.ProjectEditor;
import net.sf.dysis.planning.ui.navigator.ProjectNavigatorElement;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;

import com.swtdesigner.ResourceManager;

/**
 * Action for editing a <code>Project</code>.
 * 
 * @author Stefan Reichert
 */
public class ProjectEditAction implements IObjectActionDelegate {

	/** This class' IPluginLogger instance. */
	private static IPluginLogger logger = PluginLogger.getLogger(Activator
			.getDefault());

	/** The target part. */
	private IWorkbenchPart targetPart;

	/** The <code>ProjectNavigatorElement</code> to edit. */
	private ProjectNavigatorElement selectedNavigatorElement;

	/** The <code>ProjectDTO</code> to edit. */
	private ProjectDTO selectedProjectDTO;

	/**
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
	}

	/**
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		if (selectedProjectDTO != null) {
			String name = new StringBuilder(Messages.getString("ProjectEditAction.editor.input.name.prefix")).append( //$NON-NLS-1$
					selectedProjectDTO.getName()).append(" (").append( //$NON-NLS-1$
					selectedProjectDTO.getDescription()).append(")").toString(); //$NON-NLS-1$
			String tooltip = new StringBuilder(Messages.getString("ProjectEditAction.editor.input.tooltip.prefix")).append(name) //$NON-NLS-1$
					.toString();
			ImageDescriptor imageDescriptor = ResourceManager
					.getPluginImageDescriptor(Activator.getDefault(),
							"img/project.gif"); //$NON-NLS-1$
			DataProviderInputDetails inputDetails = new DataProviderInputDetails(
					name, tooltip, imageDescriptor);
			DataProviderEditorInput<ProjectDTO> editorInput = new DataProviderEditorInput<ProjectDTO>(
					new PrimaryKey(selectedProjectDTO.getId()),
					ProjectDataProvider.TYPE, inputDetails);
			try {
				IEditorPart editorPart = targetPart.getSite().getPage()
						.openEditor(editorInput, ProjectEditor.ID);
				// refresh navigator on editor save
				editorPart.addPropertyListener(new IPropertyListener() {
					/** {@inheritDoc} */
					public void propertyChanged(Object source, int propId) {
						if (propId == IEditorProperties.PROP_EDITOR_SAVE) {
							if (targetPart instanceof INavigatorView) {
								INavigatorView navigatorView = (INavigatorView) targetPart;
								navigatorView.getNavigatorTreeViewer().refresh(
										selectedNavigatorElement, true);
							}
						}
					}
				});
			}
			catch (PartInitException partInitException) {
				logger.error(partInitException.getMessage(), partInitException);
			}
		}
	}

	/**
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		selectedNavigatorElement = null;
		selectedProjectDTO = null;
		action.setEnabled(false);
		if (!structuredSelection.isEmpty()) {
			Object selectedObject = structuredSelection.getFirstElement();
			if (selectedObject instanceof ProjectNavigatorElement) {
				selectedNavigatorElement = (ProjectNavigatorElement) selectedObject;
				selectedProjectDTO = (ProjectDTO) selectedNavigatorElement
						.getElement();
				action.setEnabled(true);
			}
			if (selectedObject instanceof ProjectDTO) {
				selectedProjectDTO = (ProjectDTO) selectedObject;
				action.setEnabled(true);
			}
		}
	}

}
