package ro.mta.se.lab.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Locations {
    private StringProperty locationID;
    private StringProperty locationName;
    private StringProperty latitude;
    private StringProperty longitude;
    private StringProperty countryCode;
    private StringProperty countryName;

    public Locations(String locationID, String locationName, String latitude, String longitude, String countryCode)
    {
        this.locationID= new SimpleStringProperty(locationID);
        this.locationName= new SimpleStringProperty(locationName);
        this.latitude= new SimpleStringProperty(latitude);
        this.longitude= new SimpleStringProperty(longitude);
        this.countryCode= new SimpleStringProperty(countryCode);
    }

    public StringProperty locationIDProperty()
    {
        return this.locationID;
    }

    public StringProperty locationNameProperty()
    {
        return this.locationName;
    }

    public StringProperty latitudeProperty()
    {
        return this.latitude;
    }

    public StringProperty longitudeProperty()
    {
        return this.longitude;
    }

    public StringProperty countryCodeProperty()
    {
        return this.countryCode;
    }

    public String getCountryCode()
    {
        return this.countryCode.get();
    }

    public String getCountryName()
    {
        return this.countryName.get();
    }

    public void setCountriyName(StringProperty countryName)
    {
        this.countryName=countryName;
    }

    public String getCityName()
    {
        return this.locationName.get();
    }

    public String getLocationID() { return this.locationID.get(); }

    public String getLatitude() { return this.latitude.get(); }

    public String getLongitude() { return this.longitude.get(); }


}
