package person;

import mySQL.MySql;
import network.PersonsDTO;
import network.RandomApi;

import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private List<Person> personsData = new ArrayList<>();

    public List<Person> getPersonsData() { return personsData; }

    public void getData(int numberOfPersons){
        List<PersonsDTO> personsDtoData = getDataFromAPI(numberOfPersons);
        if (personsDtoData == null) {
            System.out.println("No internet connection.\nWe will receive data from the database.");
            getDataFromDatabase(numberOfPersons);
            if(personsData.size() != numberOfPersons){
                System.out.println("There is not enough data in the database.\n" +
                        "We will receive data from resource files.");
                getDataFromResources(numberOfPersons - personsData.size());
            }
        } else {
            RandomApi.personsDtoToPersons(personsData);
            setDataInDatabase(personsData);
            //добавить перезапись существущих ФИО
        }
    }

    private void getDataFromDatabase(int numberOfPersons){
        MySql.requestForGetData(personsData, numberOfPersons);
    }


    private void setDataInDatabase(List<Person> personsData){
        for(Person person : personsData){
            int personId = MySql.setDataInAddressTable(person);
            MySql.setDataInPersonsTable(person, personId);
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

