package de.fom.kp.dao;

import java.security.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import javax.annotation.Resource;
import javax.enterprise.inject.Alternative;
import javax.sql.*;

import org.apache.commons.lang3.*;
import org.apache.commons.validator.routines.EmailValidator;

import de.fom.kp.controller.*;
import de.fom.kp.persistence.*;

@Alternative
public class JdbcPersonDao implements PersonDao {
	
	//@Resource(mappedName="java:comp/env/tomee/kpdatasource")
	private DataSource ds;

	public JdbcPersonDao() {
		
	}
	
	public JdbcPersonDao(DataSource ds) {
		// DB Verbindungen zur Verf¸gung stellen
		this.ds = ds;
	}

	@Override
	public Person read(Integer id) throws DaoException {
		try (Connection c = ds.getConnection()) {
			PreparedStatement pst = c.prepareStatement(
					"select * from kundenportal.person p where p.id = ?");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			Person p = null;
			while(rs.next()) {
				if(p==null){
					p = readPersonFromResultset(rs);
				}
			}
			return p;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(Person person) throws DaoException {
		try (Connection c = ds.getConnection()) {
			PreparedStatement pst = null;
			if (person.getId() == null) {
				final Random r = new SecureRandom();
				byte[] salt = new byte[32];
				r.nextBytes(salt);
				String encodedSalt = Base64.getEncoder().encodeToString(salt);
				pst = c.prepareStatement(
						"INSERT INTO person (Vorname, Nachname, EMail, Geburtstag, Anrede, Straﬂe, HausNr, PLZ, Ort, Land, Adminrechte, Passwort, salt)"
								+ "VALUES (?,?,?,?,?,?,?,?,?,?,?, sha2(?, 512), ?)", Statement.RETURN_GENERATED_KEYS);

				pst.setString(12, person.getPasswort() + encodedSalt);
				pst.setString(13, encodedSalt);
			} else {
				pst = c.prepareStatement(
						"UPDATE person set Vorname=?, Nachname=?, EMail=?, Geburtstag=?, Anrede=?, Straﬂe=?, HausNr=?, PLZ=?, Ort=?, Land=?, Adminrechte=? WHERE (id=?)");
				pst.setInt(12, person.getId());
			}
			pst.setString(1, person.getVorname());
			pst.setString(2, person.getNachname());
			pst.setString(3, person.getEmail());
			pst.setObject(4, person.getGeburtsdatum());
			pst.setString(5, person.getAnrede());
			pst.setString(6, person.getStraﬂe());
			pst.setString(7, person.getHausNr());
			pst.setInt(8, person.getPlz());
			pst.setString(9, person.getOrt());
			pst.setString(10, person.getLand());
			pst.setInt(11, person.isAdminrechte() == true ? 1 : 0);
			
			pst.executeUpdate();
			
			if(person.getId()==null){
				// automatisch generierten Prim‰rschl¸ssel von DB auslesen
				ResultSet rs = pst.getGeneratedKeys();
				rs.next();
				person.setId(rs.getInt(1));
			}
			
			
			
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
	}

	@Override
	public Person delete(Integer id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Person> list() throws DaoException {
		List<Person> list = new ArrayList<>();
		try (Connection c = ds.getConnection()) {
			PreparedStatement pst = c.prepareStatement("select * from kundenportal.person");
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Person p = readPersonFromResultset(rs);
				list.add(p);
			}
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
		return list;
	}

	@Override
	public Person login(String email, String password) throws DaoException {
		try (Connection c = ds.getConnection()) {
			PreparedStatement pst = c.prepareStatement(
					"select * from kundenportal.person p where p.EMail = ? and p.Passwort = sha2(CONCAT(?, salt), 512)");
			pst.setString(1, email);
			pst.setString(2, password);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				Person p = readPersonFromResultset(rs);
				return p;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean updatePassword(String email, String oldpassword, String newpassword) throws DaoException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int checkEmail(String value, Integer id) throws DaoException {
		try(Connection c = ds.getConnection()){
				PreparedStatement pst = c.prepareStatement("select id from kundenportal.person where email = ?");
				pst.setString(1, value);
				ResultSet rs = pst.executeQuery();
				if(rs.next()){
					Integer pid = rs.getInt("id");
					if (id!=null){
						if(pid.equals(id)){
							return 200;
						}else{
							return 406;
						}
					}else{
						return 406;
					}
				}else{
					return 200;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		return 406;
	}

	// ----------- private Hilfsmethoden ----------------------------------
	private Person readPersonFromResultset(ResultSet rs) throws SQLException {
		Person p = new Person();
		p.setId(rs.getInt("id"));
		p.setEmail(rs.getString("EMail"));
		p.setVorname(rs.getString("Vorname"));
		p.setNachname(rs.getString("Nachname"));
		p.setAnrede(rs.getString("Anrede"));
		p.setGeburtsdatum(rs.getDate("Geburtstag"));
		p.setStraﬂe(rs.getString("Straﬂe"));
		p.setHausNr(rs.getString("HausNr"));
		p.setPlz(rs.getInt("PLZ"));
		p.setOrt(rs.getString("Ort"));
		p.setLand(rs.getString("Land"));
		p.setAdminrechte(rs.getInt("Adminrechte") == 1 ? true : false);
		return p;
	}
}