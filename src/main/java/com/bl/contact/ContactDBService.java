package com.bl.contact;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContactDBService {
    private static ContactDBService contactDBService;
    private PreparedStatement contactStatement;

    private ContactDBService(){}

    public static ContactDBService getInstance() {
        if (contactDBService == null)
            contactDBService = new ContactDBService();
        return contactDBService;

    }

    public List<Contact> readData() {
        String sql = "SELECT * FROM contact;";
       return this.getContactList(sql);

    }
    public List<Contact> getContactList(String sql){
        List<Contact> employeePayrollDataList = new ArrayList<>();
        try (Connection connection = this.getConnection();) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            employeePayrollDataList = this.getContact(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return employeePayrollDataList;
    }

    private List<Contact> getContact(ResultSet resultSet) {
        List<Contact> contactDataList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String  lastName= resultSet.getString(3);
                String address= resultSet.getString(4);
                String city = resultSet.getString(5);
                String state = resultSet.getString(6);
                String zip = resultSet.getString(7);
                String number= resultSet.getString(8);
                String email = resultSet.getString(9);
                LocalDate startDate = resultSet.getDate(10).toLocalDate();
                contactDataList.add(new Contact(id, firstName, lastName, address,city, state,zip,number,email, startDate));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactDataList;
    }

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/employeepayroll?userSSL=false";
        String userName = "root";
        String password = "root";
        Connection connection;
        System.out.println("Connecting to . . ." + jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("Connected " + connection);
        return connection;
    }

    public List<Contact> readDateRangeData(LocalDate start, LocalDate end) {
        String sql=String.format("select * from contact where start between '%s' and '%s';", Date.valueOf(start), Date.valueOf(end));
        return this.getContactList(sql);
    }

    public List<Contact> readContactsByCity(String city) {
        String sql=String.format("select * from contact where city='%s'",city);
        return this.getContactList(sql);

    }
    public List<Contact> readContactsByState(String city) {
        String sql=String.format("select * from contact where state='%s'",city);
        return this.getContactList(sql);

    }

    public int updateContactData(String name, String address) {
        String sql = String.format("update contact set address='%s' where first_name='%s';", address, name);
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Contact> getContactFromDB(String name) throws SQLException {
        List<Contact> contactList = null;
        if (this.contactStatement == null)
            this.prepareStatementForEmployeeData();
        try {
            contactStatement.setString(1, name);
            ResultSet resultSet = contactStatement.executeQuery();
           contactList = this.getContact(resultSet);
            getConnection().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactList;


    }

    private void prepareStatementForEmployeeData() throws SQLException {
        try {
            Connection connection = this.getConnection();
            String sql = "SELECT * FROM contact where first_name= ?";
            contactStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
