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

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.junit.Test;

import com.rockagen.commons.util.CommUtil;
import com.rockagen.commons.util.MDUtil;
import com.rockagen.gnext.tool.Crypto;

/**
 *
 * @author RA
 */
public class ToolsTest {
	
	@Test
	public void testCrypto() throws NoSuchAlgorithmException, NoSuchProviderException{
		String password="admin123";
		String salt=Crypto.getHexSalt();
		System.err.println("plain: "+MDUtil.sha1Hex(password));
		System.err.println("salt: "+salt);
		System.out.println(Crypto.sha1WithSalt(password, salt));
	}

}
