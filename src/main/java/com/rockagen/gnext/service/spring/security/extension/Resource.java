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

/**
 * An extension Provide to {@link JdbcFilterInvocationSecurityMetadataSourceFactoryBean}
 * @author RA
 * @see JdbcFilterInvocationSecurityMetadataSourceFactoryBean
 */
public class Resource {

	// ~ Instance fields ==================================================

	/** The url. */
	private String url;
	
	/** The role. */
	private String role;

	//~ Constructors ==================================================
	
	/**
	 * Instantiates a new resource.
	 * 
	 * @param url
	 *            the url
	 * @param role
	 *            the role
	 */
	public Resource(String url, String role) {
		this.url = url;
		this.role = role;
	}
	
	//Getters and Setters ...

	/**
	 * Gets the url.
	 * 
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Gets the role.
	 * 
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
}