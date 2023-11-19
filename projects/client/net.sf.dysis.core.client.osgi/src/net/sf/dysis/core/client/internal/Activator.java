/**
 * Activator.java created on 27.02.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.core.client.internal;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * TODO Please describe what this class is for
 * 
 * @author Stefan Reichert
 */
public class Activator extends AbstractUIPlugin {
	/** The plug-in ID. */
	public static final String PLUGIN_ID = "net.sf.dysis.core.client.internal";

	/** The shared instance. */
	private static Activator plugin;

	/**
	 * Constructor for <class>Activator</class>.
	 */
	public Activator() {
	}

	/** {@inheritDoc} */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/** {@inheritDoc} */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
}
