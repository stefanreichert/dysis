package net.sf.dysis.base.ui.dataprovider.internal.cache;

import java.util.Date;

import net.sf.dysis.base.ui.dataprovider.cache.ICacheEntryInformation;

/**
 * Internal implementation for <code>ICacheEntryInformation</code>.
 * 
 * @author Stefan Reichert
 */
public class CacheEntryInformation implements ICacheEntryInformation {

	/** The creation <code>Date</code>. */
	private final Date creation;

	/** The last access <code>Date</code>. */
	private Date lastAccess;

	/**
	 * Constructor for <class>CacheEntryInformation</class>.
	 */
	public CacheEntryInformation() {
		super();
		creation = new Date();
		lastAccess = creation;
	}

	/**
	 * @see net.sf.dysis.base.ui.dataprovider.cache.ICacheEntryInformation#getCreation()
	 */
	public Date getCreation() {
		return creation;
	}

	/**
	 * @see net.sf.dysis.base.ui.dataprovider.cache.ICacheEntryInformation#getLastAccess()
	 */
	public Date getLastAccess() {
		return lastAccess;
	}

	/**
	 * Proceeds an update on the last access <code>Date</code>.
	 */
	public void registerAccess() {
		lastAccess = new Date();
	}
}