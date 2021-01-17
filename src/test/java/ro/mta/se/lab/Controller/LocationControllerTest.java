package ro.mta.se.lab.Controller;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ro.mta.se.lab.Model.Locations;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.*;

public class LocationControllerTest {

    private ObservableList<String> citiesTest = FXCollections.observableArrayList();
    private ObservableList<Locations> location = FXCollections.observableArrayList();
    private LocationController lController;
    private ObservableList<String> cities= FXCollections.observableArrayList();

    @Before
    public void setUp() throws Exception {
        System.out.println("Test started!");
        location.add(new Locations("683506", "Bucharest", "44.432251", "26.10626", "RO"));
        location.add(new Locations("666767", "Slatina", "44.433331", "24.366671","RO"));
        lController= new LocationController(location);
        lController.setCountryName();
        cities.add("Bucharest");
        cities.add("Slatina");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Test ended");
    }

    @Test
    public void getTempAsString() {
        assertEquals("80 \u00B0C", LocationController.getTempAsString((float)80.00));
        assertEquals("23 \u00B0C", LocationController.getTempAsString((float) 23.61));
        assertEquals("-12 \u00B0C", LocationController.getTempAsString((float) -12.913));
    }

    @Test
    public void getCitiesTest(){
        assertEquals(cities, LocationController.getCities("Romania"));
        assertEquals(null, LocationController.getCities(null));
    }

}