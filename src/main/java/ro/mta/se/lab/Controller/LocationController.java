package ro.mta.se.lab.Controller;

import com.eclipsesource.json.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import ro.mta.se.lab.Model.Locations;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Member;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;


public class LocationController {
    public static ObservableList<Locations> locationData;
    private static ObservableList<String> countries= FXCollections.observableArrayList();
    private static ObservableList<String> cities = FXCollections.observableArrayList();
    private LocationLogger logger;

    private String cityValue;

    @FXML
    private HBox chooseMenu;

    @FXML
    private ComboBox<String> countryBox;

    @FXML
    private ComboBox<String> cityBox;

    @FXML
    private Button searchButton;

    @FXML
    private Label tempLabel;

    @FXML
    private Label windLabel;

    @FXML
    private Label rainLabel;

    @FXML
    private Label tempValue;

    @FXML
    private Label windValue;

    @FXML
    private Label rainValue;

    public LocationController(ObservableList<Locations> locationData)
    {
        this.locationData=locationData;
        setCountryName();
    }

    public static void setCountryName()
    {
        locationData.forEach((location) ->{
            String countryCode=location.countryCodeProperty().get();
            if(countryCode.equalsIgnoreCase("RU")) {
                location.setCountriyName(new SimpleStringProperty("Rusia"));
            }
            if(countryCode.equalsIgnoreCase("FR")) {
                location.setCountriyName(new SimpleStringProperty("Franta"));
            }
            if(countryCode.equalsIgnoreCase("RO")) {
                location.setCountriyName(new SimpleStringProperty("Romania"));
            }
            if(countryCode.equalsIgnoreCase("IT")) {
                location.setCountriyName(new SimpleStringProperty("Italia"));
            }
            if(countryCode.equalsIgnoreCase("ES")) {
                location.setCountriyName(new SimpleStringProperty("Spania"));
            }
            if(countryCode.equalsIgnoreCase("PH")) {
                location.setCountriyName(new SimpleStringProperty("Filipine"));
            }
            if(countryCode.equalsIgnoreCase("CN")) {
                location.setCountriyName(new SimpleStringProperty("China"));
            }
            if(countryCode.equalsIgnoreCase("BY")) {
                location.setCountriyName(new SimpleStringProperty("Belarus"));
            }
            if(countryCode.equalsIgnoreCase("ID")) {
                location.setCountriyName(new SimpleStringProperty("Indonezia"));
            }
            if(countryCode.equalsIgnoreCase("GB")) {
                location.setCountriyName(new SimpleStringProperty("England"));
            }
        });
        locationData.forEach((location) -> {
        String countryName = location.getCountryName();
        if(countries.isEmpty() == true)
        {
            countries.add(countryName);
        }
        else
        {
            AtomicInteger ok = new AtomicInteger(0);
            countries.forEach((cName) ->
            {
                if(cName.equalsIgnoreCase(countryName))
                {
                    ok.set(1);
                }
            });
            if(ok.get() == 0)
            {
                countries.add(countryName);
            }
        }
    });
    }

    public static ObservableList<String> getCities(String country)
    {
        if(cities.isEmpty()==false)
            cities.clear();
        ObservableList<String> localCities = FXCollections.observableArrayList();
        if(country == null)
            return null;
        locationData.forEach(location -> {
            if(location.getCountryName().equalsIgnoreCase(country))
            {
                localCities.add(location.getCityName());
            }
        });
        return localCities;
    }

    public String indentMessageForLogging(String locationID, String cityName, String locationLatitude,
                                          String locationLongitude, String countryCode)
    {
        String messageToLog="\n" + locationID;
        if(locationID.length() < 8)
            messageToLog += "\t\t" + cityName;
        else
            messageToLog += "\t" + cityName;
        if(cityName.length() < 8)
            messageToLog += "\t\t" + locationLatitude;
        else
            messageToLog += "\t" + locationLatitude;
        if(locationLatitude.length() < 8)
            messageToLog += "\t\t" + locationLongitude;
        else
            messageToLog += "\t" +locationLongitude;
        if(locationLongitude.length() < 8)
            messageToLog += "\t\t" + countryCode;
        else
            messageToLog += "\t" + countryCode;

        return messageToLog;
    }

    public static String getTempAsString(Float temperature)
    {
        Integer temp = temperature.intValue();
        String stringTemperature = temp.toString();
        return stringTemperature + " \u00B0" + "C";
    }

    public String getWindSpeedAsString(Float windSpeed)
    {
        Integer integerWindSpeed = windSpeed.intValue();
        String windSpeedAsString = integerWindSpeed.toString();
        return windSpeedAsString + " m/s";
    }

    public String getHumidityAsString(Float humidity)
    {
        Integer integerHumidity = humidity.intValue();
        String humidityAsString = integerHumidity.toString();
        return humidityAsString + "%";
    }


    public void getWeatherInfo(String cityName) throws IOException {
        String urlString= "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=metric&appid=f2b674b2e5c3f8c80fa2d9446dbbea18";
        URL url=new URL(urlString);
        URLConnection conn= url.openConnection();

        BufferedReader reader= new BufferedReader( new InputStreamReader(conn.getInputStream()));
        String content=reader.readLine();
        JsonObject obj= Json.parse(content).asObject().get("main").asObject();
        Float temp = obj.get("temp").asFloat();
        Float humidity = obj.get("humidity").asFloat();
        JsonObject wObj = Json.parse(content).asObject().get("wind").asObject();
        Float windSpeed = wObj.get("speed").asFloat();

        rainValue.setText(getHumidityAsString(humidity));
        rainValue.setVisible(true);
        tempValue.setText(getTempAsString(temp));
        tempValue.setVisible(true);
        windValue.setText(getWindSpeedAsString(windSpeed));
        windValue.setVisible(true);
        locationData.forEach((location) -> {
            String cityN = location.getCityName();
            if(cityN.equalsIgnoreCase(cityName))
            {
                String messageForLogging = indentMessageForLogging(location.getLocationID(), location.getCityName(), location.getLatitude(),
                        location.getLongitude(), location.getCountryCode());
                logger.setMessageForLog(messageForLogging);
                String loggedMessage = LocationLogger.updateHistoryRecord();
                System.out.println(loggedMessage);
            }
        });
    }

    public void initialize()
    {
        if(this.logger == null)
            logger = new LocationLogger();
        tempValue.setVisible(false);
        rainValue.setVisible(false);
        windValue.setVisible(false);
        cityBox.setDisable(true);
        searchButton.setDisable(true);
        countryBox.setValue("Choose a country");
        countryBox.setItems(countries);
        countryBox.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {
                        if(newValue == null)
                            return;
                        cityBox.setDisable(false);
                        cities=getCities(newValue);
                        searchButton.setDisable(true);
                        cityBox.setValue("Choose a city");
                        tempValue.setVisible(false);
                        rainValue.setVisible(false);
                        windValue.setVisible(false);

                    }
                });
        cityBox.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {
                        cityBox.setItems(cities);
                        if(newValue == null)
                            return;
                        cityValue = new String(newValue);
                        if(!cityValue.equalsIgnoreCase("Choose a city"))
                        {
                            searchButton.setDisable(false);
                        }
                    }
                });
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                        getWeatherInfo(cityValue);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
