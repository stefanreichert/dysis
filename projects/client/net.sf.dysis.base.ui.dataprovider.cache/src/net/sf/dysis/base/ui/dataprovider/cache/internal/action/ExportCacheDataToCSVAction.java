/**
 * ExportCacheDataToCSVAction.java created on 04.09.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.dataprovider.cache.internal.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import net.sf.dysis.base.ui.dataprovider.cache.ICachingProperty;
import net.sf.dysis.base.ui.dataprovider.cache.IManageableCacheEntry;
import net.sf.dysis.base.ui.dataprovider.cache.IManageableCachingStrategy;
import net.sf.dysis.base.ui.dataprovider.cache.ManageableCachingStrategyRegistry;
import net.sf.dysis.base.ui.dataprovider.cache.internal.Activator;
import net.sf.dysis.base.ui.dataprovider.cache.internal.Messages;
import net.sf.dysis.base.ui.dataprovider.cache.internal.view.provider.ManageableCacheEntryLabelProvider;
import net.sf.dysis.base.ui.dataprovider.cache.internal.view.provider.ManageableCacheEntryContentProvider.Category;
import net.sf.dysis.base.ui.dataprovider.cache.internal.view.provider.ManageableCacheEntryContentProvider.ElementCategory;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Table;

import com.swtdesigner.ResourceManager;

/**
 * A base {@link Action} exporting the raw content of a {@link TreeViewer}'s
 * {@link Table} to a .csv file as seperated values.
 * 
 * @author Stefan Reichert
 */
public class ExportCacheDataToCSVAction extends Action {

	/**
	 * Constructor for {@link ExportCacheDataToCSVAction}.
	 */
	public ExportCacheDataToCSVAction() {
		super();
		setText(Messages.getString("ExportCacheDataToCSVAction.export.action.label")); //$NON-NLS-1$
		setToolTipText(Messages.getString("ExportCacheDataToCSVAction.export.action.tooltip")); //$NON-NLS-1$
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator
				.getDefault(), "img/cacheConsole_exportDump.gif")); //$NON-NLS-1$
	}

	/** {@inheritDoc} */
	@Override
	public void run() {
		// prepare the target file dialog
		FileDialog targetFileDialog = new FileDialog(Display.getDefault()
				.getActiveShell());
		targetFileDialog.setText(Messages.getString("ExportCacheDataToCSVAction.export.dialog.text")); //$NON-NLS-1$
		targetFileDialog.setFileName(getExportFileName());
		targetFileDialog.setFilterExtensions(new String[] { "*.csv" }); //$NON-NLS-1$
		targetFileDialog.setFilterNames(new String[] { Messages.getString("ExportCacheDataToCSVAction.export.dialog.fileextension") }); //$NON-NLS-1$
		// get the name of the target file
		final String targetFileName = targetFileDialog.open();
		final int jobsize = ManageableCachingStrategyRegistry
				.getManageableStrategies().size();
		if (targetFileName != null) {
			final File targetFile = new File(targetFileName);
			Job exportJob = new Job(Messages.getString("ExportCacheDataToCSVAction.export.job.name")) { //$NON-NLS-1$

				/** {@inheritDoc} */
				@Override
				protected IStatus run(final IProgressMonitor monitor) {
					monitor.beginTask(Messages.getString("ExportCacheDataToCSVAction.export.job.task.data.prefix") //$NON-NLS-1$
							+ targetFileName + "] ...", jobsize); //$NON-NLS-1$
					monitor
							.subTask(Messages.getString("ExportCacheDataToCSVAction.export.job.task.file.prefix") + targetFileName //$NON-NLS-1$
									+ "]..."); //$NON-NLS-1$
					try {
						// write the content to the target file
						targetFile.createNewFile();
						final BufferedWriter targetFileWriter = new BufferedWriter(
								new FileWriter(targetFile));
						if (!monitor.isCanceled()) {
							writeHeader(targetFileWriter, monitor);
						}
						if (!monitor.isCanceled()) {
							writeContent(targetFileWriter, monitor);
						}
						targetFileWriter.flush();
						targetFileWriter.close();
						monitor.done();
						if (!monitor.isCanceled()) {
							return Status.OK_STATUS;
						}
						else {
							return Status.CANCEL_STATUS;
						}
					}
					catch (IOException exception) {
						return new Status(IStatus.ERROR, Activator.PLUGIN_ID,
								Messages.getString("ExportCacheDataToCSVAction.export.job.task.data.prefix") + targetFileName //$NON-NLS-1$
										+ Messages.getString("ExportCacheDataToCSVAction.export.job.task.data.failed.suffix"), exception); //$NON-NLS-1$
					}
				}
			};
			exportJob.setUser(true);
			exportJob.schedule();
		}
	}

	/**
	 * Writes the csv content.
	 * 
	 * @param targetFileWriter
	 *            the {@link BufferedWriter} to write the header
	 * @param monitor
	 *            The monitor to use for displaying the status
	 * @throws IOException
	 *             when writing the information failed
	 */
	private void writeContent(final BufferedWriter targetFileWriter,
			final IProgressMonitor monitor) throws IOException {
		for (IManageableCachingStrategy cachingStrategy : ManageableCachingStrategyRegistry
				.getManageableStrategies()) {
			writeStrategyDump(targetFileWriter, cachingStrategy);
			targetFileWriter.newLine();
			if (monitor.isCanceled()) {
				return;
			}
			monitor.worked(1);
		}
	}

	/**
	 * Writes the csv content for a {@link IManageableCachingStrategy}.
	 * 
	 * @param targetFileWriter
	 *            the {@link BufferedWriter} to write the header
	 * @param cachingStrategy
	 *            The {@link IManageableCachingStrategy} to export
	 * @throws IOException
	 * @throws IOException
	 *             when writing the information failed
	 */
	private void writeStrategyDump(BufferedWriter targetFileWriter,
			IManageableCachingStrategy cachingStrategy) throws IOException {
		ITableLabelProvider labelProvider = new ManageableCacheEntryLabelProvider();
		targetFileWriter.write(labelProvider.getColumnText(cachingStrategy, 0));
		// leave other columns empty
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.newLine();
		targetFileWriter.write(labelProvider.getColumnText(new ElementCategory(
				cachingStrategy, Category.PROPERTIES), 0));
		// leave other columns empty
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.newLine();
		for (ICachingProperty cachingProperty : cachingStrategy
				.getCachingProperties()) {
			targetFileWriter.write(labelProvider.getColumnText(cachingProperty,
					0));
			// leave other columns empty
			targetFileWriter.write(getSeperatorChar());
			targetFileWriter.write(getSeperatorChar());
			targetFileWriter.write(getSeperatorChar());
			targetFileWriter.write(getSeperatorChar());
			targetFileWriter.write(getSeperatorChar());
			targetFileWriter.write(getSeperatorChar());
			targetFileWriter.newLine();
		}
		targetFileWriter.write(labelProvider.getColumnText(new ElementCategory(
				cachingStrategy, Category.ENTRIES), 0));
		// leave other columns empty
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.newLine();
		for (IManageableCacheEntry cacheEntry : cachingStrategy
				.getCacheEntries()) {
			for (int index = 0; index < 6; index++) {
				targetFileWriter.write(labelProvider.getColumnText(cacheEntry,
						index));
				targetFileWriter.write(getSeperatorChar());
			}
			targetFileWriter.newLine();
		}
	}

	/**
	 * Writes the csv header line.
	 * 
	 * @param targetFileWriter
	 *            the {@link BufferedWriter} to write the header
	 * @param monitor
	 *            The monitor to use for displaying the status
	 * @throws IOException
	 *             when writing the information failed
	 */
	private void writeHeader(final BufferedWriter targetFileWriter,
			final IProgressMonitor monitor) throws IOException {
		monitor.subTask(Messages.getString("ExportCacheDataToCSVAction.export.job.subtask.header")); //$NON-NLS-1$
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.write(Messages.getString("ExportCacheDataToCSVAction.export.job.column.initial.entry")); //$NON-NLS-1$
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.write(Messages.getString("ExportCacheDataToCSVAction.export.job.column.access.count")); //$NON-NLS-1$
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.write(Messages.getString("ExportCacheDataToCSVAction.export.job.column.cache.hit.rate")); //$NON-NLS-1$
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.write(Messages.getString("ExportCacheDataToCSVAction.export.job.column.current.entry.creation")); //$NON-NLS-1$
		targetFileWriter.write(getSeperatorChar());
		targetFileWriter.write(Messages.getString("ExportCacheDataToCSVAction.export.job.column.last.access")); //$NON-NLS-1$
		targetFileWriter.write(getSeperatorChar());
		// return when cancel was pressed
		if (monitor.isCanceled()) {
			return;
		}
		monitor.worked(1);
		targetFileWriter.newLine();
	}

	/**
	 * Subclasses may overwrite.
	 * 
	 * @return the seperator char for the .csv file
	 */
	protected char getSeperatorChar() {
		return ';';
	}

	/**
	 * Subclasses may overwrite.
	 * 
	 * @return the name for the suggested .csv target file
	 */
	protected String getExportFileName() {
		return "export.csv"; //$NON-NLS-1$
	}

}
