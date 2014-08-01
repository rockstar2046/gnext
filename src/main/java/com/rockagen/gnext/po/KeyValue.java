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
package com.rockagen.gnext.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.rockagen.commons.util.CommUtil;


/**
 * Key-Value configuration file
 * @author RA
 * @since JPA2.0
 */
@Entity
@Table(
	name="KEY_VALUE"
)
public class KeyValue implements Serializable{
	
	
	//~ Instance fields ==================================================
	/**
	 */
	private static final long serialVersionUID = -6420800914626635890L;

	/**
	 * id
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = "SEQ_KEY_VALUE_ID")
	@SequenceGenerator(
			name = "SEQ_KEY_VALUE_ID", 
			sequenceName = "SEQ_KEY_VALUE_ID",
			initialValue=1,allocationSize=1)
	private Long id;

	/**
	 * key
	 */
	@Column(name = "KEY", length = 64, unique=true)
	private String key;
	/**
	 * value
	 */
	@Column(name = "VALUE", length = 1024)
	private String value;
	/**
	 * persistance ? 
	 * <li>0 is activity</li>
	 * <li>1 is persist </li>
	 */
	@Column(name = "PERSIST", length = 1)
	private Integer persist;

	/**
	 * description
	 */
	@Column(name = "DESCRIPTION", length = 512)
	private String description;
	
	@Version
	private Long version;
	
	//~ Methods ==============================================
	
	/**
	 * Copy editable properties
	 * 
	 * @param keyValue
	 */
	public void copy(KeyValue keyValue){
		this.key=keyValue.getKey();
		this.value=keyValue.getValue();
		this.description=keyValue.getDescription();
		this.persist=keyValue.getPersist();
	}
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the persist
	 */
	public Integer getPersist() {
		return persist;
	}
	/**
	 *  persistance ? 
	 * <li>0 is not activity</li>
	 * <li>1 is persist </li>
	 * @param persist the persist to set
	 */
	public void setPersist(Integer persist) {
		this.persist = persist;
	}
	
	
	@Override
	public String toString() {
		return "KeyValue [id=" + id + ", key=" + key + ", value=" +  CommUtil.subPostfix(value, 0, 3, "***") + ", persist=" + persist + ", description="
				+ description + "]";
	}
	
}