package net.sf.dysis.resource.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "net.sf.dysis.resource.ui"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

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
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
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

	/**
	 * Proceeds a lookup in the OSGi service registry for a service of the given
	 * {@link Class}. It is expected, that the requested service was already
	 * registered by the Spring DM <i>extender</i> bundle.
	 * 
	 * @param <ServiceType>
	 *            The service type
	 * @param clazz
	 *            The {@link Class} object of the service type
	 * @return The service of the given service type
	 */
	@SuppressWarnings("unchecked")
	public <ServiceType> ServiceType getService(Class<ServiceType> clazz) {
		ServiceReference serviceReference = getBundle().getBundleContext()
				.getServiceReference(clazz.getName());
		return (ServiceType) getBundle().getBundleContext().getService(
				serviceReference);
	}

}
