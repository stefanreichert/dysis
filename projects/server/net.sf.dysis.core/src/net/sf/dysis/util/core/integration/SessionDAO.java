/**
 * SessionDAO.java
 *
 * Copyright (c) 2008-2009 Stefan Reichert
 * All rights reserved.
 *
 * This program and the accompanying materials are proprietary information
 * of Stefan Reichert. Use is subject to license terms.
 */
package net.sf.dysis.util.core.integration;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;

import java.lang.String;

import java.util.HashSet;
import java.util.Set;

import net.sf.dysis.util.core.domain.SessionImpl;

/**
 *
 *
 * <b>ATTENTION</b>
 * The class {@link SessionDAO} is a generated class. Changes will be overwritten by
 * the next build.
 *
 * @author Stefan Reichert
 */
public abstract class SessionDAO extends HibernateDaoSupport
    implements ISessionDAO {

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public Set<SessionImpl> loadAll() {
        return new HashSet<SessionImpl>(getHibernateTemplate()
                                            .loadAll(SessionImpl.class));
    }

    /** {@inheritDoc} */
    public SessionImpl loadSession(Long id) {
        return (SessionImpl) getHibernateTemplate().load(SessionImpl.class, id);
    }

    /** {@inheritDoc} */
    public SessionImpl saveSession(SessionImpl session) {
        if (session.getId() == null) {
            Serializable id = getHibernateTemplate()
                                  .save(session);
            return (SessionImpl) getHibernateTemplate()
                                     .load(SessionImpl.class, id);
        } else {
            return (SessionImpl) getHibernateTemplate().merge(session);
        }
    }

    /** {@inheritDoc} */
    public void deleteSession(SessionImpl session) {
        getHibernateTemplate().delete(session);
    }

    /**
     * <i>internal method delegate</i><br>
     * No comment available.
     */
    protected abstract SessionImpl internalFindBySessionId(String sessionId);

    /** {@inheritDoc} */
    public final SessionImpl findBySessionId(String sessionId) {
        return internalFindBySessionId(sessionId);

    }
}
