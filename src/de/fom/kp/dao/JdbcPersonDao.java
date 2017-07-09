package de.fom.kp.dao;

import java.security.*;
import java.sql.*;
import java.util.*;

import javax.enterprise.inject.Alternative;
import javax.sql.*;

import de.fom.kp.controller.*;
import de.fom.kp.persistence.*;

@Alternative
public class JdbcPersonDao extends JdbcDao implements PersonDao {
	

	public JdbcPersonDao() {
		super();
	}
	
	public JdbcPersonDao(DataSource ds) {
		// DB Verbindungen zur Verf¸gung stellen
		super(ds);
	}

	@Override
	public Person read(Integer id) throws DaoException {
		try (Connection c = ds.getConnection()) {
			PreparedStatement pst = c.prepareStatement(
					"SELECT * from kundenportal.person p WHERE p.id = ?");
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
						"UPDATE person set Vorname=?, Nachname=?, EMail=?, Geburtstag=?, Anrede=?, Straﬂe=?, HausNr=?, PLZ=?, Ort=?, Land=? WHERE id=?");
				pst.setInt(11, person.getId());
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
		return null;
	}

	@Override
	public List<Person> list() throws DaoException {
		List<Person> list = new ArrayList<>();
		try (Connection c = ds.getConnection()) {
			PreparedStatement pst = c.prepareStatement("SELECT * from kundenportal.person");
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
					"SELECT * from kundenportal.person p where p.EMail = ? and p.Passwort = sha2(CONCAT(?, salt), 512)");
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
	public boolean updatePassword(Integer id, String Passwort_alt, String Passwort_neu) throws DaoException {
		try (Connection c = ds.getConnection()) {
			PreparedStatement pst = c.prepareStatement(
					"SELECT * from kundenportal.person p WHERE p.ID = ? and p.Passwort = sha2(CONCAT(?, salt), 512)");
			pst.setInt(1, id);
			pst.setString(2, Passwort_alt);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				final Random r = new SecureRandom();
				byte[] salt = new byte[32];
				r.nextBytes(salt);
				String encodedSalt = Base64.getEncoder().encodeToString(salt);
				pst = c.prepareStatement("UPDATE Person set Passwort=sha2(?, 512), Salt=? WHERE id=?");
				pst.setString(1, Passwort_neu + encodedSalt);
				pst.setString(2, encodedSalt);
				pst.setInt(3, id);
				pst.executeUpdate();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}	

	@Override
	public int checkEmail(String value, Integer id) throws DaoException {
		try(Connection c = ds.getConnection()){
				PreparedStatement pst = c.prepareStatement("SELECT id from kundenportal.person WHERE email = ?");
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
		return p;
	}
}