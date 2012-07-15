package com.arunpjohny.core.dao.criteria;

import java.util.Map;

public interface SearchCriteria {

	String getQuery();

	String getCountQuery();

	String[] getFields();

	Map<String, Object> getParameters();

	String getSort();

	String getDir();

	int getStart();

	int getPageSize();

	void setStart(int start);

	void setPageSize(int pageSize);
}