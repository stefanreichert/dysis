/**
 * NavigatorElementAdapter.java created on 10.01.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.internal.navigator;

import net.sf.dysis.base.ui.navigator.NavigatorElement;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.progress.IElementCollector;

/**
 * The adapter for <code>NavigatorElement</code>.
 * 
 * @author Stefan Reichert
 */
public abstract class NavigatorElementAdapter {

	/**
	 * <code>true</code> to serialize fetching, <code>false</code>
	 * otherwise.
	 */
	private static boolean serializeFetching = false;

	/**
	 * <code>true</code> to batch fetched children, <code>false</code>
	 * otherwise.
	 */
	private static boolean batchFetchedChildren = true;

	/** Empty array. */
	private static final Object[] EMPTY_ARRAY = new Object[0];

	/** Scheduling rule. */
	private final ISchedulingRule mutexRule = new ISchedulingRule() {
		public boolean isConflicting(ISchedulingRule rule) {
			return rule == mutexRule;
		}

		public boolean contains(ISchedulingRule rule) {
			return rule == mutexRule;
		}
	};

	/**
	 * Fetches the deferred children (lazy loading).
	 * 
	 * @param object
	 *            the object
	 * @param collector
	 *            the collector collecting the objects to be collected
	 *            collectingwise
	 * @param monitor
	 *            monitors the progress
	 */
	public void fetchDeferredChildren(Object object,
			IElementCollector collector, IProgressMonitor monitor) {
		if (object instanceof NavigatorElement) {
			Object[] children = ((NavigatorElement) object).getChildren();
			if (isBatchFetchedChildren()) {
				collector.add(children, monitor);
			}
			else {
				for (int i = 0; i < children.length; i++) {
					if (monitor.isCanceled()) {
						return;
					}
					collector.add(children[i], monitor);
				}
			}
		}

	}

	/**
	 * Returns the scheduling rule for a given object.
	 * 
	 * @param object
	 *            the object
	 * @return the object's rule
	 */
	public ISchedulingRule getRule(Object object) {
		if (isSerializeFetching()) {
			return mutexRule;
		}
		// allow several ExplorerElement parents to fetch children concurrently
		return null;
	}

	/**
	 * Returns the children of a given object.
	 * 
	 * @param object
	 *            the object
	 * @return the object's children on an empty array
	 */
	public Object[] getChildren(Object object) {
		if (object instanceof NavigatorElement) {
			return ((NavigatorElement) object).getChildren();
		}
		return EMPTY_ARRAY;
	}

	/**
	 * Returns the image descriptor of a given object.
	 * 
	 * @param object
	 *            the object
	 * @return the object's image descriptor or <code>null</code>
	 */
	public ImageDescriptor getImageDescriptor(Object object) {
		if (object instanceof NavigatorElement) {
			return ((NavigatorElement) object).getImageDescriptor();
		}
		return null;
	}

	/**
	 * Returns the label of a given object.
	 * 
	 * @param object
	 *            the object
	 * @return the object's label or <code>null</code>
	 */
	public String getLabel(Object object) {
		if (object instanceof NavigatorElement) {
			return ((NavigatorElement) object).getLabel();
		}
		return null;
	}

	/**
	 * Returns the parent of a given object.
	 * 
	 * @param object
	 *            the child
	 * @return the parent or <code>null</code>
	 */
	public Object getParent(Object object) {
		if (object instanceof NavigatorElement) {
			return ((NavigatorElement) object).getParent();
		}
		return null;
	}

	/**
	 * Returns the batchFetchedChildren.
	 * 
	 * @return batch fetched children flag
	 */
	public static boolean isBatchFetchedChildren() {
		return batchFetchedChildren;
	}

	/**
	 * Sets the batch fetched children flag.
	 * 
	 * @param batchFetchedChildren
	 *            the batchFetchedChildren to set
	 */
	public static void setBatchFetchedChildren(boolean batchFetchedChildren) {
		NavigatorElementAdapter.batchFetchedChildren = batchFetchedChildren;
	}

	/**
	 * Returns the serialize fetching flag.
	 * 
	 * @return the serialize fetching flag
	 */
	public static boolean isSerializeFetching() {
		return serializeFetching;
	}

	/**
	 * Sets the serialize fetching flag.
	 * 
	 * @param serializeFetching
	 *            the serialize fetching flag to set.
	 */
	public static void setSerializeFetching(boolean serializeFetching) {
		NavigatorElementAdapter.serializeFetching = serializeFetching;
	}
}
