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
package com.rockagen.gnext.dao.hb;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;

import com.rockagen.commons.util.CommUtil;
import com.rockagen.commons.util.ReflexUtil;
import com.rockagen.gnext.dao.Hibernate4GenericDao;

/**
 * Implementation of the <code>Hibernate4GenericDao</code> interface
 * 
 * @author RA
 * @see Hibernate4GenericDao
 */
public class Hibernate4GenericDaoImpl <E, PK extends Serializable>
extends BaseDao implements Hibernate4GenericDao<E, PK>{

	// ~ Instance fields ==================================================

	/**
	 * Query all
	 */
	public final String QUERY_ALL = "from " + getEntityClass().getSimpleName();

	@Override
	public void delete(PK id) {
		if (id != null) {
			E instance = get(id);
			if (instance != null) {
				delete(instance);
			}
		}

	}

	@Override
	public void delete(E pojo) {
		if (pojo != null) {
			super.getSession().delete(pojo);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public E get(PK id) {
		if (id != null) {
			return (E) super.getSession().get(getEntityClass(), id);
		}
		return null;
	}

	/**
	 * Create a Hibernate {@link Query}
	 * 
	 * @param hql
	 * @param firstResult
	 * @param maxResults
	 *            negatives meaning no limit...
	 * @param map
	 * @param values
	 * @return Query
	 */
	protected Query createQuery(String hql, int firstResult, int maxResults,final Map<String, Object> map,final Object... values) {
		if (CommUtil.isBlank(hql)) {
			hql = QUERY_ALL;
		}
		Query query = super.getSession().createQuery(hql);
		
		if (firstResult > 0) {
			query.setFirstResult(firstResult);
		}

		if (maxResults > 0) {
			query.setMaxResults(maxResults);
		}
		
		if(map!=null && !map.isEmpty()){
			for(Map.Entry<String, Object> entry : map.entrySet()){
				 query.setParameter(entry.getKey(),entry.getValue());
			}
		}else if(values !=null && values.length>0){
			for (int i = 0; i < values.length; i++) {
			 	query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * Create a hibernate {@link Criteria} by {@link DetachedCriteria}
	 * 
	 * @param criterions
	 * @param firstResult
	 * @param maxResults
	 *            negatives meaning no limit...
	 * @return Criteria
	 */
	protected Criteria createCriteria(DetachedCriteria dcriteria,
			int firstResult, int maxResults) {
		if (dcriteria == null) {
			dcriteria = DetachedCriteria.forClass(getEntityClass());
		}
		Criteria criteria = dcriteria.getExecutableCriteria(super.getSession());
		if (firstResult > 0) {
			criteria.setFirstResult(firstResult);
		}

		if (maxResults > 0) {
			criteria.setMaxResults(maxResults);
		}
		return criteria;
	}

	@Override
	public List<?> query(String hql, int firstResult, int maxResults,
			Object... values) {
		List<?> list = createQuery(hql, firstResult, maxResults, null,values).list();
		return list;
	}

	@Override
	public List<?> query(String hql, Object... values) {
		return query(hql, 0, -1, values);
	}
	
	@Override
	public List<?> query(String hql, int firstResult, int maxResults,
			Map<String, Object> values) {
		List<?> list = createQuery(hql, firstResult, maxResults, values).list();
		return list;
	}

	@Override
	public List<?> query(String hql, Map<String, Object> values) {
		return query(hql, 0, -1, values);
	}

	@Override
	public List<?> queryByCriteria(DetachedCriteria dcriteria,
			final int firstResult, final int maxResults) {

		List<?> list = createCriteria(dcriteria, firstResult, maxResults)
				.list();
		return list;
	}

	@Override
	public List<?> queryByCriteria(DetachedCriteria dcriteria) {
		return queryByCriteria(dcriteria, 0, -1);
	}

	/**
	 * <b>NOte: update operation id must not null</b>
	 * <p>
	 * Recommended to persistence at first, and then call the setXxx(x)
	 * </p>
	 * 
	 * @see org.hibernate.Session#saveOrUpdate(Object object)
	 */
	@Override
	public void save(E pojo) {
		if (pojo != null) {
			//XXX  not ideal
			super.getSession().saveOrUpdate(pojo);
		}

	}

	@Override
	public int executeUpdate(String hql, Object... values) {
		return createQuery(hql, 0, -1, null,values).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	protected Class<E> getEntityClass() {
		return (Class<E>) ReflexUtil.getSuperClassGenricClass(getClass(), 0);
	}


	
}
