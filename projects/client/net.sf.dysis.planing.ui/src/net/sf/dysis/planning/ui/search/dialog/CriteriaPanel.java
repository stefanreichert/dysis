package net.sf.dysis.planning.ui.search.dialog;

import net.sf.dysis.planning.ui.Messages;

import org.eclipse.nebula.widgets.datechooser.DateChooserCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class CriteriaPanel extends Composite {

	/** Widget. */
	private DateChooserCombo dateStartdatumVon;

	/** Widget. */
	private DateChooserCombo dateStartdatumBis;

	/** Widget. */
	private Text textBeschreibung;

	/** Widget. */
	private Text textName;

	/** Widget. */
	private DateChooserCombo dateEnddatumBis;

	/** Widget. */
	private DateChooserCombo dateEnddatumVon;

	/**
	 * Constructor for <class>CriteriaPanel</class>.
	 */
	public CriteriaPanel(Composite parent, int style) {
		super(parent, style);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		setLayout(gridLayout);

		final Label projektnameLabel = new Label(this, SWT.NONE);
		projektnameLabel.setText(Messages.getString("CriteriaPanel.project.name")); //$NON-NLS-1$

		textName = new Text(this, SWT.BORDER);
		final GridData gd_textName = new GridData(SWT.FILL, SWT.CENTER, true,
				false, 3, 1);
		textName.setLayoutData(gd_textName);

		final Label beschreibungLabel = new Label(this, SWT.NONE);
		beschreibungLabel.setText(Messages.getString("CriteriaPanel.project.description")); //$NON-NLS-1$

		textBeschreibung = new Text(this, SWT.BORDER);
		final GridData gd_textBeschreibung = new GridData(SWT.FILL, SWT.CENTER,
				true, false, 3, 1);
		textBeschreibung.setLayoutData(gd_textBeschreibung);

		final Label startdatumVonLabel = new Label(this, SWT.NONE);
		startdatumVonLabel.setText(Messages.getString("CriteriaPanel.project.start.from")); //$NON-NLS-1$

		dateStartdatumVon = new DateChooserCombo(this, SWT.BORDER);
		dateStartdatumVon.setLayoutData(new GridData(100, SWT.DEFAULT));
		dateStartdatumVon.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_WHITE));

		final Label startdatumBisLabel = new Label(this, SWT.NONE);
		final GridData gd_startdatumBisLabel = new GridData();
		gd_startdatumBisLabel.horizontalIndent = 25;
		startdatumBisLabel.setLayoutData(gd_startdatumBisLabel);
		startdatumBisLabel.setText(Messages.getString("CriteriaPanel.project.start.to")); //$NON-NLS-1$

		dateStartdatumBis = new DateChooserCombo(this, SWT.BORDER);
		dateStartdatumBis.setLayoutData(new GridData(100, SWT.DEFAULT));
		dateStartdatumBis.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_WHITE));

		final Label enddatumVonLabel = new Label(this, SWT.NONE);
		enddatumVonLabel.setText(Messages.getString("CriteriaPanel.project.end.from")); //$NON-NLS-1$

		dateEnddatumVon = new DateChooserCombo(this, SWT.BORDER);
		dateEnddatumVon.setLayoutData(new GridData(100, SWT.DEFAULT));
		dateEnddatumVon.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_WHITE));

		final Label enddatumBisLabel = new Label(this, SWT.NONE);
		final GridData gd_enddatumBisLabel = new GridData();
		gd_enddatumBisLabel.horizontalIndent = 25;
		enddatumBisLabel.setLayoutData(gd_enddatumBisLabel);
		enddatumBisLabel.setText(Messages.getString("CriteriaPanel.project.end.to")); //$NON-NLS-1$

		dateEnddatumBis = new DateChooserCombo(this, SWT.BORDER);
		dateEnddatumBis.setLayoutData(new GridData(100, SWT.DEFAULT));
		dateEnddatumBis.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_WHITE));
		//
	}

	/** {@inheritDoc} */
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/**
	 * @return the dateStartdatumVon
	 */
	public DateChooserCombo getDateStartdatumVon() {
		return dateStartdatumVon;
	}

	/**
	 * @return the dateStartdatumBis
	 */
	public DateChooserCombo getDateStartdatumBis() {
		return dateStartdatumBis;
	}

	/**
	 * @return the textBeschreibung
	 */
	public Text getTextBeschreibung() {
		return textBeschreibung;
	}

	/**
	 * @return the textName
	 */
	public Text getTextName() {
		return textName;
	}

	/**
	 * @return the dateEnddatumBis
	 */
	public DateChooserCombo getDateEnddatumBis() {
		return dateEnddatumBis;
	}

	/**
	 * @return the dateEnddatumVon
	 */
	public DateChooserCombo getDateEnddatumVon() {
		return dateEnddatumVon;
	}

}
