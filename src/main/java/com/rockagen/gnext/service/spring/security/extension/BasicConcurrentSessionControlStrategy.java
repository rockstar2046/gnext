/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rockagen.gnext.service.spring.security.extension;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.util.Assert;

/**
 * An extension for spring security when turned off the browser with do not logout case can not login
 * 
 * @author RA
 */
public class BasicConcurrentSessionControlStrategy extends SessionFixationProtectionStrategy{

	// ~ Instance fields ==================================================

	 protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	 private final SessionRegistry sessionRegistry;
	 private boolean exceptionIfMaximumExceeded = false;
	 private int maximumSessions = 1;

	/** The max inactive interval. 20 minutes*/
	private int maxInactiveInterval = 1200;

	// ~ Constructors ==================================================


	/**
	 * Instantiates a new basic concurrent session control strategy.
	 * 
	 * @param sessionRegistry
	 *            the session registry
	 */
	public BasicConcurrentSessionControlStrategy(SessionRegistry sessionRegistry) {
		 Assert.notNull(sessionRegistry, "The sessionRegistry cannot be null");
	        super.setAlwaysCreateSession(true);
	        this.sessionRegistry = sessionRegistry;
	}

	// ~ Methods ==================================================

	/**
	 * @see org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy#onAuthentication(org.springframework.security.core.Authentication, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void onAuthentication(Authentication authentication, javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response) {
		Assert.notNull(authentication, "Authentication cannot be null (violation of interface contract)");
		checkAuthenticationAllowed(authentication, request);
		// Allow the parent to create a new session if necessary
		super.onAuthentication(authentication, request, response);
		// XXX: authentication.authentication.getPrincipal()
		sessionRegistry.registerNewSession(request.getSession().getId(), new BasicPrincipal(authentication));
		request.getSession().setMaxInactiveInterval(maxInactiveInterval);
		
	}

	/**
	 * Check authentication allowed.
	 * 
	 * @param authentication
	 *            the authentication
	 * @param request
	 *            the request
	 * @throws AuthenticationException
	 *             the authentication exception
	 */
	private void checkAuthenticationAllowed(Authentication authentication, HttpServletRequest request)
			throws AuthenticationException {

        final List<SessionInformation> sessions = sessionRegistry.getAllSessions(authentication.getPrincipal(), false);

        int sessionCount = sessions.size();
        int allowedSessions = getMaximumSessionsForThisUser(authentication);

        if (sessionCount < allowedSessions) {
            // They haven't got too many login sessions running at present
            return;
        }

        if (allowedSessions == -1) {
            // We permit unlimited logins
            return;
        }

        if (sessionCount == allowedSessions) {
            HttpSession session = request.getSession(false);

            if (session != null) {
                // Only permit it though if this request is associated with one of the already registered sessions
                for (SessionInformation si : sessions) {
                    if (si.getSessionId().equals(session.getId())) {
                        return;
                    }
                }
            }
            // If the session is null, a new one will be created by the parent class, exceeding the allowed number
        }

        
        BasicPrincipal basicPrincipal = new BasicPrincipal(authentication);
		//
		// verify the ip value in the basicPrincipal
		//
		boolean sameIp = false;
		List<Object> allValidPrincipals = new ArrayList<Object>();
		for (SessionInformation sessionInformation : sessions) {
			allValidPrincipals.add(sessionInformation.getPrincipal());
		}

		for (Object savedPrincipal : allValidPrincipals) {
			if (basicPrincipal.equals(savedPrincipal)) {
				sameIp = basicPrincipal.equalsIp((BasicPrincipal) savedPrincipal);

				break;
			}
		}
		allowableSessionsExceeded(sessions, allowedSessions, sameIp, sessionRegistry);
	}

	/**
	 * Allowable sessions exceeded.
	 * 
	 * @param sessions
	 *            the sessions
	 * @param allowableSessions
	 *            the allowable sessions
	 * @param sameIp
	 *            the same ip
	 * @param registry
	 *            the registry
	 * @throws SessionAuthenticationException
	 *             the session authentication exception
	 */
	protected void allowableSessionsExceeded(java.util.List<SessionInformation> sessions, int allowableSessions,
			boolean sameIp, SessionRegistry registry) throws SessionAuthenticationException {
		// new IP handle
		if (!sameIp) {
			// deny login if exceptionIfMaximumExceeded
			if (exceptionIfMaximumExceeded || (sessions == null)) {
				throw new SessionAuthenticationException(messages.getMessage(
						"ConcurrentSessionControllerImpl.exceededAllowed",
						new Object[] { Integer.valueOf(allowableSessions) },
						"Maximum sessions of {0} for this principal exceeded"));
			}
		}
		// Determine least recently used session, and mark it for invalidation
		SessionInformation leastRecentlyUsed = null;

		for (int i = 0; i < sessions.size(); i++) {
			if ((leastRecentlyUsed == null)
					|| sessions.get(i).getLastRequest().before(leastRecentlyUsed.getLastRequest())) {
				leastRecentlyUsed = sessions.get(i);
			}
		}

		if (sessions.size() > allowableSessions && !sameIp) {

			BasicPrincipal basicPrincipal = (BasicPrincipal) leastRecentlyUsed.getPrincipal();

			for (int i = 0; i < sessions.size(); i++) {
				if (sessions.get(i).getPrincipal().equals(leastRecentlyUsed.getPrincipal())) {
					if (basicPrincipal.equalsIp((BasicPrincipal) (sessions.get(i).getPrincipal()))) {
						sessions.get(i).expireNow();
					}
				}
			}
			leastRecentlyUsed.expireNow();
		} else if (!sameIp) {
			leastRecentlyUsed.expireNow();
		} else {
			// TODO
		}

	}


	/**
	 * 
	 * @see org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy#onSessionChange(java.lang.String, javax.servlet.http.HttpSession, org.springframework.security.core.Authentication)
	 */
	@Override
	protected void onSessionChange(String originalSessionId, HttpSession newSession, Authentication auth) {
		// Update the session registry
		BasicPrincipal basicPrincipal = new BasicPrincipal(auth);
		sessionRegistry.removeSessionInformation(originalSessionId);
		sessionRegistry.registerNewSession(newSession.getId(), basicPrincipal);
	}
	
	
    /**
     * Method intended for use by subclasses to override the maximum number of sessions that are permitted for
     * a particular authentication. The default implementation simply returns the <code>maximumSessions</code> value
     * for the bean.
     *
     * @param authentication to determine the maximum sessions for
     *
     * @return either -1 meaning unlimited, or a positive integer to limit (never zero)
     */
    protected int getMaximumSessionsForThisUser(Authentication authentication) {
        return maximumSessions;
    }

	/**
	 * Sets the exception if maximum exceeded.
	 * 
	 * @param exceptionIfMaximumExceeded
	 *            the new exception if maximum exceeded
	 */
	public void setExceptionIfMaximumExceeded(boolean exceptionIfMaximumExceeded) {
		this.exceptionIfMaximumExceeded = exceptionIfMaximumExceeded;
	}

	/**
	 * Sets the maximum sessions.
	 * 
	 * @param maximumSessions
	 *            the new maximum sessions
	 */
	public void setMaximumSessions(int maximumSessions) {
		Assert.isTrue(maximumSessions != 0,
				"MaximumLogins must be either -1 to allow unlimited logins, or a positive integer to specify a maximum");
		this.maximumSessions = maximumSessions;
	}

	/**
	 * @see org.springframework.context.MessageSourceAware#setMessageSource(org.springframework.context.MessageSource)
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

	/** 
	 * @see org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy#setAlwaysCreateSession(boolean)
	 */
	@Override
	public final void setAlwaysCreateSession(boolean alwaysCreateSession) {
		if (!alwaysCreateSession) {
			throw new IllegalArgumentException("Cannot set alwaysCreateSession to false when concurrent session "
					+ "control is required");
		}
	}

	/**
	 * Sets the max inactive interval.
	 * 
	 * @param maxInactiveInterval
	 *            the new max inactive interval
	 */
	public void setMaxInactiveInterval(int maxInactiveInterval) {
		if (maxInactiveInterval > 0) {
			this.maxInactiveInterval = maxInactiveInterval * 60;
		}
	}

}
