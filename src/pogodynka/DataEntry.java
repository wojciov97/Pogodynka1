package pogodynka;





import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DataEntry {
    private String weatherURL;
    private String city;
    private String country;
    private float temperature;
    private float pressure;

    DataEntry(){
        city="undefined";
    }
    DataEntry(String city, String country,float temperature, float pressure){
        this.city=city;
        this.country=country;
        this.temperature=temperature;
        this.pressure=pressure;
    }


    public void setCity (String city) {
        this.city = city;
    }
    public void setCountry (String country) {
        this.country = country;
    }
    public void setPressure (float pressure) {
        this.pressure = pressure;
    }
    public void setTemperature (float temperature) {
        this.temperature = temperature;
    }



    public String getCity (){
        return city;
    }
    public String getCountry() { return country;    }
    public float getPressure() {return pressure;    }
    public float getTemperature() {return temperature;   }

}
