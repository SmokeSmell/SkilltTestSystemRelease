package com.gong.domain;
public final class ItemInfo {
	private String ItemName;
	private String DAOClassName;
	private String DomainClassName;
	private String TableName;
	private String[] propertyNames;
	private String[] importColNames;
	
	public String[] getImportColNames() {
		return importColNames;
	}
	public void setImportColNames(String[] importColNames) {
		this.importColNames = importColNames;
	}
	public String getItemName() {
		return ItemName;
	}
	public void setItemName(String itemName) {
		ItemName = itemName;
	}
	public String getDAOClassName() {
		return DAOClassName;
	}
	public void setDAOClassName(String dAOClassName) {
		DAOClassName = dAOClassName;
	}
	public String getDomainClassName() {
		return DomainClassName;
	}
	public void setDomainClassName(String domainClassName) {
		DomainClassName = domainClassName;
	}
	public String[] getPropertyNames() {
		return propertyNames;
	}
	public void setPropertyNames(String[] propertyNames) {
		this.propertyNames = propertyNames;
	}
	public String getTableName() {
		return TableName;
	}
	public void setTableName(String tableName) {
		TableName = tableName;
	}
	
	
}

