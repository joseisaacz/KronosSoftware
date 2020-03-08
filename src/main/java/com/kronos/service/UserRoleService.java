package com.kronos.service;

import java.sql.CallableStatement;
import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kronos.model.Role;
import com.kronos.model.User;

@Repository
public class UserRoleService {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public void insertUserRole(User user, Role role) throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
        CallableStatement statement = connection.prepareCall("{call insertUserRole(?, ?)}");
        statement.setString(1, user.getTempUser().getEmail());
        statement.setString(2, role.getName());
        statement.executeUpdate();
        statement.close();
	}
}
