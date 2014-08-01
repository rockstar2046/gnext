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

import java.util.HashSet;
import java.util.LinkedHashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Authorized resources,provide for spring security.
 * 
 * @author RA
 * @since JPA2.0
 */
@Entity
@Table(name = "AUTH_RES")
public class AuthResource{

	// ~ Instance fields ==================================================
	
	/** The id. */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AUTH_RES_ID")
	@SequenceGenerator(name = "SEQ_AUTH_RES_ID", sequenceName = "SEQ_AUTH_RES_ID", initialValue = 1, allocationSize = 1)
	private Long id;

	/** The res url. <p><b>NOTE: Using ANT path mode</b></p>*/
	@Column(name = "RES_URL", length = 1024, nullable = false)
	private String resUrl;

	/** The res priority. the smaller the value the higher the priority*/
	@Column(name = "RES_PRIORITY", length = 20, nullable = false, unique = true)
	private Integer resPriority;

	/** The res description. */
	@Column(name = "RES_DESCRIPTION", length = 512, nullable = true)
	private String resDescription;

	/** The roles. */
	@ManyToMany(cascade = CascadeType.REFRESH)
	@JoinTable(name = "AUTH_RES_ROLE", inverseJoinColumns = @JoinColumn(name = "ROLE_ID"), joinColumns = @JoinColumn(name = "RES_ID"))
	@OrderBy("ID DESC")
	private HashSet<AuthRole> roles = new LinkedHashSet<AuthRole>();
	
	@Version
	private Long version;

	// ~ Methods ==================================================

	/**
	 * Copy.
	 * 
	 * @param src
	 *            the src
	 */
	public void copy(AuthResource src) {
		this.resDescription = src.getResDescription();
		this.resPriority = src.getResPriority();
		this.resUrl = src.getResUrl();
	}

	// Getters and Setters

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the res url.
	 * 
	 * @return the res url
	 */
	public String getResUrl() {
		return resUrl;
	}

	/**
	 * Sets the res url.
	 * <p><b>NOTE: Using ANT path mode</b></p>
	 * @param resUrl
	 *            the new res url
	 */
	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	/**
	 * Gets the res priority.
	 * 
	 * @return the res priority
	 */
	public Integer getResPriority() {
		return resPriority;
	}

	/**
	 * Sets the res priority.
	 * <p>The smaller the value the higher the priority</p>
	 * @param resPriority
	 *            the new res priority
	 */
	public void setResPriority(Integer resPriority) {
		this.resPriority = resPriority;
	}

	/**
	 * Gets the res description.
	 * 
	 * @return the res description
	 */
	public String getResDescription() {
		return resDescription;
	}

	/**
	 * Sets the res description.
	 * 
	 * @param resDescription
	 *            the new res description
	 */
	public void setResDescription(String resDescription) {
		this.resDescription = resDescription;
	}

	/**
	 * Gets the roles.
	 * 
	 * @return the roles
	 */
	public HashSet<AuthRole> getRoles() {
		return roles;
	}

	/**
	 * Sets the roles.
	 * 
	 * @param roles
	 *            the new roles
	 */
	public void setRoles(HashSet<AuthRole> roles) {
		this.roles = roles;
	}

}
