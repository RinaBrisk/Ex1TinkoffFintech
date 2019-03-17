package helper;

import mySQL.MySql;
import network.PersonsDTO;
import network.RandomApi;
import person.Person;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private List<Person> personsData = new ArrayList<>();

    public List<Person> getPersonsData() { return personsData; }

    public void getData(int numberOfPersons){
        List<PersonsDTO> personsDtoData = getDataFromAPI(numberOfPersons);
        if (personsDtoData == null) {
            System.out.println("No internet connection.\nWe will receive data from the database.");
            MySql.getConnection();
            getDataFromDatabase(numberOfPersons);
            MySql.breakConnection();
            if(personsData.size() != numberOfPersons){
                System.out.println("There is not enough data in the database.\n" +
                        "We will receive data from resource files.");
                getDataFromResources(numberOfPersons - personsData.size());
            }
        } else {
            RandomApi.personsDtoToPersons(personsData);
            MySql.getConnection();
            setDataInDatabase(personsData);
            MySql.breakConnection();
        }
    }

    private void getDataFromDatabase(int numberOfPersons){
        MySql.requestForGetData(personsData, numberOfPersons);
    }


    private void setDataInDatabase(List<Person> personsData){
        for(Person person : personsData){
            try {
                Integer addressId = MySql.personIsPresent(person);
                if(addressId != null){
                    {
                        MySql.updateDataInAddressTable(person, addressId);
                        MySql.updateDataInPersonsTable(person, addressId);
                    }
                }
            int personId = MySql.insertDataInAddressTable(person);
            MySql.insertDataInPersonsTable(person, personId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private List<PersonsDTO> getDataFromAPI(int numberOfPersons){
        RandomApi randomApi = new RandomApi();
        randomApi.buildClient();
        return randomApi.getSearchRequest(numberOfPersons);
    }

    private void getDataFromResources(int numberOfPersons) {
        for (int i = 0; i < numberOfPersons; i++) {
            Person person = new Person();
            person.setDataFromResources();
            personsData.add(person);
        }
    }
}

