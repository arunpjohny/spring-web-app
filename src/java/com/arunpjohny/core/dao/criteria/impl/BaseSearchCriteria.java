package com.arunpjohny.core.dao.criteria.impl;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.arunpjohny.core.dao.criteria.SearchCriteria;

public abstract class BaseSearchCriteria implements SearchCriteria {
	protected final Map<String, String> columnMap = new HashMap<String, String>();

	private final Map<String, Object> parameters = new HashMap<String, Object>();

	protected String[] fields;

	protected String sort;

	protected String dir = "ASC";

	protected int pageSize;

	protected int start;

	protected String defaultSort;

	private boolean parameInitialized;

	public BaseSearchCriteria() {
		addColumns(columnMap);
	}

	public BaseSearchCriteria(String[] fields) {
		this();
		this.fields = fields;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize > 0 ? pageSize : 10;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public String getSort() {
		return StringUtils.isBlank(sort) ? defaultSort : sort;
	}

	public void setSort(String sort) {
		this.sort = columnMap.get(sort);
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = StringUtils.equalsIgnoreCase("desc", dir) ? "desc" : "asc";
	}

	public String[] getFields() {
		return fields;
	}

	public String getQuery() {
		return getSql();
	}

	protected String getSql() {
		return getSelectSql() + " " + getBaseSql() + " " + getSorting();
	}

	protected String getSorting() {
		return StringUtils.isBlank(getSort()) ? "" : " order by " + getSort()
				+ " " + getDir();
	}

	public String getCountQuery() {
		return getCountSql();
	}

	protected String getCountSql() {
		return "select count(*) " + getBaseSql();
	}

	public Map<String, Object> getParameters() {
		if (!parameInitialized) {
			parameInitialized = true;
			addParameters(parameters);
		}
		return parameters;
	}

	public int getStart() {
		return start;
	}

	public int getPageSize() {
		return pageSize;
	}

	public String getDefaultSort() {
		return defaultSort;
	}

	public void setDefaultSort(String defaultSort) {
		this.defaultSort = columnMap.get(defaultSort);
	}

	protected abstract String getSelectSql();

	protected abstract String getBaseSql();

	protected abstract void addColumns(Map<String, String> columnMap);

	protected abstract void addParameters(Map<String, Object> parameters);
}