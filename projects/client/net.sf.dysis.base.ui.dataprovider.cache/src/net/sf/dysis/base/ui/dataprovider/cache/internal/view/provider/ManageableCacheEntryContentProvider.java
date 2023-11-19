/**
 * ManageableCacheEntryContentProvider.java created on 11.12.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.cache.internal.view.provider;

import java.util.Collection;

import net.sf.dysis.base.ui.dataprovider.cache.IManageableCachingStrategy;
import net.sf.dysis.base.ui.dataprovider.cache.internal.Messages;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * A {@link IContentProvider} implementation for the management console.
 * 
 * @author Stefan Reichert
 */
public class ManageableCacheEntryContentProvider implements
		ITreeContentProvider {

	/**
	 * Categories for sub elements of the tree.
	 */
	public static enum Category {
		PROPERTIES(Messages.getString("ManageableCacheEntryContentProvider.node.properties.label")), ENTRIES(Messages.getString("ManageableCacheEntryContentProvider.node.entries.label")); //$NON-NLS-1$ //$NON-NLS-2$

		/** The label of this category. */
		private String label;

		/**
		 * Constructor for <class>Category</class>.
		 */
		private Category(String label) {
			this.label = label;
		}

		/**
		 * @return the label
		 */
		public String getLabel() {
			return label;
		}

	}

	/**
	 * A sub element of the tree.
	 */
	public static class ElementCategory {
		/** The elements {@link Category}. */
		private Category category;

		/** The elements {@link IManageableCachingStrategy}. */
		private IManageableCachingStrategy cachingStrategy;

		/**
		 * Constructor for <class>ElementCategory</class>.
		 */
		public ElementCategory(IManageableCachingStrategy cachingStrategy,
				Category category) {
			super();
			this.cachingStrategy = cachingStrategy;
			this.category = category;
		}

		/**
		 * @return the category
		 */
		public Category getCategory() {
			return category;
		}

		/**
		 * @return the cachingStrategy
		 */
		public IManageableCachingStrategy getCachingStrategy() {
			return cachingStrategy;
		}

		/** {@inheritDoc} */
		@Override
		public boolean equals(Object object) {
			if (object instanceof ElementCategory) {
				ElementCategory otherElementCategory = (ElementCategory) object;
				return otherElementCategory.getCachingStrategy().equals(
						cachingStrategy)
						&& otherElementCategory.getCategory() == category;
			}
			return super.equals(object);
		}

		/** {@inheritDoc} */
		@Override
		public int hashCode() {
			return cachingStrategy.hashCode();
		}

	}

	/** {@inheritDoc} */
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IManageableCachingStrategy) {
			return new Object[] {
					new ElementCategory(
							(IManageableCachingStrategy) parentElement,
							Category.PROPERTIES),
					new ElementCategory(
							(IManageableCachingStrategy) parentElement,
							Category.ENTRIES) };

		}
		else if (parentElement instanceof ElementCategory) {
			ElementCategory elementCategory = (ElementCategory) parentElement;
			if (elementCategory.category == Category.ENTRIES) {
				return elementCategory.getCachingStrategy().getCacheEntries()
						.toArray();
			}
			else {
				return elementCategory.getCachingStrategy()
						.getCachingProperties().toArray();
			}
		}
		else {
			return new Object[0];
		}
	}

	/** {@inheritDoc} */
	public Object getParent(Object element) {
		return null;
	}

	/** {@inheritDoc} */
	public boolean hasChildren(Object element) {
		if (element instanceof IManageableCachingStrategy) {
			return true;
		}
		else if (element instanceof ElementCategory) {
			ElementCategory elementCategory = (ElementCategory) element;
			if (elementCategory.category == Category.ENTRIES) {
				return elementCategory.getCachingStrategy().hasCacheEntries();
			}
			else {
				return true;
			}
		}
		else {
			return false;
		}
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	public Object[] getElements(Object inputElement) {
		Collection collection = (Collection) inputElement;
		return collection.toArray();
	}

	/** {@inheritDoc} */
	public void dispose() {
	}

	/** {@inheritDoc} */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

}
