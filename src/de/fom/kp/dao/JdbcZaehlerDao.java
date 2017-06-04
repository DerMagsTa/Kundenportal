package de.fom.kp.dao;

import java.util.List;

import javax.sql.DataSource;

import de.fom.kp.controller.DaoException;
import de.fom.kp.persistence.Zaehler;

public class JdbcZaehlerDao extends JdbcDao implements ZaehlerDao {

	public JdbcZaehlerDao(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	public JdbcZaehlerDao() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Zaehler read(Integer id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Zaehler e) throws DaoException {
		// TODO Auto-generated method stub

	}

	@Override
	public Zaehler delete(Integer id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Zaehler> list() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Zaehler> listByEStelle(Integer eId) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

}
