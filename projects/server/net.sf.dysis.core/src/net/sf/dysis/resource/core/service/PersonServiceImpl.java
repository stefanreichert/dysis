/**
 * PersonServiceImpl.java
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

/**
 * <b>ATTENTION</b> The class {@link PersonServiceImpl} is a generated class.
 * Changes to this file will <b>NOT</b> be overwritten by the next build. Your
 * changes are safe.
 * 
 * @author Stefan Reichert
 */
public class PersonServiceImpl extends PersonService {

	/** {@inheritDoc} */
	@Override
	protected List<PersonImpl> internalLoadAll() {
		return new ArrayList<PersonImpl>(getPersonDAO().loadAll());
	}

	/** {@inheritDoc} */
	@Override
	protected PersonImpl internalLoad(Long id) {
		return getPersonDAO().loadPerson(id);
	}

	/** {@inheritDoc} */
	@Override
	protected PersonImpl internalSave(PersonImpl person) {
		return getPersonDAO().savePerson(person);
	}

	/** {@inheritDoc} */
	@Override
	protected PersonImpl internalFindByUserIdAndPassword(String userid,
			String password) {
		return getPersonDAO().findByUserIdAndPassword(userid, password);
	}

	/** {@inheritDoc} */
	@Override
	protected PersonImpl internalFindByUserId(String userid) {
		return getPersonDAO().findByUserId(userid);
	}
}
