package se.andreas;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public class APIfetcher {
    public static String lon = "17.29";
    public static String lat = "62.39";
    private static final String BASE_URL = "https://opendata-download-metfcst.smhi.se/";

    /**
     * Set the longitude and latitude for the API to fetch data for
     * @param lon The longitude
     * @param lat The latitude
     */
    public static void setLonLat(String lon, String lat) {
        APIfetcher.lon = lon;
        APIfetcher.lat = lat;
    }

    /**
     * Retrofit requires an interface to work
     */
    private interface WeatherApi {
        @GET("api/category/pmp3g/version/2/geotype/point/lon/{lon}/lat/{lat}/data.json")
        Call<WeatherData> getWeatherData(@Path("lon") String lon, @Path("lat") String lat);
    }

    /**
     * Create a Retrofit client with the base url and a Jackson converter
     * @return The client
     */
    private static Retrofit getClient() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    /**
     * Get the WeatherApi interface
     * @return The WeatherApi interface
     */
    private static WeatherApi getWeatherApi() {
        return getClient().create(WeatherApi.class);
    }


    /**
     * Fetch the weather data from the API asynchronously
     * @param callback The callback to be called when the data is fetched
     * <pre>
     * {@code
     *   APIfetcher.getWeatherData(new Callback<>() {
     *       @Override
     *       public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
     *           if (response.isSuccessful() && response.body() != null) {
     *               WeatherData weatherData = response.body();
     *
     *               // This needs to be done inside onResponse because it is asynchronous so this is basically another thread right now
     *               Map<String, Double> timeTemperatureMap = weatherData.getMapFromParameterName("t");
     *               // sorted print
     *               SortedSet<String> keys = new TreeSet<>(timeTemperatureMap.keySet());
     *               for (String key : keys) {
     *                   Double value = timeTemperatureMap.get(key);
     *                   System.out.println("Time: " + key + ", Temperature: " + value);
     *               }
     *           } else {
     *               System.err.println("Failed to fetch weather data");
     *           }
     *       }
     *
     *       @Override
     *       public void onFailure(Call<WeatherData> call, Throwable t) {
     *           t.printStackTrace();
     *       }
     *   });
     * }
     * </pre>

     */
    public static void getWeatherData(Callback<WeatherData> callback) {
        WeatherApi weatherApi = getWeatherApi();
        Call<WeatherData> call = weatherApi.getWeatherData(lon, lat);
        call.enqueue(callback);
    }
}