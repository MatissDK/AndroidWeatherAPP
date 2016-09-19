
package com.theopentutorials.android.xml.retrofitrus.network.res.weather;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

import java.util.List;


@Root(name="wind", strict=false)
public class Wind {

    @Element(name = "name")
    private String name;

    @Element(name = "direction")
    private String direction;

    @Element(name = "speedmin")
    private String speedmin;

    @Element(name = "speedmax")
    private String speedmax;

    /* ======== ??? just closing tag ??? ======== */
    @Element(name = "gust", required = false)
    public String gust;


    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getSpeedmin() {
        return speedmin;
    }

    public void setSpeedmin(String speedmin) {
        this.speedmin = speedmin;
    }

    public String getSpeedmax() {
        return speedmax;
    }

    public void setSpeedmax(String speedmax) {
        this.speedmax = speedmax;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGust() {
        return gust;
    }

    public void setGust(String gust) {
        this.gust = gust;
    }
}
