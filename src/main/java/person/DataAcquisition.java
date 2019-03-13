package person;

import mySQL.MySql;
import network.PersonsDTO;
import network.RandomApi;

import java.sql.SQLException;
import java.util.List;

public class DataAcquisition {

    public List<Person> getDataFromDatabase(){
        MySql mySql = new MySql();
        mySql.getConnection();
        try {
            mySql.getQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mySql.breakConnection();

        return null;
    }

    public List<PersonsDTO> getDataFromAPI(int numberOfPersons){
        RandomApi randomApi = new RandomApi();
        randomApi.buildClient();
        randomApi.getSearchRequest(numberOfPersons);
        return randomApi.getPersonsData();
    }

    private void getDataFromResources(){

    }
}
