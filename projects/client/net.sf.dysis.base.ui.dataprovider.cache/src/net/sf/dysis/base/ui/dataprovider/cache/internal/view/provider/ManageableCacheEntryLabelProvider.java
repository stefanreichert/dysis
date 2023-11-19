/**
 * ManageableCacheEntryLabelProvider.java created on 11.12.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.cache.internal.view.provider;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import net.sf.dysis.base.ui.dataprovider.cache.ICachingProperty;
import net.sf.dysis.base.ui.dataprovider.cache.IManageableCacheEntry;
import net.sf.dysis.base.ui.dataprovider.cache.IManageableCacheEntryStatistics;
import net.sf.dysis.base.ui.dataprovider.cache.IManageableCachingStrategy;
import net.sf.dysis.base.ui.dataprovider.cache.internal.Activator;
import net.sf.dysis.base.ui.dataprovider.cache.internal.Messages;
import net.sf.dysis.base.ui.dataprovider.cache.internal.view.provider.ManageableCacheEntryContentProvider.Category;
import net.sf.dysis.base.ui.dataprovider.cache.internal.view.provider.ManageableCacheEntryContentProvider.ElementCategory;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.swtdesigner.ResourceManager;

/**
 * A {@link LabelProvider} implementation for the management console.
 * 
 * @author Stefan Reichert
 */
public class ManageableCacheEntryLabelProvider extends LabelProvider implements
		ITableLabelProvider, ITableColorProvider {

	/** {@inheritDoc} */
	public Image getColumnImage(Object element, int columnIndex) {
		if (columnIndex == 0) {
			if (element instanceof IManageableCachingStrategy) {
				IManageableCachingStrategy cachingStrategy = (IManageableCachingStrategy) element;
				if (cachingStrategy.hasCacheEntries()) {
					return ResourceManager.getPluginImage(Activator
							.getDefault(), "img/cacheInstance.gif"); //$NON-NLS-1$
				}
				return ResourceManager.getPluginImage(Activator.getDefault(),
						"img/cacheInstance_empty.gif"); //$NON-NLS-1$
			}
			else if (element instanceof IManageableCacheEntry) {
				return ResourceManager.getPluginImage(Activator.getDefault(),
						"img/cacheEntry.gif"); //$NON-NLS-1$
			}
			else if (element instanceof ElementCategory) {
				ElementCategory elementCategory = (ElementCategory) element;
				if (elementCategory.getCategory() == Category.ENTRIES) {
					return ResourceManager.getPluginImage(Activator
							.getDefault(), "img/cacheInstanceEntries.gif"); //$NON-NLS-1$
				}
				else {
					return ResourceManager.getPluginImage(Activator
							.getDefault(), "img/cacheInstanceProperties.gif"); //$NON-NLS-1$
				}
			}
			else if (element instanceof ICachingProperty) {
				return ResourceManager.getPluginImage(Activator.getDefault(),
						"img/cacheInstanceProperty.gif"); //$NON-NLS-1$
			}
		}
		return null;
	}

	/** {@inheritDoc} */
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof IManageableCachingStrategy) {
			IManageableCachingStrategy cachingStrategy = (IManageableCachingStrategy) element;
			if (columnIndex == 0) {
				return Messages.getString("ManageableCacheEntryLabelProvider.row.cache.label.prefix") + cachingStrategy.getCacheHostname() + "] - " + cachingStrategy.getCacheEntryCount() + Messages.getString("ManageableCacheEntryLabelProvider.row.cache.label.suffix"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
			return new String();
		}
		else if (element instanceof IManageableCacheEntry) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"dd/MM/yy HH:mm:ss"); //$NON-NLS-1$
			DecimalFormat decimalFormat = new DecimalFormat("##.##"); //$NON-NLS-1$
			IManageableCacheEntry cacheEntry = (IManageableCacheEntry) element;
			IManageableCacheEntryStatistics cacheEntryStatistics = cacheEntry
					.getHostStrategy().getCacheEntryStatistics(cacheEntry);
			switch (columnIndex) {

				case 0:
					return Messages.getString("ManageableCacheEntryLabelProvider.row.cache.entry.label.prefix") + cacheEntry.getKey().toString() + "]"; //$NON-NLS-1$ //$NON-NLS-2$

				case 1:
					return simpleDateFormat.format(cacheEntryStatistics
							.getInitialCreation());

				case 2:
					if (cacheEntryStatistics.getAccessCount() == 1) {
						return cacheEntryStatistics.getAccessCount() + Messages.getString("ManageableCacheEntryLabelProvider.column.access.count.label.one.suffix"); //$NON-NLS-1$
					}
					return cacheEntryStatistics.getAccessCount() + Messages.getString("ManageableCacheEntryLabelProvider.column.access.count.label.many.suffix"); //$NON-NLS-1$

				case 3:
					if (cacheEntryStatistics.getCacheHits() == 1) {
						return cacheEntryStatistics.getCacheHits() + Messages.getString("ManageableCacheEntryLabelProvider.column.access.count.label.one.suffix"); //$NON-NLS-1$
					}
					return cacheEntryStatistics.getCacheHits() + Messages.getString("ManageableCacheEntryLabelProvider.column.access.count.label.many.suffix"); //$NON-NLS-1$
					
				case 4:
					if (cacheEntryStatistics.getAccessCount() == 0) {
						return Messages.getString("ManageableCacheEntryLabelProvider.column.access.count.label.none"); //$NON-NLS-1$
					}
					double requestCount = cacheEntryStatistics.getAccessCount();
					double cacheHits = cacheEntryStatistics.getCacheHits();
					double cacheHitRate = cacheHits / requestCount * 100;
					return decimalFormat.format(cacheHitRate) + Messages.getString("ManageableCacheEntryLabelProvider.column.cache.hit.rate.label.suffix"); //$NON-NLS-1$

				case 5:
					return simpleDateFormat.format(cacheEntry
							.getCacheEntryInformation().getCreation());

				case 6:
					return simpleDateFormat.format(cacheEntry
							.getCacheEntryInformation().getLastAccess());
			}
			return new String();
		}
		else if (element instanceof ElementCategory && columnIndex == 0) {
			ElementCategory elementCategory = (ElementCategory) element;
			return elementCategory.getCategory().getLabel();
		}
		else if (element instanceof ICachingProperty && columnIndex == 0) {
			ICachingProperty cachingProperty = (ICachingProperty) element;
			return Messages.getString("ManageableCacheEntryLabelProvider.row.cache.property.label.prefix") + cachingProperty.getName() + "] = [" //$NON-NLS-1$ //$NON-NLS-2$
					+ cachingProperty.getValue() + "]"; //$NON-NLS-1$
		}
		return new String();
	}

	/** {@inheritDoc} */
	public Color getBackground(Object element, int columnIndex) {
		return null;
	}

	/** {@inheritDoc} */
	public Color getForeground(Object element, int columnIndex) {
		if (element instanceof IManageableCacheEntry && columnIndex == 4) {
			IManageableCacheEntry cacheEntry = (IManageableCacheEntry) element;
			IManageableCacheEntryStatistics cacheEntryStatistics = cacheEntry
					.getHostStrategy().getCacheEntryStatistics(cacheEntry);
			if (cacheEntryStatistics.getAccessCount() == 0) {
				return null;
			}
			double requestCount = cacheEntryStatistics.getAccessCount();
			double cacheHits = cacheEntryStatistics.getCacheHits();
			double cacheHitRate = cacheHits / requestCount * 100;
			if(cacheHitRate >= 50){
				return Display.getDefault().getSystemColor(SWT.COLOR_DARK_GREEN);
			}
			if(cacheHitRate >= 33.33){
				return Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY);
			}
			if(cacheHitRate >= 20){
				return Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED);
			}
			return Display.getDefault().getSystemColor(SWT.COLOR_RED);
		}
		return null;
	}
}
