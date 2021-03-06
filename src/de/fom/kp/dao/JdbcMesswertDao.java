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
import de.fom.kp.persistence.Messwert;

public class JdbcMesswertDao extends JdbcDao implements MesswertDao {

	public JdbcMesswertDao(DataSource ds) {
		super(ds);
	}

	public JdbcMesswertDao() {
	}

	@Override
	public Messwert read(Integer id) throws DaoException {
		try (Connection c = ds.getConnection()) {
			PreparedStatement pst = c.prepareStatement(
					"SELECT * from kundenportal.messwerte m WHERE m.id = ?");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			Messwert m = null;
			while(rs.next()) {
				if(m==null){
					m = readMesswertFromResultset(rs);
				}
			}
			return m;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(Messwert m) throws DaoException {
		try (Connection c = ds.getConnection()) {
			PreparedStatement pst = null;
			
			if (m.getId() == null) {
				pst = c.prepareStatement("INSERT INTO kundenportal.messwerte VALUES(NULL, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				pst.setInt(1, m.getZaehlerId());
				pst.setObject(2, m.getAblesedatum());
				pst.setDouble(3, m.getMesswert());
			} 
			
			else {
				pst = c.prepareStatement(
						"UPDATE kundenportal.messwerte set Messwert=?, Ablesedatum=? WHERE ID=?");
				pst.setDouble(1, m.getMesswert());
				pst.setObject(2, m.getAblesedatum());
				pst.setInt(3, m.getId());
			}
			
			pst.executeUpdate();			
			if(m.getId()==null){
				// automatisch generierten Primärschlüssel von DB auslesen
				ResultSet rs = pst.getGeneratedKeys();
				rs.next();
				m.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}

	}

	@Override
	public Messwert delete(Integer id) throws DaoException {
		try (Connection c = ds.getConnection()) {
			PreparedStatement pst = null;
			if (id != null) {
				pst = c.prepareStatement("DELETE FROM kundenportal.messwerte WHERE ID=?");
				pst.setInt(1, id);
				pst.executeUpdate();
			}
			} catch (SQLException error) {
				throw new DaoException(error.getMessage(), error);
			}
		return null;
	}

	@Override
	public List<Messwert> list() throws DaoException {
		List<Messwert> list = new ArrayList<>();
		try (Connection c = ds.getConnection()) {
			PreparedStatement pst = c.prepareStatement("SELECT * from kundenportal.messwerte");
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Messwert m = readMesswertFromResultset(rs);
				list.add(m);
			}
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
		return list;
	}

	@Override
	public List<Messwert> listByZaehler(Integer zId) throws DaoException {
		List<Messwert> list = new ArrayList<>();
		try (Connection c = ds.getConnection()) {
			PreparedStatement pst = c.prepareStatement("SELECT * from kundenportal.messwerte m WHERE m.ZaehlerID = ?");
			pst.setInt(1, zId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Messwert m = readMesswertFromResultset(rs);
				list.add(m);
			}
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
		return list;
	}
	
	// ----------- private Hilfsmethoden ----------------------------------
		private Messwert readMesswertFromResultset(ResultSet rs) throws SQLException {
			Messwert m = new Messwert();
			m.setId(rs.getInt("ID"));
			m.setZaehlerId(rs.getInt("ZaehlerID"));
			m.setAblesedatum(rs.getDate("Ablesedatum"));
			m.setMesswert(rs.getDouble("Messwert"));
			return m;
		}
}
