package de.fom.kp.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import de.fom.kp.controller.DaoException;
import de.fom.kp.persistence.Entnahmestelle;

public class JdbcEntnahmestelleDao extends JdbcDao implements EntnahmestelleDao {
	
	public JdbcEntnahmestelleDao() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JdbcEntnahmestelleDao(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Entnahmestelle read(Integer id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Entnahmestelle e) throws DaoException {
		// TODO Auto-generated method stub

	}

	@Override
	public Entnahmestelle delete(Integer id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Entnahmestelle> list() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Entnahmestelle> listByPerson() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}
	

}
