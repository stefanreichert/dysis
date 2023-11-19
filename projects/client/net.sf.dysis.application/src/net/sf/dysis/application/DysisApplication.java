package net.sf.dysis.application;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.dysis.application.internal.Activator;
import net.sf.dysis.application.internal.ApplicationWorkbenchAdvisor;
import net.sf.dysis.application.internal.Messages;
import net.sf.dysis.application.internal.authentication.AuthenticationProvider;
import net.sf.dysis.application.internal.login.dialog.LoginDialog;
import net.sf.dysis.application.update.UpdateManager;
import net.sf.dysis.base.ui.authentication.AuthenticationException;
import net.sf.dysis.base.ui.authentication.IAuthenticationProvider;
import net.sf.dysis.base.ui.authentication.IAuthenticationToken;
import net.sf.dysis.base.ui.authentication.IAuthenticator;
import net.sf.dysis.resource.core.dto.PersonDTO;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

/**
 * This class controls all aspects of the application's execution
 */
public class DysisApplication implements IApplication {

	/** The username argument key. */
	private static final String USERNAME_ARGUMENT_KEY = "-dysis.username"; //$NON-NLS-1$

	/** The password argument key. */
	private static final String PASSWORD_ARGUMENT_KEY = "-dysis.password"; //$NON-NLS-1$

	/** The language argument key. */
	private static final String LANGUAGE_ARGUMENT_KEY = "-dysis.language"; //$NON-NLS-1$

	/** The language argument key. */
	private static final String UPDATE_SITE_ARGUMENT_KEY = "-dysis.updatesite"; //$NON-NLS-1$

	/** The eclipse language argument key. */
	private static final String ECLIPSE_LANGUAGE_KEY = "-nl"; //$NON-NLS-1$

	/** The eclipse property for the restart arguments. */
	private static final String ECLIPSE_EXITDATA_PROPERTY = "eclipse.exitdata"; //$NON-NLS-1$

	/** This class' PluginLogger instance. */
	private static Logger logger = Logger.getLogger(DysisApplication.class);

	/** This {@link Application}s {@link IAuthenticationToken}. */
	private IAuthenticationToken<String, PersonDTO> authenticationToken;

	/**
	 * @return the authenticationToken
	 */
	public IAuthenticationToken<String, PersonDTO> getAuthenticationToken() {
		return authenticationToken;
	}

	/** The shared instance. */
	private static DysisApplication application;

	/** The arguments this applications was started with. */
	private Map<String, String> arguments;

	/**
	 * The enumeration of possible login states.
	 * 
	 * @author Stefan Reichert
	 * @version $Rev$, $Date: 2008/07/07 19:53:55 $
	 */
	private enum AuthenticationResult {
		ABORT, INITIAL, FAILED, SUCCESS, RESTART
	};

	/**
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) throws Exception {
		application = this;
		printStartupMessage();
		Display display = PlatformUI.createDisplay();
		Shell shell = new Shell(display);
		// read the application arguments
		arguments = readArguments();
		if (arguments.containsKey(UPDATE_SITE_ARGUMENT_KEY)) {
			// check for updates
			UpdateManager updateManager = new UpdateManager(Messages
					.getString("DysisApplication.title"), arguments
					.get(UPDATE_SITE_ARGUMENT_KEY));
			if (updateManager.checkForUpdates(shell)) {
				MessageDialog
						.openInformation(
								shell,
								Messages
										.getString("DysisApplication.update.dialog.title"), //$NON-NLS-1$
								Messages
										.getString("DysisApplication.update.dialog.message")); //$NON-NLS-1$
				// if updates have taken place, restart the application
				return IApplication.EXIT_RESTART;
			}
		}
		try {
			AuthenticationResult authenticationResult = authenticate(shell);
			if (authenticationResult == AuthenticationResult.SUCCESS) {
				printStartupFinishedMessage();
				int returnCode = PlatformUI.createAndRunWorkbench(display,
						new ApplicationWorkbenchAdvisor());
				if (returnCode == PlatformUI.RETURN_RESTART) {
					return IApplication.EXIT_RESTART;
				}
			}
			else if (authenticationResult == AuthenticationResult.RESTART) {
				return IApplication.EXIT_RELAUNCH;
			}
			return IApplication.EXIT_OK;
		}
		finally {
			display.dispose();
		}
	}

	/**
	 * Prints the startup information.
	 */
	private void printStartupMessage() {
		logger.info("--------------------------------------------------------"); //$NON-NLS-1$
		logger.info("Starting Application"); //$NON-NLS-1$
		logger.info("--- Stefan Reichert - Dysis TimeTracking "); //$NON-NLS-1$
		logger.info("--------------------------------------------------------"); //$NON-NLS-1$
		logger.info("Language"); //$NON-NLS-1$
		logger.info("--- " + Locale.getDefault().getDisplayLanguage()); //$NON-NLS-1$
		logger.info("--------------------------------------------------------"); //$NON-NLS-1$
		logger.info("Client                                                  "); //$NON-NLS-1$
		logger.info("--- version: " //$NON-NLS-1$
				+ Activator.getDefault().getBundle().getHeaders().get(
						"Bundle-Version")); //$NON-NLS-1$
		logger.info("--------------------------------------------------------"); //$NON-NLS-1$
		logger.info("Server                                                  "); //$NON-NLS-1$
		logger.info("--- version: unknown                                    "); //$NON-NLS-1$
		logger.info("--- build date: unknown                                 "); //$NON-NLS-1$
		logger.info("--- milestone: unknown                                  "); //$NON-NLS-1$
		logger.info("--------------------------------------------------------"); //$NON-NLS-1$

	}

	/**
	 * Prints the startup information.
	 */
	private void printStartupFinishedMessage() {
		logger.info("Application Started"); //$NON-NLS-1$
		logger.info("--- Stefan Reichert - Dysis TimeTracking "); //$NON-NLS-1$
		logger.info("--------------------------------------------------------"); //$NON-NLS-1$
	}

	/**
	 * Provides a dialog for authentication. If authentication succeeds, the
	 * corresponding tokens are set.
	 * 
	 * @return whether authentication succeeded
	 */
	private AuthenticationResult authenticate(Shell shell)
			throws AuthenticationException {
		AuthenticationResult result = AuthenticationResult.INITIAL;
		logger.info("Authentication"); //$NON-NLS-1$
		result = authenticateWithArguments(shell);
		if (result != AuthenticationResult.SUCCESS) {
			result = authenticateWithDialog(shell, result);
		}
		logger.info("--------------------------------------------------------"); //$NON-NLS-1$
		return result;
	}

	/**
	 * Proceeds an authentication using the Login dialog.
	 * 
	 * @param shell
	 *            The {@link Shell} to use
	 * @param result
	 *            The initial result
	 * @return The result after authentication with dialog
	 */
	private AuthenticationResult authenticateWithArguments(Shell shell) {
		AuthenticationResult result = AuthenticationResult.INITIAL;
		// check arguments for auto login
		if (arguments.containsKey(USERNAME_ARGUMENT_KEY)
				&& arguments.containsKey(PASSWORD_ARGUMENT_KEY)
				&& arguments.containsKey(LANGUAGE_ARGUMENT_KEY)) {
			logger
					.info("--- program arguments found for automatic authentication"); //$NON-NLS-1$
			String username = arguments.get(USERNAME_ARGUMENT_KEY);
			String password = arguments.get(PASSWORD_ARGUMENT_KEY);
			Locale language = new Locale(arguments.get(LANGUAGE_ARGUMENT_KEY));
			if (checkForRestart(language)) {
				prepareRestart(username, password, language);
				return AuthenticationResult.RESTART;
			}
			logger.info("--- authenticating user: " + username); //$NON-NLS-1$
			result = doAuthenticate(shell, username, password);
		}
		return result;
	}

	/**
	 * Proceeds an authentication using the Login dialog.
	 * 
	 * @param shell
	 *            The {@link Shell} to use
	 * @param result
	 *            The initial result
	 * @return The result after authentication with dialog
	 */
	private AuthenticationResult authenticateWithDialog(Shell shell,
			AuthenticationResult result) {
		while ((result == AuthenticationResult.FAILED)
				|| (result == AuthenticationResult.INITIAL)) {
			logger.info("--- awaiting login information"); //$NON-NLS-1$
			final LoginDialog loginDialog = new LoginDialog(shell,
					result == AuthenticationResult.FAILED);
			if (loginDialog.open() == Dialog.OK) {
				String username = loginDialog.getUsername();
				String password = loginDialog.getPassword();
				Locale language = loginDialog.getLanguage();
				if (checkForRestart(language)) {
					prepareRestart(username, password, language);
					return AuthenticationResult.RESTART;
				}
				logger.info("--- authenticating user: " + username); //$NON-NLS-1$
				result = doAuthenticate(shell, username, password);
			}
			else {
				logger.error("--- aborted"); //$NON-NLS-1$
				result = AuthenticationResult.ABORT;
			}
		}
		return result;
	}

	/**
	 * Proceeds the actual authentication using a usernam, a password and the
	 * {@link IAuthenticationProvider} of this application.
	 * 
	 * @param shell
	 *            The shell to use
	 * @param username
	 *            The username to authenticate
	 * @param password
	 *            The password for authentication
	 * @return The result after authentication
	 */
	private AuthenticationResult doAuthenticate(Shell shell,
			final String username, final String password) {
		AuthenticationResult result;
		try {
			ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(
					shell);
			progressMonitorDialog.run(true, false, new IRunnableWithProgress() {
				/** {@inheritDoc} */
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					monitor
							.beginTask(
									Messages
											.getString("DysisApplication.authentication.task.message"), //$NON-NLS-1$
									IProgressMonitor.UNKNOWN);
					try {
						authenticationToken = IAuthenticator.Factory
								.getAuthenticator().authenticate(username,
										password, new AuthenticationProvider());
					}
					catch (AuthenticationException authenticationException) {
						throw new InvocationTargetException(
								authenticationException,
								authenticationException.getMessage());
					}
					finally {
						monitor.done();
					}
				}
			});
			result = AuthenticationResult.SUCCESS;
			logger.info("--- succeeded; greetings " //$NON-NLS-1$
					+ authenticationToken.getUsername() + "!"); //$NON-NLS-1$
		}
		catch (Throwable throwable) {
			logger.info("--- failed: " + throwable.getMessage()); //$NON-NLS-1$
			logger.debug("--- message: ", throwable);
			result = AuthenticationResult.FAILED;
		}
		return result;
	}

	/**
	 * Reads the {@link Map} of program arguments defined in the config. The
	 * arguments are expected to be a key-value pair. Keys start with "-",
	 * values do not. Single arguments get the value <code>null</code>.
	 * 
	 * @return the {@link Map} of program arguments defined in the config
	 */
	private Map<String, String> readArguments() {
		Map<String, String> programArgumentMap = new HashMap<String, String>();
		String[] programArguments = Platform.getApplicationArgs();
		for (int index = 0; index < programArguments.length; index++) {
			String argument = programArguments[index];
			if ((index + 1) < programArguments.length) {
				String nextArgument = programArguments[index + 1];
				// see whether it is key-value or single arguments
				if (nextArgument.startsWith("-")) { //$NON-NLS-1$
					// next argument is a key, too -> single argument
					programArgumentMap.put(argument, null);
				}
				else {
					// next argument is a value -> key-value pair
					programArgumentMap.put(argument, nextArgument);
					index++;
				}
			}
			else {
				// no further argument -> single argument
				programArgumentMap.put(argument, null);
			}
		}
		return programArgumentMap;
	}

	/**
	 * @return whether a locale switch has to be performed
	 */
	private boolean checkForRestart(Locale locale) {
		boolean isRestartNecessary = !Locale.getDefault().getLanguage().equals(
				locale.getLanguage());
		if (isRestartNecessary) {
			logger.info("--- locale mismatch detected"); //$NON-NLS-1$
		}
		return isRestartNecessary;
	}

	/**
	 * Prepare a restart using the login information to avoid a reappearing
	 * login dialog.
	 * 
	 * @param username
	 *            The username entered
	 * @param password
	 *            The password entered
	 * @param language
	 *            The {@link Locale} selected
	 */
	private void prepareRestart(String username, String password,
			Locale language) {
		logger.info("Restarting Application"); //$NON-NLS-1$
		logger.info("--- setting restart parameters"); //$NON-NLS-1$
		// update the commandline arguments witch username, password and locale
		Map<String, String> restartArgumentMap = new HashMap<String, String>();
		restartArgumentMap.put(USERNAME_ARGUMENT_KEY, username);
		restartArgumentMap.put(PASSWORD_ARGUMENT_KEY, password);
		restartArgumentMap.put(LANGUAGE_ARGUMENT_KEY, language.getLanguage());
		restartArgumentMap.put(ECLIPSE_LANGUAGE_KEY, language.getLanguage());
		// set the eclipse restart property
		StringBuffer restartArguments = new StringBuffer();
		restartArguments.append("${eclipse.vm}\n"); //$NON-NLS-1$
		for (Entry<String, String> entry : restartArgumentMap.entrySet()) {
			restartArguments.append(entry.getKey());
			restartArguments.append("\n"); //$NON-NLS-1$
			restartArguments.append(entry.getValue());
			restartArguments.append("\n"); //$NON-NLS-1$
		}
		System.getProperties().setProperty(ECLIPSE_EXITDATA_PROPERTY,
				restartArguments.toString());
	}

	/**
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		application = null;
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null)
			return;
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
		});
	}

	/**
	 * @return the dysisApplication
	 */
	public static DysisApplication getDefault() {
		return application;
	}
}
