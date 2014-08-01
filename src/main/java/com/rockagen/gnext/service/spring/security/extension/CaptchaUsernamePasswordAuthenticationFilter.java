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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;

/**
 * An extension for spring security {@link UsernamePasswordAuthenticationFilter}
 * 
 * <p>
 * By default,the capcha provide is <a href=
 * "https://jcaptcha.atlassian.net/wiki/display/general/Simple+Servlet+Integration+documentation"
 * >jcapcha</a>.
 * <p>
 * <p>Override {@link #validateCaptcha(HttpServletRequest)} to extend.
 * @author RA
 */
public class CaptchaUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {

	// ~ Instance fields ==================================================

	/**
     * 
     */
	public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "j_captcha";

	private String captchaParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;

	private boolean allowEmptyCaptcha = false;

	// ~ Methods ==================================================

	/**
	 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter#attemptAuthentication(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {

		if (!allowEmptyCaptcha)
			validateCaptcha(request);
		return super.attemptAuthentication(request, response);
	}

	/**
	 * Validate the captcha
	 * 
	 * @param request
	 * @see SimpleImageCaptchaServlet
	 */
	protected void validateCaptcha(HttpServletRequest request) {
		boolean captchaPassed = SimpleImageCaptchaServlet.validateResponse(
				request, captchaParameter);
		if (!captchaPassed) {
			throw new AuthenticationInvalidatedCodeException(
					messages.getMessage("Captcha.notEquals"));
		}
	}

	public void setCaptchaParameter(String captchaParameter) {
		this.captchaParameter = captchaParameter;
	}

	public void setAllowEmptyCaptcha(boolean allowEmptyCaptcha) {
		this.allowEmptyCaptcha = allowEmptyCaptcha;
	}

}