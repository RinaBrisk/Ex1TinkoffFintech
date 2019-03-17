package mySQL;

import helper.Helper;
import person.Person;

import java.sql.*;
import java.util.List;

public class MySql {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    enum mySqlConData {CONNECTION_STRING, LOGIN, PASSWORD}

    public static void getConnection() {
        try {
            connection = DriverManager.getConnection(Helper.getMySqlConnection(mySqlConData.CONNECTION_STRING.ordinal()),
                    Helper.getMySqlConnection(mySqlConData.LOGIN.ordinal()),
                    Helper.getMySqlConnection(mySqlConData.PASSWORD.ordinal()));
        } catch (SQLException e) {
            System.out.println("Can't get connection. Incorrect URL");
            e.printStackTrace();
        }
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void breakConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Can't close connection");
            e.printStackTrace();
        }
    }

    public static Integer personIsPresent(Person person) throws SQLException {
        String query = "SELECT * FROM persons.persons WHERE name = '" + person.getName() + "' AND surname = '"
                + person.getSurname() + "' AND middlename = '" + person.getPatronymic() + "';";
        try {
            statement.executeQuery(query);
            resultSet = statement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(resultSet.next()){
            return resultSet.getInt(8);
        }else return null;
    }


    public static int insertDataInAddressTable(Person person) {
        int id = 0;
        String query = "INSERT INTO persons.address (postcode, country, region, city, street, house, flat) \n" +
                " VALUES (" + person.getZipCode() + ", '" + person.getCountry() + "', '" + person.getArea() + "', '" +
                person.getCity() + "', '" + person.getStreet() + "', " + person.getHouse() + ", " + person.getApartment() + ");";
        try {
            statement.execute(query, Statement.RETURN_GENERATED_KEYS);
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static void insertDataInPersonsTable(Person person, int personId) {
        String query = "INSERT INTO persons.persons (surname, name, middlename, birthday, gender, inn, address_id, age) \n" +
                " VALUES (" + "'" + person.getSurname() + "', '" + person.getName() + "', '" + person.getPatronymic() + "', " +
                "STR_TO_DATE('" + person.getDateOfBirth() + "','%d-%m-%Y'),'" + person.getGender() + "', " + person.getItp() +
                ", " + personId + ", " + person.getAge() + ");";
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateDataInAddressTable(Person person, int addressId){
        String query = "UPDATE persons.address SET postcode = '" + person.getZipCode() + "', country = '" + person.getCountry() + "', region = '" +
        person.getArea() + "', city = '" + person.getCity() + "', street = '" + person.getStreet() + "', house = '" + person.getHouse() + "', flat = '" + person.getApartment() + "' " +
                "WHERE id = '" + addressId + "';";
        try {
            statement.execute(query);
            resultSet = statement.getGeneratedKeys();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateDataInPersonsTable(Person person, int addressId){
        String query = "UPDATE persons.persons SET birthday = '" + person.getDateOfBirth() + "', inn = '" + person.getItp() + "', age = '" +
                person.getAge() +"' " + "WHERE address_id = '" + addressId + "';";
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void requestForGetData(List<Person> personsData, int numberOfPersons) {
        String query = "SELECT * FROM persons.persons JOIN persons.address \n where persons.address_id = address.id \n" +
                "ORDER BY RAND() LIMIT " + numberOfPersons;
            try {
                resultSet = statement.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            databaseToPersons(resultSet, personsData);
    }

    private static void databaseToPersons(ResultSet resultSet, List<Person> personsData){
        try {
            while (resultSet.next()) {
                Person person = new Person();
                person.setSurname(resultSet.getString(2));
                person.setName(resultSet.getString(3));
                person.setPatronymic(resultSet.getString(4));
                person.setDateOfBirth(Helper.createCorrectFormatDateOfBirth(resultSet.getString(5)));
                person.setGender(resultSet.getString(6));
                person.setItp(resultSet.getString(7));
                person.setAge(resultSet.getInt(9));
                person.setZipCode(Integer.valueOf(resultSet.getString(11)));
                person.setCountry(resultSet.getString(12));
                person.setArea(resultSet.getString(13));
                person.setCity(resultSet.getString(14));
                person.setStreet(resultSet.getString(15));
                person.setHouse(resultSet.getInt(16));
                person.setApartment(resultSet.getInt(17));
                personsData.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

