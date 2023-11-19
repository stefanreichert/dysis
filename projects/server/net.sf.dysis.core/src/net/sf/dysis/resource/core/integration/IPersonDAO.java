/**
 * IPersonDAO.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.resource.core.integration;

import java.lang.String;

import java.util.List;
import java.util.Set;

import net.sf.dysis.planing.core.domain.ProjectImpl;
import net.sf.dysis.resource.core.domain.PersonImpl;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link IPersonDAO} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public interface IPersonDAO {
    /**
     * Loads all existing {@link net.sf.dysis.resource.core.domain.PersonImpl} entities from the database.
     */
    Set<PersonImpl> loadAll();

    /**
     * Loads the {@link net.sf.dysis.resource.core.domain.PersonImpl} with the given key from the database.
     */
    PersonImpl loadPerson(Long id);

    /**
     * Creates or updates the given {@link net.sf.dysis.resource.core.domain.PersonImpl}.
     */
    PersonImpl savePerson(PersonImpl person);

    /**
     * Deletes the given {@link net.sf.dysis.resource.core.domain.PersonImpl}.
     */
    void deletePerson(PersonImpl person);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    List<PersonImpl> findByProject(ProjectImpl theProject);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    PersonImpl findByUserIdAndPassword(String userid, String password);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    PersonImpl findByUserId(String userid);
}
