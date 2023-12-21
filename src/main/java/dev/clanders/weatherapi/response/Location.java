package dev.clanders.weatherapi.response;

public class Location {
    public double lat;
    public double lon;
    public String name;
    public String type;

    public String getCountry() {
        // Probably not efficient to be splitting this string every time, but does
        // it really matter in an app this size????
        String[] locationValues = name.split(",");

        return locationValues[locationValues.length - 1].trim();
    }
}
