import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import se.andreas.JsonReader;
import se.andreas.WeatherData;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import static se.andreas.JsonReader.getWeatherData;

public class MainActivity {


    public static void main(String[] args) {
        JsonReader.getWeatherData(new Callback<>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherData weatherData = response.body();

                    // This needs to be done inside onResponse because it is asynchronous so this is basically another thread right now
                    Map<String, Double> timeTemperatureMap = weatherData.getMapFromParameterName("t");
                    // sorted print
                    SortedSet<String> keys = new TreeSet<>(timeTemperatureMap.keySet());
                    for (String key : keys) {
                        Double value = timeTemperatureMap.get(key);
                        System.out.println("Time: " + key + ", Temperature: " + value);
                    }
                } else {
                    System.err.println("Failed to fetch weather data");
                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                t.printStackTrace();
            }
        });






    }

}