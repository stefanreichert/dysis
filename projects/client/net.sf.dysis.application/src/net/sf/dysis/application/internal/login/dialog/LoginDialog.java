package net.sf.dysis.application.internal.login.dialog;

import java.util.Locale;

import net.sf.dysis.application.internal.Activator;
import net.sf.dysis.application.internal.Messages;
import net.sf.dysis.application.internal.login.LoginProposalProvider;
import net.sf.dysis.application.internal.login.LoginProposalStore;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.swtdesigner.ResourceManager;

/**
 * Represents the <i>login</i> dialog fetching user name and password from the
 * user.
 * 
 * @author Stefan Reichert
 */
public class LoginDialog extends TitleAreaDialog {

	public static final String KEY_LOGIN_USERNAME = "net.sf.dysis.application.LoginUsername"; //$NON-NLS-1$

	private static final String KEY_LOGIN_LANGUAGE = "net.sf.dysis.application.LoginLanguage"; //$NON-NLS-1$

	private Text textPassword;

	private Text textUsername;

	private String username;

	private String password;

	private Locale language;

	private ComboViewer comboViewerLanguage;

	private boolean showError;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public LoginDialog(Shell parentShell, boolean showError) {
		super(parentShell);
		this.showError = showError;
	}

	/**
	 * Create contents of the dialog
	 * 
	 * @param parent
	 */
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);
		final GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.widthHint = 353;
		container.setLayoutData(gridData);

		final Label labelUser = new Label(container, SWT.NONE);
		labelUser.setLayoutData(new GridData(120, SWT.DEFAULT));
		labelUser.setText(Messages.getString("LoginDialog.username.label")); //$NON-NLS-1$

		textUsername = new Text(container, SWT.BORDER);
		textUsername.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));
		textUsername.setText(Activator.getDefault().getPreferenceStore()
				.getString(KEY_LOGIN_USERNAME));

		final Label labelPassword = new Label(container, SWT.NONE);
		labelPassword.setLayoutData(new GridData(120, SWT.DEFAULT));
		labelPassword.setText(Messages.getString("LoginDialog.password.label")); //$NON-NLS-1$

		textPassword = new Text(container, SWT.PASSWORD | SWT.BORDER);
		textPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));

		final Label labelLanguage = new Label(container, SWT.NONE);
		labelLanguage.setText(Messages.getString("LoginDialog.language.label")); //$NON-NLS-1$

		comboViewerLanguage = new ComboViewer(container, SWT.READ_ONLY);
		comboViewerLanguage.setContentProvider(new ArrayContentProvider());
		comboViewerLanguage.setLabelProvider(new LabelProvider() {
			/** {@inheritDoc} */
			@Override
			public String getText(Object element) {
				Locale locale = (Locale) element;
				return locale.getDisplayLanguage();
			}
		});
		Combo combo = comboViewerLanguage.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		//
		comboViewerLanguage.setInput(new Locale[] { Locale.GERMAN,
				Locale.ENGLISH });
		initFieldAssist();
		if (showError) {
			setErrorMessage(Messages.getString("LoginDialog.login.failed.message")); //$NON-NLS-1$
		}
		else {
			setMessage(Messages.getString("LoginDialog.login.failed.message")); //$NON-NLS-1$
		}
		setTitle(Messages.getString("LoginDialog.dialog.title")); //$NON-NLS-1$
		setHelpAvailable(false);
		setTitleImage(ResourceManager.getPluginImage(Activator.getDefault(),
				"icons/login_dialog.gif")); //$NON-NLS-1$

		return area;
	}

	private void initFieldAssist() {
		try {
			// Setup postal code completion
			ContentProposalAdapter loginAdapter = new ContentProposalAdapter(
					textUsername, new TextContentAdapter(),
					new LoginProposalProvider(), null, null);
			loginAdapter.setAutoActivationDelay(200);
			loginAdapter.setLabelProvider(new LabelProvider() {

				/**
				 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
				 */
				@Override
				public String getText(Object element) {
					return ((IContentProposal) element).getContent();
				}

				/**
				 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
				 */
				@Override
				public Image getImage(Object element) {
					return ResourceManager.getPluginImage(Activator
							.getDefault(), "icons/login_proposal.gif"); //$NON-NLS-1$
				}
			});

			loginAdapter
					.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
		}
		catch (Throwable throwable) {
			throwable.printStackTrace();
		}

	}

	/**
	 * Create contents of the button bar
	 * 
	 * @param parent
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		// setup data and handling
		ModifyListener modifyListener = new ModifyListener() {
			/**
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			public void modifyText(ModifyEvent event) {
				getButton(IDialogConstants.OK_ID).setEnabled(
						textUsername.getText().length() > 0
								&& textPassword.getText().length() > 0
								&& !comboViewerLanguage.getSelection()
										.isEmpty());
			}
		};
		textUsername.addModifyListener(modifyListener);
		textPassword.addModifyListener(modifyListener);
		comboViewerLanguage.getCombo().addModifyListener(modifyListener);
		// set former values if present
		textUsername.setText(Activator.getDefault().getPreferenceStore()
				.getString(KEY_LOGIN_USERNAME));
		Locale language = new Locale(Activator.getDefault()
				.getPreferenceStore().getString(KEY_LOGIN_LANGUAGE));
		comboViewerLanguage.setSelection(new StructuredSelection(language));

		if (textUsername.getText().length() > 0) {
			// Focus password text as login failed before an
			textPassword.setFocus();
		}
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		LoginProposalStore.addProposal(textUsername.getText());
		username = textUsername.getText();
		password = textPassword.getText();
		language = (Locale) ((IStructuredSelection) comboViewerLanguage
				.getSelection()).getFirstElement();
		Activator.getDefault().getPreferenceStore().setValue(
				KEY_LOGIN_USERNAME, username);
		Activator.getDefault().getPreferenceStore().setValue(
				KEY_LOGIN_LANGUAGE, language.getLanguage());
		super.okPressed();
	}

	/**
	 * Return the initial size of the dialog
	 */
	protected Point getInitialSize() {
		return new Point(375, 225);
	}

	/**
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.getString("LoginDialog.shell.title")); //$NON-NLS-1$
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Locale getLanguage() {
		return language;
	}

}
