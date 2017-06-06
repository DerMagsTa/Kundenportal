package de.fom.kp.dao;

import java.util.List;

import de.fom.kp.controller.DaoException;
import de.fom.kp.persistence.Entnahmestelle;

public interface EntnahmestelleDao {
	
	public Entnahmestelle read(Integer id) throws DaoException;
	public void save(Entnahmestelle e) throws DaoException;
	public Entnahmestelle delete(Integer id) throws DaoException;
	public List<Entnahmestelle> list() throws DaoException;
	public List<Entnahmestelle> listByPerson(Integer id) throws DaoException;
	public List<Entnahmestelle> listByPerson() throws DaoException;
	
}
