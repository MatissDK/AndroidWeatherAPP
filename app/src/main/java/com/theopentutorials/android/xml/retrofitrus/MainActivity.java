package com.theopentutorials.android.xml.retrofitrus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.theopentutorials.android.xml.retrofitrus.conversion.EnglishNumberToWords;
import com.theopentutorials.android.xml.retrofitrus.network.DataManager;
import com.theopentutorials.android.xml.retrofitrus.network.NetworkStatus;
import com.theopentutorials.android.xml.retrofitrus.network.res.weather.Day;
import com.theopentutorials.android.xml.retrofitrus.network.res.weather.Forecast;
import com.theopentutorials.android.xml.retrofitrus.network.res.weather.Forecasts;
import com.theopentutorials.android.xml.retrofitrus.network.res.weather.Night;
import com.theopentutorials.android.xml.retrofitrus.network.res.weather.Place;
import com.theopentutorials.android.xml.retrofitrus.network.res.weather.Wind;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //==== VIEWS =====
    private TextView tnight, tday, tdate, tday_text, tnight_text;
    private ListView listView;
    private LinearLayout innerLayout;
    private ScrollView scrollView;
    private Button rButton;
    private String keysharedPreferences;

    // === NETWORK ====
    private DataManager mDataManager;

    // == FORECAST == //
    private List<Forecast> forecastList;
    private boolean forecastReceived = false;
    private String curDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Initialization();

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        keysharedPreferences = sharedPreferences.getString("key", "N/A");

        // getForecast() method populates forecastList if there is no forecast data received
        // otherwise we used populateData() method with already populated forecastList
        if (!forecastReceived)
            getForecast();
        else {
            populateData(forecastList, curDate);
        }
    }


    private void Initialization() {
        mDataManager = DataManager.getInstance();

        tday = (TextView) findViewById(R.id.day);
        tnight = (TextView) findViewById(R.id.night);
        tdate = (TextView) findViewById(R.id.date);
        tday_text = (TextView) findViewById(R.id.day_text);
        tnight_text = (TextView) findViewById(R.id.night_text);
        rButton = (Button) findViewById(R.id.reload_button);
        rButton.setOnClickListener(new myButtonClick());
        listView = (ListView) findViewById(R.id.listView);
        innerLayout = (LinearLayout) findViewById(R.id.innerList);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
    }

    //OnClick listener for reload button
    class myButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            saveToSharedPreferences("1");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }


    //OnItemClickListener for the list
    class myItemSelected implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView textView = (TextView) view;
            String selectedCity = textView.getText().toString();
            saveToSharedPreferences(selectedCity);
            populateDateCity(forecastList, curDate, selectedCity);
        }
    }

    //save to shared preferences
    private void saveToSharedPreferences(String city) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("key", city);
        editor.commit();
    }


    public void populateDateCity(List<Forecast> forecastList, String curDate, String city) {
        innerLayout.setBackgroundColor(Color.argb(255, 33, 33, 33));
        scrollView.scrollTo(0, 0);

        tday.setText("");
        tnight.setText("");
        tday_text.setText("");
        tnight_text.setText("");

        Forecast curForecast = selectForecast(forecastList, curDate);
        Day curDay = curForecast.getLday().get(0);
        Night curNight = curForecast.getLnight().get(0);

        String tempPlaceName = "";
        String tempPlacePhenomDay = "";
        String tempPlaceMaxDay = "";
        String tempPlaceMinNight = "";
        String tempPlacePhenomNight = "";

        for (Place curPlaceDay : curDay.getLplace())
            if (curPlaceDay.getName().equals(city)) {
                tempPlaceName = curPlaceDay.getName();
                tempPlacePhenomDay = curPlaceDay.getPhenomenon();
                tempPlaceMaxDay = curPlaceDay.getTempmax();
            }

        for (Place curPlaceNight : curNight.getLplace())
            if (curPlaceNight.getName().equals(city)) {
                tempPlacePhenomNight = curPlaceNight.getPhenomenon();
                tempPlaceMinNight = curPlaceNight.getTempmin();
            }

        if (tempPlacePhenomDay.contains("clouds") || tempPlacePhenomDay.contains("Cloudy"))
            innerLayout.setBackgroundResource(R.drawable.clouds);
        else if (tempPlacePhenomDay.contains("rain") || tempPlacePhenomDay.contains("Rainy"))
            innerLayout.setBackgroundResource(R.drawable.rain);
        else if (tempPlacePhenomDay.contains("sun") || tempPlacePhenomDay.contains("Sunny"))
            innerLayout.setBackgroundResource(R.drawable.sun);
        else if (tempPlacePhenomDay.contains("snow"))
            innerLayout.setBackgroundResource(R.drawable.snow);

        tday.setText(tempPlaceName + "\n" + "min night: " + tempPlaceMinNight + "... max day " + tempPlaceMaxDay);
        tday_text.setText("Day information: " + tempPlacePhenomDay + "\n\n" + "Night information: " + tempPlacePhenomNight);

    }

    private String NumberToWords(String yourString) {
        int yourNumber = Integer.parseInt(yourString);
        String numberString = EnglishNumberToWords.convert(Math.abs(yourNumber));

        if (yourNumber == 0)
            return numberString;
        return yourNumber < 0 ? "minus " + numberString : "plus " + numberString;
    }


    private void getForecast() {
        if (NetworkStatus.isNetworkAvailable(this)) {

            Call<Forecasts> call = mDataManager.getForcasts();
            call.enqueue(new Callback<Forecasts>() {
                @Override
                public void onResponse(Call<Forecasts> call, Response<Forecasts> response) {
                    if (response.isSuccessful()) {
                        forecastList = response.body().getForecastsList();
                        forecastReceived = true;

                        Forecast forecast = forecastList.get(0);
                        curDate = forecast.getAttributeDate();

                        populateData(forecastList, curDate);

                        if (keysharedPreferences.equals("N/A") || keysharedPreferences.equals("1"))
                            populateData(forecastList, curDate);
                        else
                            populateDateCity(forecastList, curDate, keysharedPreferences);

                    } else {
                        Toast.makeText(getApplicationContext(), "Error receiving data.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Forecasts> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Error connecting to the server.FAIL", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Network not available. Try later", Toast.LENGTH_LONG).show();
        }
    }


    /*private String changeCharsetEncoding(String curString) {
        Charset iso88591 = Charset.forName("ISO-8859-1");
        Charset utf8 = Charset.forName("UTF-8");

        CharBuffer curBuffer = iso88591.decode(ByteBuffer.wrap(curString.getBytes()));
        ByteBuffer outputBuffer = utf8.encode(curBuffer);

        return new String(outputBuffer.array(), utf8);
    }*/

    // Display current forecast data on screen
    private void populateData(List<Forecast> forecastList, String curDate) {
        Forecast curForecast = selectForecast(forecastList, curDate);
        Day curDay = curForecast.getLday().get(0);
        Night curNight = curForecast.getLnight().get(0);

        String tempDayMin, tempDayMax, tempNightMin, tempNightMax, tempDayMinMax, tempNightMinMax, tempDayText, tempNightText;

        tempDayMin = curDay.getTempmin();
        tempDayMax = curDay.getTempmax();
        tempNightMin = curNight.getTempmin();
        tempNightMax = curNight.getTempmax();

        tempDayText = curDay.getText();
        tempNightText = curNight.getText();

        tempDayMinMax = "min " + tempDayMin + " C" + " ... " + "max " + tempDayMax + " C";
        tempNightMinMax = "min " + tempNightMin + " C" + " ... " + "max " + tempNightMax + " C";

        String wordsDayMin = NumberToWords(tempDayMin);
        String wordsDayMax = NumberToWords(tempDayMax);
        String wordsNightMin = NumberToWords(tempNightMin);
        String wordsNightMax = NumberToWords(tempNightMax);

        CreateList(curDay);

        String windDay = "";
        String windNigh = "";

        for (Wind curWind : curDay.getLwind()){
            windDay += "\n" + curWind.getName() + " wind direction: " + curWind.getDirection() + ", min speed: " +curWind.getSpeedmin() + " max speed: " + curWind.getSpeedmax() + "\n" ;
        }
        for (Wind curWind : curNight.getLwind()){
            windNigh += "\n " + curWind.getName() + " wind direction: " + curWind.getDirection() + ", min speed: " +curWind.getSpeedmin() + " max speed: " + curWind.getSpeedmax() + "\n" ;
        }

        tdate.setText(curDate);
        tday.setText("Day temperature: " + tempDayMinMax + " " + "(" + wordsDayMin + "..." + wordsDayMax + ")");
        tnight.setText("Night temperature : " + tempNightMinMax + " " + "(" + wordsNightMin + "..." + wordsNightMax + ")");
        tday_text.setText("Day information: " + tempDayText + "\n" + windDay);
        tnight_text.setText("Night information: " + tempNightText + "\n" + windNigh);
    }

    // create ListView
    private void CreateList(Day curDay) {
        List<String> myTempList = new ArrayList<>();
        for (Place place : curDay.getLplace())
            myTempList.add(place.getName());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, myTempList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new myItemSelected());
    }

    // Return a forecast according to the selected date
    private Forecast selectForecast(List<Forecast> forecastList, String curDate) {
        for (Forecast curForecast : forecastList)
            if (curForecast.getAttributeDate().equals(curDate))
                return curForecast;
        return null;
    }

}
