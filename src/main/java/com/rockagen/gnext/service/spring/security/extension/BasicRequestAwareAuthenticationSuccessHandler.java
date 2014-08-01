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

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.util.Assert;

import com.rockagen.gnext.po.AuthUser;
import com.rockagen.gnext.service.AuthUserServ;

/**
 * Handle authentication if success
 * @author RA
 */
public class BasicRequestAwareAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	//~ Instance fields ==================================================
	
	/**
	 * Service
	 */
	private AuthUserServ authUserServ;

	//~ Methods ==================================================

	/**
	 * Override {@link #onAuthenticationSuccess} implements unlocked user
	 * @see org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		super.onAuthenticationSuccess(request, response, authentication);
		handlerLocked(authentication);
	}
	
	
	/**
	 * Handler locked.
	 * 
	 * @param userId
	 *            the user id
	 */
	protected void handlerLocked(Authentication authentication) {
		
		String latestIp=((BasicWebAuthenticationDetails) authentication.getDetails()).getRemoteAddress();
		String username = ((UserDetails) authentication.getPrincipal()).getUsername();
		AuthUser user = authUserServ.load(username);
		// error count clean
		user.setErrorCount(0);
		user.setStateTime(new Date());
		user.setLatestIp(latestIp);
		authUserServ.add(user);
	}

	//~ Services
	
	public void setAuthUserServ(AuthUserServ authUserServ) {
		Assert.notNull(authUserServ, "authUserServ must not be empty or null");
		this.authUserServ = authUserServ;
	}
	

}
