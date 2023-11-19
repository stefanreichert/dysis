/**
 * UpdateLog.java created on 08.01.2010
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.application.update.internal.log;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.dysis.application.update.internal.Activator;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.update.core.IFeatureReference;
import org.osgi.framework.Bundle;

/**
 * A log file for an update
 * 
 * @author Stefan Reichert
 */
public class UpdateLog {

	/** This class' PluginLogger instance. */
	private static Logger logger = Logger.getLogger(UpdateLog.class);

	/** The {@link Date} when the update occured. */
	private final Date updateDate;

	/** The {@link Map} of features installed. */
	private final Map<IFeatureReference, String> installedFeatures;

	/** The {@link Map} of features removed. */
	private final Map<IFeatureReference, String> removedFeatures;

	/** The {@link Map} of bundles removed. */
	private final Map<Bundle, String> removedBundles;

	/** The name of the application which was updated. */
	private String applicationName;

	/**
	 * Constructor for <class>UpdateLog</class>.
	 */
	public UpdateLog(String applicationName) {
		super();
		this.applicationName = applicationName;
		updateDate = new Date();
		installedFeatures = new HashMap<IFeatureReference, String>();
		removedFeatures = new HashMap<IFeatureReference, String>();
		removedBundles = new HashMap<Bundle, String>();
	}

	/**
	 * Adds an {@link IFeatureReference} which was installed during update.
	 * 
	 * @param feature
	 *            The installed {@link IFeatureReference} to add
	 * @param message
	 *            The accompanying message
	 */
	public void addInstalledFeature(IFeatureReference feature, String message) {
		installedFeatures.put(feature, message);
	}

	/**
	 * Adds an {@link IFeatureReference} which was remove during update.
	 * 
	 * @param feature
	 *            The removed {@link IFeatureReference} to add
	 * @param message
	 *            The accompanying message
	 */
	public void addRemovedFeature(IFeatureReference feature, String message) {
		removedFeatures.put(feature, message);
	}

	/**
	 * Adds an {@link Bundle} which was remove during update.
	 * 
	 * @param bundle
	 *            The removed {@link Bundle} to add
	 * @param message
	 *            The accompanying message
	 */
	public void addRemovedBundle(Bundle bundle, String message) {
		removedBundles.put(bundle, message);
	}

	/**
	 * @return whether any update events occured
	 */
	public boolean hasData() {
		return !removedBundles.isEmpty() || !removedFeatures.isEmpty()
				|| !installedFeatures.isEmpty();
	}

	/**
	 * Writes a file for this update log. The file is located in this bundle's
	 * state location and named after the application and the update date.<br>
	 * <br>
	 * Filename:
	 * <b>stateLocation</b>/<b>updateDate</b>-<b>applicationName</b>.log
	 */
	public void writeUpdateLogFile() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss"); //$NON-NLS-1$
		PrintWriter logFileWriter = null;
		String logFileName = dateFormat.format(updateDate)
				+ "#[" + applicationName + "].log"; //$NON-NLS-1$ //$NON-NLS-2$
		logger.info("--- writing log file for update of application [" //$NON-NLS-1$
				+ applicationName + "]"); //$NON-NLS-1$
		try {
			File logFile = Activator.getDefault().getStateLocation().append(
					logFileName).toFile();
			logger.debug("--- * log file is [" + logFile.getAbsolutePath() //$NON-NLS-1$
					+ "]"); //$NON-NLS-1$
			logFileWriter = new PrintWriter(logFile);
			logFileWriter
					.println("update log for application [" //$NON-NLS-1$
							+ applicationName
							+ "], " + DateFormat.getDateTimeInstance().format(updateDate)); //$NON-NLS-1$
			logFileWriter.println("Features installed: " //$NON-NLS-1$
					+ installedFeatures.size());
			for (IFeatureReference feature : installedFeatures.keySet()) {
				String message = installedFeatures.get(feature);
				logFileWriter.println(" * feature: " //$NON-NLS-1$
						+ feature.getVersionedIdentifier().getIdentifier()
						+ " [" + feature.getVersionedIdentifier().getVersion() //$NON-NLS-1$
						+ "] - " + message); //$NON-NLS-1$
			}
			logFileWriter
					.println("Features removed: " + removedFeatures.size()); //$NON-NLS-1$
			for (IFeatureReference feature : removedFeatures.keySet()) {
				String message = removedFeatures.get(feature);
				logFileWriter.println(" * feature: " //$NON-NLS-1$
						+ feature.getVersionedIdentifier().getIdentifier()
						+ " [" + feature.getVersionedIdentifier().getVersion() //$NON-NLS-1$
						+ "] - " + message); //$NON-NLS-1$
			}
			logFileWriter.println("Bundles removed: " + removedBundles.size()); //$NON-NLS-1$
			for (Bundle bundle : removedBundles.keySet()) {
				String message = removedBundles.get(bundle);
				logFileWriter.println(" * bundle: " + bundle.getSymbolicName() //$NON-NLS-1$
						+ " [" + bundle.getHeaders().get("Bundle-Version") //$NON-NLS-1$ //$NON-NLS-2$
						+ "] - " + message); //$NON-NLS-1$
			}
		}
		catch (IOException exception) {
			exception.printStackTrace();
		}
		catch (CoreException exception) {
			exception.printStackTrace();
		}
		finally {
			if (logFileWriter != null) {
				logFileWriter.flush();
				logFileWriter.close();
			}
		}
	}
}
