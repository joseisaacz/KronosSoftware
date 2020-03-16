package com.kronos.service;

import java.sql.CallableStatement;
import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kronos.model.Accord;
import com.kronos.model.Department;

@Repository
public class AccordDepartmentService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void insertAccordDepartment(Accord acc, Department dep) throws Exception {
		
		Connection connection=null;
		CallableStatement statement=null;
		
		try {
		
		connection = jdbcTemplate.getDataSource().getConnection();
		statement = connection.prepareCall("{call  insertACCDPRMTNT(?, ?) }");
		statement.setString(1, acc.getAccNumber());
		statement.setInt(2,dep.getId());
		
		
		// statement.registerOutParameter(1, new );
		statement.executeUpdate();
	
	
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			
			if(statement != null) {
				try {
					statement.close();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			if(connection != null) {
				try {
					connection.close();
				
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		
		}


}
	
}
