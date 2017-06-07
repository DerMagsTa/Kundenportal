package de.fom.kp.dao;

import java.util.List;

import de.fom.kp.controller.DaoException;
import de.fom.kp.persistence.Entnahmestelle;

public class TestEntnahmestelleDao implements EntnahmestelleDao {

	@Override
	public Entnahmestelle read(Integer id) throws DaoException {
		// TODO Auto-generated method stub
		Entnahmestelle e = new Entnahmestelle();
		e.setId(id);
		e.setStraﬂe("Teststraﬂe");
		e.setHausNr("1a");
		e.setPlz(53562);
		e.setOrt("St.Katharinen");
		e.setPersonId(1);
		e.setLand("DE");
		return e;
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

	@Override
	public List<Entnahmestelle> listByPerson(Integer id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

}
