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
	
	@Resource(mappedName="java:comp/env/tomee/wpdatasource")
	private DataSource ds;

	public JdbcPersonDao() {
		
	}
	
	public JdbcPersonDao(DataSource ds) {
		// DB Verbindungen zur Verfügung stellen
		this.ds = ds;
	}

	@Override
	public Person read(Integer id) throws DaoException {
		try (Connection c = ds.getConnection()) {
			//!TODO SQL
			PreparedStatement pst = c.prepareStatement("select pi.interest_fid as interestid, p.* from wp.person p left join wp.person_interest pi on pi.person_fid = p.id where p.id = ?");
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
				byte[] pass = new byte[8];
				r.nextBytes(salt);
				r.nextBytes(pass);
				String encodedSalt = Base64.getEncoder().encodeToString(salt);
				String password = Base64.getEncoder().encodeToString(pass);
				//!TODO SQL Text und pst Nummern
				pst = c.prepareStatement(
						"INSERT INTO person (firstname, lastname, email, birthday, gender, height, company_fid, comment, newsletter"
								+ ", passphrase, passphrase_md5, passphrase_sha2, passphrase_sha2_salted, salt) VALUES (?,?,?,?,?,?,?,?,?,?, md5(?), sha2(?, 512), sha2(?, 512), ?)", Statement.RETURN_GENERATED_KEYS);
				pst.setString(2, password);
				pst.setString(11, password);
				pst.setString(12, password);
				pst.setString(13, password + encodedSalt);
				pst.setString(14, encodedSalt);
			} else {
				//!TODO SQL Text und pst Nummern
				pst = c.prepareStatement(
						"UPDATE person set firstname=?, lastname=?, email=?, birthday=?, gender=?, height=?, company_fid=?, comment=?, newsletter=? WHERE (id=?)");
				pst.setInt(13, person.getId());
			}
			pst.setString(1, person.getEmail());
			
			pst.setString(3, person.getVorname());
			pst.setString(4, person.getNachname());
			pst.setDate(5, (Date) person.getGeburtsdatum());
			pst.setString(6, person.getAnrede());
			
			pst.setString(7, person.getStraße());
			pst.setString(8, person.getHausNr());
			pst.setInt(9, person.getPlz());
			pst.setString(10, person.getOrt());
			pst.setString(11, person.getLand());
			
			pst.setInt(12, person.isAdminrechte() ? 1 : 0);
			
			pst.executeUpdate();
			
			if(person.getId()==null){
				// automatisch generierten Primärschlüssel von DB auslesen
				ResultSet rs = pst.getGeneratedKeys();
				rs.next();
				person.setId(rs.getInt(1));
			}
			
			PreparedStatement pd = c.prepareStatement("delete from person_interest where person_fid = ?");
			pd.setInt(1, person.getId());
			pd.executeUpdate();	
			
			
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
			PreparedStatement pst = c.prepareStatement("select * from wp.person");
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
					"select * from wp.person  p where p.email = ? and p.passphrase_sha2_salted = sha2(CONCAT(?, salt), 512)");
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
		EmailValidator v = EmailValidator.getInstance();
		if(v.isValid(value)){
			try(Connection c = ds.getConnection()){
				PreparedStatement pst = c.prepareStatement("select id from person where email = ?");
				pst.setString(1, value);
				ResultSet rs = pst.executeQuery();
				if(rs.next()){
					Integer pid = rs.getInt("id");
					if(pid.equals(id)){
						return 200;
					}else{
						return 406;
					}
				}else{
					return 200;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			return 406;
		}
		return 404;
	}

	// ----------- private Hilfsmethoden ----------------------------------
	private Person readPersonFromResultset(ResultSet rs) throws SQLException {
		Person p = new Person();
		p.setId(rs.getInt("id"));
		p.setEmail(rs.getString("email"));
		p.setVorname(rs.getString("vorname"));
		p.setNachname(rs.getString("nachname"));
		p.setAnrede(rs.getString("anrede"));
		p.setGeburtsdatum(rs.getDate("geburtsdatum"));
		p.setStraße(rs.getString("straße"));
		p.setHausNr(rs.getString("hausNr"));
		p.setPlz(rs.getInt("plz"));
		p.setOrt(rs.getString("ort"));
		p.setLand(rs.getString("land"));
		p.setAdminrechte(rs.getInt("adminrechte") == 1 ? true : false);
		return p;
	}
}