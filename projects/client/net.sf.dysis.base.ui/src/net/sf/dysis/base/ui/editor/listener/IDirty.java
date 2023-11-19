/**
 * IDirty.java created on 23.03.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.editor.listener;

/**
 * Interface for an <code>EditorPart</code> to be marked as dirty.
 *
 * @author Stefan Reichert
 *
 */
public interface IDirty {

	/**
	 * Marks the editor as dirty;
	 */
	void markDirty();
}
