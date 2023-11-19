/**
 * ServerContextProvider.java
 *
 * Copyright (c) Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.system.core;

/**
 * @author Stefan Reichert
 */
public class ServerContextProvider implements IContextProvider {

	/** A {@link ThreadLocal} holding the thread specific request context. */
	private static ThreadLocal<Context> THREAD_LOCAL = new ThreadLocal<Context>();

	/** {@inheritDoc} */
	public String getContextUserId() {
		return getServerContext().getUserId();
	}

	/** {@inheritDoc} */
	public String getContextSessionId() {
		return getServerContext().getSessionId();
	}

	/** {@inheritDoc} */
	public String getContextVersion() {
		return getServerContext().getVersion();
	}

	/**
	 * Returns the {@link ThreadBasedRequestContextProvider.RequestContext} of
	 * the current {@link Thread}.
	 */
	private static Context getServerContext() {
		return THREAD_LOCAL.get();
	}

	/**
	 * Returns the {@link ThreadBasedRequestContextProvider.RequestContext} of
	 * the current {@link Thread}.
	 */
	public static void setServerContext(String userId, String sessionId,
			String version) {
		THREAD_LOCAL.set(new Context(userId, sessionId, version));
	}

	/**
	 * The context representation for the executing {@link Thread}.
	 */
	public static class Context {
		private String userId;
		private String sessionId;
		private String version;

		public Context(String userId, String sessionId, String version) {
			super();
			this.sessionId = sessionId;
			this.userId = userId;
			this.version = version;
		}

		public String getUserId() {
			return userId;
		}

		public String getSessionId() {
			return sessionId;
		}

		public String getVersion() {
			return version;
		}

	}

}
