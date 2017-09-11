package net.glm.firebasetest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Michael on 08/09/2017.
 */

public class MyFrebaseMessagingService extends FirebaseMessagingService{

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) { //will recieve message when in foreground

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        //1st Stage
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);

        //3rd Stage
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent,PendingIntent.FLAG_ONE_SHOT);

        //2nd Stage
        notificationBuilder.setContentTitle("FCM Notification");
        notificationBuilder.setContentText(remoteMessage.getNotification().getBody());
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setAutoCancel(true);

        Notification notification = notificationBuilder.build();
        notificationManager.notify(1,notification);

    }
}
