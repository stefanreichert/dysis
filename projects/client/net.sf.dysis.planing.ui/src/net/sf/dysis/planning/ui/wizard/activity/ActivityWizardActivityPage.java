package net.sf.dysis.planning.ui.wizard.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sf.dysis.base.ui.dataprovider.key.ICollectionKey;
import net.sf.dysis.base.ui.dataprovider.registry.Registry;
import net.sf.dysis.base.ui.wizard.IValidationPage;
import net.sf.dysis.base.ui.wizard.ValidationListener;
import net.sf.dysis.planing.core.dto.ProjectDTO;
import net.sf.dysis.planning.ui.Activator;
import net.sf.dysis.planning.ui.Messages;
import net.sf.dysis.planning.ui.dataprovider.ProjectDataProvider;
import net.sf.dysis.planning.ui.wizard.ActivityWizard;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.swtdesigner.ResourceManager;

public class ActivityWizardActivityPage extends WizardPage implements
		IValidationPage {

	/** Widget. */
	private Text textActivityName;

	/** Widget. */
	private Text textActivityDescription;

	/** Widget. */
	private ComboViewer comboViewer;

	/** The <code>Stack</code> for errors occured in this page. */
	private SortedSet<String> errors;

	/** Error for missing Activity name. * */
	private static final String ERROR_MISSING_NAME = Messages.getString("ActivityWizardActivityPage.error.missing.name"); //$NON-NLS-1$

	/** Error for missing Activity description. * */
	private static final String ERROR_MISSING_DESCRIPTION = Messages.getString("ActivityWizardActivityPage.error.missing.description"); //$NON-NLS-1$

	/** Error for missing project. * */
	private static final String ERROR_MISSING_PROJECT = Messages.getString("ActivityWizardActivityPage.error.missing.project"); //$NON-NLS-1$

	/** The name. */
	private String name;

	/** The description. */
	private String description;

	/** The startDate. */
	private Date startDate;

	/** The endDate. */
	private Date endDate;

	/** The project. */
	private ProjectDTO project;

	/**
	 * Create the wizard
	 */
	public ActivityWizardActivityPage() {
		super("Activity.wizard.Activity"); //$NON-NLS-1$
		setTitle(Messages.getString("ActivityWizardActivityPage.title")); //$NON-NLS-1$
		setDescription(Messages.getString("ActivityWizardActivityPage.description")); //$NON-NLS-1$
		errors = new TreeSet<String>();
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator
				.getDefault(), "img/Activity_wizard.gif")); //$NON-NLS-1$
	}

	/**
	 * Create contents of the wizard
	 * 
	 * @param parent
	 */
	@SuppressWarnings("unchecked")
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);
		//
		setControl(container);

		final Label labelActivityName = new Label(container, SWT.NONE);
		labelActivityName.setLayoutData(new GridData(150, SWT.DEFAULT));
		labelActivityName.setText(Messages.getString("ActivityWizardActivityPage.name.label")); //$NON-NLS-1$

		textActivityName = new Text(container, SWT.BORDER);
		textActivityName
				.setToolTipText(Messages.getString("ActivityWizardActivityPage.name.tooltip")); //$NON-NLS-1$
		final GridData gd_textActivityName = new GridData(SWT.FILL, SWT.CENTER,
				true, false);
		textActivityName.setLayoutData(gd_textActivityName);
		textActivityName.addModifyListener(new ValidationListener(this));

		final Label labelActivityDescription = new Label(container, SWT.NONE);
		final GridData gd_labelActivityDescription = new GridData(SWT.LEFT,
				SWT.TOP, false, false);
		gd_labelActivityDescription.widthHint = 150;
		labelActivityDescription.setLayoutData(gd_labelActivityDescription);
		labelActivityDescription.setText(Messages.getString("ActivityWizardActivityPage.description.label")); //$NON-NLS-1$

		textActivityDescription = new Text(container, SWT.BORDER | SWT.WRAP);
		textActivityDescription
				.setToolTipText(Messages.getString("ActivityWizardActivityPage.description.description")); //$NON-NLS-1$
		final GridData gd_textActivityDescription = new GridData(SWT.FILL,
				SWT.CENTER, true, false);
		gd_textActivityDescription.heightHint = 32;
		textActivityDescription.setLayoutData(gd_textActivityDescription);
		textActivityDescription.addModifyListener(new ValidationListener(this));

		final Label label_two = new Label(container, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		final GridData gd_label_two = new GridData(SWT.FILL, SWT.CENTER, false,
				false, 2, 1);
		gd_label_two.verticalIndent = 5;
		label_two.setLayoutData(gd_label_two);

		final Label labelProjekt = new Label(container, SWT.NONE);
		labelProjekt.setText(Messages.getString("ActivityWizardActivityPage.project.label")); //$NON-NLS-1$

		comboViewer = new ComboViewer(container, SWT.READ_ONLY);
		comboViewer.setContentProvider(new ArrayContentProvider());
		comboViewer.setLabelProvider(new LabelProvider() {

			/**
			 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
			 */
			@Override
			public String getText(Object element) {
				return ((ProjectDTO) element).getName();
			}

		});
		comboViewer.getCombo().addSelectionListener(
				new ValidationListener(this));
		Combo combo = comboViewer.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		ActivityWizard activityWizard = (ActivityWizard) getWizard();
		List<ProjectDTO> projects = new ArrayList<ProjectDTO>();
		if (activityWizard.getActivityWizardHandler().getActivityProject() == null) {
			projects.addAll(Registry.getRegistry().lookupDataProvider(
					ProjectDataProvider.TYPE).getDataCollection(
					ICollectionKey.ALL));
		}
		else {
			projects.add(activityWizard.getActivityWizardHandler()
					.getActivityProject());
		}
		comboViewer.setInput(projects);
		setPageComplete(false);
	}

	/**
	 * @see net.sf.dysis.base.ui.wizard.IValidationPage#checkContents()
	 */
	public void checkContents() {
		// Check Activity name
		if (textActivityName.getText().length() == 0) {
			errors.add(ERROR_MISSING_NAME);
			name = null;
		}
		else if (textActivityName.getText().length() > 0) {
			errors.remove(ERROR_MISSING_NAME);
			name = textActivityName.getText();
		}
		// Check Activity description
		if (textActivityDescription.getText().length() == 0) {
			errors.add(ERROR_MISSING_DESCRIPTION);
			description = null;
		}
		else if (textActivityDescription.getText().length() > 0) {
			errors.remove(ERROR_MISSING_DESCRIPTION);
			description = textActivityDescription.getText();
		}
		// Check project
		if (comboViewer.getSelection().isEmpty()) {
			errors.add(ERROR_MISSING_PROJECT);
			project = null;
		}
		else {
			errors.remove(ERROR_MISSING_PROJECT);
			IStructuredSelection selection = (IStructuredSelection) comboViewer
					.getSelection();
			project = (ProjectDTO) selection.getFirstElement();
		}
		if (errors.isEmpty()) {
			setErrorMessage(null);
			setPageComplete(true);
		}
		else {
			setErrorMessage(errors.first());
			setPageComplete(false);
		}
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @return the project
	 */
	public ProjectDTO getProject() {
		return project;
	}

}
