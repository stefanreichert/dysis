/**
 * LoginProposalProvider.java created on 31.03.2008
 * 
 * Copyright (c) 2008 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */

package net.sf.dysis.application.internal.login;

import java.util.List;

import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;

/**
 * Provider for logins.
 * 
 * @author Stefan Reichert
 */
public class LoginProposalProvider extends SimpleContentProposalProvider {

	/**
	 * Constructor for <class>LoginProposalProvider</class>.
	 */
	public LoginProposalProvider() {
		super(getLoginProposals());
		setFiltering(true);
	}

	/**
	 * @return
	 */
	private static String[] getLoginProposals() {
		List<String> loginProposals = LoginProposalStore.loadProposals();
		return loginProposals.toArray(new String[loginProposals.size()]);
	}

}
