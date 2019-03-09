package network;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public final class RandomApi {

    private static final String URL = "https://randomuser.me";
    private Retrofit retrofit;
    private List<PersonsDTO> personsData;
    private Response<DefaultResponse<List<PersonsDTO>>> response;
    public List<PersonsDTO> getPersonsData() {
        return personsData;
    }

    public void  buildClient() {

       retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void getSearchRequest(int numberOfPersons){
        PersonsEndpoint personsEndpoint = retrofit.create(PersonsEndpoint.class);
        try {
            response = personsEndpoint.search(numberOfPersons,"us").execute();
            checkResponse(response);
        } catch (IOException e) {
            System.out.println("Request error: " + e.toString() + "\nData will be taken from file resources.");
        }
    }

    private void checkResponse(Response<DefaultResponse<List<PersonsDTO>>> response){
            final DefaultResponse<List<PersonsDTO>> body = response.body();
            if(body != null){
                personsData = body.getResults();
            }
            else{
                System.out.println("In the response is an empty body");
            }
    }
}
