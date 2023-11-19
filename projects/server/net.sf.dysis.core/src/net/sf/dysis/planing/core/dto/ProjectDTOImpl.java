/**
 * ProjectDTOImpl.java
 *
 * Copyright (c) Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.planing.core.dto;

import java.util.UUID;


/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link ProjectDTOImpl} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public class ProjectDTOImpl extends ProjectDTO {

    /** The serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The object id. */
	private String oid = UUID.randomUUID().toString();

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object object) {
		if (object instanceof ProjectDTOImpl) {
			ProjectDTOImpl otherProjectDTOImpl = (ProjectDTOImpl) object;
			return otherProjectDTOImpl.getOid().equals(getOid());
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return oid.hashCode();
	}
	
	/** {@inheritDoc} */
	@Override
	public void setId(Long id) {
		super.setId(id);
		if (id != null) {
			oid = id.toString();
		}
	}
	
	/**
	 * @return the oid
	 */
	protected String getOid() {
		return oid;
	}
}
