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
package com.rockagen.gnext.tool;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import com.rockagen.commons.util.CommUtil;
import com.rockagen.commons.util.MDUtil;

/**
 * Crypto utils
 * 
 * @author RA
 */
public class Crypto {

	// ~ Methods ==================================================

	/**
	 * Obtain salt
	 * 
	 * @return 16 bytes salt
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	public static byte[] getSalt() throws NoSuchAlgorithmException,
			NoSuchProviderException {
		// Use SecureRandom generator
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
		// Create 16 bytes salt
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

	/**
	 * Obtain Hex salt
	 * 
	 * @return 16 bytes salt
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	public static String getHexSalt() throws NoSuchAlgorithmException,
			NoSuchProviderException {
		return CommUtil.hexdump(getSalt()).toLowerCase();
	}

	/**
	 * Use md5 with a salt algorithm
	 * 
	 * @param password
	 * @param salt
	 * @return Encrypted string
	 * @throws IllegalArgumentException
	 */
	public static String md5WithSalt(String password, String salt)
			throws IllegalArgumentException {
		checkArguments(password, salt);
		// md5(md5(password)+salt)
		String plain = MDUtil.md5Hex16(password) + salt;
		String cipher = MDUtil.md5Hex16(plain);
		return cipher.toLowerCase();
	}

	/**
	 * Use sha1 with a salt algorithm
	 * 
	 * @param password
	 * @param salt
	 * @return Encrypted string
	 */
	public static String sha1WithSalt(String password, String salt) {
		checkArguments(password, salt);
		// sha1(sha1(password)+salt)
		String plain = MDUtil.sha1Hex(password) + salt;
		String cipher = MDUtil.sha1Hex(plain);
		return cipher.toLowerCase();
	}

	/**
	 * Check encrypt arguments
	 * 
	 * @param password
	 * @param salt
	 */
	private static void checkArguments(String password, String salt) {
		if (CommUtil.isBlank(password))
			throw new IllegalArgumentException(
					"password must not be null or empty.");
		if (CommUtil.isBlank(salt))
			throw new IllegalArgumentException(
					"password salt must not be null or empty.");
	}

}
