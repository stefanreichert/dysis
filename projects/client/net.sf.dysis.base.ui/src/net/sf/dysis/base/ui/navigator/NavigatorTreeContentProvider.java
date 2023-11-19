/**
 * NavigatorTreeContentProvider.java created on 10.01.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.navigator;

import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.progress.DeferredTreeContentManager;

/**
 * <code>ITreeContentProvider</code> supporting deferred loading of it's
 * elements.
 * 
 * @author Stefan Reichert
 */
public abstract class NavigatorTreeContentProvider extends
		BaseWorkbenchContentProvider {

	/** The hosting <code>IWorkbenchPartSite</code>. */
	private IWorkbenchPartSite workbenchPartSite;

	/** The managing <code>DeferredTreeContentManager</code>. */
	private DeferredTreeContentManager manager;

	/**
	 * Constructor for <class>NavigatorTreeContentProvider</class>.
	 */
	public NavigatorTreeContentProvider(IWorkbenchPartSite workbenchPartSite) {
		super();
		this.workbenchPartSite = workbenchPartSite;
	}

	/**
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
	 *      java.lang.Object, java.lang.Object)
	 */
	public final void inputChanged(Viewer viewer, Object oldInput,
			Object newInput) {
		assert viewer instanceof TreeViewer : "content provider for may only be connected to a tree"; //$NON-NLS-1$
		assert workbenchPartSite != null : "site cannot be null - check constructor call"; //$NON-NLS-1$
		manager = new DeferredTreeContentManager((TreeViewer) viewer,
				workbenchPartSite);
		super.inputChanged(viewer, oldInput, newInput);
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	public final boolean hasChildren(Object element) {
		if (manager != null && manager.isDeferredAdapter(element)) {
			return manager.mayHaveChildren(element);
		}
		return super.hasChildren(element);
	}

	/**
	 * Returns the adapted list of children of the element of the given parent
	 * <code>NavigatorElement</code>.
	 * 
	 * @param parent
	 *            The parent <code>NavigatorElement</code>
	 * @param treeViewer
	 *            The hosting <code>TreeViewer</code>
	 * @return the adapted children as array
	 */
	public abstract NavigatorElement[] getChildren(
			NavigatorElement parent, AbstractTreeViewer treeViewer);

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public final Object[] getChildren(Object element) {
		if (manager != null) {
			Object[] children = manager.getChildren(element);
			if (children != null) {
				return children;
			}
			else {
				return new Object[0];
			}
		}
		return super.getChildren(element);
	}
}
