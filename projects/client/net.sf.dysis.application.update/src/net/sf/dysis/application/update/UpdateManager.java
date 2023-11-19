/**
 * UpdateManager.java created on 23.02.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.application.update;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.dysis.application.update.internal.Activator;
import net.sf.dysis.application.update.internal.Messages;
import net.sf.dysis.application.update.internal.log.UpdateLog;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.update.configuration.IConfiguredSite;
import org.eclipse.update.configuration.ILocalSite;
import org.eclipse.update.core.IFeature;
import org.eclipse.update.core.IFeatureReference;
import org.eclipse.update.core.IPluginEntry;
import org.eclipse.update.core.ISite;
import org.eclipse.update.core.ISiteFeatureReference;
import org.eclipse.update.core.SiteManager;
import org.eclipse.update.core.VersionedIdentifier;
import org.eclipse.update.operations.IInstallFeatureOperation;
import org.eclipse.update.operations.IUnconfigFeatureOperation;
import org.eclipse.update.operations.OperationsManager;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

/**
 * A manager to update local features from an update site.
 * 
 * @author Stefan Reichert
 */
public class UpdateManager {

	/** This class' PluginLogger instance. */
	private static Logger logger = Logger.getLogger(UpdateManager.class);

	/** The update site connection {@link String}. */
	private String[] updateSiteStrings;

	/** Flag whether updates for features should be skipped. */
	private boolean skipUpdate;

	/** Flag whether new features should be skipped. */
	private boolean skipNew;

	/**
	 * Flag whether removing unconfigured bundles permanently from the disc
	 * should be skipped.
	 */
	private boolean skipRemoveUnconfiguredBundles;

	/** The {@link Comparator} for the feature versions. */
	private Comparator<Version> versionComparator;

	/** This manager's {@link UpdateLog}. */
	private final UpdateLog updateLog;

	/**
	 * Constructor for <class>UpdateManager</class>.
	 */
	public UpdateManager(String applicationName, String updateSiteString) {
		this(applicationName, updateSiteString, false, true, true);
	}

	/**
	 * Constructor for <class>UpdateManager</class>.
	 */
	public UpdateManager(String applicationName, String[] updateSiteStrings) {
		this(applicationName, updateSiteStrings, false, true, true);
	}

	/**
	 * Constructor for <class>UpdateManager</class>.
	 */
	public UpdateManager(String applicationName, String updateSiteString,
			boolean skipUpdate, boolean skipNew,
			boolean skipRemoveUnconfiguredBundles) {
		this(applicationName, new String[] { updateSiteString }, skipUpdate,
				skipNew, skipRemoveUnconfiguredBundles);
	}

	/**
	 * Constructor for <class>UpdateManager</class>.
	 */
	public UpdateManager(String applicationName, String[] updateSiteStrings,
			boolean skipUpdate, boolean skipNew,
			boolean skipRemoveUnconfiguredBundles) {
		super();
		this.updateSiteStrings = updateSiteStrings;
		this.skipUpdate = skipUpdate;
		this.skipNew = skipNew;
		this.skipRemoveUnconfiguredBundles = skipRemoveUnconfiguredBundles;
		this.updateLog = new UpdateLog(applicationName);
	}

	/**
	 * Checks for updates on the given update site. Depending on the values
	 * {@link #skipUpdate} and {@link #skipNew}, new and yet not downloaded
	 * features will be downloaded to the current installation directory.
	 * 
	 * @param shell
	 *            The {@link Shell} to use for the dialog
	 * @return whether updates are available
	 */
	public boolean checkForUpdates(Shell shell) {
		final List<IFeatureReference> installedFeatures = new ArrayList<IFeatureReference>();
		final List<IFeatureReference> removedFeatures = new ArrayList<IFeatureReference>();
		final List<Bundle> removedBundles = new ArrayList<Bundle>();
		logger
				.info("Update Manager v." + Activator.getDefault().getBundle().getHeaders().get("Bundle-Version")); //$NON-NLS-1$ //$NON-NLS-2$
		ProgressMonitorDialog monitorDialog = new ProgressMonitorDialog(shell);
		try {
			monitorDialog.run(true, false, new IRunnableWithProgress() {
				/** {@inheritDoc} */
				public void run(IProgressMonitor monitor) {
					monitor.beginTask(Messages
							.getString("UpdateManager.task.message") //$NON-NLS-1$
							, -1);
					try {
						// get the current local site
						final ILocalSite localSite = SiteManager.getLocalSite();
						// first remove duplicate features...
						removedFeatures.addAll(removeDuplicateFeaturesFromDisc(
								monitor, localSite));
						// ... then get the installed features
						Map<String, IFeatureReference> installedFeatureMap = getInstalledFeatures(
								monitor, localSite);
						// ... delete the unnecessary bundles...
						removedBundles
								.addAll(removeUnconfiguredBundlesFromDisc(
										monitor, installedFeatureMap));
						// ... and then check for new features to install...
						installedFeatures.addAll(scanUpdateSites(monitor,
								localSite, installedFeatureMap));
					}
					catch (Exception exception) {
						logger.error(exception.getMessage(), exception);
					}
				}
			});
		}
		catch (Exception exception) {
			logger.error(exception.getMessage(), exception);
		}
		// write log file if events available
		if (updateLog.hasData()) {
			updateLog.writeUpdateLogFile();
		}
		logger
				.info("--- update manager finished - " + installedFeatures.size() //$NON-NLS-1$
						+ " features installed, " + removedFeatures.size() + " features removed, " + removedBundles.size() + " bundles removed"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return !installedFeatures.isEmpty();
	}

	/**
	 * Checks the listed update sites for new or updated features.
	 * 
	 * @param monitor
	 *            The {@link IProgressMonitor} to use
	 * @param installedFeatureMap
	 *            The {@link Map} of installed features
	 * @param localSite
	 *            The {@link ILocalSite}
	 * @return the list of installed features
	 */
	@SuppressWarnings("deprecation")
	private List<IFeatureReference> scanUpdateSites(IProgressMonitor monitor,
			ILocalSite localSite,
			Map<String, IFeatureReference> installedFeatureMap)
			throws CoreException {
		final List<IFeatureReference> installedFeatures = new ArrayList<IFeatureReference>();
		// check the defined update sites for remote features
		for (String updateSiteString : updateSiteStrings) {
			monitor.subTask(updateSiteString);
			try {
				// get the remote features
				List<ISiteFeatureReference> remoteFeatures = getRemoteFeatures(
						monitor, updateSiteString);
				for (IFeatureReference remoteFeatureReference : remoteFeatures) {
					// check each remote feature
					VersionedIdentifier remoteIdentifier = remoteFeatureReference
							.getVersionedIdentifier();
					logger.info("--- check remote feature [" //$NON-NLS-1$
							+ remoteIdentifier.getIdentifier()
							+ "] - version [" //$NON-NLS-1$
							+ remoteIdentifier.getVersion().toString() + "]"); //$NON-NLS-1$
					if (installedFeatureMap.containsKey(remoteIdentifier
							.getIdentifier())) {
						// if it exists, check for update
						if (checkForUpdate(monitor, localSite,
								installedFeatureMap.get(remoteIdentifier
										.getIdentifier()),
								remoteFeatureReference)) {
							installedFeatures.add(remoteFeatureReference);
						}
					}
					else {
						// if it does not yet exist, check for
						// creation
						if (checkForCreation(monitor, localSite,
								remoteFeatureReference)) {
							installedFeatures.add(remoteFeatureReference);
						}
					}
				}
			}
			catch (Exception exception) {
				logger.error(exception.getMessage(), exception);
			}
		}
		return installedFeatures;
	}

	/**
	 * Checks whether a feature from the update site should be downloaded.
	 * 
	 * @param localSite
	 *            The {@link ILocalSite} to download to
	 * @param remoteFeatureReference
	 *            The {@link IFeatureReference} of the feature from the remote
	 *            site
	 * @param monitor
	 *            The {@link IProgressMonitor} to use on action
	 * @return whether an update of the {@link ILocalSite} has taken place
	 * @throws CoreException
	 * @throws InvocationTargetException
	 */
	private boolean checkForCreation(IProgressMonitor monitor,
			ILocalSite localSite, IFeatureReference remoteFeatureReference) {
		if (skipNew) {
			logger.info("--- - skipped new remote feature"); //$NON-NLS-1$
		}
		else {
			logger.info("--- - new feature available"); //$NON-NLS-1$
			try {
				return downloadForNewFeature(localSite, remoteFeatureReference,
						monitor);
			}
			catch (Exception exception) {
				logger.error(exception.getMessage(), exception);
			}
		}
		return false;
	}

	/**
	 * Checks whether an update for a feature from the local site is available
	 * on the remote site and therefore should be downloaded.
	 * 
	 * @param monitor
	 *            The {@link IProgressMonitor} to use on action
	 * @param localSite
	 *            The {@link ILocalSite} to download to
	 * @param localFeatureReference
	 *            The {@link IFeatureReference} of the feature from the local
	 *            site
	 * @param remoteFeatureReference
	 *            The {@link IFeatureReference} of the feature from the remote
	 *            site
	 * @return whether an update of the {@link ILocalSite} has taken place
	 * @throws CoreException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("deprecation")
	private boolean checkForUpdate(IProgressMonitor monitor,
			ILocalSite localSite, IFeatureReference localFeatureReference,
			IFeatureReference remoteFeatureReference) {
		try {
			VersionedIdentifier remoteIdentifier = remoteFeatureReference
					.getVersionedIdentifier();
			VersionedIdentifier localIdentifier = localFeatureReference
					.getVersionedIdentifier();
			logger.info("--- - found matching installed feature - version [" //$NON-NLS-1$
					+ localIdentifier.getVersion().toString() + "]"); //$NON-NLS-1$
			if (isGreaterThan(remoteIdentifier, localIdentifier)) {
				logger.info("--- - new version available"); //$NON-NLS-1$
				if (!skipUpdate) {
					return (downloadForExistingFeature(monitor, localSite,
							localFeatureReference, remoteFeatureReference));
				}
				else {
					logger.info("--- - skipped new remote feature version"); //$NON-NLS-1$
				}
			}
			else {
				logger.info("--- - version is up-to-date"); //$NON-NLS-1$
			}
		}
		catch (Exception exception) {
			logger.error(exception.getMessage(), exception);
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	private boolean isGreaterThan(VersionedIdentifier versionIdentifier_one,
			VersionedIdentifier versionIdentifier_two) {
		if (versionComparator != null) {
			// using comparator
			return versionComparator.compare(toVersion(versionIdentifier_one),
					toVersion(versionIdentifier_two)) > 0;
		}
		else {
			// using simple comparism
			return versionIdentifier_one.getVersion().isGreaterThan(
					versionIdentifier_two.getVersion());
		}
	}

	/**
	 * Proceeds the actual download of an {@link IFeatureReference} to the
	 * {@link ILocalSite}.
	 * 
	 * @param localSite
	 *            The {@link ILocalSite} to download to
	 * @param remoteFeatureReference
	 *            The {@link IFeatureReference} of the feature from the remote
	 *            site
	 * @param monitor
	 *            The {@link IProgressMonitor} to use on action
	 * @return whether an update of the {@link ILocalSite} has taken place
	 * @throws CoreException
	 * @throws InvocationTargetException
	 */
	private boolean downloadForNewFeature(final ILocalSite localSite,
			IFeatureReference remoteFeatureReference, IProgressMonitor monitor)
			throws CoreException, InvocationTargetException {
		logger.info("--- - downloading new version..."); //$NON-NLS-1$
		IConfiguredSite targetSite = localSite.getCurrentConfiguration()
				.getConfiguredSites()[0];
		IInstallFeatureOperation installFeatureOperation = OperationsManager
				.getOperationFactory().createInstallOperation(targetSite,
						remoteFeatureReference.getFeature(monitor), null, null,
						null);
		boolean downloaded = installFeatureOperation.execute(monitor, null);
		logger.info("--- - ... done"); //$NON-NLS-1$
		updateLog.addInstalledFeature(remoteFeatureReference,
				"new feature (download succeeded: " + downloaded + ")"); //$NON-NLS-1$ //$NON-NLS-2$
		return downloaded;
	}

	/**
	 * Proceeds the actual download of an {@link IFeatureReference} to the
	 * {@link ILocalSite}.
	 * 
	 * @param monitor
	 *            The {@link IProgressMonitor} to use on action
	 * @param localSite
	 *            The {@link ILocalSite} to download to
	 * @param remoteFeatureReference
	 *            The {@link IFeatureReference} of the feature from the remote
	 *            site
	 * @return whether an update of the {@link ILocalSite} has taken place
	 * @throws CoreException
	 * @throws InvocationTargetException
	 */
	private boolean downloadForExistingFeature(IProgressMonitor monitor,
			ILocalSite localSite, IFeatureReference localFeatureReference,
			IFeatureReference remoteFeatureReference) throws CoreException,
			InvocationTargetException {
		IConfiguredSite targetSite = localSite.getCurrentConfiguration()
				.getConfiguredSites()[0];
		IFeature localFeature = localFeatureReference.getFeature(monitor);
		logger.info("--- - unconfiguring old version..."); //$NON-NLS-1$
		IUnconfigFeatureOperation unconfigureFeatureOperation = OperationsManager
				.getOperationFactory().createUnconfigOperation(targetSite,
						localFeature);
		boolean unconfigured = false;
		try {
			unconfigured = unconfigureFeatureOperation.execute(monitor, null);
		}
		catch (Throwable throwable) {
			logger.info("--- - " + throwable.getMessage()); //$NON-NLS-1$
		}
		if (!unconfigured) {
			logger.info("--- - ... unconfiguring failed"); //$NON-NLS-1$
		}
		else {
			logger.info("--- - ... done"); //$NON-NLS-1$
		}
		localSite.save();
		logger.info("--- - downloading new version"); //$NON-NLS-1$
		IInstallFeatureOperation installFeatureOperation = OperationsManager
				.getOperationFactory().createInstallOperation(targetSite,
						remoteFeatureReference.getFeature(monitor), null, null,
						null);
		boolean downloaded = installFeatureOperation.execute(monitor, null);
		localSite.save();
		logger.info("--- - ... done"); //$NON-NLS-1$
		updateLog.addInstalledFeature(remoteFeatureReference,
				"update for existing version [" //$NON-NLS-1$
						+ localFeatureReference.getVersionedIdentifier()
								.getVersion() + "] (download succeeded: " //$NON-NLS-1$
						+ downloaded + ")"); //$NON-NLS-1$
		return downloaded;
	}

	/**
	 * Inspects the update site for it's provided features.
	 * 
	 * @param monitor
	 *            The {@link IProgressMonitor} to use on action
	 * @param updateSiteString
	 *            The update site {@link String}
	 * @return the {@link List} of {@link ISiteFeatureReference} available at
	 *         the remote site
	 * @throws CoreException
	 * @throws MalformedURLException
	 */
	private List<ISiteFeatureReference> getRemoteFeatures(
			IProgressMonitor monitor, String updateSiteString)
			throws CoreException, MalformedURLException {
		logger
				.info("--- getting remote features from update site [" + updateSiteString + "]"); //$NON-NLS-1$ //$NON-NLS-2$
		ISite updateSite = SiteManager.getSite(new URL(updateSiteString),
				monitor);
		List<ISiteFeatureReference> remoteFeatureReferences = Arrays
				.asList(updateSite.getFeatureReferences());
		for (ISiteFeatureReference remoteFeatureReference : remoteFeatureReferences) {
			logger.info("--- - found remote feature [" //$NON-NLS-1$
					+ remoteFeatureReference.getVersionedIdentifier()
							.getIdentifier()
					+ "] (" + remoteFeatureReference.getName() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return remoteFeatureReferences;
	}

	/**
	 * Inspects the given {@link ILocalSite} site for it's installed features.
	 * 
	 * @param monitor
	 *            The {@link IProgressMonitor} to use on action
	 * @param localSite
	 *            The {@link ILocalSite}
	 * @return the {@link Map} of {@link IFeatureReference} accessable by it's
	 *         {@link VersionedIdentifier} available at the local site
	 * @throws CoreException
	 * @throws InvocationTargetException
	 */
	public Map<String, IFeatureReference> getInstalledFeatures(
			IProgressMonitor monitor, ILocalSite localSite)
			throws CoreException, InvocationTargetException {
		logger.info("--- getting local installed features"); //$NON-NLS-1$
		Map<String, IFeatureReference> map = new HashMap<String, IFeatureReference>();
		for (IFeatureReference featureReference : localSite
				.getCurrentConfiguration().getConfiguredSites()[0]
				.getFeatureReferences()) {
			logger
					.info("--- - found local feature [" //$NON-NLS-1$
							+ featureReference.getVersionedIdentifier()
									.getIdentifier()
							+ "] - version [" + featureReference.getVersionedIdentifier().getVersion() + "] (" + featureReference.getName() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			map.put(featureReference.getVersionedIdentifier().getIdentifier(),
					featureReference);
		}
		return map;
	}

	/**
	 * Inspects the given {@link ILocalSite} site for it's installed features.
	 * 
	 * @param monitor
	 *            The {@link IProgressMonitor} to use on action
	 * @param localSite
	 *            The {@link ILocalSite}
	 * @return the {@link Map} of {@link IFeatureReference} accessable by it's
	 *         {@link VersionedIdentifier} available at the local site
	 * @throws CoreException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("deprecation")
	public List<IFeatureReference> removeDuplicateFeaturesFromDisc(
			IProgressMonitor monitor, ILocalSite localSite)
			throws CoreException, InvocationTargetException {
		List<IFeatureReference> removedFeatures = new ArrayList<IFeatureReference>();
		Map<String, IFeatureReference> featureMap = new HashMap<String, IFeatureReference>();
		logger.info("--- removing local duplicate features"); //$NON-NLS-1$
		for (IFeatureReference featureReference : localSite
				.getCurrentConfiguration().getConfiguredSites()[0]
				.getFeatureReferences()) {
			if (featureMap.containsKey(featureReference
					.getVersionedIdentifier().getIdentifier())) {
				logger.info("--- - identifying duplicate for local feature [" //$NON-NLS-1$
						+ featureReference.getVersionedIdentifier()
								.getIdentifier()
						+ "] (" + featureReference.getName() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
				IFeatureReference mappedFeatureReference = featureMap
						.get(featureReference.getVersionedIdentifier()
								.getIdentifier());
				IFeatureReference featureReferenceToUninstall;
				if (isGreaterThan(mappedFeatureReference
						.getVersionedIdentifier(), featureReference
						.getVersionedIdentifier())) {
					// remove the duplicate version
					featureReferenceToUninstall = featureReference;
				}
				else {
					// replace list entry...
					featureMap.put(featureReference.getVersionedIdentifier()
							.getIdentifier(), featureReference);
					// and remove the former version
					featureReferenceToUninstall = mappedFeatureReference;
				}
				IFeature featureToUninstall = featureReferenceToUninstall
						.getFeature(monitor);
				logger
						.info("--- - * unconfiguring old version [" + featureReferenceToUninstall.getVersionedIdentifier().getVersion().toString() + "]"); //$NON-NLS-1$ //$NON-NLS-2$
				localSite.getCurrentConfiguration().getConfiguredSites()[0]
						.unconfigure(featureToUninstall);
				logger
						.info("--- - * removing old version [" + featureReferenceToUninstall.getVersionedIdentifier().getVersion().toString() + "]"); //$NON-NLS-1$ //$NON-NLS-2$
				localSite.getCurrentConfiguration().getConfiguredSites()[0]
						.remove(featureToUninstall, monitor);
				localSite.save();
				File featureToUninstallResource = new Path(
						featureReferenceToUninstall.getURL().getPath())
						.toFile();
				logger.info("--- - * removing feature [" //$NON-NLS-1$
						+ featureReferenceToUninstall.getVersionedIdentifier()
								.getIdentifier()
						+ "] from disc - resource is [" //$NON-NLS-1$
						+ featureToUninstallResource.getAbsolutePath() + "]"); //$NON-NLS-1$
				String removeOperationMessage = removeResource(featureToUninstallResource);
				removedFeatures.add(featureReferenceToUninstall);
				updateLog
						.addRemovedFeature(
								featureReference,
								"updated by feature version [" + featureMap.get(featureReferenceToUninstall.getVersionedIdentifier().getIdentifier()).getVersionedIdentifier().getVersion() + "] - " + removeOperationMessage);//$NON-NLS-1$ //$NON-NLS-2$
			}
			else {
				featureMap.put(featureReference.getVersionedIdentifier()
						.getIdentifier(), featureReference);
			}
		}
		return removedFeatures;
	}

	/**
	 * Removes unconfigured bundles, i.e. bundles that are not referenced by a
	 * feature, permanantly from the disk.
	 * 
	 * @param monitor
	 *            The {@link IProgressMonitor} to use
	 * @param installedFeatureMap
	 *            The {@link Map} of installed features
	 */
	@SuppressWarnings("deprecation")
	public List<Bundle> removeUnconfiguredBundlesFromDisc(
			IProgressMonitor monitor,
			Map<String, IFeatureReference> installedFeatureMap)
			throws CoreException, InvocationTargetException {
		List<Bundle> removedBundles = new ArrayList<Bundle>();
		logger.info("--- removing unconfigured installed bundles"); //$NON-NLS-1$
		if (!skipRemoveUnconfiguredBundles) {
			// get the list of installed bundles
			logger
					.debug("--- - identifying configured bundles from installed features"); //$NON-NLS-1$
			Map<String, IPluginEntry> configuredBundleMap = new HashMap<String, IPluginEntry>();
			for (IFeatureReference featureReference : installedFeatureMap
					.values()) {
				logger
						.debug("--- - * inspecting installed feature [" + featureReference.getVersionedIdentifier().getIdentifier() + "] - version [" + featureReference.getVersionedIdentifier().getVersion() + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				for (IPluginEntry configuredBundle : featureReference
						.getFeature(monitor).getPluginEntries()) {
					String bundleName = configuredBundle
							.getVersionedIdentifier().getIdentifier()
							+ "_" //$NON-NLS-1$
							+ configuredBundle.getVersionedIdentifier()
									.getVersion().toString();
					logger
							.debug("--- - * - indentified configured bundle [" + bundleName + "]"); //$NON-NLS-1$ //$NON-NLS-2$
					configuredBundleMap.put(bundleName, configuredBundle);
				}
			}
			logger.debug("--- - inspecting osgi bus for installed bundles"); //$NON-NLS-1$
			if (monitor != null) {
				monitor.beginTask("Removing unconfigured installed bundles", //$NON-NLS-1$
						IProgressMonitor.UNKNOWN);
			}
			for (Bundle bundle : Activator.getDefault().getInstalledBundles()) {
				StringBuffer bundleNameBuffer = new StringBuffer();
				bundleNameBuffer.append(bundle.getSymbolicName());
				bundleNameBuffer.append("_"); //$NON-NLS-1$
				bundleNameBuffer.append(bundle.getHeaders().get(
						"Bundle-Version")); //$NON-NLS-1$
				String bundleName = bundleNameBuffer.toString();
				logger.info("--- - * found bundle [" + bundleName + "]"); //$NON-NLS-1$ //$NON-NLS-2$
				if (configuredBundleMap.containsKey(bundleName)) {
					logger
							.debug("--- - * - bundle [" + bundleName + "] is configured"); //$NON-NLS-1$ //$NON-NLS-2$
					if (monitor != null) {
						monitor
								.subTask(Messages
										.getString("UpdateManager.subtask.found.bundle.message") //$NON-NLS-1$
										+ bundleName + "]"); //$NON-NLS-1$
					}
				}
				else {
					logger
							.info("--- - * - bundle [" + bundleName + "] is not configured"); //$NON-NLS-1$ //$NON-NLS-2$
					try {
						File bundleToUninstallResource = FileLocator
								.getBundleFile(bundle);
						if (monitor != null) {
							monitor
									.subTask(Messages
											.getString("UpdateManager.subtask.uninstall.bundle.message") //$NON-NLS-1$
											+ bundleName + "]"); //$NON-NLS-1$
						}
						logger
								.info("--- - * removing unconfigured installed bundle [" //$NON-NLS-1$
										+ bundleName
										+ "] from disc - resource is [" //$NON-NLS-1$
										+ bundleToUninstallResource
												.getAbsolutePath() + "]"); //$NON-NLS-1$
						logger
								.debug("--- - * - uninstalling bundle [" + bundleName + "]"); //$NON-NLS-1$ //$NON-NLS-2$
						bundle.uninstall();
						logger
								.debug("--- - * - bundle [" + bundleName + "] state after uninstalling: " + bundle.getState()); //$NON-NLS-1$ //$NON-NLS-2$
						logger
								.debug("--- - * - removing bundle [" + bundleName + "] from disc"); //$NON-NLS-1$ //$NON-NLS-2$
						String removeOperationMessage = removeResource(bundleToUninstallResource);
						removedBundles.add(bundle);
						updateLog.addRemovedBundle(bundle,
								removeOperationMessage);

					}
					catch (IOException exception) {
						exception.printStackTrace();
					}
					catch (BundleException exception) {
						exception.printStackTrace();
					}

				}
			}
		}
		else {
			logger.info("--- skipped removing unreferenced bundles"); //$NON-NLS-1$
		}
		return removedBundles;
	}

	/**
	 * Removes the given file from the installation directory. If the
	 * {@link File} is a file, it is deleted, if it is a directory, it is
	 * deleted recursivly with all it's content.
	 * 
	 * @param file
	 *            The {@link File} to delete
	 * @return the result message of the operation
	 */
	private String removeResource(File file) {
		if (!file.exists()) {
			logger
					.debug("--- - * - resource [" + file.getAbsolutePath() + "] does not exist"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			return "resource [" + file.getAbsolutePath() + "] does not exist"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		else {
			if (file.isDirectory()) {
				if (file.listFiles() != null) {
					for (File child : file.listFiles()) {
						removeResource(child);
					}
				}
			}
			boolean delete = file.delete();
			logger
					.debug("--- - * - removing resource [" + file.getAbsolutePath() + "] - succeeded: " + delete); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			if (!delete) {
				file.deleteOnExit();
				logger
						.debug("--- - * - will try to remove resource [" + file.getAbsolutePath() + "] on JVM exit"); //$NON-NLS-1$ //$NON-NLS-2$
				return "will try to remove resource [" + file.getAbsolutePath() //$NON-NLS-1$
						+ "] on JVM exit"; //$NON-NLS-1$
			}
			return "removing resource [" + file.getAbsolutePath() //$NON-NLS-1$
					+ "] succeeded"; //$NON-NLS-1$
		}
	}

	/**
	 * @return the downloadUpdate
	 */
	public boolean isDownloadUpdate() {
		return skipUpdate;
	}

	/**
	 * @param downloadUpdate
	 *            the downloadUpdate to set
	 */
	public void setDownloadUpdate(boolean downloadUpdate) {
		this.skipUpdate = downloadUpdate;
	}

	/**
	 * @return the skipNew
	 */
	public boolean isSkipNew() {
		return skipNew;
	}

	/**
	 * @param skipNew
	 *            the skipNew to set
	 */
	public void setSkipNew(boolean skipNew) {
		this.skipNew = skipNew;
	}

	/**
	 * @return the skipUpdate
	 */
	public boolean isSkipUpdate() {
		return skipUpdate;
	}

	/**
	 * @param skipUpdate
	 *            the skipUpdate to set
	 */
	public void setSkipUpdate(boolean skipUpdate) {
		this.skipUpdate = skipUpdate;
	}

	/**
	 * @return the skipRemoveUnconfiguredBundles
	 */
	public boolean isSkipRemoveUnconfiguredBundles() {
		return skipRemoveUnconfiguredBundles;
	}

	/**
	 * @param skipRemoveUnconfiguredBundles
	 *            the skipRemoveUnconfiguredBundles to set
	 */
	public void setSkipRemoveUnconfiguredBundles(
			boolean skipRemoveUnconfiguredBundles) {
		this.skipRemoveUnconfiguredBundles = skipRemoveUnconfiguredBundles;
	}

	/**
	 * @return the updateSiteStrings
	 */
	public String[] getUpdateSiteStrings() {
		return updateSiteStrings;
	}

	/**
	 * @return the versionComparator
	 */
	public Comparator<Version> getVersionComparator() {
		return versionComparator;
	}

	/**
	 * @param versionComparator
	 *            the versionComparator to set
	 */
	public void setVersionComparator(Comparator<Version> versionComparator) {
		this.versionComparator = versionComparator;
	}

	/**
	 * Converts an {@link VersionedIdentifier} to a {@link Version}.
	 * 
	 * @param versionIdentifier
	 *            The {@link VersionedIdentifier} to convert
	 * @return the corresponding {@link Version}
	 */
	@SuppressWarnings("deprecation")
	private Version toVersion(VersionedIdentifier versionIdentifier) {
		return new Version(versionIdentifier.getVersion().getMajorComponent(),
				versionIdentifier.getVersion().getMinorComponent(),
				versionIdentifier.getVersion().getServiceComponent(),
				versionIdentifier.getVersion().getQualifierComponent());
	}

	/**
	 * Pojo that represents a plugin, fragment or feature version.
	 * 
	 * @author Stefan Reichert
	 */
	public class Version {
		/** The major bit. */
		private final int major;

		/** The minor bit. */
		private final int minor;

		/** The micro bit. */
		private final int micro;

		/** The qualifier bit. */
		private final String qualifier;

		/**
		 * Constructor for <class>Version</class>.
		 */
		public Version(int major, int minor, int micro, String qualifier) {
			super();
			this.major = major;
			this.minor = minor;
			this.micro = micro;
			this.qualifier = qualifier;
		}

		/**
		 * @return the major
		 */
		public int getMajor() {
			return major;
		}

		/**
		 * @return the minor
		 */
		public int getMinor() {
			return minor;
		}

		/**
		 * @return the micro
		 */
		public int getMicro() {
			return micro;
		}

		/**
		 * @return the qualifier
		 */
		public String getQualifier() {
			return qualifier;
		}
	}
}
