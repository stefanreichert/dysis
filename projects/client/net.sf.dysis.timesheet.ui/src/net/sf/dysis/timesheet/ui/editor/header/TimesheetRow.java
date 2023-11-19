package net.sf.dysis.timesheet.ui.editor.header;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import net.sf.dysis.base.ui.editor.listener.IDirty;
import net.sf.dysis.planing.core.dto.ActivityTimeEntryDTO;
import net.sf.dysis.planing.core.dto.TimeEntryDTO;
import net.sf.dysis.planing.core.dto.TimeEntryDTOImpl;
import net.sf.dysis.timesheet.ui.Messages;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.nebula.widgets.compositetable.GridRowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.swtdesigner.ResourceManager;

/**
 * A row of the timesheet containing {@link Text} widgets to enter the amount of
 * hours worked on a specific day.
 * 
 * @author Stefan Reichert
 */
public class TimesheetRow extends Composite {

	/** The <code>Text</code> containing the task's name. */
	private final Label labelTaskName;

	/** The array of <code>Text</code> containing the working hours per day. */
	private final TimeEntryCell[] cells;

	/** The context {@link ActivityTimeEntryDTO}. */
	private ActivityTimeEntryDTO activityTimeEntryDTO;

	/** The context {@link IDirty} object. */
	private IDirty dirty;

	/**
	 * Create the <code>TimesheetRow</code> <code>Composite</code>.
	 * 
	 * @param parent
	 *            The parent <code>Composite</code>
	 * @param style
	 *            The style bits
	 */
	public TimesheetRow(Composite parent, int style) {
		super(parent, style);
		Calendar timeEntryDay = Calendar.getInstance();
		timeEntryDay.clear();
		timeEntryDay.set(Calendar.MONTH, Calendar.getInstance().get(
				Calendar.MONTH));
		timeEntryDay.set(Calendar.YEAR, Calendar.getInstance().get(
				Calendar.YEAR));
		int[] layoutArray = new int[timeEntryDay
				.getActualMaximum(Calendar.DAY_OF_MONTH) + 1];
		labelTaskName = new Label(this, SWT.NULL);
		int daysOfMonth = timeEntryDay.getActualMaximum(Calendar.DAY_OF_MONTH);
		cells = new TimeEntryCell[daysOfMonth];
		layoutArray[0] = 150;
		for (int i = 0; i < daysOfMonth; i++) {
			cells[i] = new TimeEntryCell(this, timeEntryDay.getTime());
			timeEntryDay.add(Calendar.DAY_OF_MONTH, 1);
			layoutArray[i + 1] = 35;
		}
		setLayout(new GridRowLayout(layoutArray, false));
	}

	/**
	 * Set the context {@link ActivityTimeEntryDTO}. The row is cleared from the
	 * currently attached data and setup with the given data.
	 * 
	 * @param activityTimeEntryDTO
	 *            The context {@link ActivityTimeEntryDTO}
	 */
	public void setActivity(ActivityTimeEntryDTO activityTimeEntryDTO) {
		this.activityTimeEntryDTO = activityTimeEntryDTO;
		clear();
		labelTaskName.setText(" " //$NON-NLS-1$
				+ activityTimeEntryDTO.getActivity().getName());
		labelTaskName.setToolTipText(activityTimeEntryDTO.getActivity()
				.getDescription());
		// initialize cells where context data exists
		for (TimeEntryDTO timeEntryDTO : new ArrayList<TimeEntryDTO>(
				activityTimeEntryDTO.getTimeEntries())) {
			Calendar timeEntryDate = Calendar.getInstance();
			int currentYear = timeEntryDate.get(Calendar.YEAR);
			int currentMonth = timeEntryDate.get(Calendar.MONTH);
			timeEntryDate.setTime(timeEntryDTO.getDate());
			// if time entry is relevant, initialize corresponding cell
			if (timeEntryDate.get(Calendar.YEAR) == currentYear
					&& timeEntryDate.get(Calendar.MONTH) == currentMonth) {
				int dayOfMonthIndex = timeEntryDate.get(Calendar.DAY_OF_MONTH) - 1;
				cells[dayOfMonthIndex].init(timeEntryDTO);
			}
		}
	}

	/**
	 * @param dirty
	 *            The context {@link IDirty}
	 */
	public void setIDirty(IDirty dirty) {
		this.dirty = dirty;
	}

	/**
	 * Clears this row by calling {@link TimeEntryCell#clear(boolean)} on each
	 * {@link TimeEntryCell}.
	 */
	private void clear() {
		for (int index = 0; index < cells.length; index++) {
			boolean matchFromDate = activityTimeEntryDTO.getBookableFromDate()
					.compareTo(cells[index].getDay()) <= 0;
			boolean matchToDate = activityTimeEntryDTO.getBookableToDate()
					.compareTo(cells[index].getDay()) >= 0;
			cells[index].clear(matchFromDate && matchToDate);
		}
	}

	/**
	 * A cell implementation for a time entry. It contains a corresponding
	 * {@link Text} widget, the context {@link Date} and a {@link TimeEntryDTO}
	 * representing the persistent object. A {@link ModifyListener} ensures the
	 * transfer of the entered data to the persistent object.
	 * 
	 * @author Stefan Reichert
	 */
	private class TimeEntryCell {
		/** The context {@link Date}. */
		private final Date day;

		/** The {@link Text} widget. */
		private final Text cellText;

		/** The {@link ModifyListener} ensures the data transfer. */
		private final ModifyListener cellModifyListener;

		/** The persistent {@link TimeEntryDTO} object. */
		private TimeEntryDTO cellTimeEntry;

		/**
		 * Constructor for <class>TimeEntryCell</class>.
		 */
		public TimeEntryCell(Composite parent, final Date day) {
			super();
			this.day = day;
			cellModifyListener = new ModifyListener() {
				/** {@inheritDoc} */
				public void modifyText(ModifyEvent event) {
					int workingHours = 0;
					if (cellText.getText().length() > 0) {
						boolean valid = false;
						try {
							int enteredWorkingHours = Integer.valueOf(cellText
									.getText());
							valid = enteredWorkingHours <= 12
									&& enteredWorkingHours >= 0;
							workingHours = enteredWorkingHours;
						}
						catch (Exception exception) {
							// intentionally do nothing
						}
						if (!valid) {
							MessageDialog
									.openInformation(getShell(), Messages.getString("TimesheetRow.dialog.error.title"), //$NON-NLS-1$
											Messages.getString("TimesheetRow.dialog.error.message")); //$NON-NLS-1$
							cellText.setText("0"); //$NON-NLS-1$
						}
					}
					if (cellTimeEntry == null) {
						// no persistent object available -> create one
						cellTimeEntry = new TimeEntryDTOImpl();
						cellTimeEntry.setActivity(activityTimeEntryDTO
								.getActivity());
						cellTimeEntry.setPerson(activityTimeEntryDTO
								.getPerson());
						cellTimeEntry.setDate(day);
						// attach object to context
						activityTimeEntryDTO.getTimeEntries()
								.add(cellTimeEntry);
					}
					cellTimeEntry.setHours(workingHours);
					if (dirty != null) {
						dirty.markDirty();
					}
				}
			};
			cellText = new Text(parent, SWT.TRAIL);
			cellText.setTextLimit(2);
			cellText.addModifyListener(cellModifyListener);
		}

		/**
		 * Clears the content of this cell. This means both clear the
		 * {@link Text} widget and reseting the persistent {@link TimeEntryDTO}.
		 * 
		 * @param enabled
		 *            The flag whether the cell should be set enabled
		 */
		public void clear(boolean enabled) {
			cellText.removeModifyListener(cellModifyListener);
			cellText.setText(""); //$NON-NLS-1$
			cellText.setEnabled(enabled);
			if (enabled) {
				cellText.setBackground(ResourceManager
						.getColor(SWT.COLOR_WHITE));
			}
			else {
				cellText.setBackground(ResourceManager
						.getColor(SWT.COLOR_YELLOW));
			}
			cellTimeEntry = null;
			cellText.addModifyListener(cellModifyListener);
		}

		/**
		 * Initializes this cell with an existing {@link TimeEntryDTO}. The
		 * value is transferred to the {@link Text} widget.
		 * 
		 * @param timeEntryDTO The persistent object
		 */
		public void init(TimeEntryDTO timeEntryDTO) {
			cellText.removeModifyListener(cellModifyListener);
			cellTimeEntry = timeEntryDTO;
			cellText.setText(timeEntryDTO.getHours().toString());
			cellText.addModifyListener(cellModifyListener);
		}

		/**
		 * @return the context {@link Date}
		 */
		public Date getDay() {
			return day;
		}
	}
}
