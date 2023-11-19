package net.sf.dysis.planning.ui.wizard.project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.dysis.base.ui.wizard.IValidationPage;
import net.sf.dysis.base.ui.wizard.ValidationListener;
import net.sf.dysis.planning.ui.Activator;
import net.sf.dysis.planning.ui.Messages;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.nebula.widgets.datechooser.DateChooserCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.swtdesigner.ResourceManager;

public class ProjectWizardProjectPage extends WizardPage implements
		IValidationPage {

	/** Widget. */
	private Text textProjectName;

	/** Widget. */
	private Text textProjectDescription;

	/** Widget. */
	private DateChooserCombo dateChooserProjectStart;

	/** Widget. */
	private DateChooserCombo dateChooserProjectEnd;

	/** The <code>Stack</code> for errors occured in this page. */
	private List<String> errors;

	/** Error for missing project name. * */
	private static final String ERROR_MISSING_NAME = Messages.getString("ProjectWizardProjectPage.error.missing.name"); //$NON-NLS-1$

	/** Error for missing project description. * */
	private static final String ERROR_MISSING_DESCRIPTION = Messages.getString("ProjectWizardProjectPage.error.missing.description"); //$NON-NLS-1$

	/** Error for missing project start. * */
	private static final String ERROR_MISSING_START = Messages.getString("ProjectWizardProjectPage.error.missing.start"); //$NON-NLS-1$

	/** Error for missing project end. * */
	private static final String ERROR_MISSING_END = Messages.getString("ProjectWizardProjectPage.error.missing.end"); //$NON-NLS-1$

	/** The name. */
	private String name;

	/** The description. */
	private String description;

	/** The startDate. */
	private Date startDate;

	/** The endDate. */
	private Date endDate;

	/**
	 * Create the wizard
	 */
	public ProjectWizardProjectPage() {
		super("project.wizard.project"); //$NON-NLS-1$
		setTitle(Messages.getString("ProjectWizardProjectPage.title")); //$NON-NLS-1$
		setDescription(Messages.getString("ProjectWizardProjectPage.description")); //$NON-NLS-1$
		errors = new ArrayList<String>();
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator
				.getDefault(), "img/project_wizard.gif")); //$NON-NLS-1$
	}

	/**
	 * Create contents of the wizard
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);
		//
		setControl(container);

		final Label labelProjectName = new Label(container, SWT.NONE);
		labelProjectName.setLayoutData(new GridData(150, SWT.DEFAULT));
		labelProjectName.setText(Messages.getString("ProjectWizardProjectPage.project.name.label")); //$NON-NLS-1$

		textProjectName = new Text(container, SWT.BORDER);
		textProjectName
				.setToolTipText(Messages.getString("ProjectWizardProjectPage.project.name.description")); //$NON-NLS-1$
		final GridData gd_textProjectName = new GridData(SWT.FILL, SWT.CENTER,
				true, false);
		textProjectName.setLayoutData(gd_textProjectName);
		textProjectName.addModifyListener(new ValidationListener(this));

		final Label labelProjectDescription = new Label(container, SWT.NONE);
		final GridData gd_labelProjectDescription = new GridData(SWT.LEFT,
				SWT.TOP, false, false);
		gd_labelProjectDescription.widthHint = 150;
		labelProjectDescription.setLayoutData(gd_labelProjectDescription);
		labelProjectDescription.setText(Messages.getString("ProjectWizardProjectPage.project.description.label")); //$NON-NLS-1$

		textProjectDescription = new Text(container, SWT.BORDER | SWT.WRAP);
		textProjectDescription
				.setToolTipText(Messages.getString("ProjectWizardProjectPage.project.description.description")); //$NON-NLS-1$
		final GridData gd_textProjectDescription = new GridData(SWT.FILL,
				SWT.CENTER, true, false);
		gd_textProjectDescription.heightHint = 32;
		textProjectDescription.setLayoutData(gd_textProjectDescription);
		textProjectDescription.addModifyListener(new ValidationListener(this));

		final Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		final GridData gd_label = new GridData(SWT.FILL, SWT.CENTER, false,
				false, 2, 1);
		gd_label.verticalIndent = 5;
		label.setLayoutData(gd_label);

		final Label labelProjectStart = new Label(container, SWT.NONE);
		labelProjectStart.setText(Messages.getString("ProjectWizardProjectPage.project.start.label")); //$NON-NLS-1$

		dateChooserProjectStart = new DateChooserCombo(container, SWT.BORDER);
		dateChooserProjectStart
				.setToolTipText(Messages.getString("ProjectWizardProjectPage.project.start.description")); //$NON-NLS-1$
		dateChooserProjectStart.setGridVisible(false);
		final GridData gd_dateChooserProjectStart = new GridData(SWT.FILL,
				SWT.CENTER, true, false);
		dateChooserProjectStart.setLayoutData(gd_dateChooserProjectStart);
		dateChooserProjectStart.addModifyListener(new ValidationListener(this));

		final Label labelProjectEnd = new Label(container, SWT.NONE);
		labelProjectEnd.setText(Messages.getString("ProjectWizardProjectPage.project.end.label")); //$NON-NLS-1$

		dateChooserProjectEnd = new DateChooserCombo(container, SWT.BORDER);
		dateChooserProjectEnd
				.setToolTipText(Messages.getString("ProjectWizardProjectPage.project.end.description")); //$NON-NLS-1$
		dateChooserProjectEnd.setGridVisible(false);
		final GridData gd_dateChooserProjectEnd = new GridData(SWT.FILL,
				SWT.CENTER, true, false);
		dateChooserProjectEnd.setLayoutData(gd_dateChooserProjectEnd);
		dateChooserProjectEnd.addModifyListener(new ValidationListener(this));
		setPageComplete(false);
	}

	/**
	 * Checks the content and updates the <code>List</code> of errors.
	 */
	public void checkContents() {
		// Check project name
		if (textProjectName.getText().length() == 0
				&& !errors.contains(ERROR_MISSING_NAME)) {
			errors.add(ERROR_MISSING_NAME);
		}
		else if (textProjectName.getText().length() > 0) {
			errors.remove(ERROR_MISSING_NAME);
			name = textProjectName.getText();
		}
		// Check project description
		if (textProjectDescription.getText().length() == 0
				&& !errors.contains(ERROR_MISSING_DESCRIPTION)) {
			errors.add(ERROR_MISSING_DESCRIPTION);
		}
		else if (textProjectDescription.getText().length() > 0) {
			errors.remove(ERROR_MISSING_DESCRIPTION);
			description = textProjectDescription.getText();
		}
		// Check project start
		if (dateChooserProjectStart.getValue() == null
				&& !errors.contains(ERROR_MISSING_START)) {
			errors.add(ERROR_MISSING_START);
		}
		else if (dateChooserProjectStart.getValue() != null) {
			errors.remove(ERROR_MISSING_START);
			startDate = dateChooserProjectStart.getValue();
		}
		// Check project end
		if (dateChooserProjectEnd.getValue() == null
				&& !errors.contains(ERROR_MISSING_END)) {
			errors.add(ERROR_MISSING_END);
		}
		else if (dateChooserProjectEnd.getValue() != null) {
			errors.remove(ERROR_MISSING_END);
			endDate = dateChooserProjectEnd.getValue();
		}
		// check whether errors exist
		if (errors.isEmpty()) {
			// everything is fine if not
			setErrorMessage(null);
			setPageComplete(true);
		}
		else {
			// present first error message
			setErrorMessage(errors.get(0));
			setPageComplete(false);
		}
	}

	private Date dateOnly(Date date) {
		Calendar calendarOriginal = Calendar.getInstance();
		calendarOriginal.setTime(date);
		Calendar calendarModified = Calendar.getInstance();
		calendarModified.clear();
		calendarModified.set(calendarOriginal.get(Calendar.YEAR),
				calendarOriginal.get(Calendar.MONTH), calendarOriginal
						.get(Calendar.DATE));
		return calendarModified.getTime();
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
		return dateOnly(endDate);
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
		return dateOnly(startDate);
	}
}
