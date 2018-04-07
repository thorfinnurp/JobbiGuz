package com.example.thorfinnur.tobias;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends Activity {

    Intent mServiceIntent;
    private SensorService mSensorService;
    Context ctx;
    public Context getCtx() {
        return ctx;
    }

    TextView textOut1, textOut2, textOut3;
    public static final String EXTRA_MESSAGE = "com.example.thorfinnur.tobias";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        normalMode();
    }

    public void normalMode(){

        setContentView(R.layout.activity_main);
        Log.d("Hello","Hello2");
        HttpGetRequest httpGetRequest = new HttpGetRequest();
        String arr = "Hellop";
        httpGetRequest.execute(arr);
//        httpGetRequest.onPostExecute("aarrr");
        textOut3 = (TextView)findViewById(R.id.textView4);
        textOut2 = (TextView)findViewById(R.id.textView3);
        //Get the Intent that started this activity and extract the string
        textOut3.setText("SetText1");
        textOut2.setText("SetText2");
        //  startService(new Intent(this, NotificationService.class));

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {


                Uri uri = Uri.parse("https://www.instagram.com/jobbiguz/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
        Button radio = (Button) findViewById(R.id.toggleButton);

        radio.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                flexMode();

            }
        });
        Button scooter = (Button) findViewById(R.id.button2);
        scooter.setOnClickListener(new View.OnClickListener()
        {
            MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.scooter_fish);
            public void onClick(View v)
            {


                if(mp.isPlaying()){
                    mp.pause();
                } else {
                    mp.start();
                }

            }
        });

        ctx = this;
        mSensorService = new SensorService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run your one time code here

            SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("your_int_key", 95);
            editor.commit();

            // mark first time has runned.
            SharedPreferences.Editor editor2 = prefs.edit();
            editor2.putBoolean("firstTime", true);
            editor2.commit();

        }

    }

    public void flexMode(){

        setContentView(R.layout.activity_main2);
        HttpGetRequest httpGetRequest = new HttpGetRequest();
        String arr = "Hellop";
        httpGetRequest.execute(arr);
        textOut3 = (TextView)findViewById(R.id.textView4);
        textOut2 = (TextView)findViewById(R.id.textView3);
        textOut3.setText("SetText1");
        textOut2.setText("SetText2");

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Uri uri = Uri.parse("https://www.instagram.com/jobbiguz/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        Button radio = (Button) findViewById(R.id.toggleButton);
        radio.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                normalMode();
            }
        });
        

        Button facts = (Button) findViewById(R.id.toggleButton);
        facts.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                TextView myAwesomeTextView = (TextView)findViewById(R.id.textView6);

                myAwesomeTextView.setText("Ég er 100% prótín");
            }
        });

        Button scooter = (Button) findViewById(R.id.button2);
        scooter.setOnClickListener(new View.OnClickListener()
        {
            MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.scooter_fish);
            public void onClick(View v)
            {
                if(mp.isPlaying()){
                    mp.pause();
                } else {
                    mp.start();
                }
            }
        });

        ctx = this;
        mSensorService = new SensorService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
        }

    }



    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }

    @Override
    protected void onDestroy() {
        stopService(mServiceIntent);
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();

    }


    public class HttpGetRequest extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        public String Follower = "Ehv";

        @Override
        protected String doInBackground(String... params){
            String stringUrl = params[0];
            String result;
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL("https://www.instagram.com/jobbiguz/");
                //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
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
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            }
            catch(IOException e){
                e.printStackTrace();
                result = null;
            }

            Log.d("Result", result);
            return result;
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            org.jsoup.nodes.Document doc =  Jsoup.parse(result);
            //org.jsoup.nodes.Element div = doc.getElementById("meta");

           // String attr = div.attr("content");


            org.jsoup.nodes.Element meta = doc.select("meta").get(16);

            String ehv = meta.attr("content");

            Log.d("Followers1",ehv);
            //Element meta = doc.select("a").first();

            ehv = ehv.replaceAll("(.*?"+ "-" + ")" + "(.*?)" + "(" + "from" + ".*)", "$1$3");
            ehv = ehv.replace("-","");

            ehv = ehv.replace(",","\n");
            ehv = ehv.split(" J")[0];
            String ehv2 = "Jón Gunnar Björnsson(@jobbiguz)";

           // String linkHref = link.attr("href");

            //textOut3 = (TextView) textOut3.findViewById(R.id.textView3);
            // textView.setText("Whutt");

            textOut3.setText(ehv);
            textOut2.setText(ehv2);

            super.onPostExecute(result);
        }

    }
}
