package com.arunpjohny.core.service.impl;


import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.arunpjohny.core.dao.DAO;
import com.arunpjohny.core.dao.criteria.SearchCriteria;
import com.arunpjohny.core.dao.criteria.SearchCriteriaResult;
import com.arunpjohny.core.exception.client.ResourceNotFoundException;
import com.arunpjohny.core.service.Manager;

@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class BaseManager implements Manager {

	protected DAO dao;

	public void setDao(DAO dao) {
		this.dao = dao;
	}

	@Override
	public <T> T getObject(Class<T> clazz, Serializable id) {
		return dao.getObject(clazz, id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public <T> T saveObject(T object, Class<T> clazz) {
		return dao.saveObject(object, clazz);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public <T> T remove(Class<T> clazz, Serializable id) {
		return dao.remove(clazz, id);
	}

	@Override
	public <T> byte[] getBytes(Class<T> clazz, Serializable id, String field)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		T object = getObject(clazz, id);
		if (object == null) {
			throw new ResourceNotFoundException("Unable to find the resource "
					+ clazz + " with id " + id + ".");
		}
		Field f = ReflectionUtils.findField(clazz, field);
		if (f == null) {
			throw new ResourceNotFoundException("Unable to find field" + f
					+ " from " + clazz + ".");
		}
		f.setAccessible(true);
		return (byte[]) f.get(object);
	}

	@Override
	public <T> List<T> query(SearchCriteria criteria, Class<T> clazz) {
		return dao.query(criteria, clazz);
	}

	@Override
	public List<Map<String, Object>> query(SearchCriteria criteria) {
		return dao.query(criteria);
	}

	@Override
	public long getCount(SearchCriteria criteria) {
		return dao.getCount(criteria);
	}

	@Override
	public <T> SearchCriteriaResult<T> queryResult(SearchCriteria criteria,
			Class<T> clazz) {
		return dao.queryResult(criteria, clazz);
	}
}
