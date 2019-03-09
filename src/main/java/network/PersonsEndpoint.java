package network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import java.util.List;

public interface PersonsEndpoint {

    @GET("/api/")
    Call<DefaultResponse<List<PersonsDTO>>> search(@Query("results")int numberOfPerson, @Query("nat")String nat);
}
