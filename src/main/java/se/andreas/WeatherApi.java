package se.andreas;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import se.andreas.WeatherData;

import java.util.Map;

public interface WeatherApi {
    @GET("category/pmp3g/version/2/geotype/point/lon/{lon}/lat/{lat}/data.json")
    Call<Map<String, Double>> getWeatherData(@Query("lon") String lon, @Query("lat") String lat);


}

