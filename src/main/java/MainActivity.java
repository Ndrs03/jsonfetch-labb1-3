import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import se.andreas.JsonReader;
import se.andreas.WeatherData;

public class MainActivity {
    private static JsonReader.WeatherApi weatherApi;

    public static void main(String[] args) {
        Retrofit retrofit = JsonReader.getClient();
        weatherApi = retrofit.create(JsonReader.WeatherApi.class);
        fetchData();
    }

    private static void fetchData() {
        Call<WeatherData> call = weatherApi.getWeatherData(JsonReader.lon, JsonReader.lat);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                if (response.isSuccessful()) {
                    WeatherData weatherData = response.body();
                    System.out.println("Approved time: " + weatherData.getApprovedTime());
                    System.out.println("Reference time: " + weatherData.getReferenceTime());
                    System.out.println("Time series: " + weatherData.getTimeSeries());
                    System.out.println("Weather now: " + weatherData.getTimeSeries().get(0).getParameterName());
                } else {
                    System.out.println("Failed to fetch data");
                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                System.out.println("Failed to fetch data: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }
}