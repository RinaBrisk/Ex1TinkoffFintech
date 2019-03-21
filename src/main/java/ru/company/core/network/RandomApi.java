package ru.company.core.network;
import ru.company.core.utils.DataGeneration;
import ru.company.core.models.Person;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public final class RandomApi {

    private static final String URL = "https://randomuser.me";
    private Retrofit retrofit;
    private static List<PersonsDTO> personsDtoData;
    private Response<DefaultResponse<List<PersonsDTO>>> response;

    public void  buildClient() {

       retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public List<PersonsDTO> getSearchRequest(int numberOfPersons){
        PersonsEndpoint personsEndpoint = retrofit.create(PersonsEndpoint.class);
        try {
            response = personsEndpoint.search(numberOfPersons,"us").execute();
            checkResponse(response);
        } catch (IOException e) {
            System.out.println("Request error: " + e.toString());
        }
        return personsDtoData;
    }

    private void checkResponse(Response<DefaultResponse<List<PersonsDTO>>> response){
            final DefaultResponse<List<PersonsDTO>> body = response.body();
            if(body != null){
                personsDtoData = body.getResults();
            }
            else{
                System.out.println("In the response is an empty body");
            }
    }

    public static void personsDtoToPersons(List<Person> personsData){
        for (PersonsDTO personDto : personsDtoData) {
            Person person = new Person();
            person.setAPIGenderContent(personDto);
            person.setAPIAgeContent(personDto);
            DataGeneration.createITNContent(person);
            person.setAPIAddressContent(personDto);
            personsData.add(person);
        }
    }

}
