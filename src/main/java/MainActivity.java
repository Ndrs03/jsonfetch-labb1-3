public class MainActivity {
        public static void main(String[] args) {
        WeatherContainer weatherContainer = new WeatherContainer();
        weatherContainer.insertObject("Sundsvall");

        WeatherObject weatherObject = weatherContainer.getObject("Sundsvall");
        if (weatherObject != null) {
            // Process and print the weather data
            System.out.println("Temperature: " + weatherObject.getTemperature());
            System.out.println("Wind Speed: " + weatherObject.getWindSpeed());
            System.out.println("Cloud Cover: " + weatherObject.getCloudCover());
            System.out.println("Percipitation: " + weatherObject.getPercipitation());
            System.out.println("Weather Symbol: " + weatherObject.getPercipitation());
        } else {
            System.out.println("Weather data for Stockholm not found.");
        }
    }
}