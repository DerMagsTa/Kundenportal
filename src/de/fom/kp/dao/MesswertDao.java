package de.fom.kp.dao;

import java.util.List;
import de.fom.kp.controller.DaoException;
import de.fom.kp.persistence.Messwert;


public interface MesswertDao {

	public Messwert read(Integer id) throws DaoException;
	public void save(Messwert m) throws DaoException;
	public Messwert delete(Integer id) throws DaoException;
	public List<Messwert> list() throws DaoException;
	public List<Messwert> listByZaehler(Integer zaehlerId) throws DaoException;
}
