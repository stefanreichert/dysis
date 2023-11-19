/**
 * ProjectNavigatorContentProvider.java created on 10.01.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planning.ui.provider;

import net.sf.dysis.base.ui.dataprovider.IDataProvider;
import net.sf.dysis.base.ui.dataprovider.key.ForeignKey;
import net.sf.dysis.base.ui.dataprovider.key.ICollectionKey;
import net.sf.dysis.base.ui.dataprovider.registry.Registry;
import net.sf.dysis.base.ui.navigator.NavigatorElement;
import net.sf.dysis.base.ui.navigator.NavigatorTreeContentProvider;
import net.sf.dysis.planing.core.dto.ProjectDTO;
import net.sf.dysis.planning.ui.dataprovider.ActivityDataProvider;
import net.sf.dysis.planning.ui.dataprovider.ProjectDataProvider;
import net.sf.dysis.planning.ui.navigator.ActivityNavigatorElement;
import net.sf.dysis.planning.ui.navigator.ProjectNavigatorElement;

import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.ui.IWorkbenchPartSite;

/**
 * Represents the <code>NavigatorTreeContentProvider</code> for the
 * <code>ProjectNavigatorView</code>
 * 
 * @author Stefan Reichert
 */
public class ProjectNavigatorContentProvider extends
		NavigatorTreeContentProvider {

	/**
	 * Constructor for <class>ProjectNavigatorContentProvider</class>.
	 */
	public ProjectNavigatorContentProvider(IWorkbenchPartSite workbenchPartSite) {
		super(workbenchPartSite);
	}

	/** {@inheritDoc} */
	public NavigatorElement[] getChildren(NavigatorElement parent,
			AbstractTreeViewer treeViewer) {
		// the root element has all projects available as children
		if (parent.getElement() instanceof String) {
			IDataProvider dataProvider = Registry.getRegistry()
					.lookupDataProvider(ProjectDataProvider.TYPE);
			return ProjectNavigatorElement.adapt(parent,
					ProjectNavigatorElement.class, dataProvider
							.getDataCollection(ICollectionKey.ALL).toArray(),
					treeViewer);
		}
		// each project has it's activities as children
		else if (parent.getElement() instanceof ProjectDTO) {
			ProjectDTO project = (ProjectDTO) parent.getElement();
			IDataProvider dataProvider = Registry.getRegistry()
					.lookupDataProvider(ActivityDataProvider.TYPE);
			return ActivityNavigatorElement.adapt(parent,
					ActivityNavigatorElement.class,
					dataProvider.getDataCollection(
							new ForeignKey(project.getId(),
									ActivityDataProvider.FOREIGN_KEY_PROJECT))
							.toArray(), treeViewer);
		}
		else {
			return NavigatorElement.adapt(parent, new Object[0], treeViewer);
		}
	}

}
