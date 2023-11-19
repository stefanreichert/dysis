/**
 * ProjectDataProvider.java created on 28.03.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planning.ui.dataprovider;

import java.util.Collection;

import net.sf.dysis.base.ui.dataprovider.IDataProvider;
import net.sf.dysis.base.ui.dataprovider.IWritableDataProvider;
import net.sf.dysis.base.ui.dataprovider.key.ICollectionKey;
import net.sf.dysis.base.ui.dataprovider.key.IKey;
import net.sf.dysis.base.ui.dataprovider.key.PrimaryKey;
import net.sf.dysis.base.ui.logging.IPluginLogger;
import net.sf.dysis.base.ui.logging.PluginLogger;
import net.sf.dysis.planing.core.dto.ProjectDTO;
import net.sf.dysis.planing.core.service.IProjectRemoteService;
import net.sf.dysis.planning.ui.Activator;

/**
 * <code>IDataProvider</code> for activities. This {@link IDataProvider} uses
 * the Spring services promoted on the OSGi bus by using the
 * {@link Activator#getService(Class)} method.
 * 
 * @author Stefan Reichert
 */
public class ProjectDataProvider implements IWritableDataProvider {

	/** This class' IPluginLogger instance. */
	private static IPluginLogger logger = PluginLogger.getLogger(Activator
			.getDefault());

	/** This data providers type. */
	public static final String TYPE = "core.project.dto"; //$NON-NLS-1$

	/** {@inheritDoc} */
	public Object getData(IKey key) {
		assert key instanceof PrimaryKey;
		PrimaryKey primaryKey = (PrimaryKey) key;
		try {
			return Activator.getDefault().getService(
					IProjectRemoteService.class).load(
					primaryKey.getPrimaryKey());
		}
		catch (Exception exception) {
			logger.info(exception.getMessage(), exception);
			return null;
		}
	}

	/** {@inheritDoc} */
	public String getProviderGroup() {
		return "core.project"; //$NON-NLS-1$
	}

	/** {@inheritDoc} */
	public String getType() {
		return TYPE;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	public Collection getDataCollection(ICollectionKey collectionKey) {
		if (collectionKey == ICollectionKey.ALL) {
			try {
				return Activator.getDefault().getService(
						IProjectRemoteService.class).loadAll();
			}
			catch (Exception exception) {
				logger.info(exception.getMessage(), exception);
				return null;
			}
		}
		else {
			throw new UnsupportedOperationException("Invalid key");
		}
	}

	/** {@inheritDoc} */
	public IKey getKey(Object data) {
		assert data instanceof ProjectDTO : "Object of type <ProjectDTO> expected"; //$NON-NLS-1$
		ProjectDTO project = (ProjectDTO) data;
		return new PrimaryKey(project.getId());
	}

	/** {@inheritDoc} */
	public Object putData(IKey key, Object data) {
		assert data instanceof ProjectDTO : "Object of type <ProjectDTO> expected"; //$NON-NLS-1$
		ProjectDTO project = (ProjectDTO) data;
		return Activator.getDefault().getService(IProjectRemoteService.class)
				.save(project);
	}

	/** {@inheritDoc} */
	public void removeData(IKey key) {
		throw new UnsupportedOperationException();
	}

}
