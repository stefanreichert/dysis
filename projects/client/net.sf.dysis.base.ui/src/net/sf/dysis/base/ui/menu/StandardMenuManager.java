/**
 * MenuHelper.java created on 22.02.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.base.ui.menu;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;

/**
 * This class represents a standard <code>MenuManager</code> with a default set
 * of groups.
 * 
 * @author Stefan Reichert
 */
public class StandardMenuManager extends MenuManager {

	/** Path entry for "create" actions. */
	public static final String GROUP_CREATE = "create"; //$NON-NLS-1$

	/** Path entry for "edit" actions. */
	public static final String GROUP_EDIT = "edit"; //$NON-NLS-1$

	/** Path entry for "copy" actions. */
	public static final String GROUP_COPY = "copy"; //$NON-NLS-1$

	/** Path entry for "delete" actions. */
	public static final String GROUP_DELETE = "delete"; //$NON-NLS-1$

	/** Path entry for "help" actions. */
	public static final String GROUP_HELP = "help"; //$NON-NLS-1$

	/** Path entry for "state" actions (usually checker menu items). */
	public static final String GROUP_STATE = "state"; //$NON-NLS-1$

	/** Path entry for "special" actions. */
	public static final String GROUP_SPECIAL = "special"; //$NON-NLS-1$

	/**
	 * Path entry for "additions" (usually those that do not fit under any other
	 * constant).
	 */
	public static final String GROUP_ADDITIONS = "additions"; //$NON-NLS-1$

	/** Path entry for "remove" action (usually "remove row" in tables). */
	public static final String GROUP_REMOVE = "remove"; //$NON-NLS-1$

	/** Path entry for "insert" action (usually "insert row" in tables). */
	public static final String GROUP_INSERT = "insert"; //$NON-NLS-1$

	public static void applyStandard(IContributionManager contributionManager) {
		contributionManager.add(new GroupMarker(GROUP_INSERT));
		contributionManager.add(new GroupMarker(GROUP_REMOVE));
		contributionManager.add(new Separator());
		contributionManager.add(new GroupMarker(GROUP_CREATE));
		contributionManager.add(new Separator());
		contributionManager.add(new GroupMarker(GROUP_EDIT));
		contributionManager.add(new GroupMarker(GROUP_COPY));
		contributionManager.add(new GroupMarker(GROUP_DELETE));
		contributionManager.add(new Separator());
		contributionManager.add(new GroupMarker(GROUP_HELP));
		contributionManager.add(new Separator());
		contributionManager.add(new GroupMarker(GROUP_STATE));
		contributionManager.add(new Separator());
		contributionManager.add(new GroupMarker(GROUP_SPECIAL));
		contributionManager.add(new Separator());
		contributionManager.add(new GroupMarker(GROUP_ADDITIONS));
	}

	/**
	 * Constructor for <class>StandardMenuManager</class>. This
	 * <code>MenuManager</code> contains the following <code>GroupMarkers</code>
	 * : <li>GROUP_INSERT</li> <li>GROUP_REMOVE</li> <li>GROUP_CREATE</li> <li>
	 * GROUP_EDIT</li> <li>GROUP_COPY</li> <li>GROUP_DELETE</li> <li>GROUP_STATE
	 * </li> <li>GROUP_SPECIAL</li> <li>GROUP_ADDITIONS</li>
	 * 
	 * @param menuLabel
	 *            The label of this menu
	 * @param menuID
	 *            The id of this menu
	 * @return The standard <code>MenuManager</code>
	 */
	public StandardMenuManager(String menuLabel, String menuID) {
		super(menuLabel, menuID);
		applyStandard(this);
	}

	/**
	 * Constructor for <class>StandardMenuManager</class>. This
	 * <code>MenuManager</code> contains the following <code>GroupMarkers</code>
	 * : <li>GROUP_INSERT</li> <li>GROUP_REMOVE</li> <li>GROUP_CREATE</li> <li>
	 * GROUP_EDIT</li> <li>GROUP_COPY</li> <li>GROUP_DELETE</li> <li>GROUP_STATE
	 * </li> <li>GROUP_SPECIAL</li> <li>GROUP_ADDITIONS</li>
	 * 
	 * @return The standard <code>MenuManager</code>
	 */
	public StandardMenuManager() {
		this(null, null);
	}
}
