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
package com.rockagen.gnext.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.rockagen.commons.util.MDUtil;
import com.rockagen.gnext.po.AuthResource;
import com.rockagen.gnext.po.AuthRole;
import com.rockagen.gnext.po.AuthUser;
import com.rockagen.gnext.po.KeyValue;
import com.rockagen.gnext.qo.QueryObject;
import com.rockagen.gnext.service.AuthResourceServ;
import com.rockagen.gnext.service.AuthRoleServ;
import com.rockagen.gnext.service.AuthUserServ;
import com.rockagen.gnext.service.KeyValueServ;
import com.rockagen.gnext.tool.Crypto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class ServiceTest {

	@Resource
	private KeyValueServ keyValueServ;
	@Resource
	private AuthUserServ authUserServ;
	@Resource
	private AuthResourceServ authResourceServ;
	@Resource
	private AuthRoleServ authRoleServ;

	@Test
	@Ignore
	// @Rollback(true)
	public void testIniKeyValueServ() {

		
		 KeyValue a=new KeyValue(); a.setKey("test1"); a.setPersist(0);
		 a.setValue("test1-value"); a.setDescription("some test");
		 keyValueServ.add(a);
		 

	}
	
	@Test
	@Rollback(true)
	public void testKeyValueServ() {
		List<KeyValue> list = keyValueServ.findNoPersist();
		
		for (KeyValue kv : list) {
			System.out.println(kv);
		}
		
	}

	@Test
	@Ignore
	// @Rollback(true)
	public void testIniAuthUserServ() {

	


		AuthRole ar = new AuthRole();
		ar.setName("ROLE_ROOT");
		ar.setDescription("root privileges");

		AuthRole ar2 = new AuthRole();
		ar2.setName("ROLE_USER");
		ar2.setDescription("user privileges");

		HashSet<AuthRole> roles = new HashSet<AuthRole>();
		roles.add(ar);
		HashSet<AuthRole> roles2 = new HashSet<AuthRole>();
		roles2.add(ar2);
		
		

		AuthResource res = new AuthResource();
		res.setResDescription("Need ROLE_ROOT privileges");
		res.setResPriority(0);
		res.setRoles(roles);
		res.setResUrl("/*");
		
		
		AuthResource res2 = new AuthResource();
		res2.setResDescription("Need ROLE_USER privileges");
		res2.setResPriority(50);
		res2.setRoles(roles2);
		res2.setResUrl("/user/*");
		
		authRoleServ.add(ar);
		authRoleServ.add(ar2);
		
		authResourceServ.add(res);
		authResourceServ.add(res2);
		
		
		
		AuthUser a = new AuthUser();
		a.setEmail("ra@rockagen.com");
		a.setEnabled(1);
		a.setErrorCount(0);
		a.setNickName("rockagen");
		a.setUserName("root");
		a.setPassWord("admin");

		a.setRoles(roles);
		authUserServ.add(a);
	}
	 
	
	@Test
	public void testAuthUserServ() {
		//AuthUser a = authUserServ.load("root");
		QueryObject qp=new QueryObject();
		qp.setSql("from AuthUser where id< ?");
//		qp.setSql("select id,email from AuthUser where id< ?0");
		qp.setArgs(new Object[]{new Long(10)});
//		qp.setSql("select id,email from AuthUser where id<:id");
//		Map<String, Object> map=new HashMap<String, Object>();
//		map.put("id", 10L);
//		qp.setMap(map);
//		DetachedCriteria dc=qp.generateDetachedCriteria(AuthUser.class);
//		dc.add(Restrictions.lt("id", 10L));
//		qp.setDetachedCriteria(dc);
		List<AuthUser> a = authUserServ.find(qp);
		System.out.println(a.get(0));
		a.get(0).setPassWord(Crypto.sha1WithSalt("admin",a.get(0).getSalt()));
	}
	
	
	@Test
	public void testPasswd(){
/*		QueryObject qp=new QueryObject();
		qp.setSql("from AuthUser where id<?");
		qp.setArgs(new Object[]{new Long(10)});
		List<AuthUser> a = authUserServ.find(qp);
		System.out.println(a.get(0));*/
		authUserServ.passwd(2L, "admin111", "admin");
		
	}
}
