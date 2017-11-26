package com.blibli.seagullpos.dao;

import com.blibli.seagullpos.connection.ConnectionManager;
import com.blibli.seagullpos.model.EmployeeModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    private Connection connection = null;
    private PreparedStatement ps = null;

    public EmployeeDAO(){

    }

    public EmployeeModel authenticateUser(String email, String password){

        EmployeeModel employeeModel = null;
        try{
            connection = ConnectionManager.createConnection();
            final String query = "SELECT * FROM employee WHERE employeeemail = ? AND password = md5(?)";
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            employeeModel = processEmployeeRow(rs);

            if(employeeModel != null) updateLoginTime(employeeModel);

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(connection != null) ConnectionManager.closeConnection(connection);
            if(ps != null) try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return employeeModel;
    }

    public List<EmployeeModel> getAllUser(){
        List<EmployeeModel> listUser = null;

        try{
            connection = ConnectionManager.createConnection();
            String query = "SELECT * FROM employee ORDER BY employeeid ASC";
            ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            listUser = processAllROw(rs);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(connection != null) ConnectionManager.closeConnection(connection);
            if(ps != null) try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return  listUser;
    }


    public String getLastID(String role){
        String query = "SELECT employeeid FROM employee WHERE role = ? ORDER BY employeeid DESC LIMIT 1";
        String id = "";

        try{
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, role);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getString(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(connection != null) ConnectionManager.closeConnection(connection);
            if(ps != null) try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println(id);
        return id;
    }

    public boolean insertUser(EmployeeModel employee){
        String query = "INSERT INTO Employee (employeeid, employeename, employeeemail, employeegender, password, role, lastlogin)" +
                " VALUES (?, ?, ?, ?, md5(?), ?, ?)";

        boolean insertStatus = false;
        try{
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, employee.getEmployeeId());
            ps.setString(2, employee.getEmployeeName());
            ps.setString(3, employee.getEmployeeEmail());
            ps.setString(4, employee.getEmployeeGender());
            ps.setString(5, employee.getEmployeePassword());
            ps.setString(6, employee.getEmployeeRole());
            ps.setTimestamp(7, null);
            insertStatus = ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(connection != null) ConnectionManager.closeConnection(connection);
            if(ps != null) try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return insertStatus;
    }

    public boolean updateUser(EmployeeModel employee){
        String query = "UPDATE employee " +
                "SET " +
                "employeeID = ?," +
                "employeeName = ?" +
                "employeeEmail = ?" +
                "employeeGender = ?" +
                "password = md5(?)" +
                "role = ?" +
                "WHERE employeeid = ?";

        boolean updateStatus = false;

        try{
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);

            ps.setString(1, employee.getEmployeeId());
            ps.setString(2, employee.getEmployeeName());
            ps.setString(3, employee.getEmployeeEmail());
            ps.setString(4, employee.getEmployeeGender());
            ps.setString(5, employee.getEmployeePassword());
            ps.setString(6, employee.getEmployeeRole());

            updateStatus = ps.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(connection != null) ConnectionManager.closeConnection(connection);
            if(ps != null) try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return updateStatus;
    }

    public void updateLoginTime(EmployeeModel employee){
        try{
            connection = ConnectionManager.createConnection();
            connection.setAutoCommit(false);
            String query = "UPDATE employee " +
                    "SET lastlogin = ?" +
                    "WHERE employeeid = ?";

            Timestamp newTime = new Timestamp(System.currentTimeMillis());

            ps = connection.prepareStatement(query);
            ps.setTimestamp(1, newTime);
            ps.setString(2, employee.getEmployeeId());

            ps.executeUpdate();
            connection.commit();
            employee.setLastLogin(newTime);
        }catch (Exception e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            if(connection != null) ConnectionManager.closeConnection(connection);
            if(ps != null) try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean deleteUser(String employeeID){
        String query = "DELETE FROM employee WHERE employeeID = ?";
        boolean deleteStatus = false;
        try{
            connection = ConnectionManager.createConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, employeeID);
            deleteStatus = ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(connection != null) ConnectionManager.closeConnection(connection);
            if(ps != null) try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return deleteStatus;
    }


    public List<EmployeeModel> processAllROw(ResultSet rs) throws SQLException{
        List<EmployeeModel> listEmployee = new ArrayList<>();
        while(rs.next()){
            EmployeeModel model = new EmployeeModel();
            model.setEmployeeEmail(rs.getString("employeeemail"));
            model.setEmployeeGender(rs.getString("employeegender"));
            model.setEmployeeId(rs.getString("employeeid"));
            model.setEmployeeName(rs.getString("employeename"));
            model.setEmployeePassword(rs.getString("password"));
            model.setEmployeeRole(rs.getString("role"));

            if(rs.getDate("lastlogin") != null)
                model.setLastLogin(rs.getTimestamp("lastlogin"));
            else
                model.setLastLogin(null);
            listEmployee.add(model);
        }
        return listEmployee;
    }

    public EmployeeModel processEmployeeRow(ResultSet rs) throws SQLException{
        EmployeeModel employeeModel = null;
        if(rs.next()){
            employeeModel = new EmployeeModel();
            employeeModel.setEmployeeEmail(rs.getString("employeeemail"));
            employeeModel.setEmployeeGender(rs.getString("employeegender"));
            employeeModel.setEmployeeId(rs.getString("employeeid"));
            employeeModel.setEmployeeName(rs.getString("employeename"));
            employeeModel.setEmployeePassword(rs.getString("password"));
            if(rs.getDate("lastlogin") != null)
                employeeModel.setLastLogin(rs.getTimestamp("lastlogin"));
            else employeeModel.setLastLogin(null);
            employeeModel.setEmployeeRole(rs.getString("role"));
        }
        return employeeModel;
    }

}
