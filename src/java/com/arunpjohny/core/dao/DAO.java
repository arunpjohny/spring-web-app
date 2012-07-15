package com.arunpjohny.core.dao;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.arunpjohny.core.dao.criteria.SearchCriteria;
import com.arunpjohny.core.dao.criteria.SearchCriteriaResult;

public interface DAO {
	<T> T getObject(Class<T> clazz, Serializable id);

	<T> T saveObject(T object, Class<T> clazz);

	<T> T remove(Class<T> clazz, Serializable id);

	<T> List<T> query(SearchCriteria criteria, Class<T> clazz);

	List<Map<String, Object>> query(SearchCriteria criteria);

	long getCount(SearchCriteria criteria);

	<T> SearchCriteriaResult<T> queryResult(SearchCriteria criteria,
			Class<T> clazz);
}
