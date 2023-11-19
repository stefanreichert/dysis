package net.sf.dysis.timesheet.ui.editor.header;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.sf.dysis.timesheet.ui.Messages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.nebula.widgets.compositetable.GridRowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.swtdesigner.ResourceManager;

/**
 * The header of the timesheet containing {@link Label} widgets as column
 * headers.
 * 
 * @author Stefan Reichert
 */
public class TimesheetHeader extends Composite {

	/**
	 * Create the <code>TimesheetHeader</code> <code>Composite</code>.
	 */
	public TimesheetHeader(Composite parent, int style) {
		super(parent, style);
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar
				.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
		calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		int[] layoutArray = new int[calendar
				.getActualMaximum(Calendar.DAY_OF_MONTH) + 1];
		new Label(this, SWT.NULL).setText(Messages.getString("TimesheetHeader.column.activity.header")); //$NON-NLS-1$
		layoutArray[0] = 150;
		// add a label for each day of the month
		for (int i = 1; i < (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + 1); i++) {
			Label label = new Label(this, SWT.NULL);
			label
					.setText(" " //$NON-NLS-1$
							+ new SimpleDateFormat("dd.MM.").format(calendar //$NON-NLS-1$
									.getTime()));
			if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
					|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				label.setForeground(ResourceManager.getColor(SWT.COLOR_RED));
			}
			calendar.roll(Calendar.DATE, 1);
			layoutArray[i] = 35;
		}
		setLayout(new GridRowLayout(layoutArray, false));
	}
}
