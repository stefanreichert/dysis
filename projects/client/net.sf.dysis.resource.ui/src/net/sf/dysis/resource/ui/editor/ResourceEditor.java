package net.sf.dysis.resource.ui.editor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.dysis.base.ui.databinding.ObservableAdapter;
import net.sf.dysis.base.ui.dataprovider.IDataProvider;
import net.sf.dysis.base.ui.dataprovider.IWritableDataProvider;
import net.sf.dysis.base.ui.dataprovider.key.ICollectionKey;
import net.sf.dysis.base.ui.dataprovider.key.IKey;
import net.sf.dysis.base.ui.dataprovider.key.PrimaryKey;
import net.sf.dysis.base.ui.dataprovider.registry.Registry;
import net.sf.dysis.base.ui.editor.IEditorProperties;
import net.sf.dysis.base.ui.editor.controller.EditorLoadController;
import net.sf.dysis.base.ui.editor.controller.EditorSaveController;
import net.sf.dysis.base.ui.editor.input.DataProviderEditorInput;
import net.sf.dysis.base.ui.editor.listener.DirtyListener;
import net.sf.dysis.base.ui.editor.listener.IDirty;
import net.sf.dysis.base.ui.editor.validation.FormValidationAdapter;
import net.sf.dysis.base.ui.editor.validation.FormValidationAdapter.ValidatorFactory;
import net.sf.dysis.planing.core.dto.ActivityDTO;
import net.sf.dysis.resource.core.dto.PersonDTO;
import net.sf.dysis.resource.ui.Messages;
import net.sf.dysis.resource.ui.dataprovider.ResourceDataProvider;
import net.sf.dysis.resource.ui.provider.ActivityLabelProvider;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.EditorPart;

import com.swtdesigner.ResourceManager;

public class ResourceEditor extends EditorPart implements IDirty {

	/** The id of this editor. */
	public static final String ID = "net.sf.dysis.resource.ui.ResourceEditor"; //$NON-NLS-1$

	/** Widget. */
	private Text textWeekHours;

	/** Widget. */
	private Text textLastname;

	/** Widget. */
	private Text textFirstname;

	/** Widget. */
	private Text textID;

	/** Widget. */
	private TableViewer tableViewerSelected;

	/** Widget. */
	private TableViewer tableViewerAvailable;

	/** The <code>FormsToolkit</code>. */
	private FormToolkit toolkit = ResourceManager.getFormToolkit();

	/** <code>Set</code> of available tasks. */
	private List<ActivityDTO> availableActivities;

	/** <code>Set</code> of available tasks. */
	private List<ActivityDTO> selectedActivities;

	/** The underlaying resource. */
	private ObservableAdapter<PersonDTO> resourceAdapter;

	/** The underlaying <code>DataBindingContext</code>. */
	private DataBindingContext bindingContext;

	/** This editors <code>DirtyListener</code>. */
	private DirtyListener dirtyListener;

	/** The dirty flag. */
	private boolean dirty;

	private FormValidationAdapter formValidationAdapter;

	private EditorSaveController<PersonDTO> saveController;

	private EditorLoadController<PersonDTO> loadController;

	private Composite container;

	/**
	 * Constructor for <class>ResourceEditor</class>.
	 */
	public ResourceEditor() {
		super();
		availableActivities = new ArrayList<ActivityDTO>();
		dirtyListener = new DirtyListener(this);
		initControllers();
	}

	/**
	 * Initializes the controllers of this editor.
	 */
	private void initControllers() {
		saveController = new EditorSaveController<PersonDTO>(this,
				PersonDTO.class) {

			/** {@inheritDoc} */
			@Override
			protected IWritableDataProvider getDataProvider() {
				return (IWritableDataProvider) Registry.getRegistry()
						.lookupDataProvider(ResourceDataProvider.TYPE);
			}

			/** {@inheritDoc} */
			@Override
			protected IKey getKey(PersonDTO data) {
				return new PrimaryKey(data.getId());
			}

			/** {@inheritDoc} */
			@Override
			protected void handleResult(IStatus status) {
				dirty = false;
				firePropertyChange(PROP_DIRTY);
				firePropertyChange(IEditorProperties.PROP_EDITOR_SAVE);
				container.setEnabled(true);
			}

		};
		loadController = new EditorLoadController<PersonDTO>(this) {

			/** {@inheritDoc} */
			@SuppressWarnings("unchecked")
			@Override
			protected IStatus internalProceedServerAccess(
					IProgressMonitor monitor) {
				IStatus resultStatus = super
						.internalProceedServerAccess(monitor);
				selectedActivities = getData().getActivities();
				IDataProvider activityDataProvider = Registry.getRegistry()
						.lookupDataProvider("core.activity.dto"); //$NON-NLS-1$
				availableActivities
						.addAll((List<ActivityDTO>) activityDataProvider
								.getDataCollection(ICollectionKey.ALL));
				return resultStatus;
			}

			/** {@inheritDoc} */
			@Override
			protected void handleResult(IStatus status) {
				resourceAdapter = ObservableAdapter.createAdapter(getData(),
						PersonDTO.class);
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
				.getString("ResourceEditor.loading.message"), SWT.NONE); //$NON-NLS-1$
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
		final ScrolledForm scrolledForm = toolkit.createScrolledForm(container);
		scrolledForm.setText(Messages.getString("ResourceEditor.form.text")); //$NON-NLS-1$
		formValidationAdapter = FormValidationAdapter.adapt(toolkit,
				scrolledForm);
		final Composite body = scrolledForm.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		final Section sectionResourceDetails = toolkit.createSection(body,
				Section.TWISTIE | Section.DESCRIPTION | Section.EXPANDED
						| Section.TITLE_BAR);
		sectionResourceDetails.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));
		sectionResourceDetails.setDescription(Messages
				.getString("ResourceEditor.section.description")); //$NON-NLS-1$
		sectionResourceDetails.setText(Messages
				.getString("ResourceEditor.section.text")); //$NON-NLS-1$

		final Composite compositeDetails = toolkit.createComposite(
				sectionResourceDetails, SWT.NONE);
		final GridLayout gd_compositeDetails = new GridLayout();
		gd_compositeDetails.marginHeight = 2;
		gd_compositeDetails.marginBottom = 15;
		gd_compositeDetails.verticalSpacing = 10;
		gd_compositeDetails.horizontalSpacing = 1;
		gd_compositeDetails.numColumns = 4;
		compositeDetails.setLayout(gd_compositeDetails);
		toolkit.paintBordersFor(compositeDetails);
		sectionResourceDetails.setClient(compositeDetails);

		final Label labelID = toolkit.createLabel(compositeDetails, Messages
				.getString("ResourceEditor.id"), SWT.NONE); //$NON-NLS-1$
		final GridData gd_labelID = new GridData(120, SWT.DEFAULT);
		labelID.setLayoutData(gd_labelID);

		textID = toolkit.createText(compositeDetails, null, SWT.NONE);
		final GridData gd_textID = new GridData(SWT.FILL, SWT.CENTER, true,
				false);
		gd_textID.horizontalIndent = 15;
		textID.setLayoutData(gd_textID);

		final Label labelName = new Label(compositeDetails, SWT.NONE);
		final GridData gd_labelName = new GridData(120, SWT.DEFAULT);
		gd_labelName.horizontalIndent = 15;
		labelName.setLayoutData(gd_labelName);
		toolkit.adapt(labelName, true, true);
		labelName.setText(Messages.getString("ResourceEditor.name")); //$NON-NLS-1$

		textFirstname = toolkit.createText(compositeDetails, null, SWT.NONE);
		final GridData gd_textName = new GridData(SWT.FILL, SWT.CENTER, true,
				false);
		gd_textName.horizontalIndent = 15;
		textFirstname.setLayoutData(gd_textName);

		final Label abteilungLabel = toolkit.createLabel(compositeDetails,
				Messages.getString("ResourceEditor.surname"), SWT.NONE); //$NON-NLS-1$
		final GridData gd_abteilungLabel = new GridData(120, SWT.DEFAULT);
		abteilungLabel.setLayoutData(gd_abteilungLabel);

		textLastname = toolkit.createText(compositeDetails, null, SWT.NONE);
		final GridData gd_textDepartement = new GridData(SWT.FILL, SWT.CENTER,
				true, false);
		gd_textDepartement.horizontalIndent = 15;
		textLastname.setLayoutData(gd_textDepartement);

		final Label wochenstundenLabel = toolkit.createLabel(compositeDetails,
				Messages.getString("ResourceEditor.week.hours"), SWT.NONE); //$NON-NLS-1$
		final GridData gd_wochenstundenLabel = new GridData(120, SWT.DEFAULT);
		gd_wochenstundenLabel.horizontalIndent = 15;
		wochenstundenLabel.setLayoutData(gd_wochenstundenLabel);

		textWeekHours = toolkit.createText(compositeDetails, null, SWT.NONE);
		final GridData gd_textWeekHours = new GridData(SWT.FILL, SWT.CENTER,
				true, false);
		gd_textWeekHours.horizontalIndent = 15;
		textWeekHours.setLayoutData(gd_textWeekHours);

		final Section sectionResourceTasks = toolkit.createSection(body,
				Section.TWISTIE | Section.DESCRIPTION | Section.EXPANDED
						| Section.TITLE_BAR);
		sectionResourceTasks.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				true, true));
		sectionResourceTasks.setDescription(Messages
				.getString("ResourceEditor.section.resource.description")); //$NON-NLS-1$
		sectionResourceTasks.setText(Messages
				.getString("ResourceEditor.section.resource.text")); //$NON-NLS-1$

		final Composite compositeTasks = toolkit.createComposite(
				sectionResourceTasks, SWT.NONE);
		final GridLayout gl_compositeTasks = new GridLayout();
		gl_compositeTasks.numColumns = 3;
		compositeTasks.setLayout(gl_compositeTasks);
		toolkit.paintBordersFor(compositeTasks);
		sectionResourceTasks.setClient(compositeTasks);

		final Label labelAvailable = new Label(compositeTasks, SWT.NONE);
		labelAvailable.setText(Messages
				.getString("ResourceEditor.available.tasks")); //$NON-NLS-1$

		toolkit.createLabel(compositeTasks, "", SWT.NONE); //$NON-NLS-1$

		final Label labelSelected = new Label(compositeTasks, SWT.NONE);
		labelSelected.setText(Messages
				.getString("ResourceEditor.selected.tasks")); //$NON-NLS-1$

		tableViewerAvailable = new TableViewer(compositeTasks, SWT.MULTI);
		tableViewerAvailable.setContentProvider(new ArrayContentProvider());
		tableViewerAvailable.setLabelProvider(new ActivityLabelProvider());
		tableViewerAvailable.setSorter(new ViewerSorter());
		// Filter all yet selected items
		tableViewerAvailable.addFilter(new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement,
					Object element) {
				return !selectedActivities.contains(element);
			}

		});
		tableViewerAvailable.setInput(availableActivities);

		Table tableAvailable = tableViewerAvailable.getTable();
		tableAvailable.setLinesVisible(true);
		final GridData gd_listAvailable = new GridData(SWT.FILL, SWT.FILL,
				true, true);
		gd_listAvailable.widthHint = 200;
		tableAvailable.setLayoutData(gd_listAvailable);
		toolkit.adapt(tableAvailable, false, false);

		final Composite composite = new Composite(compositeTasks, SWT.NONE);
		composite.setLayout(new GridLayout());

		final Button buttonAddAll = new Button(composite, SWT.FLAT);
		buttonAddAll.setLayoutData(new GridData(40, SWT.DEFAULT));
		buttonAddAll.setText(">>"); //$NON-NLS-1$
		buttonAddAll.addSelectionListener(new SelectionAdapter() {
			/**
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent event) {
				selectedActivities.clear();
				selectedActivities.addAll(availableActivities);
				tableViewerSelected.setInput(selectedActivities);
				tableViewerAvailable.refresh();
				markDirty();
			}
		});

		final Button addSelected = new Button(composite, SWT.FLAT);
		addSelected.setLayoutData(new GridData(40, SWT.DEFAULT));
		addSelected.setText(">"); //$NON-NLS-1$
		addSelected.addSelectionListener(new SelectionAdapter() {
			/**
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@SuppressWarnings("unchecked")
			@Override
			public void widgetSelected(SelectionEvent event) {
				IStructuredSelection structuredSelection = (IStructuredSelection) tableViewerAvailable
						.getSelection();
				selectedActivities.addAll(structuredSelection.toList());
				tableViewerSelected.setInput(selectedActivities);
				tableViewerAvailable.refresh();
				markDirty();
			}
		});

		final Button buttonRemoveSelected = new Button(composite, SWT.FLAT);
		buttonRemoveSelected.setLayoutData(new GridData(40, SWT.DEFAULT));
		buttonRemoveSelected.setText("<"); //$NON-NLS-1$
		buttonRemoveSelected.addSelectionListener(new SelectionAdapter() {
			/**
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent event) {
				IStructuredSelection structuredSelection = (IStructuredSelection) tableViewerSelected
						.getSelection();
				selectedActivities.removeAll(structuredSelection.toList());
				tableViewerSelected.setInput(selectedActivities);
				tableViewerAvailable.refresh();
				markDirty();
			}
		});

		final Button buttonRemoveAll = new Button(composite, SWT.FLAT);
		buttonRemoveAll.setLayoutData(new GridData(40, SWT.DEFAULT));
		buttonRemoveAll.setText("<<"); //$NON-NLS-1$
		buttonRemoveAll.addSelectionListener(new SelectionAdapter() {
			/**
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent event) {
				selectedActivities.clear();
				tableViewerSelected.setInput(selectedActivities);
				tableViewerAvailable.refresh();
				markDirty();
			}
		});

		tableViewerSelected = new TableViewer(compositeTasks, SWT.MULTI);
		tableViewerSelected.setContentProvider(new ArrayContentProvider());
		tableViewerSelected.setLabelProvider(new ActivityLabelProvider());
		tableViewerSelected.setSorter(new ViewerSorter());
		tableViewerSelected.setInput(selectedActivities);

		Table tableSelected = tableViewerSelected.getTable();
		tableSelected.setLinesVisible(true);
		final GridData gd_listSelected = new GridData(SWT.FILL, SWT.FILL, true,
				true);
		gd_listSelected.widthHint = 200;
		tableSelected.setLayoutData(gd_listSelected);
		toolkit.adapt(tableSelected, false, false);
		//

		initDataBindings();
		activateDirtyListener();

		// layout the container with the new controls
		container.layout();
	}

	/**
	 * Proceeds the setup of the data binding.
	 */
	private void initDataBindings() {
		bindingContext = new DataBindingContext();
		// Bind the id
		IObservableValue idWidget = SWTObservables.observeText(textID,
				SWT.Modify);
		bindingContext.bindValue(idWidget, (IObservableValue) resourceAdapter
				.getObservableValue("userId"), //$NON-NLS-1$
				formValidationAdapter.getUpdateValueStrategyFactory()
						.getUpdateValueStrategy("textID", textID, //$NON-NLS-1$
								ValidatorFactory.MANDATORY_VALIDATOR), null);
		// Bind the name
		IObservableValue firstnameWidget = SWTObservables.observeText(
				textFirstname, SWT.Modify);
		bindingContext.bindValue(firstnameWidget,
				(IObservableValue) resourceAdapter
						.getObservableValue("firstname"), formValidationAdapter //$NON-NLS-1$
						.getUpdateValueStrategyFactory()
						.getUpdateValueStrategy("textFirstname", textFirstname, //$NON-NLS-1$
								ValidatorFactory.MANDATORY_VALIDATOR), null);
		// Bind the departement
		IObservableValue lastnameWidget = SWTObservables.observeText(
				textLastname, SWT.Modify);
		bindingContext.bindValue(lastnameWidget,
				(IObservableValue) resourceAdapter
						.getObservableValue("lastname"), formValidationAdapter //$NON-NLS-1$
						.getUpdateValueStrategyFactory()
						.getUpdateValueStrategy("textLastname", textLastname, //$NON-NLS-1$
								ValidatorFactory.MANDATORY_VALIDATOR), null);
		// Bind the weekHours
		IObservableValue weekHoursWidget = SWTObservables.observeText(
				textWeekHours, SWT.Modify);
		bindingContext.bindValue(weekHoursWidget,
				(IObservableValue) resourceAdapter
						.getObservableValue("weekHours"), formValidationAdapter //$NON-NLS-1$
						.getUpdateValueStrategyFactory()
						.getUpdateValueStrategy("textWeekHours", textWeekHours, //$NON-NLS-1$
								ValidatorFactory.MANDATORY_VALIDATOR), null);
		bindingContext.updateTargets();
	}

	/**
	 * Activates the dirty handling.
	 */
	private void activateDirtyListener() {
		resourceAdapter.addChangeListener(dirtyListener);
	}

	/**
	 * @see net.sf.dysis.base.ui.editor.listener.IDirty#markDirty()
	 */
	public void markDirty() {
		dirty = true;
		firePropertyChange(PROP_DIRTY);
	}

	/**
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		if (textID != null) {
			textID.setFocus();
		}
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		// disable editor
		container.setEnabled(false);
		// validate the input
		for (Iterator<?> bindingIterator = bindingContext.getBindings()
				.iterator(); bindingIterator.hasNext();) {
			Binding binding = (Binding) bindingIterator.next();
			binding.validateTargetToModel();
		}
		if (!formValidationAdapter.containsErrors()) {
			bindingContext.updateModels();
			// delegate to save controller to actually save the data
			saveController.setData(resourceAdapter.getAdaptedObservable());
			saveController.initiateServerAccess();
		}
		else {
			MessageDialog.openError(getSite().getShell(), Messages
					.getString("ResourceEditor.dialog.error.title"), //$NON-NLS-1$
					Messages.getString("ResourceEditor.dialog.error.message")); //$NON-NLS-1$
		}
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		// Not allowed
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#init(org.eclipse.ui.IEditorSite,
	 *      org.eclipse.ui.IEditorInput)
	 */
	@SuppressWarnings( { "unchecked" })
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		DataProviderEditorInput<PersonDTO> dataProviderEditorInput = (DataProviderEditorInput<PersonDTO>) input;
		setInput(input);
		setPartName(dataProviderEditorInput.getName());
		setTitleToolTip(dataProviderEditorInput.getToolTipText());
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#isDirty()
	 */
	@Override
	public boolean isDirty() {
		return dirty;
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

}
