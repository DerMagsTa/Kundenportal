package de.fom.kp.dao;

import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

import javax.enterprise.inject.Alternative;

import de.fom.kp.persistence.*;


@Alternative
public class TestPersonDao implements PersonDao {

	private Map<Integer, Person> map = new Hashtable<Integer, Person>();
	private int idcounter=4;
	
	public TestPersonDao(){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			map.put(1, new Person(1, "test@test.de", "test", "Markus", "Lanzrath", "Herr", sdf.parse("1993-15-01"),
					"Teststr.", "1a", 53359, "Testort", "testland", true));
			map.put(1, new Person(2, "test2@test.de", "test", "Markus2", "Lanzrath2", "Herr", sdf.parse("1993-15-02"),
					"Teststr.", "2a", 53359, "Testort", "testland", false));
			map.put(1, new Person(3, "test3@test.de", "test", "Markus3", "Lanzrath3", "Herr", sdf.parse("1993-15-03"),
					"Teststr.", "3a", 53359, "Testort", "testland", false));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Person login(String email, String password) {
		if("geheim".equals(password)){
			return map.get(1);
		}else{
			return null;
		}
	}

	@Override
	public Person read(Integer id) {
		return map.get(id);
	}

	@Override
	public void save(Person person) {
		if(person.getId()==null){
			person.setId(idcounter++);
		}
		map.put(person.getId(), person);
	}

	@Override
	public Person delete(Integer id) {
		Person p = map.remove(id);
		return p;
	}

	@Override
	public List<Person> list() {
		List<Person> list = new ArrayList<>();
		list.addAll(map.values());
		return list;
	}

	
	public void shutdown() {
		// ggf. Personen persistent sichern und im Konstruktor laden
	}

	@Override
	public boolean updatePassword(String email, String oldpassword, String newpassword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int checkEmail(String value, Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	

}
