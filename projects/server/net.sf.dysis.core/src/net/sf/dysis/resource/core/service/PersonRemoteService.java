/**
 * PersonRemoteService.java
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

import net.sf.dysis.resource.core.dto.PersonDTO;
import net.sf.dysis.util.core.service.ITransformationService;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link PersonRemoteService} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public abstract class PersonRemoteService implements IPersonRemoteService {

    /** The private field for {@link net.sf.dysis.resource.core.service.IPersonService}. */
    private IPersonService personService;

    /** The private field for {@link net.sf.dysis.util.core.service.ITransformationService}. */
    private ITransformationService transformationService;

    /**
     * @return the {@link net.sf.dysis.resource.core.service.IPersonService} to get
     */
    public IPersonService getPersonService() {
        return personService;
    }

    /**
     *  @param personService The {@link net.sf.dysis.resource.core.service.IPersonService} to set
     */
    public void setPersonService(IPersonService personService) {
        this.personService = personService;
    }

    /**
     * @return the {@link net.sf.dysis.util.core.service.ITransformationService} to get
     */
    public ITransformationService getTransformationService() {
        return transformationService;
    }

    /**
     *  @param transformationService The {@link net.sf.dysis.util.core.service.ITransformationService} to set
     */
    public void setTransformationService(
        ITransformationService transformationService) {
        this.transformationService = transformationService;
    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract List<PersonDTO> internalLoadAll();

    /** {@inheritDoc} */
    public final List<PersonDTO> loadAll() {
        return internalLoadAll();

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract PersonDTO internalLoad(Long id);

    /** {@inheritDoc} */
    public final PersonDTO load(Long id) {
        return internalLoad(id);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract PersonDTO internalSave(PersonDTO person);

    /** {@inheritDoc} */
    public final PersonDTO save(PersonDTO person) {
        return internalSave(person);

    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract PersonDTO internalFindByUserId(String userid);

    /** {@inheritDoc} */
    public final PersonDTO findByUserId(String userid) {
        return internalFindByUserId(userid);

    }
}
