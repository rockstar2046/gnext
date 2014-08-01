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

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rockagen.commons.util.CommUtil;
import com.rockagen.gnext.po.KeyValue;
import com.rockagen.gnext.service.KeyValueServ;
import com.rockagen.gnext.tool.Conf;

/**
 * Replace Properties configuration
 * <p>
 * <b>NOTE: Manager by Spring &lt;task:scheduled-tasks&gt;<b>
 * </p>
 * 
 * <p>
 * example:
 * </p>
 * 
 * <pre>
 * &lt;bean id="keyValueMap" class="com.rockagen.gnext.service.spring.KeyValueMap" init-method="init"&gt;
 * 	&lt;property name="keyValueServ" ref="keyValueServ"/&gt;
 * &lt;/bean&gt;
 *   
 * &lt;!-- KEY VALUE MAP TASK --&gt;
 * &lt;task:scheduled-tasks&gt;  
 * 	&lt;task:scheduled ref="keyValueMap" method="update" cron="0 *<code>/</code>5 9-21 * * ?"/&gt;  
 * &lt;/task:scheduled-tasks&gt;
 * </pre>
 * 
 * @author RA
 * @see KeyValue
 */
public class KeyValueMapImpl {

	// ~ Instance fields ==================================================

	private static final Log log = LogFactory.getLog(KeyValueMapImpl.class);

	private KeyValueServ keyValueServ;

	/**
	 * Key Value Map
	 */
	private final Map<String, String> KEY_VALUE_MAP = new ConcurrentHashMap<String, String>();

	// ~ Methods ==================================================

	/**
	 * Initialize (spring init-method)
	 */
	public void init() {

		List<KeyValue> results = keyValueServ.findAll();
		if (log.isDebugEnabled()) {
			log.info("Start put key value to map...");
		}
		for (KeyValue result : results) {
			if (log.isDebugEnabled()) {
				log.info("now put : " + result.getKey() + "--->"
						+ CommUtil.subPostfix(result.getValue(), 0, 3, "***"));
			}
			KEY_VALUE_MAP.put(result.getKey(), result.getValue());
		}
		log.info("done.");
		// Initialize
		Conf.init(getMap());
		Conf.onChange(getMap());

	}

	/**
	 * Get value
	 * 
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		if (CommUtil.isBlank(key)) {
			return null;
		}
		return KEY_VALUE_MAP.get(key);
	}

	/**
	 * Get the duplicate map
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, String> getMap() {

		final Map<String, String> temp = new ConcurrentHashMap<String, String>();
		temp.putAll(KEY_VALUE_MAP);

		return temp;
	}

	/**
	 * Update not persist data
	 */
	public void update() {

		List<KeyValue> results = keyValueServ.findNoPersist();
		if (log.isDebugEnabled()) {
			log.debug("start update KEY_VALUE_MAP...");
		}
		for (KeyValue result : results) {
			if (log.isDebugEnabled()) {
				log.info("now update : " + result.getKey() + "--->"
						+ CommUtil.subPostfix(result.getValue(), 0, 3, "***"));
			}
			KEY_VALUE_MAP.put(result.getKey(), result.getValue());
		}
		if (log.isDebugEnabled()) {
			log.info("done.");
		}
		// update
		Conf.onChange(getMap());
	}

	/**
	 * @param keyValueServ
	 *            the keyValueServ to set
	 */
	public void setKeyValueServ(KeyValueServ keyValueServ) {
		this.keyValueServ = keyValueServ;
	}

}
