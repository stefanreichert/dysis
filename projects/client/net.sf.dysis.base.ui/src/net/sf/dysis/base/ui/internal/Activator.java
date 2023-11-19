package net.sf.dysis.base.ui.internal;

import net.sf.dysis.base.ui.internal.logging.LogTransfer;
import net.sf.dysis.base.ui.internal.navigator.NavigatorElementAdapterFactory;
import net.sf.dysis.base.ui.logging.IPluginLogger;
import net.sf.dysis.base.ui.logging.PluginLogger;
import net.sf.dysis.base.ui.navigator.NavigatorElement;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "net.sf.dysis.base.ui"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private LogTransfer logTransfer;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		// Register ApadterFactory for elements of the navigator
		IAdapterManager manager = Platform.getAdapterManager();
		IAdapterFactory factory = new NavigatorElementAdapterFactory();
		manager.registerAdapters(factory, NavigatorElement.class);
		// Register the log transfer to enabled Log4J logging
		logTransfer = new LogTransfer();
		Platform.addLogListener(logTransfer);
		IPluginLogger logger = PluginLogger.getLogger(this);
		logger.info("Dysis Anwendung wurde gestartet"); //$NON-NLS-1$
		logger
				.error("Die Verbindung zum Server konnte nicht hergestellt werden"); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		Platform.removeLogListener(logTransfer);
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
