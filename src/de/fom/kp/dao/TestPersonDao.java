package de.fom.kp.dao;

import java.sql.*;
import java.text.*;
import java.util.*;

import javax.enterprise.inject.Alternative;

import de.fom.kp.persistence.*;


/*@Singleton
@Named
@Alternative*/
@Alternative
public class TestPersonDao implements PersonDao {

	private Map<Integer, Person> map = new Hashtable<Integer, Person>();
	private int idcounter=4;
	
	public TestPersonDao(){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			map.put(1, new Person(1, "test@test.de", "Willi", "Wutz", sdf.parse("2000-01-01")));
			map.put(2, new Person(2, "a@bc.de", "Erna", "Meier", sdf.parse("1900-01-01")));
			map.put(3, new Person(3, "x@yz.de", "Lutz", "Mustermann", sdf.parse("1996-02-29")));
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
