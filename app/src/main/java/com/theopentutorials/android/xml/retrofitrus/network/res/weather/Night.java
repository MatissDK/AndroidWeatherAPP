
package com.theopentutorials.android.xml.retrofitrus.network.res.weather;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;


@Root(name="night")
public class Night {

    @Element(name = "phenomenon")
    private String phenomenon;

    @Element(name = "tempmin")
    private String tempmin;

    @Element(name = "tempmax")
    private String tempmax;

    @Element(name = "text")
    private String text;

    @Element(name = "sea", required=false)
    private String sea;

    @Element(name = "peipsi", required=false)
    private String peipsi;

    @ElementList(inline = true, required=false)
    private List<Place> lplace = new ArrayList<Place>();

    @ElementList(inline = true, required=false)
    private List<Wind> lwind = new ArrayList<Wind>();


    public List<Place> getLplace() {
        return lplace;
    }

    public void setLplace(List<Place> lplace) {
        this.lplace = lplace;
    }

    public String getTempmax() {
        return tempmax;
    }

    public void setTempmax(String tempmax) {
        this.tempmax = tempmax;
    }

    public String getSea() {
        return sea;
    }

    public void setSea(String sea) {
        this.sea = sea;
    }

    public String getPhenomenon() {
        return phenomenon;
    }

    public void setPhenomenon(String phenomenon) {
        this.phenomenon = phenomenon;
    }

    public String getPeipsi() {
        return peipsi;
    }

    public void setPeipsi(String peipsi) {
        this.peipsi = peipsi;
    }

    public List<Wind> getLwind() {
        return lwind;
    }

    public void setLwind(List<Wind> lwind) {
        this.lwind = lwind;
    }

    public String getTempmin() {
        return tempmin;
    }

    public void setTempmin(String tempmin) {
        this.tempmin = tempmin;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
