package id.net.gmedia.remotezigy.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

import id.net.gmedia.remotezigy.R;
import id.net.gmedia.remotezigy.RemoteActivity;
import id.net.gmedia.remotezigy.Utils.SessionManager;

import static id.net.gmedia.remotezigy.Utils.ServiceUtils.TAG_EMPTY;

public class MyMessagingService extends FirebaseMessagingService {
    private static String TAG = "MyFirebaseMessagingService";
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d(TAG, "Refreshed token: " + s);
    }

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, String.valueOf(remoteMessage.getData()));
//        if(!remoteMessage.getData().get("jenis").equals("empty")){
        RemoteActivity.remoteActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RemoteActivity.playVideo(RemoteActivity.remoteActivity,remoteMessage.getData().get("jenis"));
//                    RemoteActivity.playVideo(RemoteActivity.getAppContext(),remoteMessage.getData().get("jenis"));
            }
        });
//        }else{
//
//        }
    }
}
