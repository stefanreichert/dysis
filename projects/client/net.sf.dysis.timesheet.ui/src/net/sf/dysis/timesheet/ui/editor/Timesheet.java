package net.sf.dysis.timesheet.ui.editor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.dysis.application.DysisApplication;
import net.sf.dysis.base.ui.dataprovider.IWritableDataProvider;
import net.sf.dysis.base.ui.dataprovider.key.ForeignKey;
import net.sf.dysis.base.ui.dataprovider.key.IKey;
import net.sf.dysis.base.ui.dataprovider.registry.Registry;
import net.sf.dysis.base.ui.editor.controller.EditorLoadController;
import net.sf.dysis.base.ui.editor.controller.EditorSaveController;
import net.sf.dysis.base.ui.editor.listener.IDirty;
import net.sf.dysis.planing.core.dto.ActivityTimeEntryDTO;
import net.sf.dysis.resource.core.dto.PersonDTO;
import net.sf.dysis.timesheet.ui.Messages;
import net.sf.dysis.timesheet.ui.dataprovider.ActivityTimeEntryDataProvider;
import net.sf.dysis.timesheet.ui.editor.header.TimesheetHeader;
import net.sf.dysis.timesheet.ui.editor.header.TimesheetRow;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.nebula.widgets.compositetable.CompositeTable;
import org.eclipse.swt.nebula.widgets.compositetable.IRowContentProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.EditorPart;

import com.swtdesigner.ResourceManager;

public class Timesheet extends EditorPart implements IDirty {

	private FormToolkit toolkit = ResourceManager.getFormToolkit();

	private CompositeTable timesheetTable;

	public static final String ID = "net.sf.dysis.timesheet.ui.editor.Timesheet"; //$NON-NLS-1$

	private List<ActivityTimeEntryDTO> personActivities;

	@SuppressWarnings("unchecked")
	private EditorLoadController<List> loadController;

	@SuppressWarnings("unchecked")
	private EditorSaveController<List> saveController;

	private boolean dirty;

	private Composite container;

	/**
	 * Constructor for <class>Timesheet</class>.
	 */
	public Timesheet() {
		initControllers();
	}

	/**
	 * Initializes the controllers of this editor.
	 */
	@SuppressWarnings("unchecked")
	private void initControllers() {
		saveController = new EditorSaveController<List>(this, List.class) {

			/** {@inheritDoc} */
			@Override
			protected IWritableDataProvider getDataProvider() {
				return (IWritableDataProvider) Registry.getRegistry()
						.lookupDataProvider(ActivityTimeEntryDataProvider.TYPE);
			}

			/** {@inheritDoc} */
			@Override
			protected IKey getKey(List data) {
				PersonDTO principal = DysisApplication.getDefault()
						.getAuthenticationToken().getPrincipal();
				return new ForeignKey(principal.getId(),
						ActivityTimeEntryDataProvider.FOREIGN_KEY_PERSON);
			}

			/** {@inheritDoc} */
			@Override
			protected void handleResult(IStatus status) {
				personActivities = getData();
				timesheetTable.refreshAllRows();
				dirty = false;
				firePropertyChange(PROP_DIRTY);
				container.setEnabled(true);
			}

		};
		loadController = new EditorLoadController<List>(this) {
			/** {@inheritDoc} */
			@Override
			protected void handleResult(IStatus status) {
				personActivities = getData();
				fillPartControl(container);
			}
		};
	}

	/**
	 * Create contents of the editor part
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout());
		// create temporary content
		toolkit.createLabel(container, Messages
				.getString("Timesheet.loading.message"), SWT.NONE); //$NON-NLS-1$
		// proceed loading
		loadController.initiateServerAccess();
	}

	/**
	 * Fills the {@link #container} with the {@link Control} elements. This
	 * method should be invoked after the data was loaded from the backend.
	 * 
	 * @param container
	 */
	private void fillPartControl(Composite container) {
		// dispose temporary content
		for (Control control : container.getChildren()) {
			control.dispose();
		}
		final Form form = toolkit.createForm(container);
		toolkit.paintBordersFor(form);
		form.getToolBarManager().add(
				ActionFactory.SAVE.create(getSite().getWorkbenchWindow()));
		form.getToolBarManager().update(true);

		final Composite body = form.getBody();
		final FillLayout fillLayout = new FillLayout();
		fillLayout.marginWidth = 5;
		body.setLayout(fillLayout);
		toolkit.paintBordersFor(body);

		final Section section = toolkit.createSection(body, Section.DESCRIPTION
				| Section.TITLE_BAR);
		section.setDescription(Messages
				.getString("Timesheet.section.description")); //$NON-NLS-1$
		section
				.setText(Messages.getString("Timesheet.section.text.prefix") + new SimpleDateFormat("MMMM").format(new Date())); //$NON-NLS-1$ //$NON-NLS-2$

		final Composite composite = toolkit.createComposite(section);
		final FillLayout fillLayout_1 = new FillLayout(SWT.VERTICAL);
		fillLayout_1.marginWidth = 1;
		fillLayout_1.marginHeight = 2;
		composite.setLayout(fillLayout_1);
		section.setClient(composite);
		toolkit.paintBordersFor(composite);

		timesheetTable = new CompositeTable(composite, SWT.NONE);
		new TimesheetHeader(timesheetTable, SWT.NULL);
		new TimesheetRow(timesheetTable, SWT.NULL);

		timesheetTable.setInsertHint(Messages
				.getString("Timesheet.message.empty")); //$NON-NLS-1$

		timesheetTable.addRowContentProvider(new IRowContentProvider() {
			public void refresh(CompositeTable sender, int currentObjectOffset,
					Control rowControl) {
				TimesheetRow row = (TimesheetRow) rowControl;
				row.setIDirty(Timesheet.this);
				row.setActivity(personActivities.get(currentObjectOffset));
			}
		});

		timesheetTable.setData(FormToolkit.KEY_DRAW_BORDER,
				FormToolkit.TEXT_BORDER);
		timesheetTable.setRunTime(true);
		timesheetTable.setNumRowsInCollection(personActivities.size());
		form.setText(Messages.getString("Timesheet.form.text")); //$NON-NLS-1$

		// layout the container with the new controls
		container.layout();
	}

	/** {@inheritDoc} */
	@Override
	public void setFocus() {
		if (timesheetTable != null) {
			timesheetTable.setFocus();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void doSave(IProgressMonitor monitor) {
		container.setEnabled(false);
		saveController.setData(personActivities);
		saveController.initiateServerAccess();
	}

	/** {@inheritDoc} */
	@Override
	public void doSaveAs() {
		// Do the Save As operation
	}

	/** {@inheritDoc} */
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		setPartName(getPartName()
				+ " " + new SimpleDateFormat("MMMM").format(new Date())); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDirty() {
		return dirty;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	/** {@inheritDoc} */
	public void markDirty() {
		dirty = true;
		firePropertyChange(PROP_DIRTY);
	}

}
