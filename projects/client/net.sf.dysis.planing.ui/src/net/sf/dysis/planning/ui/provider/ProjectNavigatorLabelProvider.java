/**
 * ProjectNavigatorLabelProvider.java created on 10.01.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planning.ui.provider;

import net.sf.dysis.base.ui.navigator.NavigatorElement;
import net.sf.dysis.planing.core.dto.ActivityDTO;
import net.sf.dysis.planing.core.dto.ProjectDTO;
import net.sf.dysis.planning.ui.Activator;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.swtdesigner.ResourceManager;

/**
 * Represents the <code>LabelProvider</code> for the
 * <code>ProjectNavigatorView</code>
 * 
 * @author Stefan Reichert
 */
public class ProjectNavigatorLabelProvider extends LabelProvider {

	/**
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof NavigatorElement) {
			NavigatorElement navigatorElement = (NavigatorElement) element;
			if (navigatorElement.getElement() instanceof ProjectDTO) {
				ProjectDTO project = (ProjectDTO) navigatorElement.getElement();
				return new StringBuilder(project.getName()).append(" (") //$NON-NLS-1$
						.append(project.getDescription()).append(")") //$NON-NLS-1$
						.toString();
			}
			else if (navigatorElement.getElement() instanceof ActivityDTO) {
				ActivityDTO activity = (ActivityDTO) navigatorElement
						.getElement();
				return new StringBuilder(activity.getName()).append(" (") //$NON-NLS-1$
						.append(activity.getDescription()).append(")") //$NON-NLS-1$
						.toString();
			}
			else {
				return super.getText(navigatorElement.getElement());
			}
		}
		return super.getText(element);
	}

	/**
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof NavigatorElement) {
			NavigatorElement navigatorElement = (NavigatorElement) element;
			if (navigatorElement.getElement() instanceof ProjectDTO) {
				return ResourceManager.getPluginImage(Activator.getDefault(),
						"img/project.gif"); //$NON-NLS-1$
			}
			else if (navigatorElement.getElement() instanceof ActivityDTO) {
				return ResourceManager.getPluginImage(Activator.getDefault(),
						"img/activity.gif"); //$NON-NLS-1$
			}
		}
		return super.getImage(element);
	}
}
