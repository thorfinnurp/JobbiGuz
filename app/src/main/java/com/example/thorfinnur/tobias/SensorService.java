package com.example.thorfinnur.tobias;

/**
 * Created by Thorfinnur on 06/04/18.
 */

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class SensorService extends Service {
    public int counter=0;

    public SensorService(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
    }

    public SensorService() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();

        Log.d("OnSTart","Byrjad!!");
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        Log.d("OnDestroy","Buid!!");
        Intent broadcastIntent = new Intent("uk.ac.shef.oak.ActivityRecognition.RestartSensor");
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    public void startTimer() {
        Log.d("TimerStart","Timinn lidur hratt i gledibankanum");
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 150000, 150000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {

        timerTask = new TimerTask() {

            public void run() {
                HttpGetRequest httpGetRequest = new HttpGetRequest();
                Log.i("in timer", "in timer ++++  "+ (counter++));
                Log.d("TimerTask", "slinur");
                httpGetRequest.execute("arr");

            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
    public void sendNotification(){
        startService(new Intent(this, NotificationService.class));
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public class HttpGetRequest extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        public String Follower = "Ehv";

        @Override
        protected String doInBackground(String... params) {
            String stringUrl = params[0];
            String result;
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL("https://www.instagram.com/jobbiguz/");
                //Create a connection
                HttpURLConnection connection = (HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();
                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
                result = null;
            }

            Log.d("Result", result);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            org.jsoup.nodes.Document doc = Jsoup.parse(result);
            org.jsoup.nodes.Element meta = doc.select("meta").get(16);

            String ehv = meta.attr("content");

            Log.d("Followers1", ehv);

            ehv = ehv.split("g")[1];

            ehv = ehv.split(" P")[0];
            ehv = ehv.replaceAll(",", " ");
            ehv = ehv.replaceAll(" ", "");
            ehv = ehv.trim();
            int posts = Integer.parseInt(ehv);




            SharedPreferences spa = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
            int myIntValue = spa.getInt("your_int_key", -1);


            String tala = Integer.toString(myIntValue);
            Log.d("Skellur", ehv);
            Log.d("Skellur2", tala);

            if(Integer.parseInt(ehv) != Integer.parseInt(tala)){

                sendNotification();

                SharedPreferences store = getSharedPreferences("your_int_key", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = store.edit();
                editor.putInt("your_int_key", posts);
                editor.commit();

            }


            super.onPostExecute(result);
        }
    }
}