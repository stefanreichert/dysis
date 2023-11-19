package net.sf.dysis.resource.ui.wizard.resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.dysis.base.ui.dataprovider.IDataProvider;
import net.sf.dysis.base.ui.dataprovider.key.ICollectionKey;
import net.sf.dysis.base.ui.dataprovider.registry.Registry;
import net.sf.dysis.planing.core.dto.ActivityDTO;
import net.sf.dysis.resource.ui.Activator;
import net.sf.dysis.resource.ui.Messages;
import net.sf.dysis.resource.ui.provider.ActivityLabelProvider;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;

import com.swtdesigner.ResourceManager;

public class ResourceWizardActivityPage extends WizardPage {

	/** Widget. */
	private TableViewer tableViewerSelected;

	/** Widget. */
	private TableViewer tableViewerAvailable;

	/** <code>Set</code> of available tasks. */
	private Set<ActivityDTO> availableActivities;

	/** <code>Set</code> of selected tasks. */
	private Set<ActivityDTO> selectedActivities;

	/**
	 * Create the wizard
	 */
	public ResourceWizardActivityPage() {
		super("resource.wizard.resource"); //$NON-NLS-1$
		setTitle(Messages.getString("ResourceWizardActivityPage.title")); //$NON-NLS-1$
		setDescription(Messages.getString("ResourceWizardActivityPage.description")); //$NON-NLS-1$
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator
				.getDefault(), "img/resource_wizard.gif")); //$NON-NLS-1$
		availableActivities = getAvailableActivities();
		selectedActivities = new HashSet<ActivityDTO>();
	}

	/**
	 * @return all available tasks
	 */
	@SuppressWarnings("unchecked")
	private Set<ActivityDTO> getAvailableActivities() {
		IDataProvider activitydataProvider = Registry.getRegistry()
				.lookupDataProvider("core.activity.dto"); //$NON-NLS-1$
		return new HashSet<ActivityDTO>((List<ActivityDTO>) activitydataProvider
				.getDataCollection(ICollectionKey.ALL));
	}

	/**
	 * Create contents of the wizard
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		container.setLayout(gridLayout);
		//
		setControl(container);
		Table tableAvailable;

		final Label labelAvailable = new Label(container, SWT.NONE);
		labelAvailable.setText(Messages.getString("ResourceWizardActivityPage.available.tasks")); //$NON-NLS-1$
		new Label(container, SWT.NONE);

		final Label labelSelected = new Label(container, SWT.NONE);
		labelSelected.setText(Messages.getString("ResourceWizardActivityPage.selected.tasks")); //$NON-NLS-1$

		tableViewerAvailable = new TableViewer(container, SWT.VIRTUAL
				| SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER);
		tableViewerAvailable.setContentProvider(new ArrayContentProvider());
		tableViewerAvailable.setLabelProvider(new ActivityLabelProvider());
		tableViewerAvailable.setSorter(new ViewerSorter());
		tableViewerAvailable.setInput(availableActivities);
		tableAvailable = tableViewerAvailable.getTable();
		final GridData gd_tableAvailable = new GridData(SWT.FILL, SWT.FILL,
				true, true);
		gd_tableAvailable.widthHint = 200;
		tableAvailable.setLayoutData(gd_tableAvailable);

		final Composite composite = new Composite(container, SWT.NONE);
		composite.setLayout(new GridLayout());

		final Button buttonAddAll = new Button(composite, SWT.NONE);
		buttonAddAll.setLayoutData(new GridData(30, SWT.DEFAULT));
		buttonAddAll.setText(">>"); //$NON-NLS-1$
		buttonAddAll.addSelectionListener(new SelectionAdapter() {
			/**
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent event) {
				availableActivities.clear();
				selectedActivities = getAvailableActivities();
				tableViewerAvailable.setInput(availableActivities);
				tableViewerSelected.setInput(selectedActivities);
			}
		});

		final Button addSelected = new Button(composite, SWT.NONE);
		addSelected.setLayoutData(new GridData(30, SWT.DEFAULT));
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
				availableActivities.removeAll(structuredSelection.toList());
				selectedActivities.addAll(structuredSelection.toList());
				tableViewerAvailable.setInput(availableActivities);
				tableViewerSelected.setInput(selectedActivities);
			}
		});

		final Button buttonRemoveSelected = new Button(composite, SWT.NONE);
		buttonRemoveSelected.setLayoutData(new GridData(30, SWT.DEFAULT));
		buttonRemoveSelected.setText("<"); //$NON-NLS-1$
		buttonRemoveSelected.addSelectionListener(new SelectionAdapter() {
			/**
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@SuppressWarnings("unchecked")
			@Override
			public void widgetSelected(SelectionEvent event) {
				IStructuredSelection structuredSelection = (IStructuredSelection) tableViewerSelected
						.getSelection();
				availableActivities.addAll(structuredSelection.toList());
				selectedActivities.removeAll(structuredSelection.toList());
				tableViewerAvailable.setInput(availableActivities);
				tableViewerSelected.setInput(selectedActivities);
			}
		});

		final Button buttonRemoveAll = new Button(composite, SWT.NONE);
		buttonRemoveAll.setLayoutData(new GridData(30, SWT.DEFAULT));
		buttonRemoveAll.setText("<<"); //$NON-NLS-1$
		buttonRemoveAll.addSelectionListener(new SelectionAdapter() {
			/**
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent event) {
				availableActivities = getAvailableActivities();
				selectedActivities.clear();
				tableViewerAvailable.setInput(availableActivities);
				tableViewerSelected.setInput(selectedActivities);
			}
		});

		tableViewerSelected = new TableViewer(container, SWT.VIRTUAL
				| SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER);
		tableViewerSelected.setContentProvider(new ArrayContentProvider());
		tableViewerSelected.setLabelProvider(new ActivityLabelProvider());
		tableViewerSelected.setSorter(new ViewerSorter());
		tableViewerSelected.setInput(selectedActivities);
		Table tableSelected = tableViewerSelected.getTable();
		final GridData gd_tableSelected = new GridData(SWT.FILL, SWT.FILL,
				true, true);
		gd_tableSelected.widthHint = 200;
		tableSelected.setLayoutData(gd_tableSelected);
	}

	/**
	 * @return the selectedActivities
	 */
	public List<ActivityDTO> getSelectedActivities() {
		return new ArrayList<ActivityDTO>(selectedActivities);
	}

}
