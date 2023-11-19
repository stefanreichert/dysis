/**
 * DataProviderEditorInput.java created on 29.02.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.editor.input;

import net.sf.dysis.base.ui.dataprovider.key.ICollectionKey;
import net.sf.dysis.base.ui.dataprovider.key.IKey;
import net.sf.dysis.base.ui.dataprovider.registry.Registry;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * IditorInput based on a <code>IDataProvider</code>.
 * 
 * @author Stefan Reichert
 */
public class DataProviderEditorInput<DataType> implements IEditorInput {

	/** Input details for this <code>EditorInput</code>. */
	private DataProviderInputDetails dataProviderInputDetails;

	/** The type of <code>IDataProvider</code> to use. */
	private String providerType;

	/** The <code>IKey</code> of the data to use. */
	private IKey key;

	/**
	 * Constructor for <class>DataProviderEditorInput</class>.
	 */
	public DataProviderEditorInput(IKey key, String providerType,
			DataProviderInputDetails dataProviderInputDetails) {
		super();
		this.key = key;
		this.providerType = providerType;
		this.dataProviderInputDetails = dataProviderInputDetails;
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#exists()
	 */
	public boolean exists() {
		return getKey() == null;
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		return dataProviderInputDetails.getImageDescriptor();
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#getName()
	 */
	public String getName() {
		return dataProviderInputDetails.getName();
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#getPersistable()
	 */
	public IPersistableElement getPersistable() {
		return null;
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 */
	public String getToolTipText() {
		return dataProviderInputDetails.getTooltip();
	}

	/**
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter) {
		return null;
	}

	/**
	 * Returns the input data for this <code>IEditorInput</code>.
	 */
	@SuppressWarnings("unchecked")
	public DataType getInputData() {
		if (key instanceof ICollectionKey) {
			return (DataType) Registry.getRegistry().lookupDataProvider(
					providerType).getDataCollection((ICollectionKey) key);
		}
		return (DataType) Registry.getRegistry().lookupDataProvider(
				providerType).getData(key);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return key.hashCode();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (object instanceof DataProviderEditorInput) {
			DataProviderEditorInput<?> otherDataProviderEditorInput = (DataProviderEditorInput<?>) object;
			return getKey().equals(otherDataProviderEditorInput.getKey())
					&& getProviderType().equals(
							otherDataProviderEditorInput.getProviderType());
		}
		return false;
	}

	/**
	 * @return the providerType
	 */
	public String getProviderType() {
		return providerType;
	}

	/**
	 * @return the key
	 */
	public IKey getKey() {
		return key;
	}
}
