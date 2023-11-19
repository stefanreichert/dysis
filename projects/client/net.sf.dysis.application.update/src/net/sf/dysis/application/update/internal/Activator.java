/**
 * Activator.java created on 07.01.2010
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.application.update.internal;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The Activator for this bundle.
 * 
 * @author Stefan Reichert
 */
public class Activator extends Plugin {

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "net.sf.dysis.application.update"; //$NON-NLS-1$

	/** The singleton instance. */
	private static Activator activator;

	/** The singleton instance. */
	private BundleContext bundleContext;

	/** {@inheritDoc} */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		activator = this;
		bundleContext = context;
	}

	/** {@inheritDoc} */
	@Override
	public void stop(BundleContext context) throws Exception {
		bundleContext = null;
		activator = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return activator;
	}

	/**
	 * @return the list of currently installed bundles
	 */
	public List<Bundle> getInstalledBundles() {
		return Arrays.asList(bundleContext.getBundles());
	}

}
