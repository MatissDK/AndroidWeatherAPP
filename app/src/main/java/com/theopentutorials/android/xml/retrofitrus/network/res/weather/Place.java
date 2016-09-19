
package com.theopentutorials.android.xml.retrofitrus.network.res.weather;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="place")
public class Place {

    @Element(name = "name")
    private String name;

    @Element(name = "phenomenon")
    private String phenomenon;

    //for night
    @Element(name = "tempmin", required=false )
    private String tempmin;

    //for day
    @Element(name = "tempmax", required=false)
    private String tempmax;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTempmin() {
        return tempmin;
    }

    public void setTempmin(String tempmin) {
        this.tempmin = tempmin;
    }

    public String getTempmax() {
        return tempmax;
    }

    public void setTempmax(String tempmax) {
        this.tempmax = tempmax;
    }

    public String getPhenomenon() {
        return phenomenon;
    }

    public void setPhenomenon(String phenomenon) {
        this.phenomenon = phenomenon;
    }
}
