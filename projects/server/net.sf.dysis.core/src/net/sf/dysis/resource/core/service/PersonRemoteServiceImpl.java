/**
 * PersonRemoteServiceImpl.java
 *
 * Copyright (c) Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.resource.core.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.dysis.resource.core.domain.PersonImpl;
import net.sf.dysis.resource.core.dto.PersonDTO;

/**
 * <b>ATTENTION</b> The class {@link PersonRemoteServiceImpl} is a generated
 * class. Changes to this file will <b>NOT</b> be overwritten by the next build.
 * Your changes are safe.
 * 
 * @author Stefan Reichert
 */
public class PersonRemoteServiceImpl extends PersonRemoteService {

	/** {@inheritDoc} */
	@Override
	protected List<PersonDTO> internalLoadAll() {
		List<PersonDTO> people = new ArrayList<PersonDTO>();
		for (PersonImpl person : getPersonService().loadAll()) {
			people.add(getTransformationService().transform(person));
		}
		return people;
	}

	/** {@inheritDoc} */
	@Override
	protected PersonDTO internalLoad(Long id) {
		return getTransformationService()
				.transform(getPersonService().load(id));
	}

	/** {@inheritDoc} */
	@Override
	protected PersonDTO internalSave(PersonDTO person) {
		return getTransformationService().transform(
				getPersonService().save(
						getTransformationService().transform(person)));
	}

	/** {@inheritDoc} */
	@Override
	protected PersonDTO internalFindByUserId(String userid) {
		return getTransformationService().transform(
				getPersonService().findByUserId(userid));
	}
}
