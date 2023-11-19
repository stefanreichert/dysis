/**
 * IPersonService.java
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

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link IPersonService} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public interface IPersonService {
    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    List<PersonImpl> loadAll();

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    PersonImpl load(Long id);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    PersonImpl save(PersonImpl person);

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
