package com.theopentutorials.android.xml.retrofitrus.network.res.weather;

import android.provider.ContactsContract;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name="forecast")
public class Forecast {

    @Attribute(name="date")
    private String attributeDate;

    @ElementList(inline = true)
    private List<Night> lnight = new ArrayList<Night>();

    @ElementList(inline = true)
    private List<Day> lday = new ArrayList<Day>();

    public String getAttributeDate() {
        return attributeDate;
    }

    public void setAttributeDate(String attributeDate) {
        this.attributeDate = attributeDate;
    }

    public List<Day> getLday() {
        return lday;
    }

    public void setLday(List<Day> lday) {
        this.lday = lday;
    }

    public List<Night> getLnight() {
        return lnight;
    }

    public void setLnight(List<Night> lnight) {
        this.lnight = lnight;
    }
}
