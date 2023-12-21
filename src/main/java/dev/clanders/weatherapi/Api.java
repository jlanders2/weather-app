package dev.clanders.weatherapi;

import dev.clanders.weatherapi.request.SearchLocation;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Api {
    private static String API_KEY = "08Yqt7FJVrndj56jsasgceKdppJ4yu90";
    public static HttpResponse<String> getWeatherByLocation(SearchLocation searchLocation) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://api.tomorrow.io/v4/weather/forecast?location=%s %s&units=imperial&apikey=%s", searchLocation.city, searchLocation.state, API_KEY).replace(" ", "%20")))
                .header("accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static void setApiKey(String newApiKey) {
        API_KEY = newApiKey;
    }
}
