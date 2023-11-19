/**
 * DysisPDETestManager.java created on 07.02.2009
 * 
 * Copyright (c) 2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.pde.test.utils;

import org.eclipse.jdt.internal.junit.model.ITestRunListener2;
import org.eclipse.jdt.internal.junit.model.RemoteTestRunnerClient;

/**
 * A manager for automated PDE JUnit Plugin-Tests. It installs a
 * {@link DysisPDETestListener} at the given port that listens for test results.<br>
 * <br>
 * <b>Note:</b><br>
 * This class is based on an article from eclipse.org by Brian Joyce. For
 * further information please look at
 * <i>http://www.eclipse.org/articles/article.php?file=Article-
 * PDEJUnitAntAutomation/index.html</i>
 * 
 * @author Stefan Reichert
 */
@SuppressWarnings("restriction")
public final class DysisPDETestManager {
	private static DysisPDETestListener pdeTestListener;

	/** The name of the suite to manage. */
	private String testName;

	/** The output file name. */
	private String outputFileName;

	/**
	 * Constructor for <class>DysisPDETestManager</class>.
	 */
	private DysisPDETestManager(String test, String outputFile) {
		testName = test;
		outputFileName = outputFile;
	}

	/**
	 * Starts listening for test results at the given port .
	 * 
	 * @param port
	 *            The port to use for listening
	 * @throws InterruptedException
	 */
	private void startListening(int port) throws InterruptedException {
		pdeTestListener = new DysisPDETestListener(this, testName,
				outputFileName);
		new RemoteTestRunnerClient().startListening(
				new ITestRunListener2[] { pdeTestListener }, port);
		System.out.println("Listening on port " + port + " for test "
				+ testName + " results - output file is " + outputFileName
				+ "...");
		synchronized (this) {
			wait();
		}
	}

	/** Main method for execution. */
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out
					.println("usage: DysisPDETestManager <test name> <port number> <output file name>");
			System.exit(0);
		}

		try {
			new DysisPDETestManager(args[0], args[2]).startListening(Integer
					.parseInt(args[1]));
		}
		catch (Throwable throwable) {
			throwable.printStackTrace();
		}

		if (pdeTestListener != null && pdeTestListener.pdeTestFailed()) {
			System.exit(1);
		}
	}
}
