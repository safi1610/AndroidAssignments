package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForcast extends AppCompatActivity {

    String selectedCity = "ottawa";

    ProgressBar prgrsBar;
    TextView minimum;
    TextView maximum;
    TextView current;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forcast);

        prgrsBar = findViewById(R.id.progressBar2);
        minimum.findViewById(R.id.minTemp);
        maximum.findViewById(R.id.maxTemp);
        current.findViewById(R.id.currentTemp);
        image.findViewById(R.id.weatherImage);
        prgrsBar.setVisibility(View.VISIBLE);

    }

    private class ForecastQuery extends AsyncTask<String, Integer, String>{
        String min;
        String max;
        String curr;
        Bitmap weatherImage;



        @Override
        protected String doInBackground(String... args) {
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=2ca3a1a3df4cc088dd240a07e3353f09&mode=xml&units=metric");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();

                XmlPullParser parse = Xml.newPullParser();
                parse.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parse.setInput(conn.getInputStream(), null);

                while(parse.next() != XmlPullParser.END_TAG){
                    if (parse.getEventType() != XmlPullParser.START_TAG){
                        continue;
                    }

                    String name = parse.getName();

                    if(name.equals("temperature")){
                        curr = parse.getAttributeValue(null, "value");
                        publishProgress(25);
                        min = parse.getAttributeValue(null, "min");
                        publishProgress(50);
                        max = parse.getAttributeValue(null, "max");
                        publishProgress(75);
                    }
                    else if(name.equals("weather")){
                        String iconName = parse.getAttributeValue(null, "icon");
                        String ACTIVITY_NAME = "WeatherForecast";
                        if(!fileExistence(iconName)){
                            Log.i(ACTIVITY_NAME, iconName + " Is Downloading from the url");
                            url = new URL("http://openweathermap.org/img/w/"+iconName+".png");
                            conn = (HttpURLConnection) url.openConnection();
                            conn.connect();
                            int respCode = conn.getResponseCode();

                            if(respCode == 200){
                                weatherImage = BitmapFactory.decodeStream(conn.getInputStream());
                            }

                            FileOutputStream outStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                            weatherImage.compress(Bitmap.CompressFormat.PNG, 80, outStream);
                            outStream.flush();
                            outStream.close();
                        }
                        else{
                            FileInputStream fileIn = null;
                            try{
                                Log.i(ACTIVITY_NAME, "Looking for file "+iconName+" locally");
                                fileIn = openFileInput(iconName);
                            }
                            catch (FileNotFoundException e){
                                e.printStackTrace();
                            }

                            weatherImage = BitmapFactory.decodeStream(fileIn);
                        }
                        parse.nextTag();

                        publishProgress(100);
                    }
                }
                parse.nextTag();
            }
            catch (IOException | XmlPullParserException e){
                e.printStackTrace();
            }

            return null;
        }

        public boolean fileExistence(String fileName){
            File file = getBaseContext().getFileStreamPath(fileName);
            return file.exists();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            prgrsBar.setVisibility(View.INVISIBLE);
            prgrsBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            minimum.setText(R.string.minTemp+ " Min: "+ min);
            minimum.setText(R.string.maxTemp+ " Max: "+ max);
            minimum.setText(R.string.currTemp+ " Current Temp: "+ curr);

            image.setImageBitmap(weatherImage);

            prgrsBar.setVisibility(View.INVISIBLE);
        }
    }


}