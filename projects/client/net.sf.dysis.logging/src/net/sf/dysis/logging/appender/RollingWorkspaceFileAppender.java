package net.sf.dysis.logging.appender;

import java.io.IOException;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.spi.OptionHandler;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

/**
 * Wrapper for the {@link RollingFileAppender} to store the log file in the
 * workspace directory.
 * 
 * @author Stefan Reichert
 */
public class RollingWorkspaceFileAppender extends
		org.apache.log4j.RollingFileAppender implements Appender, OptionHandler {

	/**
	 * @see org.apache.log4j.RollingFileAppender#constructor()
	 */
	public RollingWorkspaceFileAppender() {
		super();
	}

	/**
	 * @see org.apache.log4j.RollingFileAppender#constructor(Layout layout,
	 *      java.lang.String filename)
	 */
	public RollingWorkspaceFileAppender(Layout layout, String filename)
			throws IOException {
		super(layout, filename);
	}

	/**
	 * @see org.apache.log4j.RollingFileAppender#constructor(Layout layout,
	 *      java.lang.String filename, boolean append)
	 */
	public RollingWorkspaceFileAppender(Layout layout, String filename,
			boolean append) throws IOException {
		super(layout, filename, append);
	}

	/**
	 * Returns the path of the log file.
	 * 
	 * @param logfileName
	 *            The name of the logfile
	 * @return the path of the logfile in the workspace
	 */
	private String getLogfilePath(String logfileName) {
		IPath logfilePath = Platform.getLocation();
		if (logfilePath != null
				&& !logfileName.startsWith(logfilePath.toOSString())) {
			return logfilePath.append(".metadata").append(logfileName)
					.toOSString();
		}
		return Path.fromOSString(logfileName).toOSString();
	}

	/**
	 * @see org.apache.log4j.FileAppender#setFile(java.lang.String)
	 */
	@Override
	public void setFile(String file) {
		super.setFile(getLogfilePath(file));
	}

	/**
	 * @see org.apache.log4j.RollingFileAppender#setFile(java.lang.String,
	 *      boolean, boolean, int)
	 */
	@Override
	public synchronized void setFile(String fileName, boolean append,
			boolean bufferedIO, int bufferSize) throws IOException {
		super.setFile(getLogfilePath(fileName), append, bufferedIO, bufferSize);
	}

}
