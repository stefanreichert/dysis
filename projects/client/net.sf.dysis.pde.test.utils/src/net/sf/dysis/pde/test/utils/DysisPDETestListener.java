/**
 * DysisPDETestListener.java created on 07.02.2009
 * 
 * Copyright (c) 2009 Stefan Reichert
 * All rights reserved. 
 * 
 * This program and the accompanying materials are proprietary information 
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.pde.test.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import junit.framework.TestResult;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.apache.tools.ant.taskdefs.optional.junit.XMLJUnitResultFormatter;
import org.eclipse.jdt.internal.junit.model.ITestRunListener2;

/**
 * Represents a listener for automated PDE JUnit Plugin-Tests.<br>
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
public class DysisPDETestListener implements ITestRunListener2 {

	/** The controlling {@link DysisPDETestManager}. */
	private DysisPDETestManager testManager;

	/** statistics. */
	private int totalNumberOfTests;

	/** statistics. */
	private int testsRunCount;

	/** statistics. */
	private int testsPassedCount;

	/** statistics. */
	private int testsFailedCount;

	/** statistics. */
	private int testsWithErrorCount;

	/** statistics. */
	private boolean testRunEnded = false;

	/** The {@link XMLJUnitResultFormatter} to use for the output file. */
	private XMLJUnitResultFormatter xmlResultsFormatter;

	/** The outputFile for the results. */
	private File outputFile;

	/** The unit test to listen for. */
	private JUnitTest junitTest;

	/** The currently executed test. */
	private TestCase currentTest;

	/**
	 * Constructor for <class>DysisPDETestListener</class>.
	 * 
	 * @param manager
	 *            The corresponding {@link DysisPDETestManager} which installed
	 *            this listner
	 * @param testName
	 *            The name of the executed test
	 * @param outputFileName
	 *            The name of the result output {@link File}
	 */
	public DysisPDETestListener(DysisPDETestManager manager, String testName,
			String outputFileName) {
		testManager = manager;
		outputFile = new File(outputFileName);
		junitTest = new JUnitTest(testName);
		junitTest.setProperties(System.getProperties());
		xmlResultsFormatter = new XMLJUnitResultFormatter();
		try {
			xmlResultsFormatter.setOutput(new FileOutputStream(outputFile));
		}
		catch (FileNotFoundException exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * @return whether the whole test failed
	 */
	public boolean pdeTestFailed() {
		return ((testsFailedCount + testsWithErrorCount) > 0)
				|| (testRunEnded && (testsRunCount == 0));
	}

	/** {@inheritDoc} */
	public synchronized void testRunStarted(int testCount) {
		totalNumberOfTests = testCount;
		testsRunCount = 0;
		testsPassedCount = 0;
		testsFailedCount = 0;
		testsWithErrorCount = 0;
		testRunEnded = false;
		xmlResultsFormatter.startTestSuite(junitTest);
		System.out.println("PDE Test Run Started - running "
				+ totalNumberOfTests + " tests ...");
	}

	/** {@inheritDoc} */
	public synchronized void testRunEnded(long elapsedTime) {
		testRunEnded = true;
		junitTest.setCounts(testsRunCount, testsFailedCount,
				testsWithErrorCount);
		junitTest.setRunTime(elapsedTime);
		xmlResultsFormatter.endTestSuite(junitTest);
		System.out.println("Test Run Ended   - "
				+ (pdeTestFailed() ? "FAILED" : "PASSED") + " - Total: "
				+ totalNumberOfTests + " (Errors: " + testsWithErrorCount
				+ ", Failed: " + testsFailedCount + ", Passed: "
				+ testsPassedCount + "), duration " + elapsedTime + "ms.");

		synchronized (testManager) {
			testManager.notifyAll();
		}
	}

	/** {@inheritDoc} */
	public synchronized void testRunStopped(long elapsedTime) {
		System.out.println("Test Run Stopped");
		testRunEnded(elapsedTime);
	}

	/** {@inheritDoc} */
	public synchronized void testRunTerminated() {
		System.out.println("Test Run Terminated");
		testRunEnded(0);
	}

	/** {@inheritDoc} */
	public synchronized void testStarted(String testId, String testName) {
		testsRunCount++;
		currentTest = new ManagedTestCase(testName);
		xmlResultsFormatter.startTest(currentTest);
		System.out.println("  Test Started - " + testsRunCount + " - "
				+ testName);
	}

	/** {@inheritDoc} */
	public synchronized void testEnded(String testId, String testName) {
		testsPassedCount = testsRunCount
				- (testsFailedCount + testsWithErrorCount);
		xmlResultsFormatter.endTest(currentTest);
		System.out.println("  Test Ended   - " + testsRunCount + " - "
				+ testName);
	}

	/** {@inheritDoc} */
	public synchronized void testFailed(int status, String testId,
			String testName, String trace, String expected, String actual) {
		String statusMessage = String.valueOf(status);
		if (status == ITestRunListener2.STATUS_OK) {
			testsPassedCount++;
			statusMessage = "OK";
		}
		else if (status == ITestRunListener2.STATUS_FAILURE) {
			testsFailedCount++;
			statusMessage = "FAILED";
			xmlResultsFormatter.addFailure(currentTest,
					new AssertionFailedError(trace));
		}
		else if (status == ITestRunListener2.STATUS_ERROR) {
			testsWithErrorCount++;
			statusMessage = "ERROR";
			xmlResultsFormatter.addError(currentTest, new Exception(trace));
		}
		System.out.println("  Test Failed  - " + testsRunCount + " - "
				+ testName + " - status: " + statusMessage + ", trace: "
				+ trace + ", expected: " + expected + ", actual: " + actual);
	}

	/** {@inheritDoc} */
	public synchronized void testReran(String testId, String testClass,
			String testName, int status, String trace, String expected,
			String actual) {
		String statusMessage = String.valueOf(status);
		if (status == ITestRunListener2.STATUS_OK) {
			statusMessage = "OK";
		}
		else if (status == ITestRunListener2.STATUS_FAILURE) {
			statusMessage = "FAILED";
		}
		else if (status == ITestRunListener2.STATUS_ERROR) {
			statusMessage = "ERROR";
		}

		System.out.println("  Test ReRan   - " + testName + " - test class: "
				+ testClass + ", status: " + statusMessage + ", trace: "
				+ trace + ", expected: " + expected + ", actual: " + actual);
	}

	/** {@inheritDoc} */
	public synchronized void testTreeEntry(String description) {
		System.out.println("Test Tree Entry - Description: " + description);
	}

	/**
	 * A wrapper {@link TestCase} for the executed test cases.
	 * 
	 * @author Stefan Reichert
	 */
	class ManagedTestCase extends TestCase {

		/**
		 * Constructor for <class>WrapperTestCase</class>.
		 */
		public ManagedTestCase(String name) {
			super(name);
		}

		/** {@inheritDoc} */
		public int countTestCases() {
			return 1;
		}

		/** {@inheritDoc} */
		public void run(TestResult result) {
			// intentionally do nothing
		}
	}
}
