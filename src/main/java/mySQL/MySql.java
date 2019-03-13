package mySQL;

import helper.Helper;
import network.PersonsDTO;

import java.sql.*;
import java.util.List;

public class MySql {

    private Connection connection;
    private static Statement stmt;
    private static ResultSet rs;

    enum mySqlConData {CONNECTION_STRING, LOGIN, PASSWORD}

    public void getConnection() {
        try {
            connection = DriverManager.getConnection(Helper.getMySqlConnection(mySqlConData.CONNECTION_STRING.ordinal()),
                                                     Helper.getMySqlConnection(mySqlConData.LOGIN.ordinal()),
                                                     Helper.getMySqlConnection(mySqlConData.PASSWORD.ordinal()));
        } catch (SQLException e) {
            System.out.println("Can't get connection. Incorrect URL");
            e.printStackTrace();
        }
    }

    public void breakConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Can't close connection");
            e.printStackTrace();
        }
    }

    public void getQuery() throws SQLException {
        String query = "select count(*) from persons";

        // getting Statement object to execute query

            stmt = connection.createStatement();

        // executing SELECT query

            rs = stmt.executeQuery(query);

        while (rs.next()) {
            int count = rs.getInt(1);
            System.out.println("Total number of books in the table : " + count);
        }
    }

    public void setData(List<PersonsDTO> personsData){
        for(int i = 0; i < personsData.size(); i++){
            String query = "INSERT INTO persons.persons (id, postcode, region, city, street, house, flat) \n" +
                    " VALUES ("+ i + ", 'Head First Java', 'Kathy Sieara');";
        }

        // executing SELECT query
        try {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

