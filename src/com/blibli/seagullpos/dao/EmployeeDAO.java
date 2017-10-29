package com.blibli.seagullpos.dao;

import com.blibli.seagullpos.connection.ConnectionManager;
import com.blibli.seagullpos.model.EmployeeModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAO {

    private Connection connection = null;
    private PreparedStatement ps = null;

    public EmployeeModel authenticateEmployee(String email, String password){
        EmployeeModel employeeModel = null;

        try{
            connection = ConnectionManager.createConnection();
            final String query = "SELECT * FROM employee WHERE employeeemail = ? AND employeepassword = md5(?)";
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            employeeModel = processEmployeeRow(rs);

        }catch (SQLException e){
            e.printStackTrace();
        }

        return employeeModel;
    }

    public EmployeeModel processEmployeeRow(ResultSet rs) throws SQLException{
        EmployeeModel employeeModel = null;
        if(rs.next()){
            employeeModel = new EmployeeModel();
            employeeModel.setEmployeeEmail(rs.getString("employeeemail"));
            employeeModel.setEmployeeGender(rs.getString("employeegender"));
            employeeModel.setEmployeeId(rs.getString("employeeid"));
            employeeModel.setEmployeeName(rs.getString("employeename"));
            employeeModel.setEmployeePassword(rs.getString("employeepassword"));
            employeeModel.setEmployeeRole(rs.getString("employeerole"));
        }
        return employeeModel;
    }

}
