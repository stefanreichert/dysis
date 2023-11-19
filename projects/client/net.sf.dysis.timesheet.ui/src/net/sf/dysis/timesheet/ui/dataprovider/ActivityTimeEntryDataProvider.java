/**
 * ActivityTimeEntryDataProvider.java created on 23.03.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.timesheet.ui.dataprovider;

import java.util.Collection;
import java.util.List;

import net.sf.dysis.base.ui.dataprovider.IDataProvider;
import net.sf.dysis.base.ui.dataprovider.IWritableDataProvider;
import net.sf.dysis.base.ui.dataprovider.key.ForeignKey;
import net.sf.dysis.base.ui.dataprovider.key.ICollectionKey;
import net.sf.dysis.base.ui.dataprovider.key.IKey;
import net.sf.dysis.base.ui.dataprovider.key.PrimaryKey;
import net.sf.dysis.base.ui.logging.IPluginLogger;
import net.sf.dysis.base.ui.logging.PluginLogger;
import net.sf.dysis.planing.core.dto.ActivityTimeEntryDTO;
import net.sf.dysis.planing.core.service.IActivityRemoteService;
import net.sf.dysis.timesheet.ui.Activator;

/**
 * <code>IDataProvider</code> for activities. This {@link IDataProvider} uses
 * the Spring services promoted on the OSGi bus by using the
 * {@link Activator#getService(Class)} method.
 * 
 * @author Stefan Reichert
 */
public class ActivityTimeEntryDataProvider implements IWritableDataProvider {

	/** This class' IPluginLogger instance. */
	private static IPluginLogger logger = PluginLogger
			.getLogger(net.sf.dysis.timesheet.ui.Activator.getDefault());

	/** This data providers type. */
	public static final String TYPE = "core.activity.timesheet.dto"; //$NON-NLS-1$

	/** The foreign key objects which is supported for loading. */
	public static final String FOREIGN_KEY_PERSON = "resource"; //$NON-NLS-1$

	/** {@inheritDoc} */
	public Object getData(IKey key) {
		throw new UnsupportedOperationException();
	}

	/** {@inheritDoc} */
	public String getProviderGroup() {
		return "core.activity.timesheet"; //$NON-NLS-1$
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
					return Activator
							.getDefault()
							.getService(IActivityRemoteService.class)
							.findActivityTimeEntries(foreignKey.getForeignKey());
				}

			}
			throw new IllegalArgumentException();
		}
		catch (RuntimeException exception) {
			logger.error(exception.getMessage(), exception);
			throw exception;
		}
	}

	/** {@inheritDoc} */
	public IKey getKey(Object data) {
		assert data instanceof ActivityTimeEntryDTO : "Object of type <ActivityTimeEntryDTO> expected"; //$NON-NLS-1$
		ActivityTimeEntryDTO activityTimeEntryDTO = (ActivityTimeEntryDTO) data;
		return new PrimaryKey(activityTimeEntryDTO.getActivity().getId());
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	public Object putData(IKey key, Object data) {
		try {
			if (key instanceof ForeignKey) {
				ForeignKey foreignKey = (ForeignKey) key;
				List<ActivityTimeEntryDTO> activities = (List<ActivityTimeEntryDTO>) data;
				for (ActivityTimeEntryDTO activityTimeEntryDTO : activities) {
					Activator.getDefault().getService(
							IActivityRemoteService.class).saveTimeEntries(
							activityTimeEntryDTO.getTimeEntries());
				}
				return getDataCollection(foreignKey);
			}
			throw new IllegalArgumentException();
		}
		catch (RuntimeException exception) {
			logger.error(exception.getMessage(), exception);
			throw exception;
		}
	}

	/** {@inheritDoc} */
	public void removeData(IKey key) {
		throw new UnsupportedOperationException();
	}

}
