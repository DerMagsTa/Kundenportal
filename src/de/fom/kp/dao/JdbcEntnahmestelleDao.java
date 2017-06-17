package de.fom.kp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import de.fom.kp.controller.DaoException;
import de.fom.kp.persistence.Entnahmestelle;

public class JdbcEntnahmestelleDao extends JdbcDao implements EntnahmestelleDao {
	
	public JdbcEntnahmestelleDao() {
		super();
	}

	public JdbcEntnahmestelleDao(DataSource ds) {
		super(ds);
	}

	@Override
	public Entnahmestelle read(Integer id) throws DaoException {
		try (Connection c = ds.getConnection()) {
			PreparedStatement pst = c.prepareStatement(
					"select * from kundenportal.entnahmestelle e where e.id = ?");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			Entnahmestelle e = null;
			while(rs.next()) {
				if(e==null){
					e = readEntnahmestelleFromResultset(rs);
				}
			}
			return e;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(Entnahmestelle e) throws DaoException {
		try (Connection c = ds.getConnection()) {
			PreparedStatement pst = null;
			
			if (e.getId() == null) {
				pst = c.prepareStatement("INSERT INTO kundenportal.entnahmestelle VALUES(NULL, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				pst.setInt(1, e.getPersonId());
				pst.setString(2, e.getStraﬂe());
				pst.setString(3, e.getHausNr());
				pst.setInt(4, e.getPlz());
				pst.setString(5, e.getOrt());
				pst.setString(6, e.getLand());
				pst.setString(7, e.getHinweis());
			} 
			else {
				pst = c.prepareStatement(
						"UPDATE kundenportal.entnahmestelle set PersonId=?, Straﬂe=?, HausNr=?, Plz=?, Ort=?, Land=?, Hinweis=? WHERE (ID=?)");
				pst.setInt(1, e.getPersonId());
				pst.setString(2, e.getStraﬂe());
				pst.setString(3, e.getHausNr());
				pst.setInt(4, e.getPlz());
				pst.setString(5, e.getOrt());
				pst.setString(6, e.getLand());
				pst.setString(7, e.getHinweis());
				pst.setInt(8, e.getId());
			} 
			pst.executeUpdate();	
			if(e.getId()==null){
				// automatisch generierten Prim‰rschl¸ssel von DB auslesen
				ResultSet rs = pst.getGeneratedKeys();
				rs.next();
				e.setId(rs.getInt(1));
			}
		} catch (SQLException error) {
			throw new DaoException(error.getMessage(), error);
		}
	}

	@Override
	public Entnahmestelle delete(Integer id) throws DaoException {
		try (Connection c = ds.getConnection()) {
			PreparedStatement pst = null;
			if (id != null) {
				pst = c.prepareStatement("DELETE FROM kundenportal.entnahmestelle WHERE(ID=?)");
				pst.setInt(1, id);
				pst.executeUpdate();
			}
			} catch (SQLException error) {
				throw new DaoException(error.getMessage(), error);
			}
		return null;
	}

	@Override
	public List<Entnahmestelle> list() throws DaoException {
		List<Entnahmestelle> list = new ArrayList<>();
		try (Connection c = ds.getConnection()) {
			PreparedStatement pst = c.prepareStatement("select * from kundenportal.entnahmestelle");
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Entnahmestelle e = readEntnahmestelleFromResultset(rs);
				list.add(e);
			}
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
		return list;
	}

	@Override
	public List<Entnahmestelle> listByPerson(Integer id) throws DaoException {
		List<Entnahmestelle> list = new ArrayList<>();
		try (Connection c = ds.getConnection()) {
			PreparedStatement pst = c.prepareStatement("select * from kundenportal.entnahmestelle e Where e.PersonId = ?");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Entnahmestelle e = readEntnahmestelleFromResultset(rs);
				list.add(e);
			}
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
		return list;
	}
	
	// ----------- private Hilfsmethoden ----------------------------------
	private Entnahmestelle readEntnahmestelleFromResultset(ResultSet rs) throws SQLException {
		Entnahmestelle e = new Entnahmestelle();
		e.setId(rs.getInt("ID"));
		e.setPersonId(rs.getInt("PersonId"));
		e.setStraﬂe(rs.getString("Straﬂe"));
		e.setHausNr(rs.getString("HausNr"));
		e.setPlz(rs.getInt("PLZ"));
		e.setOrt(rs.getString("Ort"));
		e.setLand(rs.getString("Land"));
		e.setHinweis(rs.getString("Hinweis"));
		return e;
	}

	@Override
	public List<Entnahmestelle> listByPerson() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

}
