/**
 * PersonDTOImpl.java
 *
 * Copyright (c) Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.resource.core.dto;

/**
 * <b>ATTENTION</b> The class {@link PersonDTOImpl} is a generated class.
 * Changes will be overwritten by the next build.
 * 
 * @author Stefan Reichert
 */
public class PersonDTOImpl extends PersonDTO {

	/** The serial version UID. */
	private static final long serialVersionUID = 1L;

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object object) {
		if (object instanceof PersonDTOImpl) {
			PersonDTOImpl otherPersonDTOImpl = (PersonDTOImpl) object;
			return otherPersonDTOImpl.getId().equals(getId());
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return getId().intValue();
	}
}