package de.fom.kp.dao;

import java.security.*;
import java.sql.*;
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
			PreparedStatement pst = c.prepareStatement("select pi.interest_fid as interestid, p.* from wp.person p left join wp.person_interest pi on pi.person_fid = p.id where p.id = ?");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			Person p = null;
			while(rs.next()) {
				if(p==null){
					p = readPersonFromResultset(rs);
				}
				Integer interestid = rs.getInt("interestid");
				if(!rs.wasNull()){
					p.getInterests().add(interestid);
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
				pst = c.prepareStatement(
						"INSERT INTO person (firstname, lastname, email, birthday, gender, height, company_fid, comment, newsletter"
								+ ", passphrase, passphrase_md5, passphrase_sha2, passphrase_sha2_salted, salt) VALUES (?,?,?,?,?,?,?,?,?,?, md5(?), sha2(?, 512), sha2(?, 512), ?)", Statement.RETURN_GENERATED_KEYS);
				pst.setString(10, password);
				pst.setString(11, password);
				pst.setString(12, password);
				pst.setString(13, password + encodedSalt);
				pst.setString(14, encodedSalt);
			} else {
				pst = c.prepareStatement(
						"UPDATE person set firstname=?, lastname=?, email=?, birthday=?, gender=?, height=?, company_fid=?, comment=?, newsletter=? WHERE (id=?)");
				pst.setInt(10, person.getId());
			}
			pst.setString(1, person.getFirstname());
			pst.setString(2, person.getLastname());
			pst.setString(3, person.getEmail());
			pst.setObject(4, person.getBirthday());
			pst.setString(5, person.getGender().name());
			if (person.getHeight() != null) {
				pst.setDouble(6, person.getHeight());
			} else {
				pst.setNull(6, Types.INTEGER);
			}
			if (person.getCompanyid() != null) {
				pst.setInt(7, person.getCompanyid());
			} else {
				pst.setNull(7, Types.INTEGER);
			}
			pst.setString(8, person.getComment());
			pst.setInt(9, person.isNewsletter() ? 1 : 0);
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
			
			PreparedStatement pi = c.prepareStatement("insert into person_interest "
					+ "(interest_fid, person_fid) values (?,?)");
			pi.setInt(2, person.getId());
			for (Integer i : person.getInterests()) {
				pi.setInt(1, i);
				pi.executeUpdate();
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
		String g = rs.getString("gender");
		if (StringUtils.isNotBlank(g)) {
			p.setGender(Gender.valueOf(g));
		}
		p.setFirstname(rs.getString("firstname"));
		p.setLastname(rs.getString("lastname"));
		p.setEmail(rs.getString("email"));
		p.setBirthday(rs.getDate("birthday"));
		p.setHeight(rs.getDouble("height"));
		if (rs.wasNull()) {
			p.setHeight(null);
		}
		p.setCompanyid(rs.getInt("company_fid"));
		if (rs.wasNull()) {
			p.setCompanyid(null);
		}
		p.setComment(rs.getString("comment"));
		p.setNewsletter(rs.getInt("newsletter") == 1 ? true : false);
		return p;
	}
}