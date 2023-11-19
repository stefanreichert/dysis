/**
 * ResourceNavigatorLabelProvider.java created on 10.01.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.resource.ui.provider;

import net.sf.dysis.base.ui.navigator.NavigatorElement;
import net.sf.dysis.planing.core.dto.ActivityDTO;
import net.sf.dysis.resource.core.dto.PersonDTO;
import net.sf.dysis.resource.ui.Activator;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.swtdesigner.ResourceManager;

/**
 * Represents the <code>LabelProvider</code> for the
 * <code>ResourceNavigatorView</code>
 * 
 * @author Stefan Reichert
 */
public class ResourceNavigatorLabelProvider extends LabelProvider {

	/**
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof NavigatorElement) {
			NavigatorElement navigatorElement = (NavigatorElement) element;
			if (navigatorElement.getElement() instanceof PersonDTO) {
				PersonDTO resource = (PersonDTO) navigatorElement.getElement();
				return new StringBuilder(resource.getFirstname()).append(" ") //$NON-NLS-1$
						.append(resource.getLastname()).append(" (").append( //$NON-NLS-1$
								resource.getUserId()).append(")").toString(); //$NON-NLS-1$
			}
			else if (navigatorElement.getElement() instanceof ActivityDTO) {
				ActivityDTO activityDTO = (ActivityDTO) navigatorElement
						.getElement();
				return new StringBuilder(activityDTO.getName()).append(" (") //$NON-NLS-1$
						.append(activityDTO.getDescription()).append(")") //$NON-NLS-1$
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
			if (navigatorElement.getElement() instanceof PersonDTO) {
				return ResourceManager.getPluginImage(Activator.getDefault(),
						"img/resource.gif"); //$NON-NLS-1$
			}
			else if (navigatorElement.getElement() instanceof ActivityDTO) {
				return ResourceManager.getPluginImage(Activator.getDefault(),
						"img/activity.gif"); //$NON-NLS-1$
			}
		}
		return super.getImage(element);
	}
}
