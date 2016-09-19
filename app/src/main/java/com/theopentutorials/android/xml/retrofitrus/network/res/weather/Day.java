
package com.theopentutorials.android.xml.retrofitrus.network.res.weather;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Headers;

@Root(name="day", strict=false)
public class Day {

    @Element(name = "phenomenon")
    private String phenomenon;

    @Element(name = "tempmin")
    private String tempmin;

    @Element(name = "tempmax")
    private String tempmax;

    @Element(name = "text")
    private String text;

    @ElementList(inline = true, required=false)
    private List<Place> lplace = new ArrayList<Place>();

    @ElementList(inline = true, required=false)
    private List<Wind> lwind = new ArrayList<Wind>();

    @Element(name = "peipsi", required=false)
    private String peipsi;

    // ======== just closing tag for sea ==========
    @Element(name = "sea", required=false)
    private String sea;

    public List<Place> getLplace() {
        return lplace;
    }

    public void setLplace(List<Place> lplace) {
        this.lplace = lplace;
    }

    public List<Wind> getLwind() {
        return lwind;
    }

    public void setLwind(List<Wind> lwind) {
        this.lwind = lwind;
    }

    public String getPeipsi() {
        return peipsi;
    }

    public void setPeipsi(String peipsi) {
        this.peipsi = peipsi;
    }

    public String getPhenomenon() {
        return phenomenon;
    }

    public void setPhenomenon(String phenomenon) {
        this.phenomenon = phenomenon;
    }

    public String getSea() {
        return sea;
    }

    public void setSea(String sea) {
        this.sea = sea;
    }

    public String getTempmax() {
        return tempmax;
    }

    public void setTempmax(String tempmax) {
        this.tempmax = tempmax;
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
