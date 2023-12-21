package dev.clanders.weatherapi.request;

public class SearchLocationBuilder {
    public static SearchLocation createLocation(String locationStr) {
        SearchLocation searchLocation =  new SearchLocation();
        String[] locationTokenized = locationStr.split(",");
        searchLocation.city = locationTokenized[0].trim();
        searchLocation.state = locationTokenized[1].trim(); // Refactor this to intake fullname or Abbrev.

        return searchLocation;
    }
}
