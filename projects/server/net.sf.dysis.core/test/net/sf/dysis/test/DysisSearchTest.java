/**
 * DysisSearchTest.java created on 20.02.2009
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.test;

import java.util.List;

import junit.framework.TestCase;
import net.sf.dysis.ServiceLocator;
import net.sf.dysis.planing.core.dto.ProjectDTO;
import net.sf.dysis.planing.core.dto.ProjectSearchCriteriaDTOImpl;
import net.sf.dysis.planing.core.service.IProjectRemoteService;

/**
 * TODO Please describe what this class is for
 * 
 * @author Stefan Reichert
 */
public class DysisSearchTest extends TestCase {

	public void testDysisProjectSearch() {
		IProjectRemoteService projectService = ServiceLocator.instance()
				.getProjectRemoteService();
		ProjectSearchCriteriaDTOImpl criteria = new ProjectSearchCriteriaDTOImpl();
		criteria.setName("Dysis");
		List<ProjectDTO> list = projectService.findByCriteria(criteria);
		assertFalse(list.isEmpty());
	}
}
