/**
 * ActivityDataProvider.java created on 23.03.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planning.ui.dataprovider;

import java.util.Collection;
import java.util.Collections;

import net.sf.dysis.base.ui.dataprovider.IDataProvider;
import net.sf.dysis.base.ui.dataprovider.key.ForeignKey;
import net.sf.dysis.base.ui.dataprovider.key.ICollectionKey;
import net.sf.dysis.base.ui.dataprovider.key.IKey;
import net.sf.dysis.base.ui.dataprovider.key.PrimaryKey;
import net.sf.dysis.base.ui.logging.IPluginLogger;
import net.sf.dysis.base.ui.logging.PluginLogger;
import net.sf.dysis.planing.core.dto.ActivityDTO;
import net.sf.dysis.planing.core.service.IActivityRemoteService;
import net.sf.dysis.planning.ui.Activator;

/**
 * <code>IDataProvider</code> for activities. This {@link IDataProvider} uses
 * the Spring services promoted on the OSGi bus by using the
 * {@link Activator#getService(Class)} method.
 * 
 * @author Stefan Reichert
 */
public class ActivityDataProvider implements IDataProvider {

	/** This class' IPluginLogger instance. */
	private static IPluginLogger logger = PluginLogger.getLogger(Activator
			.getDefault());

	/** This data providers type. */
	public static final String TYPE = "core.activity.dto"; //$NON-NLS-1$

	/** The foreign key objects which is supported for loading. */
	public static final String FOREIGN_KEY_PERSON = "resource"; //$NON-NLS-1$

	public static final String FOREIGN_KEY_PROJECT = "project"; //$NON-NLS-1$

	/** {@inheritDoc} */
	public Object getData(IKey key) {
		assert key instanceof PrimaryKey;
		PrimaryKey primaryKey = (PrimaryKey) key;
		try {
			return Activator.getDefault().getService(
					IActivityRemoteService.class).load(
					primaryKey.getPrimaryKey());
		}
		catch (Exception exception) {
			logger.info(exception.getMessage(), exception);
			return null;
		}
	}

	/** {@inheritDoc} */
	public String getProviderGroup() {
		return "core.activity"; //$NON-NLS-1$
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
				return Activator.getDefault().getService(
						IActivityRemoteService.class).loadAll();
			}
			else if (collectionKey instanceof ForeignKey) {
				ForeignKey foreignKey = (ForeignKey) collectionKey;
				if (FOREIGN_KEY_PERSON.equals(foreignKey.getHostingEntity())) {
					return Activator.getDefault().getService(
							IActivityRemoteService.class).findByPerson(
							foreignKey.getForeignKey());
				}
				if (FOREIGN_KEY_PROJECT.equals(foreignKey.getHostingEntity())) {
					return Activator.getDefault().getService(
							IActivityRemoteService.class).findByProject(
							foreignKey.getForeignKey());
				}

			}
		}
		catch (Exception exception) {
			logger.error(exception.getMessage(), exception);
		}
		return Collections.EMPTY_LIST;
	}

	/** {@inheritDoc} */
	public IKey getKey(Object data) {
		assert data instanceof ActivityDTO : "Object of type <ActivityDTO> expected"; //$NON-NLS-1$
		ActivityDTO Activity = (ActivityDTO) data;
		return new PrimaryKey(Activity.getId());
	}

	/** {@inheritDoc} */
	public Object putData(IKey key, Object data) {
		assert data instanceof ActivityDTO : "Object of type <ActivityDTO> expected"; //$NON-NLS-1$
		ActivityDTO Activity = (ActivityDTO) data;
		return Activator.getDefault().getService(IActivityRemoteService.class)
				.save(Activity);
	}

	/** {@inheritDoc} */
	public void removeData(IKey key) {
		throw new UnsupportedOperationException();
	}

}
