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
package com.rockagen.gnext.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rockagen.commons.util.JsonUtil;
import com.rockagen.gnext.bo.BoUser;

/**
 * 
 * <p>souce: 2.json</p>
 *<pre>
 * [{"id":1,"name":"AGEN","addr":"USA","email":"agen@rockagen.com"},{"id":2,"name":"TOM","addr":"USA","email":"tom@rockagen.com"},{"id":3,"name":"LISI","addr":"CHINA","email":"lisi@rockagen.com"},{"id":4,"name":"CHE","addr":"KOREA","email":"che@rockagen.com"},{"id":5,"name":"JOE","addr":"USA","email":"joe@rockagen.com"}];
 *</pre>
 *
 * <p> client: </p>
 * <pre>
 * curl -v  -H "Content-Type: application/json" -X POST --data "@2.json" http://agen:5000/user
 * 
 * curl -i -v -X GET http://agen:5000/user
 * 
 * curl -i -v -X GET http://agen:5000/user/1
 * 
 * curl -X DELETE agen:5000/user/1
 * 
 * curl -i -v -X PUT -H "Content-Type: application/json" -d '{"id":5,"name":"SLASH","email":"slash@rockagen.com","addr":"USA","age":45}' http://agen:5000/user/5
 * </pre>
 * @author RA
 * @since JDK1.6
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	
	private final Map<String,BoUser> USERS=new ConcurrentHashMap<String,BoUser>();
	
	
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody Object getUser(){
		
//		List<BoUser> resp=new ArrayList<BoUser>();
//		for(Map.Entry<String, BoUser> entry : users.entrySet()){
//			
//		}
		
		return JsonUtil.toJson(USERS);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public @ResponseBody Object getUser(@PathVariable String id){
		BoUser bo=USERS.get(id);
		
		return JsonUtil.toJson(bo);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public @ResponseBody Object updateUser(@PathVariable String id,@RequestBody BoUser user){
		
		BoUser bo=USERS.get(id);
		if(bo!=null){
			USERS.put(id, user);
			return "success.";
		}
		return  "failed.";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody Object addUser(@RequestBody List<BoUser> users){
		if(users==null){
			return "failed.";
		}
		for(BoUser user : users){
			USERS.put(String.valueOf(user.getId()), user);
		}
		
		return  "success.";
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public @ResponseBody Object deleteUser(@PathVariable String id){
		USERS.remove(id);
		return  "success.";
	}

}
