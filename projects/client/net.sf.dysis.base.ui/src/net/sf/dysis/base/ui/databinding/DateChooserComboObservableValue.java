/**
 * DateChooserComboObservableValue.java created on 28.03.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.databinding;

import java.util.Date;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.nebula.widgets.datechooser.DateChooserCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * @author Stefan Reichert
 */
public class DateChooserComboObservableValue extends AbstractObservableValue {

	/**
	 * {@link Text} widget that this is being observed.
	 */
	private final DateChooserCombo dateChooserCombo;

	/** The old value. */
	protected Date oldValue;

	private Listener listener = new Listener() {
		public void handleEvent(final Event event) {
			Date newValue = dateChooserCombo.getValue();
			if (!newValue.equals(DateChooserComboObservableValue.this.oldValue)) {
				fireValueChange(Diffs
						.createValueDiff(
								DateChooserComboObservableValue.this.oldValue,
								newValue));
				DateChooserComboObservableValue.this.oldValue = newValue;
			}
		}
	};

	public DateChooserComboObservableValue(
			final DateChooserCombo dateChooserCombo) {
		this.dateChooserCombo = dateChooserCombo;
		this.dateChooserCombo.addListener(SWT.Modify, listener);
	}

	@Override
	protected Object doGetValue() {
		// the utility method creates a Date from the DateTime
		return dateChooserCombo.getValue();
	}

	@Override
	protected void doSetValue(final Object value) {
		if (value instanceof Date) {
			// the utility method sets the date on the DateTime
			dateChooserCombo.setValue((Date) value);
		}
	}

	public Object getValueType() {
		return Date.class;
	}

	@Override
	public synchronized void dispose() {
		this.dateChooserCombo.removeListener(SWT.Modify, this.listener);
		super.dispose();
	}

}
