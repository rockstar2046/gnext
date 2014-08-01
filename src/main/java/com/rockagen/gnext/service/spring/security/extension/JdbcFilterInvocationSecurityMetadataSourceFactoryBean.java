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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;


/**
 * An extension for spring security meta data source
 * <code>This need some like:</code>
 * <blockquote>
 * <pre>
 * 	RESOURCE_URL | ROLE_NAME
 *	-------------+-------------
 * 	/xx/root/x   | ROLE_ROOT 
 * 	/xx/admin/x  | ROLE_ADMIN
 * 	/xx/auth/x   | ROLE_AUTH
 *	-------------+-------------
 * </pre>
 * <p>note: you must provide a <code>resourceQuery</code> sql like:</p>
 * <pre>
 *  SELECT RESOURCE_URL,ROLE_NAME FROM RESOURCE_ROLE;
 * </pre>
 * </blockquote>
 * @author RA
 */
public class JdbcFilterInvocationSecurityMetadataSourceFactoryBean extends JdbcDaoSupport implements
		FactoryBean<FilterInvocationSecurityMetadataSource> {
	
    
    //~ Instance fields ==================================================
    
    /**
     * 
     */
    public final static  Log log = LogFactory.getLog(JdbcFilterInvocationSecurityMetadataSourceFactoryBean.class);
    
    /** The resource query. */
	private String resourceQuery;

	@Override
	public boolean isSingleton() {
		return false;
	}
	@Override
	public Class<?> getObjectType() {
		return FilterInvocationSecurityMetadataSource.class;
	}

	@Override
	public FilterInvocationSecurityMetadataSource getObject() {
		logger.info("###### DefaultFilterInvocationSecurityMetadataSource has changed. ######");
		return new DefaultFilterInvocationSecurityMetadataSource(buildRequestMap());

	}
	
	/**
	 * Find resources by resourceQuery from dataSource
	 * 
	 * <p>return map like this:</p>
	 * <pre>
	 *  "/xxx/root/*"  -- "ROLE_ROOT"
	 *  "/xxx/admin/*" -- "ROLE_ROOT,ROLE_ADMIN"
	 * </pre>
	 * <p><b>NOTE: Using ANT path mode</b></p>
	 * 
	 * @return the map
	 */
	protected Map<String, String> findResources() {
		

		Map<String, String> resourceMap = new LinkedHashMap<String, String>();
		//ResourceMapping resourceMapping = new ResourceMapping(getDataSource(), resourceQuery);
		//List<Resource> executeListSource = resourceMapping.execute();
		List<Resource> executeListSource = loadAllResource();

		for (Resource resource : executeListSource) {
			String url = resource.getUrl();
			String role = resource.getRole();

			if (resourceMap.containsKey(url)) {
				String value = resourceMap.get(url);
				resourceMap.put(url, value + "," + role);
			} else {
				resourceMap.put(url, role);
			}
		}

		return resourceMap;
	}

	/**
	 * Builds the request map.
	 * <p>return LinkedHashMap&lt; {@link RequestMatcher}, Collection&lt; {@link ConfigAttribute}&gt;&gt </p>
	 * 
	 * @return requestMap order-preserving map of request definitions to attribute lists
	 */
	protected LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> buildRequestMap() {
		 LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();

		Map<String, String> resourceMap = findResources();

		for (Map.Entry<String, String> entry : resourceMap.entrySet()) {
			RequestMatcher key =new AntPathRequestMatcher(entry.getKey());
			requestMap.put(key, SecurityConfig.createListFromCommaDelimitedString(entry.getValue()));
		}

		return requestMap;
	}

	
	/**
	 * Sets the resource query.
	 * 
	 * @param resourceQuery
	 *            the new resource query
	 */
	public void setResourceQuery(String resourceQuery) {
		this.resourceQuery = resourceQuery;
	}
	
    /**
     * Load all resource
     * @return resource list
     */
    protected List<Resource> loadAllResource() {
        return getJdbcTemplate().query(resourceQuery,new RowMapper<Resource>() {
            public Resource mapRow(ResultSet rs, int rowNum) throws SQLException {
            	String url = rs.getString(1);
    			String role = rs.getString(2);
                return new Resource(url, role);
            }

        });
    }

	/**
	 * Implement {@link MappingSqlQuery} to get a List&lt;{@link Resource}&gt;
	 * <p>Call ResourceMapping.execute() return a List</p>
	 */
	static class ResourceMapping extends MappingSqlQuery<Resource> {
		
		/**
		 *  Injection a {@link DataSource} and  a resourceQuery sql
		 * 
		 * @param dataSource
		 *            the data source
		 * @param resourceQuery
		 *            the resource query
		 */
		public ResourceMapping(DataSource dataSource, String resourceQuery) {
			super(dataSource, resourceQuery);
			compile();
		}
		
		@Override
		protected Resource mapRow(ResultSet rs, int rownum) throws SQLException {
			String url = rs.getString(1);
			String role = rs.getString(2);
			Resource resource = new Resource(url, role);

			return resource;
		}
	}

}
