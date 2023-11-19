/**
 * NavigatorNode.java created on 10.01.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.navigator;

import java.lang.reflect.Constructor;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ILabelProvider;

/**
 * Base class for <code>Objects</code> that appear in deferred trees.
 * 
 * @author Stefan Reichert
 */
public class NavigatorElement extends PlatformObject {

	/** This class' PluginLogger instance. */
	private static Logger logger = Logger.getLogger(NavigatorElement.class);

	/** Parent element of the adapter. */
	private NavigatorElement parent;

	/** Underlaying element of the adapter. */
	private Object element;

	/** Underlaying <code>NavigatorTreeContentProvider</code>. */
	private AbstractTreeViewer treeViewer;

	/**
	 * Adapts the given elements with the <code>NavigatorElement</code>.
	 * 
	 * @param parent
	 *            The parent <code>NavigatorElement</code>
	 * @param navigatorElementClass
	 *            The <code>Class</code> of the navigator element to use to
	 *            adapt the elements
	 * @param elements
	 *            The adapted elements
	 * @param treeViewer
	 *            The hosting <code>TreeViewer</code>
	 * @return the adapted children as array
	 */
	public static final NavigatorElement[] adapt(NavigatorElement parent,
			Class<? extends NavigatorElement> navigatorElementClass,
			Object[] elements, AbstractTreeViewer treeViewer) {
		if (elements == null) {
			return new NavigatorElement[0];
		}
		NavigatorElement[] adapters = new NavigatorElement[elements.length];
		for (int index = 0; index < adapters.length; index++) {
			adapters[index] = adapt(parent, navigatorElementClass,
					elements[index], treeViewer);
		}
		return adapters;
	}

	/**
	 * Adapts the given elements with the <code>NavigatorElement</code>.
	 * 
	 * @param parent
	 *            The parent <code>NavigatorElement</code>
	 * @param children
	 *            The children
	 * @param treeViewer
	 *            The hosting <code>TreeViewer</code>
	 * @return the adapted children as array
	 */
	public static final NavigatorElement[] adapt(NavigatorElement parent,
			Object[] elements, AbstractTreeViewer treeViewer) {
		return adapt(parent, NavigatorElement.class, elements, treeViewer);
	}

	/**
	 * Adapts the given elements with the <code>NavigatorElement</code>.
	 * 
	 * @param parent
	 *            The parent <code>NavigatorElement</code>
	 * @param navigatorElementClass
	 *            The <code>Class</code> of the navigator element to use to
	 *            adapt the element
	 * @param element
	 *            The adapted element
	 * @param treeViewer
	 *            The hosting <code>TreeViewer</code>
	 * @return the adapted children as array
	 */
	public static NavigatorElement adapt(NavigatorElement parent,
			Class<? extends NavigatorElement> navigatorElementClass,
			Object element, AbstractTreeViewer treeViewer) {
		assert element != null;
		try {
			Constructor<? extends NavigatorElement> constructor = navigatorElementClass
					.getConstructor(new Class<?>[] { NavigatorElement.class,
							Object.class, AbstractTreeViewer.class });
			return constructor.newInstance(new Object[] { parent, element,
					treeViewer });
		}
		catch (Exception exception) {
			logger.error(exception.getMessage(), exception);
		}
		return null;
	}

	/**
	 * Adapts the given elements with the <code>NavigatorElement</code>.
	 * 
	 * @param parent
	 *            The parent <code>NavigatorElement</code>
	 * @param element
	 *            The adapted element
	 * @param treeViewer
	 *            The hosting <code>TreeViewer</code>
	 * @return the adapted children as array
	 */
	public static NavigatorElement adapt(NavigatorElement parent,
			Object element, AbstractTreeViewer treeViewer) {
		return adapt(parent, NavigatorElement.class, element, treeViewer);
	}

	/**
	 * Creates a new adapter.
	 * 
	 * @param parent
	 *            parent of the new adapter
	 * @param element
	 *            element to be contained by this adapter
	 */
	public NavigatorElement(NavigatorElement parent, Object element,
			AbstractTreeViewer treeViewer) {
		this.parent = parent;
		this.element = element;
		this.treeViewer = treeViewer;
	}

	/**
	 * The parent for this node adapter.
	 * 
	 * @return the parent
	 */
	public NavigatorElement getParent() {
		return parent;
	}

	/**
	 * Returns the underlying element of the adapter.
	 * 
	 * @return the element
	 */
	public Object getElement() {
		return element;
	}

	/**
	 * Returns the image descriptor for this element. Default implementation
	 * returns <code>null</code>. Override if necessary.
	 * 
	 * @return <code>null</code>
	 */
	public ImageDescriptor getImageDescriptor() {
		if (element != null) {
			assert treeViewer.getLabelProvider() instanceof ILabelProvider;
			ILabelProvider labelProvider = (ILabelProvider) treeViewer
					.getLabelProvider();
			return ImageDescriptor.createFromImageData(labelProvider.getImage(
					element).getImageData());
		}
		return null;
	}

	/**
	 * Returns the label for this element. Default implementation returns the
	 * toString() return value of the underlying element (entity, model etc.).
	 * Override if necessary.
	 * 
	 * @return the label
	 */
	public String getLabel() {
		assert treeViewer.getLabelProvider() instanceof ILabelProvider;
		ILabelProvider labelProvider = (ILabelProvider) treeViewer
				.getLabelProvider();
		return labelProvider.getText(this);
	}

	/**
	 * Returns the children of a node. Must be implemented by subclasses to
	 * return all the children of this specific node.
	 * 
	 * @return array of children
	 */
	public NavigatorElement[] getChildren() {
		assert treeViewer.getContentProvider() instanceof NavigatorTreeContentProvider;
		NavigatorTreeContentProvider treeContentProvider = (NavigatorTreeContentProvider) treeViewer
				.getContentProvider();
		return treeContentProvider.getChildren(this, treeViewer);
	}

	/**
	 * Returns whether this node is a container node and may have children.
	 * Nodes that may never contain any children should override this method and
	 * return <code>false</code>.
	 * 
	 * @return <code>true</code> if this node is a container node,
	 *         <code>false</code> otherwise.
	 */
	public boolean isContainer() {
		return true;
	}

	/**
	 * Finds the next parent element that is of a given type.
	 * 
	 * @param type
	 *            the type of the parent element to find
	 * @return the found parent element or <code>null</code> if nothing was
	 *         found
	 */
	public NavigatorElement findElement(Class<?> type) {
		if (type.isAssignableFrom(getClass())) {
			return this;
		}
		if (getParent() == null) {
			return null;
		}
		return getParent().findElement(type);
	}
}
