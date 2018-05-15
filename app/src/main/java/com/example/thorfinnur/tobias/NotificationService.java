package com.example.thorfinnur.tobias;

/**
 * Created by Thorfinnur on 06/04/18.
 */

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Thorfinnur Petursson on 2/8/2018.
 * This is a service that reads notifications from the user
 */


public class NotificationService extends Service {
    private NotificationManager mNM;

    // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.
    private int NOTIFICATION;

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        NotificationService getService() {
            return NotificationService.this;
        }
    }

    @Override
    public void onCreate() {
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        NotificationID notificationID = new NotificationID();
        NOTIFICATION = notificationID.getID();
        // Display a notification about us starting.  We put an icon in the status bar.
        showNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        NotificationID notificationID = new NotificationID();
        NOTIFICATION = notificationID.getID();
        showNotification();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
        mNM.cancel(NOTIFICATION);

        // Tell the user we stopped.

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();

    /**
     * Show a notification while this service is running.
     */
    public class NotificationID {
        private final AtomicInteger c = new AtomicInteger(0);
        public int getID() {
            return c.incrementAndGet();
        }
    }

    private void showNotification() {

        SharedPreferences spa = getSharedPreferences("lastPost", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = spa.edit();
        String name = spa.getString("lastPost", "");


        // In this sample, we'll use the same text for the ticker and the expanded notification
        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        // Set the info for the views that show in the notification panel.
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.jobbi_logo)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.jobbi_logo))
                .setTicker("Ný " + name +"mynd á insta!")  // the status text
                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentTitle("Ný " + name + " mynd á insta!")  // the label of the entry
                .setContentText("Á ekki að smella læki á " + name + "? :)")  // the contents of the entry
                .setContentIntent(contentIntent)  // The intent to send when the entry is clicked
                .build();

        // Send the notification.
        mNM.notify(NOTIFICATION, notification);
    }

}
