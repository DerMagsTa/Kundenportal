package de.fom.kp.dao;

import java.util.*;

import de.fom.kp.controller.*;
import de.fom.kp.persistence.*;

public interface PersonDao {
	
	public Person read(Integer id) throws DaoException;
	public void save(Person p) throws DaoException;
	public Person delete(Integer id) throws DaoException;
	public List<Person> list() throws DaoException;
	public Person login(String email, String password) throws DaoException;
	public boolean updatePassword(String email, String oldpassword, String newpassword) throws DaoException;
	public int checkEmail(String value, Integer id) throws DaoException;
	
}
