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

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/**
 * An extension Provide to {@link BasicConcurrentSessionControlStrategy}
 * @author RA
 */
public class BasicPrincipal {

	// ~ Instance fields ==================================================

	/** The username. */
	private String username;
	
	/** The ip. */
	private String ip;
	
	// ~ Constructors ==================================================

	/**
	 * Instantiates a new smart principal.
	 * 
	 * @param authentication
	 *            the authentication
	 */
	public BasicPrincipal(Authentication authentication) {
		Assert.notNull(authentication, "authentication cannot be null (violation of interface contract)");

		String username = null;

		if (authentication.getPrincipal() instanceof UserDetails) {
			username = ((UserDetails) authentication.getPrincipal()).getUsername();
		} else {
			username = (String) authentication.getPrincipal();
		}

		String ip = ((BasicWebAuthenticationDetails) authentication.getDetails()).getRemoteAddress();
		this.username = username;
		this.ip = ip;
	}


	// ~ Methods ==================================================

	/**
	 * Equals ip.
	 * 
	 * @param smartPrincipal
	 *            the smart principal
	 * @return true, if successful
	 */
	public boolean equalsIp(BasicPrincipal smartPrincipal) {
		return this.ip.equals(smartPrincipal.getIp());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicPrincipal other = (BasicPrincipal) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "SmartPrincipal:{username=" + username + ",ip=" + ip + "}";
	}


	/**
	 * Gets the username.
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 * 
	 * @param username
	 *            the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the ip.
	 * 
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Sets the ip.
	 * 
	 * @param ip
	 *            the new ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}


}
