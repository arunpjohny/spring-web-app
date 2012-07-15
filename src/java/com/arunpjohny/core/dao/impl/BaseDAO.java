package com.arunpjohny.core.dao.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arunpjohny.core.dao.DAO;
import com.arunpjohny.core.dao.criteria.SearchCriteria;
import com.arunpjohny.core.dao.criteria.SearchCriteriaResult;

public class BaseDAO implements DAO {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(getClass());

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public <T> T getObject(Class<T> clazz, Serializable id) {
		return entityManager.find(clazz, id);
	}

	@Override
	public <T> T saveObject(T object, Class<T> clazz) {
		return entityManager.merge(object);
	}

	@Override
	public <T> T remove(Class<T> clazz, Serializable id) {
		T object = getObject(clazz, id);
		entityManager.remove(object);
		return object;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> query(SearchCriteria criteria) {
		String sql = criteria.getQuery();
		String[] fields = criteria.getFields();
		Query query = entityManager.createQuery(sql)
				.setFirstResult(criteria.getStart())
				.setMaxResults(criteria.getPageSize());
		Map<String, Object> parameters = criteria.getParameters();
		if (parameters != null) {
			Set<Map.Entry<String, Object>> entrySet = parameters.entrySet();
			for (Map.Entry<String, Object> entry : entrySet) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		List<Object[]> list = query.getResultList();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Object[] object : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < fields.length; i++) {
				map.put(fields[i], object[i]);
			}
			result.add(map);
		}
		return result;
	}

	@Override
	public <T> List<T> query(SearchCriteria criteria, Class<T> clazz) {
		TypedQuery<T> query = entityManager
				.createQuery(criteria.getQuery(), clazz)
				.setFirstResult(criteria.getStart())
				.setMaxResults(criteria.getPageSize());
		Map<String, Object> parameters = criteria.getParameters();
		if (parameters != null) {
			Set<Map.Entry<String, Object>> entrySet = parameters.entrySet();
			for (Map.Entry<String, Object> entry : entrySet) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query.getResultList();
	}

	@Override
	public long getCount(SearchCriteria criteria) {
		Query query = entityManager.createQuery(criteria.getCountQuery());
		Map<String, Object> parameters = criteria.getParameters();
		if (parameters != null) {
			Set<Map.Entry<String, Object>> entrySet = parameters.entrySet();
			for (Map.Entry<String, Object> entry : entrySet) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return (Long) query.getSingleResult();
	}

	@Override
	public <T> SearchCriteriaResult<T> queryResult(SearchCriteria criteria,
			Class<T> clazz) {
		SearchCriteriaResult<T> result = new SearchCriteriaResult<T>();
		result.setRows(query(criteria, clazz));
		long count = getCount(criteria);
		result.setRowCount(count);
		result.setPage((long) Math.ceil((criteria.getStart() + 1)
				/ (float) criteria.getPageSize()));
		result.setPages((long) Math.ceil(count / (float) criteria.getPageSize()));
		return result;
	}

}
