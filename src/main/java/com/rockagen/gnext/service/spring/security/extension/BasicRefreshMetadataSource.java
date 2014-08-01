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

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * An extension Since spring security in the authority information is loaded to
 * <code>SERVLETCONTEXT</code> when the server started, so here define a method to refresh the
 * <code>SERVLETCONTEXT</code>
 * 
 * @author RA
 */
public class BasicRefreshMetadataSource {

	// ~ Instance fields ==================================================

	/** The Constant logger. */
	private static final Log log = LogFactory
			.getLog(BasicRefreshMetadataSource.class);

	/** The servlet context. */
	private ServletContext servletContext;

	// ~ Methods ==================================================

	/**
	 * Refresh.
	 */
	public void refresh() {
		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		FilterInvocationSecurityMetadataSource fisms = (FilterInvocationSecurityMetadataSource) ctx
				.getBean("securityMetadataSource");
		// Get  FilterSecurityInterceptor
		FilterSecurityInterceptor fsi = (FilterSecurityInterceptor) ctx
				.getBean("filterSecurityInterceptor");
		// Set new SecurityMetadataSource to FilterSecurityInterceptor
		fsi.setSecurityMetadataSource(fisms);
		log.info(" SecurityMetadataSource updating ....");
	}

	/**
	 * Sets the servlet context.
	 * 
	 * @param servletContext
	 *            the new servlet context
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
