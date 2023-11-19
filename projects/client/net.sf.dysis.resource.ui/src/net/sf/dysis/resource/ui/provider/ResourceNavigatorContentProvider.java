/**
 * ResourceNavigatorContentProvider.java created on 10.01.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.resource.ui.provider;

import net.sf.dysis.base.ui.dataprovider.IDataProvider;
import net.sf.dysis.base.ui.dataprovider.key.ForeignKey;
import net.sf.dysis.base.ui.dataprovider.key.ICollectionKey;
import net.sf.dysis.base.ui.dataprovider.registry.Registry;
import net.sf.dysis.base.ui.navigator.NavigatorElement;
import net.sf.dysis.base.ui.navigator.NavigatorTreeContentProvider;
import net.sf.dysis.resource.core.dto.PersonDTO;
import net.sf.dysis.resource.ui.dataprovider.ResourceDataProvider;
import net.sf.dysis.resource.ui.navigator.ResourceNavigatorElement;
import net.sf.dysis.resource.ui.navigator.ActivityNavigatorElement;

import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.ui.IWorkbenchPartSite;

/**
 * Represents the <code>NavigatorTreeContentProvider</code> for the
 * <code>ResourceNavigatorView</code>
 * 
 * @author Stefan Reichert
 */
public class ResourceNavigatorContentProvider extends
		NavigatorTreeContentProvider {

	/**
	 * Constructor for <class>ResourceNavigatorContentProvider</class>.
	 */
	public ResourceNavigatorContentProvider(IWorkbenchPartSite workbenchPartSite) {
		super(workbenchPartSite);
	}

	/** {@inheritDoc} */
	public NavigatorElement[] getChildren(NavigatorElement parent,
			AbstractTreeViewer treeViewer) {
		if (parent.getElement() instanceof String) {
			// the root element has all resources available as children
			return ResourceNavigatorElement.adapt(parent,
					ResourceNavigatorElement.class, Registry.getRegistry()
							.lookupDataProvider(ResourceDataProvider.TYPE)
							.getDataCollection(ICollectionKey.ALL).toArray(),
					treeViewer);
		}
		else if (parent.getElement() instanceof PersonDTO) {
			// each person has it's activities as children
			PersonDTO resource = (PersonDTO) parent.getElement();
			IDataProvider dataProvider = Registry.getRegistry()
					.lookupDataProvider("core.activity.dto"); //$NON-NLS-1$
			return ActivityNavigatorElement.adapt(parent,
					ActivityNavigatorElement.class, dataProvider.getDataCollection(
							new ForeignKey(resource.getId(), "resource")) //$NON-NLS-1$
							.toArray(), treeViewer);
		}
		else {
			return NavigatorElement.adapt(parent, new Object[0], treeViewer);
		}
	}
}
