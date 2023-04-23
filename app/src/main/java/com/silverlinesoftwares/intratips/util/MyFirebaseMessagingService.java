package com.silverlinesoftwares.intratips.util;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.silverlinesoftwares.intratips.MainActivity;
import com.silverlinesoftwares.intratips.R;

/**
 * Created by sanjeev on 9/2/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    /**
     * Called when message is received.
     *
     */
    // [START receive_message]
    String messs;
    String titttt;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
//        super.onMessageReceived(remoteMessage);
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
//        Log.d(TAG, "From: " + remoteMessage.getFrom());
//        Log.d("msg", "onMessageReceived: " + remoteMessage.getData().get("message"));
//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.web_hi_res_512)
//                .setSound(defaultSoundUri)
//                .setContentTitle("test")
//                .setContentText(remoteMessage.getData().get("message"));
//        NotificationManager manager = (NotificationManager)     getSystemService(NOTIFICATION_SERVICE);
//        manager.notify(0, builder.build());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            sendNotification(remoteMessage.getData().get("title"),remoteMessage.getData().get("message"));
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            sendNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
          //  ShowNotification();
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
  //      messs=remoteMessage.getNotification().getBody();
    //    titttt=remoteMessage.getNotification().getTitle();

//        sendNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Handle time allotted to BroadcastReceivers.
     */



    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }


    private RemoteViews getComplexNotificationView(String t,String m) {
        // Using RemoteViews to bind custom layouts into Notification
        RemoteViews notificationView = new RemoteViews(
                getApplicationContext().getPackageName(),
                R.layout.activity_custom_notification
        );
        // Locate and set the Image into customnotificationtext.xml ImageViews
        notificationView.setImageViewResource(R.id.imagenotileft, R.drawable.web_hi_res_512);

        // Locate and set the Text into customnotificationtext.xml TextViews
        notificationView.setTextViewText(R.id.title,t);
        notificationView.setTextViewText(R.id.text, m);

        return notificationView;
    }
    @SuppressLint("UnspecifiedImmutableFlag")
    private void sendNotification(String messageBody, String title) {
        try {
            Intent intent = new Intent(this, MainActivity.class);
            String channelId = "Intraday01";
            int importance = 4;
            String channelName = "Intraday";
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                        PendingIntent.FLAG_ONE_SHOT|PendingIntent.FLAG_MUTABLE);
            }
            else{
                    PendingIntent.getActivity(this, 0 /* Request code */, intent,
                            PendingIntent.FLAG_ONE_SHOT);
            }

            Uri defaultSoundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.serious);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                    // Set Icon
                    .setSmallIcon(R.drawable.web_hi_res_512)
                    .setSound(defaultSoundUri)
                    // Set Ticker Message
                    .setChannelId(channelId)
                    .setNumber(1)
                    .setWhen(System.currentTimeMillis())
                    .setColor(255)
                    .setTicker(getApplicationContext().getString(R.string.app_name))
                    // Dismiss Notification
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    // Set PendingIntent into Notification
                    .setOnlyAlertOnce(true)
                    .setContentIntent(pendingIntent);

            // build a complex notification, with buttons and such
            //
            builder = builder.setContent(getComplexNotificationView(title, messageBody));

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            long currentTimeMillis = System.currentTimeMillis();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                mChannel.setSound(null, null);
                mChannel.setLightColor(Color.parseColor("#4CAF50"));
                mChannel.setLockscreenVisibility(0);
                AudioAttributes att = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .build();
                mChannel.setDescription("View Passenger Details");
                mChannel.enableLights(true);
                mChannel.enableVibration(true);
                mChannel.setSound(defaultSoundUri, att);
                notificationManager.createNotificationChannel(mChannel);
            }


//        Uri uri = Uri.parse("message_notifications_settings_tone", "" + default_message_notifications_settings_tone));

            long[] vibrate = new long[]{2000, 2000, 2000, 2000, 2000};
            builder.setVibrate(vibrate);

            notificationManager.notify((int) currentTimeMillis, builder.build());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



}