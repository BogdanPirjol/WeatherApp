package ro.mta.se.lab.Controller;

import de.saxsys.javafx.test.JfxRunner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import ro.mta.se.lab.Model.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JfxRunner.class)

public class  LocationControllerMock{
    private ObservableList<Locations> locationData = FXCollections.observableArrayList();
    private LocationController controller;

    @Mock
    LocationLogger logger;

    @BeforeClass
    static public void startTesting()
    {
        System.out.println("Start");
    }

    @Before
    public void setUp() throws Exception{
        locationData.add(new Locations("683506", "Bucharest", "44.432251", "26.10626", "RO"));
        locationData.add(new Locations("666767", "Slatina", "44.433331", "24.366671","RO"));
        controller = new LocationController(locationData);
        logger=mock(LocationLogger.class);
        logger.setMessageForLog("Bucharest");
    }

    @Test
    public void getWeatherInfo() throws IOException
    {
        when(logger.updateHistoryRecord()).thenReturn("ceva");
    }

}