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
package com.rockagen.gnext.service.spring.security.extension;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/**
 * An extension for spring security {@link UserDetails}
 * 
 * @author RA
 * @see org.springframework.security.core.userdetails.User
 */
public class BasicSecurityUser implements UserDetails, CredentialsContainer {
    
	
	//~ Instance fields ==================================================
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5739818223174379036L;
    
    /** The password. */
    private String password;
    
    /** The username. */
    private final String username;
    
    /** The authorities. */
    private final Set<GrantedAuthority> authorities;
    
    /** The account non expired. */
    private final boolean accountNonExpired;
    
    /** The account non locked. */
    private final boolean accountNonLocked;
    
    /** The credentials non expired. */
    private final boolean credentialsNonExpired;
    
    /** The enabled. */
    private final boolean enabled;
    
    //extend
    /** The nickname. */
    private final String nickname; 

	/** The email. */
	private final String email; 
	/** The latest ip address */
	private final String latestIp;

    //~ Constructors ===================================================================================================

	/**
	 * Instantiates a new smart user.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param enabled
	 *            the enabled
	 * @param accountNonExpired
	 *            the account non expired
	 * @param credentialsNonExpired
	 *            the credentials non expired
	 * @param accountNonLocked
	 *            the account non locked
	 * @param authorities
	 *            the authorities
	 * @param mername
	 *            the nickname
	 * @param email
	 *            the email
	 */
    public BasicSecurityUser(String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,String nickname,String email,String latestIp) {

        if (((username == null) || "".equals(username)) || (password == null)) {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }

        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
        // extend
        this.nickname=nickname;
        this.email=email;
        this.latestIp=latestIp;
    }

    //~ Methods ========================================================================================================

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
     */
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /** 
     * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
     */
    public String getPassword() {
        return password;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
     */
    public String getUsername() {
        return username;
    }
    

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
     */
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
     */
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
     */
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    /**
     * @see org.springframework.security.core.CredentialsContainer#eraseCredentials()
     */
    public void eraseCredentials() {
        password = null;
    }
    
    /**
	 * Gets the nickname.
	 * 
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}
	
    public String getLatestIp() {
		return latestIp;
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
	 * Sort authorities.
	 * 
	 * @param authorities
	 *            the authorities
	 * @return the sorted set
	 */
    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities =
            new TreeSet<GrantedAuthority>(new AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    /**
	 * The Class AuthorityComparator.
	 */
    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        
		//~ Instance fields ==================================================
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/**
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to the set.
            // If the authority is null, it is a custom authority and should precede others.
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof BasicSecurityUser) {
            return username.equals(((BasicSecurityUser) rhs).username);
        }
        return false;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return username.hashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("Username: ").append(this.username).append("; ");
        sb.append("Nickname: ").append(this.nickname).append("; ");
        sb.append("Email: ").append(this.email).append("; ");
        sb.append("Password: [PROTECTED]; ");
        sb.append("Enabled: ").append(this.enabled).append("; ");
        sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
        sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");
        sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");

        if (!authorities.isEmpty()) {
            sb.append("Granted Authorities: ");

            boolean first = true;
            for (GrantedAuthority auth : authorities) {
                if (!first) {
                    sb.append(",");
                }
                first = false;

                sb.append(auth);
            }
        } else {
            sb.append("Not granted any authorities");
        }

        return sb.toString();
    }

}
