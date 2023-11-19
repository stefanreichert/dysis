/**
 * PersonService.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.resource.core.service;

import java.lang.Long;
import java.lang.String;

import java.util.List;

import net.sf.dysis.resource.core.domain.PersonImpl;
import net.sf.dysis.resource.core.integration.IPersonDAO;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link PersonService} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public abstract class PersonService implements IPersonService {

    /** The private field for {@link net.sf.dysis.resource.core.integration.IPersonDAO}. */
    private IPersonDAO personDAO;

    /**
     * @return the {@link net.sf.dysis.resource.core.integration.IPersonDAO} to get
     */
    public IPersonDAO getPersonDAO() {
        return personDAO;
    }

    /**
     *  @param personDAO The {@link net.sf.dysis.resource.core.integration.IPersonDAO} to set
     */
    public void setPersonDAO(IPersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract List<PersonImpl> internalLoadAll();

    /** {@inheritDoc} */
    public final List<PersonImpl> loadAll() {
        return internalLoadAll();

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract PersonImpl internalLoad(Long id);

    /** {@inheritDoc} */
    public final PersonImpl load(Long id) {
        return internalLoad(id);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract PersonImpl internalSave(PersonImpl person);

    /** {@inheritDoc} */
    public final PersonImpl save(PersonImpl person) {
        return internalSave(person);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract PersonImpl internalFindByUserIdAndPassword(
        String userid, String password);

    /** {@inheritDoc} */
    public final PersonImpl findByUserIdAndPassword(String userid,
        String password) {
        return internalFindByUserIdAndPassword(userid, password);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract PersonImpl internalFindByUserId(String userid);

    /** {@inheritDoc} */
    public final PersonImpl findByUserId(String userid) {
        return internalFindByUserId(userid);

    }
}
