/**
 * LoginProposalStore.java created on 27.06.2008
 * 
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.application.internal.login;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;

/**
 * A store for login proposals.
 * 
 * @author Stefan Reichert
 */
public class LoginProposalStore {

	public static final String KEY_LOGIN_PROPOSALS = "net.sf.dysis.application.LoginProposals"; //$NON-NLS-1$

	public static void addProposal(String login) {
		List<String> loginProposals = loadProposals();
		if (!loginProposals.contains(login)) {
			loginProposals.add(login);
			storeProposals(loginProposals);
		}

	}

	public static List<String> loadProposals() {
		List<String> loginProposals = new ArrayList<String>();
		// load usernames from secure preference
		ISecurePreferences securePreferences = SecurePreferencesFactory
				.getDefault();
		String loginProposalsStreamString;
		try {
			loginProposalsStreamString = securePreferences.get(KEY_LOGIN_PROPOSALS, ""); //$NON-NLS-1$
			if (loginProposalsStreamString != null
					&& loginProposalsStreamString.length() > 0) {
				try {
					ObjectInputStream loginProposalsStream = new ObjectInputStream(
							new ByteArrayInputStream(loginProposalsStreamString
									.getBytes()));
					String[] loginProposalArray = (String[]) loginProposalsStream
							.readObject();
					loginProposals.addAll(Arrays.asList(loginProposalArray));
				}
				catch (Throwable throwable) {
					// failed to restore login proposals
					throwable.printStackTrace();
				}
			}
		}
		catch (StorageException exception) {
			// if loading failed return empty list
		}
		return loginProposals;
	}

	private static void storeProposals(List<String> loginProposals) {
		try {
			List<String> loginProposalsToStore = loginProposals;
			if (loginProposalsToStore.size() > 20) {
				loginProposalsToStore = loginProposalsToStore.subList(
						loginProposalsToStore.size() - 21,
						loginProposalsToStore.size() - 1);
			}
			ByteArrayOutputStream loginProposalsStream = new ByteArrayOutputStream();
			new ObjectOutputStream(loginProposalsStream)
					.writeObject(loginProposalsToStore
							.toArray(new String[loginProposals.size()]));
			// store usernames as secure preference
			ISecurePreferences securePreferences = SecurePreferencesFactory
					.getDefault();
			securePreferences.put(KEY_LOGIN_PROPOSALS, loginProposalsStream
					.toString(), true);
		}
		catch (Throwable throwable) {
			// failed to restore login proposals
			throwable.printStackTrace();
		}
	}
}
