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

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

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
 * Authorized users,provide for spring security.
 * 
 * @author RA
 * @since JPA2.0
 */
@Entity
@Table(name = "AUTH_USER")
public class AuthUser{
	
	//~ Instance fields ==================================================

	/** The id. */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AUTH_USER_ID")
	@SequenceGenerator(name = "SEQ_AUTH_USER_ID", sequenceName = "SEQ_AUTH_USER_ID", initialValue = 1, allocationSize = 1)
	private Long id;

	/** The user name. */
	@Column(name = "USER_NAME", length = 60, nullable = false, unique = true)
	private String userName;

	/** The pass word. */
	@Column(name = "USER_PASSWORD", length = 60, nullable = false)
	private String passWord;

	/** The enabled. */
	@Column(name = "ENABLED", length = 1, nullable = false)
	private Integer enabled;

	/** The nick name. */
	@Column(name = "NICK_NAME", length = 60, nullable = false)
	private String nickName;

	/** The email. */
	@Column(name = "EMAIL", length = 60, nullable = true)
	private String email;

	/** The Auth roles. */
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "AUTH_USER_ROLE", inverseJoinColumns = @JoinColumn(name = "ROLE_ID"), joinColumns = @JoinColumn(name = "USER_ID"))
	@OrderBy("ID DESC")
	private Set<AuthRole> roles = new LinkedHashSet<AuthRole>();

	/** The error_count. */
	@Column(name = "ERROR_COUNT", length = 1, nullable = true)
	private Integer errorCount;

	/** The state time. */
	@Column(name = "STATE_TIME", length = 16, nullable = true)
	private Date stateTime;
	/** The latest ip addr. */
	@Column(name = "LATEST_IP", length = 32, nullable = true)
	private String latestIp;

	@Version
	private Long version;

	// ~ Methods ==================================================

	/**
	 * Copy editable properties.
	 * 
	 * @param src
	 *            the src
	 */
	public void copy(AuthUser src) {
		roles = src.getRoles();
		enabled = src.getEnabled();
		errorCount = src.getErrorCount();
		stateTime = src.getStateTime();
		nickName = src.getNickName();
		email = src.getEmail();
		latestIp = src.getLatestIp();
	}
	
	// Getters and Setters ...

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
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 * 
	 * @param userName
	 *            the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the pass word.
	 * 
	 * @return the pass word
	 */
	public String getPassWord() {
		return passWord;
	}

	/**
	 * Sets the pass word.
	 * 
	 * @param passWord
	 *            the new pass word
	 */
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	/**
	 * Gets the enabled.
	 * 
	 * @return the enabled
	 */
	public Integer getEnabled() {
		return enabled;
	}

	/**
	 * Sets the enabled.
	 * 
	 * @param enabled
	 *            the new enabled
	 */
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	/**
	 * Gets the nick name.
	 * 
	 * @return the nick name
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * Sets the nick name.
	 * 
	 * @param nickName
	 *            the new nick name
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * Gets the email.
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 * 
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the roles.
	 * 
	 * @return the roles
	 */
	public Set<AuthRole> getRoles() {
		return roles;
	}

	/**
	 * Sets the roles.
	 * 
	 * @param roles
	 *            the new roles
	 */
	public void setRoles(Set<AuthRole> roles) {
		this.roles = roles;
	}

	/**
	 * Gets the error count.
	 * 
	 * @return the error count
	 */
	public Integer getErrorCount() {
		return errorCount;
	}

	/**
	 * Sets the error count.
	 * 
	 * @param errorCount
	 *            the new error count
	 */
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}

	/**
	 * Gets the state time.
	 * 
	 * @return the state time
	 */
	public Date getStateTime() {
		if (stateTime != null) {
			return new Date(stateTime.getTime());
		} else {
			return null;
		}
	}

	/**
	 * Sets the state time.
	 * 
	 * @param stateTime
	 *            the new state time
	 */
	public void setStateTime(Date stateTime) {
		if(stateTime==null){
			stateTime=new Date();
		}
		this.stateTime = stateTime;
	}

	/**
	 * Gets the latest ip.
	 * 
	 * @return the latest ip
	 */
	public String getLatestIp() {
		return latestIp;
	}

	/**
	 * Sets the latest ip.
	 * 
	 * @param latestIp
	 *            the new latest ip
	 */
	public void setLatestIp(String latestIp) {
		this.latestIp = latestIp;
	}
	
	/**
	 * To string.
	 * 
	 * @return the string
	 */
	@Override
	public String toString() {

		StringBuffer sb = new StringBuffer();
		sb.append("AuthUser [id=" + id + ", userName=" + userName
				+ ", passWord=[PROTECTED]" + ", enabled=" + enabled
				+ ", nickName=" + nickName + ", email=" + email + ", roles=[");
		Iterator<AuthRole> iterator = roles.iterator();
		while (iterator.hasNext()) {
			sb.append(iterator.next().getName() + ", ");
		}
		sb.append("]");
		sb.append("errorCount=" + errorCount + ", stateTime=" + stateTime
				+ ",latestIp-=" + latestIp + "]");

		return sb.toString();
	}

}
