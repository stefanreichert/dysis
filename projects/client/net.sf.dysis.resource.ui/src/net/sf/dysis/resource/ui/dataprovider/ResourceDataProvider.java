/**
 * ResourceDataProvider.java created on 01.03.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.resource.ui.dataprovider;

import java.util.Collection;
import java.util.Collections;

import net.sf.dysis.base.ui.dataprovider.IDataProvider;
import net.sf.dysis.base.ui.dataprovider.IWritableDataProvider;
import net.sf.dysis.base.ui.dataprovider.key.ICollectionKey;
import net.sf.dysis.base.ui.dataprovider.key.IKey;
import net.sf.dysis.base.ui.dataprovider.key.PrimaryKey;
import net.sf.dysis.base.ui.logging.IPluginLogger;
import net.sf.dysis.base.ui.logging.PluginLogger;
import net.sf.dysis.planing.core.service.IProjectRemoteService;
import net.sf.dysis.resource.core.dto.PersonDTO;
import net.sf.dysis.resource.core.service.IPersonRemoteService;
import net.sf.dysis.resource.ui.Activator;

import org.eclipse.springframework.util.SpringExtensionFactory;

/**
 * <code>IDataProvider</code> for projects. This {@link IDataProvider} uses
 * dependency injection by Spring DM to get access to the necessary
 * {@link IProjectRemoteService}. Therefor, the <i>Dataprovider Extension</i>
 * indirectly links to a Spring Bean in the context defined at
 * <i>META-INF/spring/dataproviderContext.xml</i> using the
 * {@link SpringExtensionFactory}.
 * 
 * @author Stefan Reichert
 */
public class ResourceDataProvider implements IWritableDataProvider {

	/** This class' IPluginLogger instance. */
	private static IPluginLogger logger = PluginLogger.getLogger(Activator
			.getDefault());

	/** This data providers type. */
	public static final String TYPE = "core.resource.dto"; //$NON-NLS-1$

	/** The {@link IProjectRemoteService} injected by Spring DM. */
	private IPersonRemoteService personRemoteService;

	/** {@inheritDoc} */
	public Object getData(IKey key) {
		assert key instanceof PrimaryKey;
		PrimaryKey primaryKey = (PrimaryKey) key;
		try {
			return personRemoteService.load(primaryKey.getPrimaryKey());
		}
		catch (Exception exception) {
			logger.info(exception.getMessage(), exception);
			return null;
		}
	}

	/** {@inheritDoc} */
	public String getProviderGroup() {
		return "core.resource"; //$NON-NLS-1$
	}

	/** {@inheritDoc} */
	public String getType() {
		return TYPE;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	public Collection getDataCollection(ICollectionKey collectionKey) {
		try {
			if (collectionKey == ICollectionKey.ALL) {
				return personRemoteService.loadAll();
			}
		}
		catch (Exception exception) {
			logger.error(exception.getMessage(), exception);
		}
		return Collections.EMPTY_LIST;
	}

	/** {@inheritDoc} */
	public IKey getKey(Object data) {
		assert data instanceof PersonDTO : "Object of type <PersonDTO> expected"; //$NON-NLS-1$
		PersonDTO Person = (PersonDTO) data;
		return new PrimaryKey(Person.getId());
	}

	/** {@inheritDoc} */
	public Object putData(IKey key, Object data) {
		assert data instanceof PersonDTO : "Object of type <PersonDTO> expected"; //$NON-NLS-1$
		PersonDTO Person = (PersonDTO) data;
		return personRemoteService.save(Person);
	}

	/** {@inheritDoc} */
	public void removeData(IKey key) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @param personRemoteService
	 *            the personRemoteService to set
	 */
	public void setPersonRemoteService(IPersonRemoteService personRemoteService) {
		this.personRemoteService = personRemoteService;
	}

	/**
	 * @return the personRemoteService
	 */
	public IPersonRemoteService getPersonRemoteService() {
		return personRemoteService;
	}

}
