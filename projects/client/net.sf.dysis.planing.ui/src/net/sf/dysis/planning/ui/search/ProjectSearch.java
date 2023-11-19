/**
 * ProjectSearch.java created on 20.02.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planning.ui.search;

import java.util.Collection;

import net.sf.dysis.base.ui.search.ISearchExecutable;
import net.sf.dysis.planing.core.dto.ProjectDTO;
import net.sf.dysis.planing.core.dto.ProjectSearchCriteriaDTO;
import net.sf.dysis.planing.core.service.IProjectRemoteService;
import net.sf.dysis.planning.ui.Activator;

/**
 * Executes a search for {@link ProjectDTO}s.
 * 
 * @author Stefan Reichert
 */
public class ProjectSearch implements ISearchExecutable {

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	public Collection perform(Object searchCriteria) {
		return Activator.getDefault().getService(IProjectRemoteService.class)
				.findByCriteria((ProjectSearchCriteriaDTO) searchCriteria);
	}

}
