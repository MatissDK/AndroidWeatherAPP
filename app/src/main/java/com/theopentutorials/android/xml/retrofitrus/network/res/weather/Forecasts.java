
package com.theopentutorials.android.xml.retrofitrus.network.res.weather;


import java.util.ArrayList;
import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "forecasts")
public class Forecasts {

    @ElementList(inline = true)
    private List<Forecast> forecastsList = new ArrayList<Forecast>();

    public List<Forecast> getForecastsList() {
        return forecastsList;
    }

    public void setForecastsList(List<Forecast> forecastsList) {
        this.forecastsList = forecastsList;
    }



    /*@ElementList(inline = true)
    private List<Forecast> forecastsList;

    public List<Forecast> getForecastsList() {
        return forecastsList;
    }

    public void setForecastsList(List<Forecast> forecastsList) {
        this.forecastsList = forecastsList;
    }*/
}
