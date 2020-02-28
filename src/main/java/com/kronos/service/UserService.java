package com.kronos.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kronos.model.Department;
import com.kronos.model.TempUser;
import com.kronos.model.User;

@Repository
public class UserService {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public Optional<User> getUserByEmail(String email) throws Exception{
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		  CallableStatement statement = connection.prepareCall("{call getUserByEmail(?) }");
		  statement.setString(1, email);
		  ResultSet rs=statement.executeQuery();
		  User user=null;
		  while(rs.next()) {
			  user = new User(
				new TempUser(rs.getString("NAME"),rs.getString("TEMPUSER")),
				rs.getString("PASSWORD"),
				new Department(Integer.parseInt(rs.getString("DEPARTMENT")),
						rs.getString("DEPARTMENT_NAME")));
					  
		  }
		  statement.close();
		  connection.close();
		  if(user==null)
			  return Optional.empty();
		  
		  return Optional.of(user);
	}
	
	public void insertUser(User user) throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
        CallableStatement statement = connection.prepareCall("{call insertUser(?, ?, ?, ?)}");
        statement.setInt(1, user.getDepartment().getId());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getTempUser().getEmail());
        statement.setBoolean(4, user.getStatus());
        statement.executeUpdate();
        statement.close();
	}
	
	public List<User> searchAllUsersWithoutRole(){
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			CallableStatement statement = connection.prepareCall("{call searchAllUsersWithoutRole() }");

			// statement.registerOutParameter(1, new );
			ResultSet rs = statement.executeQuery();

			List<User> result = this.mapRowList(rs);

			statement.close();
			connection.close();
			return result;

		} catch (Exception e) {
			System.out.println("\n\n\n\n\n\n\n\nERROR\n\n\n\n\n" + e.getMessage());
		}

		return null;
	}
	
	private List<User> mapRowList(ResultSet rs) throws SQLException {

		List<User> result = new ArrayList<>();
		while (rs.next()) {
			TempUser u= new TempUser();
			User t = new User();
			u.setEmail(rs.getString("TEMPUSER"));
			t.setTempUser(u);
			result.add(t);
		}

		return result;

	}
	/*public List<Department> findAll(){
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			CallableStatement statement = connection.prepareCall("{call searchAllDepartments() }");

			// statement.registerOutParameter(1, new );
			ResultSet rs = statement.executeQuery();

			List<Department> result = this.mapRowList(rs);

			statement.close();
			connection.close();
			return result;

		} catch (Exception e) {
			System.out.println("\n\n\n\n\n\n\n\nERROR\n\n\n\n\n" + e.getMessage());
		}

		return null;
	}

	private List<Department> mapRowList(ResultSet rs) throws SQLException {

		List<Department> result = new ArrayList<>();
		while (rs.next()) {
			Department t = new Department();
			t.setName(rs.getString("NAME"));
			t.setId(Character.getNumericValue(rs.getString("ID").charAt(0)));
			result.add(t);
		}

		return result;

	}*/
}
