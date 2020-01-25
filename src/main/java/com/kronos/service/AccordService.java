package com.kronos.service;

import java.sql.CallableStatement;
import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kronos.model.Accord;
import com.kronos.model.Pdf;

@Repository
public class AccordService {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
    public void insertAccord(Accord acc) throws Exception {

    	Connection connection = jdbcTemplate.getDataSource().getConnection();
        CallableStatement statement = connection.prepareCall("{call insertAccord(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
;
        statement.setString(1, acc.getAccNumber());
        statement.setDate(2, new java.sql.Date(acc.getIncorporatedDate().getTime()));
        statement.setTime(3, java.sql.Time.valueOf(acc.getIncorporatedTime()));
        statement.setDate(4, new java.sql.Date(acc.getDeadline().getTime()));
        statement.setDate(5, new java.sql.Date(acc.getSessionDate().getTime()));
        statement.setString(6, String.valueOf(acc.getType()));
        statement.setString(7, acc.getObservations());
        statement.setBoolean(8, acc.isPublished());
        statement.setBoolean(9, acc.isNotified());
        statement.setInt(10, acc.getState().getId());
        statement.setString(11, acc.getUser().getTempUser());
        statement.executeUpdate();
        statement.close();

        for (Pdf item : acc.getURL()) {
            CallableStatement statement2 = connection.prepareCall("{call insertAccPdf(?, ?, ?)}");
            statement2.setString(1, acc.getAccNumber());
            statement2.setString(2, item.getURL());
            statement2.setBoolean(3, item.isFinalResponse());
            statement2.executeUpdate();

            statement2.close();
        }

    }
	
	
}
