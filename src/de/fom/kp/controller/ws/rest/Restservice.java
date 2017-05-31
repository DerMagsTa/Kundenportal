package de.fom.kp.controller.ws.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.fom.kp.controller.DaoException;
import de.fom.kp.dao.PersonDao;
import de.fom.kp.dao.TestPersonDao;
import de.fom.kp.persistence.Person;

public class Restservice {

	@Inject
	PersonDao dao;
	
	@GET
	@Path("/personsearch")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Person> personSearch() throws DaoException{
		return dao.list();
	}
	
}
