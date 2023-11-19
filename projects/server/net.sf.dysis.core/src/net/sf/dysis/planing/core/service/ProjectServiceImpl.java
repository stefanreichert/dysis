/**
 * ProjectServiceImpl.java
 *
 * Copyright (c) Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planing.core.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.dysis.planing.core.domain.ProjectImpl;
import net.sf.dysis.planing.core.dto.ProjectSearchCriteriaDTO;

/**
 * <b>ATTENTION</b> The class {@link ProjectServiceImpl} is a generated class.
 * Changes to this file will <b>NOT</b> be overwritten by the next build. Your
 * changes are safe.
 * 
 * @author Stefan Reichert
 */
public class ProjectServiceImpl extends ProjectService {

	/** {@inheritDoc} */
	@Override
	protected List<ProjectImpl> internalLoadAll() {
		return new ArrayList<ProjectImpl>(getProjectDAO().loadAll());
	}

	/** {@inheritDoc} */
	@Override
	protected ProjectImpl internalLoad(Long id) {
		return getProjectDAO().loadProject(id);
	}

	/** {@inheritDoc} */
	@Override
	protected ProjectImpl internalSave(ProjectImpl theProject) {
		return getProjectDAO().saveProject(theProject);
	}

	/** {@inheritDoc} */
	@Override
	protected List<ProjectImpl> internalFindByCriteria(
			ProjectSearchCriteriaDTO searchCriteria) {
		return getProjectDAO().findByCriteria(searchCriteria);
	}
}
