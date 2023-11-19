/**
 * IPersonRemoteService.java
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

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link IPersonRemoteService} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public interface IPersonRemoteService {
    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    List<PersonDTO> loadAll();

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    PersonDTO load(Long id);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    PersonDTO save(PersonDTO person);

    /**
     * <i>generated method</i><br>
     * No comment available.
     */
    PersonDTO findByUserId(String userid);
}
