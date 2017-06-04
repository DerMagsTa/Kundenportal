package de.fom.kp.dao;

import javax.annotation.Resource;
import javax.sql.DataSource;

public abstract class JdbcDao {
	@Resource(mappedName="java:comp/env/tomee/wpdatasource")
	protected DataSource ds;
	
	public JdbcDao(DataSource ds) {
		this.ds = ds;
	}
	
	public JdbcDao(){
		
	}

	public DataSource getDs() {
		return ds;
	}

	public void setDs(DataSource ds) {
		this.ds = ds;
	}
	
	
	
}
