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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rockagen.commons.util.CommUtil;
import com.rockagen.gnext.po.AuthUser;
import com.rockagen.gnext.qo.QueryObject;
import com.rockagen.gnext.service.AuthUserServ;

/**
 * Implementation of the <code>AuthUserServ</code> interface
 * 
 * @author RA
 */
@Service("authUserServ")
public class AuthUserServImpl extends
		QueryObjectGenericServImpl<AuthUser, Long> implements AuthUserServ {

	@Override
	public void passwd(final Long id, final String newPass) {
		AuthUser po = super.getGenericDao().get(id);
		if (po != null) {
			po.setPassWord(newPass);
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
}
