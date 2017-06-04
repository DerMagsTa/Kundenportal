package de.fom.kp.dao;

import java.util.List;

import de.fom.kp.controller.DaoException;
import de.fom.kp.persistence.Zaehler;

public class TestZaehlerDao implements ZaehlerDao {

	@Override
	public Zaehler read(Integer id) throws DaoException {
		// TODO Auto-generated method stub
		Zaehler z =  new Zaehler();
		z.setId(id);
		z.setEnergieArt("1");
		z.setEntnahmestelleId(1);
		z.setZaehlerNr("Z001234-7");
		return z;
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
