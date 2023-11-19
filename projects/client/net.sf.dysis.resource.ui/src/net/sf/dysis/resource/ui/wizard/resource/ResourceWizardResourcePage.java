package net.sf.dysis.resource.ui.wizard.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.dysis.base.ui.wizard.IValidationPage;
import net.sf.dysis.base.ui.wizard.ValidationListener;
import net.sf.dysis.resource.ui.Activator;
import net.sf.dysis.resource.ui.Messages;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.nebula.widgets.datechooser.DateChooserCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.swtdesigner.ResourceManager;

public class ResourceWizardResourcePage extends WizardPage implements
		IValidationPage {

	private Text textResourceSurname;

	/** Widget. */
	private Text textResourceID;

	/** Widget. */
	private Text textResourceName;

	/** Widget. */
	private DateChooserCombo dateResourceEmployment;

	/** Widget. */
	private Text textResourceHoursPerWeek;

	/** Widget. */
	private Button buttonActive;

	/** The <code>Stack</code> for errors occured in this page. */
	private List<String> errors;

	private boolean canFlipToNextPage;

	private boolean active;

	private Integer hoursPerWeek;

	private Date employmentDate;

	private String surname;

	private String name;

	private String id;


	/** Error for missing resource description. * */
	private static final String ERROR_MISSING_ID = Messages.getString("ResourceWizardResourcePage.error.missing.id"); //$NON-NLS-1$

	/** Error for missing resource name. */
	private static final String ERROR_MISSING_SURNAME = Messages.getString("ResourceWizardResourcePage.error.missing.surname"); //$NON-NLS-1$
	
	/** Error for missing resource name. * */
	private static final String ERROR_MISSING_NAME = Messages.getString("ResourceWizardResourcePage.error.missing.name"); //$NON-NLS-1$

	/** Error for missing resource start. * */
	private static final String ERROR_MISSING_EMPLOYMENT_DATE = Messages.getString("ResourceWizardResourcePage.error.missing.employment.date"); //$NON-NLS-1$

	/** Error for missing hours per week value. */
	private static final String ERROR_MISSING_WORKING_HOURS = Messages.getString("ResourceWizardResourcePage.error.missing.week.hours"); //$NON-NLS-1$

	/** Error for invalid hours per week value. */
	private static final String ERROR_INVALID_WORKING_HOURS = Messages.getString("ResourceWizardResourcePage.error.invalid.week.hours"); //$NON-NLS-1$

	/**
	 * Create the wizard
	 */
	public ResourceWizardResourcePage() {
		super("resource.wizard.resource"); //$NON-NLS-1$
		setTitle(Messages.getString("ResourceWizardResourcePage.title")); //$NON-NLS-1$
		setDescription(Messages.getString("ResourceWizardResourcePage.description")); //$NON-NLS-1$
		errors = new ArrayList<String>();
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator
				.getDefault(), "img/resource_wizard.gif")); //$NON-NLS-1$
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

		final Label labelResourceID = new Label(container, SWT.NONE);
		final GridData gd_labelResourceID = new GridData(150, SWT.DEFAULT);
		labelResourceID.setLayoutData(gd_labelResourceID);
		labelResourceID.setText(Messages.getString("ResourceWizardResourcePage.id.label")); //$NON-NLS-1$

		textResourceID = new Text(container, SWT.BORDER);
		textResourceID
				.setToolTipText(Messages.getString("ResourceWizardResourcePage.id.description")); //$NON-NLS-1$
		final GridData gd_textResourceID = new GridData(SWT.FILL, SWT.CENTER,
				true, false);
		textResourceID.setLayoutData(gd_textResourceID);
		textResourceID.addModifyListener(new ValidationListener(this));

		final Label labelResourceName = new Label(container, SWT.NONE);
		final GridData gd_labelResourceName = new GridData(SWT.LEFT, SWT.TOP,
				false, false);
		gd_labelResourceName.widthHint = 150;
		labelResourceName.setLayoutData(gd_labelResourceName);
		labelResourceName.setText(Messages.getString("ResourceWizardResourcePage.name.label")); //$NON-NLS-1$

		textResourceName = new Text(container, SWT.BORDER);
		textResourceName
				.setToolTipText(Messages.getString("ResourceWizardResourcePage.name.description")); //$NON-NLS-1$
		final GridData gd_textResourceName = new GridData(SWT.FILL, SWT.CENTER,
				true, false);
		textResourceName.setLayoutData(gd_textResourceName);
		textResourceName.addModifyListener(new ValidationListener(this));

		final Label labelResourceSurame = new Label(container, SWT.NONE);
		final GridData gd_labelResourceSurname = new GridData(SWT.LEFT, SWT.TOP,
				false, false);
		gd_labelResourceSurname.widthHint = 150;
		labelResourceSurame.setLayoutData(gd_labelResourceSurname);
		labelResourceSurame.setText(Messages.getString("ResourceWizardResourcePage.surname.label")); //$NON-NLS-1$

		textResourceSurname = new Text(container, SWT.BORDER);
		textResourceSurname
				.setToolTipText(Messages.getString("ResourceWizardResourcePage.surname.description")); //$NON-NLS-1$
		final GridData gd_textResourceSurname = new GridData(SWT.FILL,
				SWT.CENTER, true, false);
		textResourceSurname.setLayoutData(gd_textResourceSurname);
		textResourceName.addModifyListener(new ValidationListener(this));

		final Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		final GridData gd_label = new GridData(SWT.FILL, SWT.CENTER, false,
				false, 2, 1);
		gd_label.verticalIndent = 5;
		label.setLayoutData(gd_label);

		final Label labelResourceStart = new Label(container, SWT.NONE);
		labelResourceStart.setText(Messages.getString("ResourceWizardResourcePage.employment.date.label")); //$NON-NLS-1$

		dateResourceEmployment = new DateChooserCombo(container, SWT.BORDER);
		dateResourceEmployment
				.setToolTipText(Messages.getString("ResourceWizardResourcePage.employment.date.description")); //$NON-NLS-1$
		final GridData gd_dateResourceEmployment = new GridData(SWT.FILL,
				SWT.CENTER, true, false);
		dateResourceEmployment.setLayoutData(gd_dateResourceEmployment);
		dateResourceEmployment.addModifyListener(new ValidationListener(this));

		final Label labelResourceHoursPerWeek = new Label(container, SWT.NONE);
		labelResourceHoursPerWeek.setText(Messages.getString("ResourceWizardResourcePage.week.hours.label")); //$NON-NLS-1$

		textResourceHoursPerWeek = new Text(container, SWT.BORDER);
		textResourceHoursPerWeek
				.setToolTipText(Messages.getString("ResourceWizardResourcePage.week.hours.description")); //$NON-NLS-1$
		final GridData gd_textResourceHoursPerWeek = new GridData(SWT.FILL,
				SWT.CENTER, true, false);
		textResourceHoursPerWeek.setLayoutData(gd_textResourceHoursPerWeek);
		textResourceHoursPerWeek
				.addModifyListener(new ValidationListener(this));
		new Label(container, SWT.NONE);

		buttonActive = new Button(container, SWT.CHECK);
		buttonActive.setText(Messages.getString("ResourceWizardResourcePage.active.label")); //$NON-NLS-1$
	}

	/**
	 * @see net.sf.dysis.base.ui.wizard.IValidationPage#checkContents()
	 */
	public void checkContents() {
		// Check resource ID
		if (textResourceID.getText().length() == 0
				&& !errors.contains(ERROR_MISSING_ID)) {
			errors.add(ERROR_MISSING_ID);
		}
		else if (textResourceID.getText().length() > 0) {
			errors.remove(ERROR_MISSING_ID);
			id = textResourceID.getText();
		}
		// Check resource name
		if (textResourceName.getText().length() == 0
				&& !errors.contains(ERROR_MISSING_NAME)) {
			errors.add(ERROR_MISSING_NAME);
		}
		else if (textResourceName.getText().length() > 0) {
			errors.remove(ERROR_MISSING_NAME);
			name = textResourceName.getText();
		}
		// Check resource name
		if (textResourceSurname.getText().length() == 0
				&& !errors.contains(ERROR_MISSING_SURNAME)) {
			errors.add(ERROR_MISSING_SURNAME);
		}
		else if (textResourceSurname.getText().length() > 0) {
			errors.remove(ERROR_MISSING_SURNAME);
			surname = textResourceSurname.getText();
		}
		// Check resource departement
		if (dateResourceEmployment.getText().length() == 0
				&& !errors.contains(ERROR_MISSING_EMPLOYMENT_DATE)) {
			errors.add(ERROR_MISSING_EMPLOYMENT_DATE);
		}
		else if (dateResourceEmployment.getText().length() > 0) {
			errors.remove(ERROR_MISSING_EMPLOYMENT_DATE);
			employmentDate = dateResourceEmployment.getValue();
		}
		// Check resource working hours
		if (textResourceHoursPerWeek.getText().length() == 0
				&& !errors.contains(ERROR_MISSING_WORKING_HOURS)) {
			errors.add(ERROR_MISSING_WORKING_HOURS);
		}
		else if (textResourceHoursPerWeek.getText().length() > 0) {
			errors.remove(ERROR_MISSING_WORKING_HOURS);
			try{
				hoursPerWeek = Integer.valueOf(textResourceHoursPerWeek.getText());
				errors.remove(ERROR_INVALID_WORKING_HOURS);
			}
			catch (NumberFormatException numberFormatException) {
				errors.add(ERROR_INVALID_WORKING_HOURS);
			}
		}
		if (errors.isEmpty()) {
			setErrorMessage(null);
			canFlipToNextPage = true;
		}
		else {
			setErrorMessage(errors.get(0));
			canFlipToNextPage = false;
		}
		active = buttonActive.getSelection();
		getWizard().getContainer().updateButtons();
	}

	/**
	 * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
	 */
	@Override
	public boolean canFlipToNextPage() {
		return canFlipToNextPage;
	}

	/**
	 * @return the id
	 */
	public String getID() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the surname
	 */
	public String getSurame() {
		return surname;
	}

	/**
	 * @return the employmentDate
	 */
	public Date getEmploymentDate() {
		return employmentDate;
	}

	/**
	 * @return the hoursPerWeek
	 */
	public Integer getHoursPerWeek() {
		return hoursPerWeek;
	}

	/**
	 * @return the active
	 */
	public boolean getActive() {
		return active;
	}

}