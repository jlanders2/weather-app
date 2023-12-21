package dev.clanders.weatherapp;

import com.google.gson.Gson;
import dev.clanders.weatherapi.Api;
import dev.clanders.weatherapi.request.SearchLocation;
import dev.clanders.weatherapi.request.SearchLocationBuilder;
import dev.clanders.weatherapi.response.WeatherLocation;
import dev.clanders.weatherapp.error.ApiNotResponsiveException;
import dev.clanders.weatherapp.error.InvalidCountryException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.net.http.HttpResponse;
import java.util.Objects;

public class WeatherAppController {
    @FXML
    private TextField locationTextField;
    @FXML
    private Label cityNameLabel;
    @FXML
    private Label locationLowLabel;
    @FXML
    private Label locationHighLabel;
    @FXML
    private Label locationCurrentLabel;
    @FXML
    private VBox forecastContainer;
    @FXML
    private TextField usersApiKeyTextField;
    @FXML
    private Label userApiKeyMessageLabel;

    @FXML protected void handleLocationEnteredAction(ActionEvent event) {
        cityNameLabel.setTextFill(Color.BLACK);
        cityNameLabel.setStyle("-fx-font-size: 24;");

        try {
            SearchLocation searchLocation = SearchLocationBuilder.createLocation(locationTextField.getText()); // This doesn't fail gracefully
            HttpResponse<String> res =  Api.getWeatherByLocation(searchLocation);

            Gson g = new Gson();
            WeatherLocation wLocation = g.fromJson(res.body(), WeatherLocation.class);

            if (wLocation.location == null) {
                throw new ApiNotResponsiveException();
            }

            if (!Objects.equals(wLocation.location.getCountry(), "United States")) {
                throw new InvalidCountryException();
            }

            System.out.printf("Location: %s\n", wLocation.location.name);
            cityNameLabel.setText(wLocation.location.name);
            forecastContainer.setVisible(true);

            locationLowLabel.setText(String.valueOf(wLocation.timelines.daily[0].values.temperatureMin));
            locationHighLabel.setText(String.valueOf(wLocation.timelines.daily[0].values.temperatureMax));
            locationCurrentLabel.setText((String.valueOf(wLocation.timelines.hourly[0].values.temperature)));
        } catch(ApiNotResponsiveException e) {
            cityNameLabel.setStyle("-fx-font-size: 12;");
            cityNameLabel.setTextFill(Color.RED);
            cityNameLabel.setText("API currently unresponsive or Invalid custom API Key. \nThis application uses the free tier of tomorrow.io.\n" +
                                  "Due to hourly limits of API calls, you will need to wait before making further requests. Or provide a new API Key below.");
            usersApiKeyTextField.setVisible(true);
        }
        catch (InvalidCountryException e) {
            cityNameLabel.setStyle("-fx-font-size: 12;");
            cityNameLabel.setTextFill(Color.RED);
            cityNameLabel.setText("City must be within United States.\n Please use format City, State without abbreviations.");
        }
        catch (Exception e) {
            cityNameLabel.setTextFill(Color.RED);
            cityNameLabel.setText("INVALID");
            e.printStackTrace();
        }
    }

    @FXML protected void handleApiKeyEnteredAction(ActionEvent event) {
        Api.setApiKey(usersApiKeyTextField.getText());
        usersApiKeyTextField.setVisible(false);
        userApiKeyMessageLabel.setVisible(true);
    }
}
