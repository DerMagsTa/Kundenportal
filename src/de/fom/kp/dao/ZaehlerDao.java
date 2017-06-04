package de.fom.kp.dao;

import java.util.List;

import de.fom.kp.controller.DaoException;
import de.fom.kp.persistence.Zaehler;


public interface ZaehlerDao {

	public Zaehler read(Integer id) throws DaoException;
	public void save(Zaehler e) throws DaoException;
	public Zaehler delete(Integer id) throws DaoException;
	public List<Zaehler> list() throws DaoException;
	public List<Zaehler> listByEStelle(Integer eId) throws DaoException;
}
