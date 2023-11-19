package net.sf.dysis.planning.ui.editor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.dysis.base.ui.databinding.DateChooserComboObservableValue;
import net.sf.dysis.base.ui.databinding.ObservableAdapter;
import net.sf.dysis.base.ui.dataprovider.IWritableDataProvider;
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
import net.sf.dysis.planing.core.dto.ProjectDTO;
import net.sf.dysis.planning.ui.Messages;
import net.sf.dysis.planning.ui.dataprovider.ProjectDataProvider;
import net.sf.dysis.planning.ui.wizard.ActivityWizard;
import net.sf.dysis.planning.ui.wizard.activity.ActivityWizardObjectProvider;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.nebula.widgets.datechooser.DateChooserCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.EditorPart;

import com.swtdesigner.ResourceManager;

public class ProjectEditor extends EditorPart implements IDirty {

	/** The id of this editor. */
	public static final String ID = "net.sf.dysis.planning.ui.editor.ProjectEditor"; //$NON-NLS-1$

	/** Widget. */
	private TableViewer tableViewerTasks;

	/** Widget. */
	private Text textProjectName;

	/** Widget. */
	private Text textProjectDescription;

	/** Widget. */
	private DateChooserCombo dateChooserProjectStart;

	/** Widget. */
	private DateChooserCombo dateChooserProjectEnd;

	/** The <code>FormsToolkit</code>. */
	private FormToolkit toolkit = ResourceManager.getFormToolkit();

	/** The underlaying resource. */
	private ObservableAdapter<ProjectDTO> projectAdapter;

	/** The underlaying <code>DataBindingContext</code>. */
	private DataBindingContext bindingContext;

	/** This editors <code>DirtyListener</code>. */
	private DirtyListener dirtyListener;

	/** The dirty flag. */
	private boolean dirty;

	private FormValidationAdapter formValidationAdapter;

	private List<ActivityDTO> projectActivities;

	private EditorSaveController<ProjectDTO> saveController;

	private EditorLoadController<ProjectDTO> loadController;

	private Composite container;

	/**
	 * Constructor for <class>ProjectEditor</class>.
	 */
	public ProjectEditor() {
		super();
		dirtyListener = new DirtyListener(this);
		initControllers();
	}

	/**
	 * Initializes the controllers of this editor.
	 */
	private void initControllers() {
		saveController = new EditorSaveController<ProjectDTO>(this,
				ProjectDTO.class) {

			/** {@inheritDoc} */
			@Override
			protected IWritableDataProvider getDataProvider() {
				return (IWritableDataProvider) Registry.getRegistry()
						.lookupDataProvider(ProjectDataProvider.TYPE);
			}

			/** {@inheritDoc} */
			@Override
			protected IKey getKey(ProjectDTO data) {
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
		loadController = new EditorLoadController<ProjectDTO>(this) {
			/** {@inheritDoc} */
			@Override
			protected void handleResult(IStatus status) {
				projectAdapter = ObservableAdapter.createAdapter(getData(),
						ProjectDTO.class);
				projectActivities = projectAdapter.getAdaptedObservable()
						.getActivities();
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
				.getString("ProjectEditor.loading.message"), SWT.NONE); //$NON-NLS-1$
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
		scrolledForm.setText(Messages.getString("ProjectEditor.form.text")); //$NON-NLS-1$
		formValidationAdapter = FormValidationAdapter.adapt(toolkit,
				scrolledForm);
		final Composite body = scrolledForm.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		final Section sectionProjectDetails = toolkit.createSection(body,
				Section.TWISTIE | Section.DESCRIPTION | Section.EXPANDED
						| Section.TITLE_BAR);
		sectionProjectDetails.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));
		sectionProjectDetails.setDescription(Messages
				.getString("ProjectEditor.section.project.description")); //$NON-NLS-1$
		sectionProjectDetails.setText(Messages
				.getString("ProjectEditor.section.project.text")); //$NON-NLS-1$

		final Composite compositeDetails = toolkit.createComposite(
				sectionProjectDetails, SWT.NONE);
		final GridLayout gd_compositeDetails = new GridLayout();
		gd_compositeDetails.marginHeight = 2;
		gd_compositeDetails.marginBottom = 15;
		gd_compositeDetails.verticalSpacing = 10;
		gd_compositeDetails.horizontalSpacing = 1;
		gd_compositeDetails.numColumns = 4;
		compositeDetails.setLayout(gd_compositeDetails);
		sectionProjectDetails.setClient(compositeDetails);

		final Label labelProjectName = toolkit
				.createLabel(
						compositeDetails,
						Messages.getString("ProjectEditor.field.project.name"), SWT.NONE); //$NON-NLS-1$
		final GridData gd_labelProjectName = new GridData(SWT.LEFT, SWT.TOP,
				false, false);
		gd_labelProjectName.widthHint = 100;
		labelProjectName.setLayoutData(gd_labelProjectName);

		textProjectName = toolkit.createText(compositeDetails, "", SWT.NONE); //$NON-NLS-1$
		textProjectName.setToolTipText(Messages
				.getString("ProjectEditor.field.project.tooltip")); //$NON-NLS-1$
		final GridData gd_textProjectName = new GridData(SWT.FILL, SWT.TOP,
				true, false);
		gd_textProjectName.horizontalIndent = 15;
		textProjectName.setLayoutData(gd_textProjectName);

		final Label labelProjectDescription = toolkit
				.createLabel(
						compositeDetails,
						Messages
								.getString("ProjectEditor.field.project.description.name"), SWT.NONE); //$NON-NLS-1$
		final GridData gd_labelProjectDescription = new GridData(SWT.LEFT,
				SWT.TOP, false, false);
		gd_labelProjectDescription.horizontalIndent = 15;
		gd_labelProjectDescription.widthHint = 100;
		labelProjectDescription.setLayoutData(gd_labelProjectDescription);

		textProjectDescription = toolkit.createText(compositeDetails, "", //$NON-NLS-1$
				SWT.WRAP);
		textProjectDescription
				.setToolTipText(Messages
						.getString("ProjectEditor.field.project.description.description")); //$NON-NLS-1$
		final GridData gd_textProjectDescription = new GridData(SWT.FILL,
				SWT.CENTER, true, false);
		gd_textProjectDescription.horizontalIndent = 15;
		gd_textProjectDescription.heightHint = 32;
		textProjectDescription.setLayoutData(gd_textProjectDescription);

		final Label labelProjectStart = toolkit
				.createLabel(
						compositeDetails,
						Messages
								.getString("ProjectEditor.field.project.start.name"), SWT.NONE); //$NON-NLS-1$
		labelProjectStart.setLayoutData(new GridData(100, SWT.DEFAULT));

		dateChooserProjectStart = new DateChooserCombo(compositeDetails,
				SWT.NONE);
		dateChooserProjectStart.setToolTipText(Messages
				.getString("ProjectEditor.field.project.start.description")); //$NON-NLS-1$
		dateChooserProjectStart.setGridVisible(false);
		final GridData gd_dateChooserProjectStart = new GridData(SWT.FILL,
				SWT.CENTER, true, false);
		gd_dateChooserProjectStart.horizontalIndent = 15;
		dateChooserProjectStart.setLayoutData(gd_dateChooserProjectStart);
		dateChooserProjectStart.setData(FormToolkit.KEY_DRAW_BORDER,
				FormToolkit.TEXT_BORDER);

		final Label labelProjectEnd = toolkit
				.createLabel(
						compositeDetails,
						Messages
								.getString("ProjectEditor.field.project.end.name"), SWT.NONE); //$NON-NLS-1$
		final GridData gd_labelProjectEnd = new GridData(100, SWT.DEFAULT);
		gd_labelProjectEnd.horizontalIndent = 15;
		labelProjectEnd.setLayoutData(gd_labelProjectEnd);

		dateChooserProjectEnd = new DateChooserCombo(compositeDetails, SWT.NONE);
		dateChooserProjectEnd.setToolTipText(Messages
				.getString("ProjectEditor.field.project.end.description")); //$NON-NLS-1$
		dateChooserProjectEnd.setGridVisible(false);
		final GridData gd_dateChooserProjectEnd = new GridData(SWT.FILL,
				SWT.CENTER, true, false);
		gd_dateChooserProjectEnd.horizontalIndent = 15;
		dateChooserProjectEnd.setLayoutData(gd_dateChooserProjectEnd);
		dateChooserProjectEnd.setData(FormToolkit.KEY_DRAW_BORDER,
				FormToolkit.TEXT_BORDER);
		toolkit.paintBordersFor(compositeDetails);

		final Section sectionProjecttasks = toolkit.createSection(body,
				Section.TWISTIE | Section.DESCRIPTION | Section.EXPANDED
						| Section.TITLE_BAR);
		sectionProjecttasks.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				true, true));
		sectionProjecttasks.setDescription(Messages
				.getString("ProjectEditor.section.activity.description")); //$NON-NLS-1$
		sectionProjecttasks.setText(Messages
				.getString("ProjectEditor.section.activity.text")); //$NON-NLS-1$

		final Composite compositeTasks = toolkit.createComposite(
				sectionProjecttasks, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 1;
		gridLayout.marginHeight = 1;
		gridLayout.numColumns = 2;
		compositeTasks.setLayout(gridLayout);
		sectionProjecttasks.setClient(compositeTasks);
		toolkit.paintBordersFor(compositeTasks);

		tableViewerTasks = new TableViewer(compositeTasks, SWT.FULL_SELECTION);
		tableViewerTasks.setContentProvider(new ArrayContentProvider());
		tableViewerTasks.setLabelProvider(new LabelProvider());
		Table tableTasks = tableViewerTasks.getTable();
		tableTasks.setHeaderVisible(true);
		tableTasks.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 3));
		tableTasks.setLinesVisible(true);
		toolkit.adapt(tableTasks, true, true);

		final TableColumn tableColumnTask = new TableColumn(tableTasks,
				SWT.NONE);
		tableColumnTask.setWidth(150);
		tableColumnTask.setText(Messages
				.getString("ProjectEditor.table.activity.column.name")); //$NON-NLS-1$

		final TableColumn tableColumnDescription = new TableColumn(tableTasks,
				SWT.NONE);
		tableColumnDescription.setWidth(200);
		tableColumnDescription.setText(Messages
				.getString("ProjectEditor.table.activity.column.description")); //$NON-NLS-1$

		final TableColumn tableColumnActive = new TableColumn(tableTasks,
				SWT.NONE);
		tableColumnActive.setWidth(100);
		tableColumnActive.setText(Messages
				.getString("ProjectEditor.table.activity.column.active")); //$NON-NLS-1$
		tableViewerTasks.setInput(projectActivities);

		final Button buttonAddTask = toolkit.createButton(compositeTasks,
				Messages.getString("ProjectEditor.button.add"), SWT.NONE); //$NON-NLS-1$
		final GridData gd_buttonAddTask = new GridData(100, SWT.DEFAULT);
		gd_buttonAddTask.horizontalIndent = 15;
		buttonAddTask.setLayoutData(gd_buttonAddTask);
		buttonAddTask.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				ActivityWizardObjectProvider provider = new ActivityWizardObjectProvider(
						projectAdapter.getAdaptedObservable());
				ActivityWizard taskWizard = new ActivityWizard(provider);
				WizardDialog wizardDialog = new WizardDialog(getSite()
						.getShell(), taskWizard);
				if (wizardDialog.open() == Dialog.OK) {
					projectActivities.add(provider.getActivity());
					tableViewerTasks.setInput(projectActivities);
					markDirty();
				}
			}
		});

		final Button buttonDisableTask = toolkit
				.createButton(compositeTasks, Messages
						.getString("ProjectEditor.button.deactivate"), SWT.NONE); //$NON-NLS-1$
		final GridData gd_buttonDisableTask = new GridData(100, SWT.DEFAULT);
		gd_buttonDisableTask.horizontalIndent = 15;
		buttonDisableTask.setLayoutData(gd_buttonDisableTask);
		buttonDisableTask.addSelectionListener(new SelectionAdapter() {
			/**
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent event) {
				IStructuredSelection structuredSelection = (IStructuredSelection) tableViewerTasks
						.getSelection();
				if (!structuredSelection.isEmpty()) {
					ActivityDTO activityDTO = (ActivityDTO) structuredSelection
							.getFirstElement();
					activityDTO.setActive(false);
					getSite().getShell().getDisplay().asyncExec(new Runnable() {
						/**
						 * @see java.lang.Runnable#run()
						 */
						public void run() {
							tableViewerTasks.refresh();
						}
					});
					markDirty();
				}
			}
		});
		//

		initDataBindings();
		initData();
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
		IObservableValue nameWidget = SWTObservables.observeText(
				textProjectName, SWT.Modify);
		bindingContext.bindValue(nameWidget, (IObservableValue) projectAdapter
				.getObservableValue("name"), formValidationAdapter //$NON-NLS-1$
				.getUpdateValueStrategyFactory().getUpdateValueStrategy(
						"textProjectName", textProjectName, //$NON-NLS-1$
						ValidatorFactory.MANDATORY_VALIDATOR), null);
		// Bind the name
		IObservableValue descriptionWidget = SWTObservables.observeText(
				textProjectDescription, SWT.Modify);
		bindingContext.bindValue(descriptionWidget,
				(IObservableValue) projectAdapter
						.getObservableValue("description"), null, null); //$NON-NLS-1$
		// Bind the departement
		IObservableValue startDateWidget = new DateChooserComboObservableValue(dateChooserProjectStart);
		bindingContext.bindValue(startDateWidget,
				(IObservableValue) projectAdapter
						.getObservableValue("startDate"), formValidationAdapter //$NON-NLS-1$
						.getUpdateValueStrategyFactory()
						.getUpdateValueStrategy(
								"dateChooserProjectStart", //$NON-NLS-1$
								dateChooserProjectStart,
								ValidatorFactory.MANDATORY_VALIDATOR), null);
		// Bind the weekHours
		IObservableValue endDateWidget = new DateChooserComboObservableValue(dateChooserProjectEnd);
		bindingContext
				.bindValue(endDateWidget, (IObservableValue) projectAdapter
						.getObservableValue("endDate"), formValidationAdapter //$NON-NLS-1$
						.getUpdateValueStrategyFactory()
						.getUpdateValueStrategy(
								"dateChooserProjectEnd", //$NON-NLS-1$
								dateChooserProjectEnd,
								ValidatorFactory.MANDATORY_VALIDATOR), null);
	}

	/**
	 * Proceeds the setup of the data.
	 */
	private void initData() {
		bindingContext.updateTargets();
		projectActivities = new ArrayList<ActivityDTO>();
		projectActivities.addAll(projectAdapter.getAdaptedObservable()
				.getActivities());
	}

	/**
	 * Activates the dirty handling.
	 */
	private void activateDirtyListener() {
		projectAdapter.addChangeListener(dirtyListener);
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
		if (textProjectName != null) {
			textProjectName.setFocus();
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
			// set the tasks
			projectAdapter.getAdaptedObservable().getActivities().clear();
			projectAdapter.getAdaptedObservable().getActivities().addAll(
					projectActivities);
			// delegate to save controller to actually save the data
			saveController.setData(projectAdapter.getAdaptedObservable());
			saveController.initiateServerAccess();
		}
		else {
			MessageDialog.openError(getSite().getShell(), Messages
					.getString("ProjectEditor.dialog.error.title"), //$NON-NLS-1$
					Messages.getString("ProjectEditor.dialog.error.message")); //$NON-NLS-1$
		}
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		// Do the Save As operation
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#init(org.eclipse.ui.IEditorSite,
	 *      org.eclipse.ui.IEditorInput)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		DataProviderEditorInput<ProjectDTO> dataProviderEditorInput = (DataProviderEditorInput<ProjectDTO>) input;
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

	private class LabelProvider extends org.eclipse.jface.viewers.LabelProvider
			implements ITableLabelProvider, IColorProvider {

		public String getColumnText(Object element, int columnIndex) {
			ActivityDTO activityDTO = (ActivityDTO) element;
			switch (columnIndex) {
				case 0:
					return activityDTO.getName();
				case 1:
					return activityDTO.getDescription();
				case 2:
					if (activityDTO.getActive()) {
						return Messages
								.getString("ProjectEditor.table.activity.column.active.yes"); //$NON-NLS-1$
					}
					return Messages
							.getString("ProjectEditor.table.activity.column.active.no"); //$NON-NLS-1$
				default:
					return "Unknown column"; //$NON-NLS-1$
			}
		}

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public Color getBackground(Object element) {
			return null;
		}

		public Color getForeground(Object element) {
			ActivityDTO activityDTO = (ActivityDTO) element;
			if (!activityDTO.getActive()) {
				return ResourceManager.getColor(SWT.COLOR_GRAY);
			}
			return null;
		}
	}
}
