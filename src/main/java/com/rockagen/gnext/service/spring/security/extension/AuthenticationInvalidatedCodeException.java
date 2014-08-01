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

import org.springframework.security.authentication.AuthenticationServiceException;

/** 
 * An extension for spring security Thrown if  captcha is validated .
 * 
 * @author RA
 * @see AuthenticationServiceException
 */
public class AuthenticationInvalidatedCodeException extends AuthenticationServiceException{
	
	//~ Instance fields ==================================================
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6983046032934651745L;

	/**
	 * Instantiates a new authentication validate code exception.
	 * 
	 * @param msg
	 *            the msg
	 */
	public AuthenticationInvalidatedCodeException(String msg) {
		super(msg);
	}

	/**
	 * Instantiates a new authentication validate code exception.
	 * 
	 * @param msg
	 *            the msg
	 * @param t
	 *            the t
	 */
	  public AuthenticationInvalidatedCodeException(String msg, Throwable t) {
	        super(msg, t);
	    }
}
