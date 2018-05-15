package com.example.thorfinnur.tobias;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class MainActivity extends Activity {

    Intent mServiceIntent;
    private SensorService mSensorService;
    Context ctx;
    EditText eText;
    public Context getCtx() {
        return ctx;
    }

    ListView listView;
    TextView jobbiTana, nameJobbi, followerCountJobbi;

    public static final String EXTRA_MESSAGE = "com.example.thorfinnur.tobias";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        normalMode();
    }

    public void normalMode(){

        setContentView(R.layout.activity_main);


        weather();
        HttpGetRequest httpGetRequest = new HttpGetRequest();
        String arr = "Hellop";
        httpGetRequest.execute(arr);


        followerCountJobbi = (TextView)findViewById(R.id.textView4);
        nameJobbi = (TextView)findViewById(R.id.textView3);
        //Get the Intent that started this activity and extract the string
        followerCountJobbi.setText("SetText1");
        nameJobbi.setText("SetText2");

        Button opnaInsta = (Button) findViewById(R.id.opnaInstaButton);
        opnaInsta.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //opnar instagram accountinn hans jobba
                Uri uri = Uri.parse("https://www.instagram.com/jobbiguz/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        Button comment = (Button) findViewById(R.id.commentModeButton);
        comment.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //Opnar siduna med commentunum
                commentMode();

            }
        });

        Button username = (Button) findViewById(R.id.usernameButton);
        username.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //opnar lista af usernames
                usernameMode();

            }
        });

        Button radio = (Button) findViewById(R.id.normalModeButton);

        radio.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                flexMode();

            }
        });
        Button scooter = (Button) findViewById(R.id.scooterButton);
        scooter.setOnClickListener(new View.OnClickListener()
        {
            MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.scooter_fish);
            public void onClick(View v)
            {
                //spilar scooter
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

            //thessi kodi er bara keyrdur i fyrsta skipti sem appid er notad
            SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("your_int_key", 95);
            editor.commit();

            // mark first time has runned.
            SharedPreferences.Editor editor2 = prefs.edit();
            editor2.putBoolean("firstTime", true);
            editor2.commit();

            SharedPreferences spa = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor1 = spa.edit();
            String value = spa.getString("account", "");
            String appendedValue = "";
            editor1.putString("account", appendedValue).commit();



        }

        Button submit = (Button) findViewById(R.id.submitButton);
        eText = (EditText) findViewById(R.id.editText);

        submit.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {
                //baetir vid nafni a username instagram listann

                String str = eText.getText().toString();
                Toast msg = Toast.makeText(getBaseContext(),"added to list: " + str,Toast.LENGTH_LONG);
                msg.show();


                SharedPreferences spa = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = spa.edit();
                String value = spa.getString("account", "");
                String appendedValue = value + str +" ";
                editor.putString("account", appendedValue).commit();


                String value1 = spa.getString("account", "");
                Log.d("FLOTTURLISTI!", value1);
                eText.getText().clear();





            }
        });

    }
    public int weather(){



        Function.placeIdTask asyncTask =new Function.placeIdTask(new Function.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description,
                                      String weather_temperature, String weather_humidity,
                                      String weather_pressure, String weather_updatedOn,
                                      String weather_iconText, String sun_rise) {

                int likur = 0;
                double hiti = 0;

                Log.d("Hiti","value" + weather_temperature);
                weather_temperature = weather_temperature.replaceAll("[^0-9.]","");
                hiti = Double.parseDouble(weather_temperature);
                Log.d("Hiti","value " + hiti);
                if(hiti > 22){
                    likur = 99;
                }
                else if(hiti > 20){
                    likur = 92;
                }
                else if(hiti > 18){
                    likur = 73;
                }
                else if(hiti > 16){
                    likur = 62;
                }
                else if(hiti > 15){
                    likur = 55;
                }
                else if(hiti > 14){
                    likur = 45;
                }
                else if(hiti > 13){
                    likur = 31;
                }
                else if(hiti > 12){
                    likur = 26;
                }
                else if(hiti > 11){
                    likur = 23;
                }
                else if(hiti > 10){
                    likur = 19;
                }
                else if(hiti > 9){
                    likur = 16;
                }
                else if(hiti > 8){
                    likur = 13;
                }
                else if(hiti > 7){
                    likur = 9;
                }
                else if(hiti > 4){
                    likur = 7;
                }
                else if(hiti > 3){
                    likur = 5;
                }
                else{
                    likur = 2;
                }
                jobbiTana = (TextView)findViewById(R.id.textView7);
                jobbiTana.setText("Líkurnar á því að\nJobbi sé að tana: \n " + likur +"%");

            }
        });
        asyncTask.execute("64.1265", "-21.8174"); //  asyncTask.execute("Latitude", "Longitude")


        return 0;
    }


    public void flexMode(){
        //Jobba Flex mode
        setContentView(R.layout.activity_main2);
        HttpGetRequest httpGetRequest = new HttpGetRequest();
        String arr = "Hellop";
        httpGetRequest.execute(arr);
        followerCountJobbi = (TextView)findViewById(R.id.textView4);
        nameJobbi = (TextView)findViewById(R.id.textView3);
        followerCountJobbi.setText("SetText1");
        nameJobbi.setText("SetText2");
        weather();

        Button button = (Button) findViewById(R.id.opnaInstaButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Uri uri = Uri.parse("https://www.instagram.com/jobbiguz/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        Button radio = (Button) findViewById(R.id.normalModeButton);
        radio.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                normalMode();
            }
        });


        Button scooter = (Button) findViewById(R.id.scooterButton);
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

        Button comment = (Button) findViewById(R.id.commentModeButton);
        comment.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                commentMode();
            }
        });

        ctx = this;
        mSensorService = new SensorService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
        }

    }
    public void commentMode(){

        setContentView(R.layout.comment);
        HttpGetRequest httpGetRequest = new HttpGetRequest();
        String arr = "Hellop";

        Button comment = (Button) findViewById(R.id.commentModeButton);
        comment.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                normalMode();
            }
        });

        listView = (ListView) findViewById(R.id.list);

        String values [] = {"þykki!", "Vövðingi", "meistari", "Sæti ;)",
                "Hvað ertu að maxa í bekk?", "Þetta fæst ekki í bónus",
                "Hvað ertu að gefa þessum byssum?", "Netti[Sólgleraugna emoji]",
                "Yoked[thumbs up emoji]", "Hrikalegur","WoW","Þéttur!",
                "Flottur!", "Sæll og massaður!", "BoBa.... BOMBA!",
                " 6 stykki af [Krepptum bicep emoji]","Beast", "BeastMode",
                "Hot!","Dem", "Slæmur dagur fyrir JobbaGux haters",
                "[hjörtu í augunum emoji]"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);

        ctx = this;
        mSensorService = new SensorService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
        }

    }

    public void usernameMode(){
        //saekir lista af usernames og hendir i view
        setContentView(R.layout.listofusers);
        HttpGetRequest httpGetRequest = new HttpGetRequest();
        String arr = "Hellop";

        Button comment = (Button) findViewById(R.id.commentModeButton);
        comment.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                normalMode();
            }
        });

        listView = (ListView) findViewById(R.id.list);

        SharedPreferences spa = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);


        String value = spa.getString("account", "");
        String values[] = value.split(" ");
        for(int i = 0; i < values.length; i++)
        {
            Log.d("NAME!!!", values[i]);
            String name = values[i].trim();

            SharedPreferences spa1 = getSharedPreferences(name, Activity.MODE_PRIVATE);
            int myIntValue = spa1.getInt(name, 0);
            SharedPreferences spa2 = getSharedPreferences(name + "@", Activity.MODE_PRIVATE);
            String followers = spa2.getString(name + "@", "");

            String posts = Integer.toString(myIntValue);


            Log.d("NAME!!!", posts);
            values[i] = values[i] + " Posts: " + posts + " Followers: " + followers;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);

        //Starta SensorService ef ekki i gangi
        ctx = this;
        mSensorService = new SensorService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
        }

        Button hreinsa = (Button) findViewById(R.id.cleanUsernamesListButton);
        hreinsa.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {
                //Hreinsar username listann
                SharedPreferences spa = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = spa.edit();
                String appendedValue = "";
                editor.putString("account", appendedValue).commit();
                usernameMode();
            }
        });

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

            //saekir follower count og fleira fra jobba
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
            //hreinsar html strenginn fra jobba
            org.jsoup.nodes.Document doc =  Jsoup.parse(result);
            org.jsoup.nodes.Element meta = doc.select("meta").get(16);

            String ehv = meta.attr("content");
            Log.d("Followers1",ehv);
            //Element meta = doc.select("a").first();

            ehv = ehv.replaceAll("(.*?"+ "-" + ")" + "(.*?)" + "(" + "from" + ".*)", "$1$3");
            ehv = ehv.replace("-","");

            ehv = ehv.replace(",","\n");

            String ehv1 = ehv.split("from ")[0];

            String ehv2 = ehv.split("from ")[1];
            Log.d("Followers2", ehv1);
            Log.d("Followers2", ehv);
           // String ehv2 = "Jón Gunnar Björnsson(@jobbiguz)";

            followerCountJobbi.setText(ehv1);
            nameJobbi.setText(ehv2);

            super.onPostExecute(result);
        }

    }

}
