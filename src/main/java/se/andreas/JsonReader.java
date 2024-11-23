package se.andreas;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import se.andreas.jsonStructure.Parameter;
import se.andreas.jsonStructure.TimeSeries;

import java.util.HashMap;
import java.util.Map;

public class JsonReader {
    public static String lon = "17.29";
    public static String lat = "62.39";
    private static final String BASE_URL = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/";

    public interface WeatherApi {
        @GET("{lon}/lat/{lat}/data.json")
        Call<WeatherData> getWeatherData(@Path("lon") String lon, @Path("lat") String lat);
    }

    public static Retrofit getClient() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public static WeatherApi getWeatherApi() {
        return getClient().create(WeatherApi.class);
    }

    public static void setLonLat(String lon, String lat) {
        JsonReader.lon = lon;
        JsonReader.lat = lat;
    }

    public static void getWeatherData(Callback<WeatherData> callback) {
        WeatherApi weatherApi = getWeatherApi();
        Call<WeatherData> call = weatherApi.getWeatherData(lon, lat);
        call.enqueue(callback);
    }
}