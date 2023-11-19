/**
 * DirtyListener.java created on 23.03.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.editor.listener;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;

/**
 * Listener implementation for marking <code>IDirty</code> implementors as
 * dirty.
 * 
 * @author Stefan Reichert
 */
public class DirtyListener implements IChangeListener {

	/** The <code>IDirty</code> to handle. */
	private IDirty dirty;

	/**
	 * Constructor for <class>DirtyListener</class>.
	 */
	public DirtyListener(IDirty dirty) {
		super();
		this.dirty = dirty;
	}

	/**
	 * @see org.eclipse.core.databinding.observable.IChangeListener#handleChange(org.eclipse.core.databinding.observable.ChangeEvent)
	 */
	public void handleChange(ChangeEvent event) {
		dirty.markDirty();
	}

}
