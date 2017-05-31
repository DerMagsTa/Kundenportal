package de.fom.kp.dao;

import java.sql.*;
import java.util.*;

import javax.sql.*;

import org.apache.commons.lang3.*;

import de.fom.kp.persistence.*;

public class JdbcMasterDataDao implements MasterDataDao {

	private DataSource ds;

	public JdbcMasterDataDao(DataSource ds) {
		// DB Verbindungen zur Verfügung stellen
		this.ds = ds;
	}

	@Override
	public List<Company> getAllCompanies() {
		List<Company> list = new ArrayList<Company>();
		try (Connection c = ds.getConnection()) {
			ResultSet rs = c.createStatement().executeQuery("select c.* from company c order by c.name");
			while (rs.next()) {
				list.add(new Company(rs.getInt("id"), rs.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Company> searchCompany(String query) {
		if (StringUtils.isNotBlank(query)) {
			List<Company> list = new ArrayList<Company>();
			try (Connection c = ds.getConnection()) {
				PreparedStatement pst = c
						.prepareStatement("select c.* from company c where lower(name) like ? order by c.name");
				pst.setString(1, "%" + query.toLowerCase() + "%");
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					list.add(new Company(rs.getInt("id"), rs.getString("name")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return list;
		} else {
			return this.getAllCompanies();
		}
	}

	@Override
	public List<Interest> getAllInterests() {
		List<Interest> list = new ArrayList<Interest>();
		try (Connection c = ds.getConnection()) {
			ResultSet rs = c.createStatement().executeQuery("select i.* from interest i order by i.name");
			while (rs.next()) {
				list.add(new Interest(rs.getInt("id"), rs.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void save(Company company) {
		try (Connection c = ds.getConnection()) {
			PreparedStatement pst = c.prepareStatement("insert into company (name) values (?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, company.getName());
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			rs.next();
			company.setId(rs.getInt(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
