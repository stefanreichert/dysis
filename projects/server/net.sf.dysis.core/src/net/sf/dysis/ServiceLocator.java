/**
 * ServiceLocator.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.ClassLoader;
import java.lang.String;
import java.lang.Thread;

import net.sf.dysis.planing.core.service.IActivityRemoteService;
import net.sf.dysis.planing.core.service.IActivityService;
import net.sf.dysis.planing.core.service.IProjectRemoteService;
import net.sf.dysis.planing.core.service.IProjectService;
import net.sf.dysis.resource.core.service.IPersonRemoteService;
import net.sf.dysis.resource.core.service.IPersonService;
import net.sf.dysis.util.core.service.ISessionRemoteService;
import net.sf.dysis.util.core.service.ISessionService;
import net.sf.dysis.util.core.service.ITransformationService;

/**
 * Locates and provides all available application services.
 */
public class ServiceLocator {

    /**
     * The default application context location.
     */
    public static final String[] DEFAULT_CONTEXT_LOCATIONS =
        new String[] {
            "applicationContext-gen.xml",
            "applicationContext-integration-gen.xml",
            "applicationContext-service-gen.xml", "applicationContext.xml",
            "applicationContext-datasource.xml"
        };

    /**
     * The shared instance of this ClientServiceLocator.
     */
    private static final ServiceLocator instance = new ServiceLocator();

    /**
     * The spring application context shared instance.
     */
    private ClassPathXmlApplicationContext context = null;

    /**
     * The application context location.
     */
    private String[] contextLocations;

    private ServiceLocator() {

        // shouldn't be instantiated
    }

    /**
     * Gets the shared instance of this Class
     *
     * @return the shared service locator instance.
     */
    public static ServiceLocator instance() {
        return instance;
    }

    /**
     * Initializes the Spring application context from
     * the given <code>applicationContextLocation</code>.  If <code>null</code>
     * is specified for the <code>applicationContextLocation</code>
     * then the default application context will be used.
     *
     * @param applicationContextLocation the location of the context
     */
    public synchronized void init(String[] applicationContextLocations) {
        contextLocations = applicationContextLocations;
        context = null;
    }

    /**
     * Gets the Spring ApplicationContext.
     */
    protected synchronized ApplicationContext getContext() {
        if (context == null) {
            if (contextLocations == null) {
                contextLocations = DEFAULT_CONTEXT_LOCATIONS;
            }
            Thread currentThread = Thread.currentThread();
            ClassLoader originalClassloader =
                currentThread.getContextClassLoader();
            try {
                currentThread.setContextClassLoader(this.getClass()
                                                        .getClassLoader());
                context = new ClassPathXmlApplicationContext(contextLocations);
            } finally {
                currentThread.setContextClassLoader(originalClassloader);
            }
        }
        return context;
    }

    /**
     * @return the {@link net.sf.dysis.util.core.service.ISessionService} to get
     */
    public final ISessionService getSessionService() {
        return (ISessionService) getContext().getBean("sessionService");
    }

    /**
     * @return the {@link net.sf.dysis.util.core.service.ISessionRemoteService} to get
     */
    public final ISessionRemoteService getSessionRemoteService() {
        return (ISessionRemoteService) getContext()
                                           .getBean("sessionRemoteService");
    }

    /**
     * @return the {@link net.sf.dysis.util.core.service.ITransformationService} to get
     */
    public final ITransformationService getTransformationService() {
        return (ITransformationService) getContext()
                                            .getBean("transformationService");
    }

    /**
     * @return the {@link net.sf.dysis.planing.core.service.IProjectService} to get
     */
    public final IProjectService getProjectService() {
        return (IProjectService) getContext().getBean("projectService");
    }

    /**
     * @return the {@link net.sf.dysis.planing.core.service.IProjectRemoteService} to get
     */
    public final IProjectRemoteService getProjectRemoteService() {
        return (IProjectRemoteService) getContext()
                                           .getBean("projectRemoteService");
    }

    /**
     * @return the {@link net.sf.dysis.planing.core.service.IActivityService} to get
     */
    public final IActivityService getActivityService() {
        return (IActivityService) getContext().getBean("activityService");
    }

    /**
     * @return the {@link net.sf.dysis.planing.core.service.IActivityRemoteService} to get
     */
    public final IActivityRemoteService getActivityRemoteService() {
        return (IActivityRemoteService) getContext()
                                            .getBean("activityRemoteService");
    }

    /**
     * @return the {@link net.sf.dysis.resource.core.service.IPersonService} to get
     */
    public final IPersonService getPersonService() {
        return (IPersonService) getContext().getBean("personService");
    }

    /**
     * @return the {@link net.sf.dysis.resource.core.service.IPersonRemoteService} to get
     */
    public final IPersonRemoteService getPersonRemoteService() {
        return (IPersonRemoteService) getContext().getBean("personRemoteService");
    }
}
