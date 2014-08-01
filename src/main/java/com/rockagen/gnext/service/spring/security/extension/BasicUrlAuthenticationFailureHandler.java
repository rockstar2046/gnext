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

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.util.Assert;

import com.rockagen.gnext.po.AuthUser;
import com.rockagen.gnext.service.AuthUserServ;

/**
 * Handle authentication if Failure
 * @author RA
 */
public class BasicUrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	//~ Instance fields ==================================================

	private MessageSourceAccessor messages = SpringSecurityMessageSource
			.getAccessor();
	
	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "j_username";
	private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
	
	/**
	 * Service
	 */
	private AuthUserServ authUserServ;
	
	
	//~ Methods ==================================================

	/**
	 * Override {@link #onAuthenticationFailure} implements locked user
	 * 
	 * @see org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse,
	 *      org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		if(exception.getClass().equals(BadCredentialsException.class)){
			exception=handlerLocked(request.getParameter(usernameParameter));
		}
		super.onAuthenticationFailure(request, response, exception);
		

	}
	
	/**
	 * handle locked ?
	 * 
	 * @param userId
	 * @return
	 */
	protected AuthenticationException handlerLocked(String userId) {

		AuthUser user = authUserServ.load(userId);
		if (user.getErrorCount() >= 5) {

			Long dateTime = user.getStateTime().getTime();
			// 1 DAY = 86 400 000 ms
			if(new Date().getTime()-dateTime <86400000){
				// Locked user if input 6 error password  
				user.setEnabled(0);
				authUserServ.add(user);
				return new DisabledException(messages.getMessage(
						"AccountStatusUserDetailsChecker.locked"));
			}
		}else{
			// error count ++
			user.setErrorCount(user.getErrorCount() + 1);
			// state time
			user.setStateTime(new Date());
		}
		int onlyCount=6-user.getErrorCount();
		authUserServ.add(user);
		return new BadCredentialsException(messages.getMessage(
				"AccountStatusUserDetailsChecker.onlyCount", new Object[] { onlyCount }));
	}

	
	
	public void setUsernameParameter(String usernameParameter) {
		this.usernameParameter = usernameParameter;
	}

	//~ Services
	
	public void setAuthUserServ(AuthUserServ authUserServ) {
		Assert.notNull(authUserServ, "authUserServ must not be empty or null");
		this.authUserServ = authUserServ;
	}


}
