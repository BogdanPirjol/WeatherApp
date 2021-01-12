package ro.mta.se.lab.Main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.mta.se.lab.Controller.LocationController;
import ro.mta.se.lab.Model.Locations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MainClass extends Application {

    private ObservableList<Locations> locationData = FXCollections.observableArrayList();

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        initLocations("Locations.txt");
        FXMLLoader loader=new FXMLLoader();
        try {
            loader.setLocation(this.getClass().getResource("/view/LocationView.fxml"));
            loader.setController(new LocationController(locationData));
            stage.setTitle("Weather App");
            stage.setScene(new Scene(loader.load()));
            stage.show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    public void initLocations(String fileName) {
        File locationsFile= new File(fileName);
        try {
            Scanner reader = new Scanner(locationsFile);
            String[] data;
            reader.nextLine();
            while(reader.hasNext())
            {
                data=reader.nextLine().split("[ \\\t]+");
                locationData.add(new Locations(data[0], data[1], data[2], data[3], data[4]));
            }
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

}
