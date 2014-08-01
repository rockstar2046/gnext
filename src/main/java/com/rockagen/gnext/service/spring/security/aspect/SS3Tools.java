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
package com.rockagen.gnext.service.spring.security.aspect;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.rockagen.gnext.service.spring.security.extension.BasicSecurityUser;
import com.rockagen.gnext.service.spring.security.extension.BasicWebAuthenticationDetails;

/**
 * Spring security 3.x tools
 * @author RA
 */
public class SS3Tools {
	
	//~ Methods ==================================================
	
	/**
	 * Obtain current user principal
	 * @return BasicSecurityUser
	 */
	public static BasicSecurityUser getUserInfo(){
		BasicSecurityUser userDetails=null;
		try{
		SecurityContext sc=SecurityContextHolder.getContext();
		Authentication au=sc.getAuthentication();
		userDetails = (BasicSecurityUser) au.getPrincipal();
		}catch(NullPointerException e){
			userDetails=new BasicSecurityUser("guest", "guest", true, true, true, true, AuthorityUtils.NO_AUTHORITIES, "root", "","127.0.0.1");
			
		}
		return userDetails;
		
	}
	
	//~ Methods ==================================================
	
	/**
	 * Get remote address 
	 * @return ip
	 */
	public static String getRemoteAddress(){
		String temp="127.0.0.1";
		try{
		 temp=((BasicWebAuthenticationDetails) SecurityContextHolder
				.getContext().getAuthentication().getDetails())
				.getRemoteAddress();
		}catch(Exception e){
			// do not
		}
		return temp;
	}
	
	
	/**
	 * Get nick name
	 * @return nick name
	 */
	public static String getNickName(){
		try{
			return getUserInfo().getNickname();
		}catch(Exception e){
			return "admin";
		}
		
	}

}
