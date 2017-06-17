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
import de.fom.kp.persistence.Zaehler;

public class JdbcZaehlerDao extends JdbcDao implements ZaehlerDao {

	public JdbcZaehlerDao(DataSource ds) {
		super(ds);
	}

	public JdbcZaehlerDao() {
	}

	@Override
	public Zaehler read(Integer id) throws DaoException {
		try (Connection c = ds.getConnection()) {
			PreparedStatement pst = c.prepareStatement(
					"select * from kundenportal.zaehler z where z.id = ?");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			Zaehler z = null;
			while(rs.next()) {
				if(z==null){
					z = readZaehlerFromResultset(rs);
				}
			}
			return z;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(Zaehler z) throws DaoException {
		try (Connection c = ds.getConnection()) {
			PreparedStatement pst = null;
			
			if (z.getId() == null) {
				pst = c.prepareStatement("INSERT INTO kundenportal.zaehler VALUES(NULL, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				pst.setInt(1,  z.getEntnahmestelleId());
				pst.setString(2, z.getEnergieArt());
				pst.setString(3, z.getZaehlerNr());
			}				
			else {
				pst = c.prepareStatement(
						"UPDATE kundenportal.zaehler set EntnahmestellenID=?, Energieart=?, ZaehlerNr=? WHERE (ID=?)");
				pst.setInt(1,  z.getEntnahmestelleId());
				pst.setString(2, z.getEnergieArt());
				pst.setString(3, z.getZaehlerNr());
				pst.setInt(4,  z.getId());
			} 
			pst.executeUpdate();
			if(z.getId()==null){
				// automatisch generierten Primärschlüssel von DB auslesen
				ResultSet rs = pst.getGeneratedKeys();
				rs.next();
				z.setId(rs.getInt(1));
			}
		} catch (SQLException error) {
			throw new DaoException(error.getMessage(), error);
		}
	}

	@Override
	public Zaehler delete(Integer id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Zaehler> list() throws DaoException {
		List<Zaehler> list = new ArrayList<>();
		try (Connection c = ds.getConnection()) {
			PreparedStatement pst = c.prepareStatement("select * from kundenportal.zaehler");
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Zaehler z = readZaehlerFromResultset(rs);
				list.add(z);
			}
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
		return list;
	}

	@Override
	public List<Zaehler> listByEStelle(Integer eId) throws DaoException {
		List<Zaehler> list = new ArrayList<>();
		try (Connection c = ds.getConnection()) {
			PreparedStatement pst = c.prepareStatement("select * from kundenportal.zaehler z where z.EntnahmestellenID = ?");
			pst.setInt(1, eId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Zaehler z = readZaehlerFromResultset(rs);
				list.add(z);
			}
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
		return list;
	}
	
	// ----------- private Hilfsmethoden ----------------------------------
		private Zaehler readZaehlerFromResultset(ResultSet rs) throws SQLException {
			Zaehler z = new Zaehler();
			z.setId(rs.getInt("ID"));
			z.setEntnahmestelleId(rs.getInt("EntnahmestellenID"));
			z.setEnergieArt(rs.getString("Energieart"));
			z.setZaehlerNr(rs.getString("ZaehlerNr"));
			return z;
		}

}
