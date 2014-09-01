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

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Authorized roles,provide for spring security.
 * 
 * @author RA
 * @since JPA2.0
 */
@Entity
@Table(name = "AUTH_ROLE")
public class AuthRole {

	// ~ Instance fields ==================================================
	/** The id. */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AUTH_ROLE_ID")
	@SequenceGenerator(name = "SEQ_AUTH_ROLE_ID", sequenceName = "SEQ_AUTH_ROLE_ID", initialValue = 1, allocationSize = 1)
	private Long id;

	/** The name. */
	@Column(name = "NAME", length = 32, nullable = false)
	private String name;

	/** The users. */
	@ManyToMany(cascade = CascadeType.MERGE, mappedBy = "roles", fetch = FetchType.LAZY)
	@OrderBy("ID DESC")
	private Set<AuthUser> users = new LinkedHashSet<AuthUser>();

	/** The ressources . */
	@ManyToMany(cascade = CascadeType.MERGE, mappedBy = "roles", fetch = FetchType.LAZY)
	@OrderBy("ID DESC")
	private Set<AuthResource> res = new LinkedHashSet<AuthResource>();

	/** The desc. */
	@Column(name = "DESCRIPTION", length = 512, nullable = true)
	private String description;

	@Version
	private Long version;

	// ~ Methods ==================================================

	/**
	 * Copy editable properties
	 * 
	 * @param src
	 *            the src
	 */
	public void copy(AuthRole src) {

		description = src.getDescription();
		name = src.getName();
	}

	// Getters and Setters ...

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the users.
	 * 
	 * @return the users
	 */
	public Set<AuthUser> getUsers() {
		return users;
	}

	/**
	 * Sets the users.
	 * 
	 * @param users
	 *            the new users
	 */
	public void setUsers(Set<AuthUser> users) {
		this.users = users;
	}

	/**
	 * Gets the res.
	 * 
	 * @return the res
	 */
	public Set<AuthResource> getRes() {
		return res;
	}

	/**
	 * Sets the res.
	 * 
	 * @param ress
	 *            the new res
	 */
	public void setRes(Set<AuthResource> res) {
		this.res = res;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
