/**
 * ProjectRemoteServiceImpl.java
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
import net.sf.dysis.planing.core.dto.ProjectDTO;
import net.sf.dysis.planing.core.dto.ProjectSearchCriteriaDTO;

/**
 * <b>ATTENTION</b> The class {@link ProjectRemoteServiceImpl} is a generated
 * class. Changes to this file will <b>NOT</b> be overwritten by the next build.
 * Your changes are safe.
 * 
 * @author Stefan Reichert
 */
public class ProjectRemoteServiceImpl extends ProjectRemoteService {

	/** {@inheritDoc} */
	@Override
	protected List<ProjectDTO> internalLoadAll() {
		List<ProjectDTO> projects = new ArrayList<ProjectDTO>();
		for (ProjectImpl project : getProjectService().loadAll()) {
			projects.add(getTransformationService().transform(project, true));
		}
		return projects;
	}

	/** {@inheritDoc} */
	@Override
	protected ProjectDTO internalLoad(Long id) {
		return getTransformationService().transform(
				getProjectService().load(id), true);
	}

	/** {@inheritDoc} */
	@Override
	protected ProjectDTO internalSave(ProjectDTO theProject) {
		return getTransformationService()
				.transform(
						getProjectService().save(
								getTransformationService().transform(
										theProject, true)), true);
	}

	/** {@inheritDoc} */
	@Override
	protected List<ProjectDTO> internalFindByCriteria(
			ProjectSearchCriteriaDTO searchCriteria) {
		List<ProjectDTO> projects = new ArrayList<ProjectDTO>();
		for (ProjectImpl project : getProjectService().findByCriteria(
				searchCriteria)) {
			projects.add(getTransformationService().transform(project, true));
		}
		return projects;
	}
}
