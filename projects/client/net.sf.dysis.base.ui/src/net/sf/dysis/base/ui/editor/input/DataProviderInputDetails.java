/**
 * DataProviderInputDetails.java created on 02.03.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.editor.input;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * Provides details for the content provided by the
 * <code>DataProviderEditorInput</code>
 * 
 * @author Stefan Reichert
 */
public final class DataProviderInputDetails {

	/** The human readable name of this <code>IDataProvider</code>. */
	private String tooltip;

	/** the human readable tooltip of this <code>IDataProvider</code>. */
	private String name;

	/** The <code>ImageDescriptor</code> of this <code>IDataProvider</code>. */
	private ImageDescriptor imageDescriptor;

	/**
	 * Constructor for <class>DataProviderInputDetails</class>.
	 */
	public DataProviderInputDetails(String name, String tooltip,
			ImageDescriptor imageDescriptor) {
		super();
		this.name = name;
		this.tooltip = tooltip;
		this.imageDescriptor = imageDescriptor;
	}

	/**
	 * @return the human readable name of this <code>IDataProvider</code>
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the human readable tooltip of this <code>IDataProvider</code>
	 */
	public String getTooltip() {
		return tooltip;
	}

	/**
	 * @return the <code>ImageDescriptor</code> of this
	 *         <code>IDataProvider</code>
	 */
	public ImageDescriptor getImageDescriptor() {
		return imageDescriptor;
	}

}
