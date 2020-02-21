package com.kronos.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kronos.model.Role;

@Repository
public class RoleService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Role> findAll(){
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			CallableStatement statement = connection.prepareCall("{call searchAllRoles() }");

			// statement.registerOutParameter(1, new );
			ResultSet rs = statement.executeQuery();

			List<Role> result = this.mapRowList(rs);

			statement.close();
			connection.close();
			return result;

		} catch (Exception e) {
			System.out.println("\n\n\n\n\n\n\n\nERROR\n\n\n\n\n" + e.getMessage());
		}

		return null;
	}

	private List<Role> mapRowList(ResultSet rs) throws SQLException {

		List<Role> result = new ArrayList<>();
		while (rs.next()) {
			Role t = new Role();
			t.setName(rs.getString("NAME"));
			t.setId(Character.getNumericValue(rs.getString("ID").charAt(0)));
			result.add(t);
		}

		return result;

	}
	
	
}
