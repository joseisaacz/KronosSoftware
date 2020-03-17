package com.kronos.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kronos.model.Department;
import com.kronos.model.Role;
import com.kronos.model.TempUser;
import com.kronos.model.User;
import com.kronos.model.UserRole;

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
	
	
	public List<UserRole> getAllUserRoles() throws Exception{
		Connection connection=null;
		CallableStatement statement=null;
		ResultSet rs=null;
		try {
			 connection = jdbcTemplate.getDataSource().getConnection();
			 statement = connection.prepareCall("{call searchAllUserRole() }");


			 rs= statement.executeQuery();

			List<UserRole> result = new ArrayList<>();
			while(rs.next()) {
				TempUser tmp = new TempUser(rs.getString("NAME"),rs.getString("EMAIL"));
				Department newDep= new Department(rs.getInt("DEP_ID"),rs.getString("DEP_NAME"));
				User user= new User(tmp,newDep,rs.getBoolean("STATUS"));
				Role role= new Role(rs.getString("ROLE_NAME"));
				UserRole userRole=new UserRole(user,role);
				result.add(userRole);				
				
			}

			
			return result;

		} catch (Exception e) {
			throw e;
		}
		finally {
			try {
			if(rs!=null)
				rs.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
			if(statement!= null)
				statement.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
			if(connection != null)
			connection.close();
			}
			catch(Exception e) {
				e.printStackTrace();
				
			}
		}
		
		}
	
	
	public List<UserRole> getUserRolesByDepartment(int dep) throws Exception{
		Connection connection=null;
		CallableStatement statement=null;
		ResultSet rs=null;
		try {
			 connection = jdbcTemplate.getDataSource().getConnection();
			 statement = connection.prepareCall("{call searchUserRoleByDepartment(?) }");
			 statement.setInt(1, dep);


			 rs= statement.executeQuery();

			List<UserRole> result = new ArrayList<>();
			while(rs.next()) {
				TempUser tmp = new TempUser(rs.getString("NAME"),rs.getString("EMAIL"));
				Department newDep= new Department(rs.getInt("DEP_ID"),rs.getString("DEP_NAME"));
				User user= new User(tmp,newDep,rs.getBoolean("STATUS"));
				Role role= new Role(rs.getString("ROLE_NAME"));
				UserRole userRole=new UserRole(user,role);
				result.add(userRole);				
				
			}

			
			return result;

		} catch (Exception e) {
			throw e;
		}
		finally {
			try {
			if(rs!=null)
				rs.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
			if(statement!= null)
				statement.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
			if(connection != null)
			connection.close();
			}
			catch(Exception e) {
				e.printStackTrace();
				
			}
		}
		
		}
	
	public List<UserRole> getBossUserRolesByDepartment(String userId, int dep) throws Exception{
		Connection connection=null;
		CallableStatement statement=null;
		ResultSet rs=null;
		try {
			 connection = jdbcTemplate.getDataSource().getConnection();
			 statement = connection.prepareCall("{call searchBossUserRoleByDepartment(?,?) }");
			 statement.setString(1, userId);
			 statement.setInt(2, dep);


			 rs= statement.executeQuery();

			List<UserRole> result = new ArrayList<>();
			while(rs.next()) {
				TempUser tmp = new TempUser(rs.getString("NAME"),rs.getString("EMAIL"));
				Department newDep= new Department(rs.getInt("DEP_ID"),rs.getString("DEP_NAME"));
				User user= new User(tmp,newDep,rs.getBoolean("STATUS"));
				Role role= new Role(rs.getString("ROLE_NAME"));
				UserRole userRole=new UserRole(user,role);
				result.add(userRole);				
				
			}

			
			return result;

		} catch (Exception e) {
			throw e;
		}
		finally {
			try {
			if(rs!=null)
				rs.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
			if(statement!= null)
				statement.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
			if(connection != null)
			connection.close();
			}
			catch(Exception e) {
				e.printStackTrace();
				
			}
		}
		
		}
		
}
