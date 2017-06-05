package de.fom.kp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		// TODO Auto-generated method stub

	}

	@Override
	public Entnahmestelle delete(Integer id) throws DaoException {
		// TODO Auto-generated method stub
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

}
