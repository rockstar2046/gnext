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
package com.rockagen.gnext.service.spring;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rockagen.commons.util.CommUtil;
import com.rockagen.gnext.po.AuthUser;
import com.rockagen.gnext.qo.QueryObject;
import com.rockagen.gnext.service.AuthUserServ;
import com.rockagen.gnext.tool.Crypto;

/**
 * Implementation of the <code>AuthUserServ</code> interface
 * 
 * @author RA
 */
@Service("authUserServ")
public class AuthUserServImpl extends
		QueryObjectGenericServImpl<AuthUser, Long> implements AuthUserServ {
	
	
	//~ Instance fields ==================================================
	
	/**
	 * 
	 */
	private static final Logger log = LoggerFactory.getLogger(AuthUserServImpl.class);

	@Override
	public void passwd(final Long id, final String oldPass,final String newPass) {
		
		AuthUser po = super.getGenericDao().get(id);
		if (po != null) {
			String salt=po.getSalt();
			String oldpassword=Crypto.sha1WithSalt(oldPass, salt);
			// Authorized success
			if(oldpassword.equals(po.getPassWord())){
				newPassword(po,newPass);
			}else{
				log.warn("User [{}] old password is invalid,not change.",po.getUserName());
			}
		}

	}

	@Override
	public AuthUser load(String account) {
		if (CommUtil.isBlank(account)) {
			return null;
		}
		QueryObject qo = new QueryObject();
		qo.setSql("from AuthUser o where o.userName=:username");
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", account);
		qo.setMap(map);
		List<AuthUser> list = find(qo);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	
	
	private void newPassword(final AuthUser po,final String newPass){
			try {
				String salt = Crypto.getHexSalt();
				String cipher=Crypto.sha1WithSalt(newPass, salt);
				po.setSalt(salt);
				po.setPassWord(cipher);
			} catch (NoSuchAlgorithmException e) {
				log.error("{}",e.getMessage(),e);
			} catch (NoSuchProviderException e) {
				log.error("{}",e.getMessage(),e);
			}
	}
	
	@Override
	public void add(AuthUser pojo) {
		if(pojo!=null){
			try {
				String salt = Crypto.getHexSalt();
				String cipher=Crypto.sha1WithSalt(pojo.getPassWord(), salt);
				pojo.setSalt(salt);
				pojo.setPassWord(cipher);
				super.add(pojo);
			} catch (NoSuchAlgorithmException e) {
				log.error("{}",e.getMessage(),e);
			} catch (NoSuchProviderException e) {
				log.error("{}",e.getMessage(),e);
			}
			
		}
		
	}
}
