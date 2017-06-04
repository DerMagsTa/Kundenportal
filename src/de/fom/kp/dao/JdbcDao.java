package de.fom.kp.dao;

import javax.annotation.Resource;
import javax.sql.DataSource;

public abstract class JdbcDao {
	@Resource(mappedName="java:comp/env/tomee/wpdatasource")
	private DataSource ds;
	
	public JdbcDao(DataSource ds) {
		this.ds = ds;
	}
	
	public JdbcDao(){
		
	}
	
}
