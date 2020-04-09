package id.net.gmedia.remotezigy.fcm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import id.net.gmedia.remotezigy.receivertv.RemoteActivity;

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
