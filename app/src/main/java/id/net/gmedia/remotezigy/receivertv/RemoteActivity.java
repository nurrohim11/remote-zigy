package id.net.gmedia.remotezigy.receivertv;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

import id.net.gmedia.remotezigy.R;
import id.net.gmedia.remotezigy.Utils.CustomVideoView;
import id.net.gmedia.remotezigy.Utils.ItemValidation;
import id.net.gmedia.remotezigy.Utils.SelectedServer;
import id.net.gmedia.remotezigy.Utils.ServiceUtils;
import id.net.gmedia.remotezigy.Utils.SessionManager;

public class RemoteActivity extends AppCompatActivity {
    static CustomVideoView cvPreview;
    RelativeLayout rlOk, rlMinus, rlPlus;
    ImageView imgPower, imgHome, imgBack, imgTop, imgBottom, imgPrev, imgNext, imgMenu, imgCursor;
    RelativeLayout rlHome, rlBack, rlUp, rlDown, rlLeft, rlRight;
    private ItemValidation iv = new ItemValidation();
    private InetAddress hostAddress;
    private int hostPort;
    private final String TAG = "remotetesting";
    private String link_tv = "";
    static ProgressBar pbLoading;
    private static double scaleVideo = 1;
    SessionManager sessionManager;
    public static RemoteActivity remoteActivity;
    private static Context mContext;
    public static LinearLayout llNoteFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);
        sessionManager = new SessionManager(RemoteActivity.this);
        remoteActivity = this;
        mContext = RemoteActivity.this;

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initUi();
    }

    public static Context getAppContext(){
        return mContext;
    }

    private void initUi(){
        cvPreview = findViewById(R.id.cv_preview);
        llNoteFound = findViewById(R.id.ll_not_found);
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading);
        imgPower = findViewById(R.id.img_power);
        imgPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link_tv ="";
//                connectToHost("26");
                try {
                    clearConnectedHost();
                }catch (Exception e){
                    e.printStackTrace();
                }
                finish();
            }
        });

        imgHome = findViewById(R.id.img_home);
        rlHome = findViewById(R.id.rl_home);
        rlHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link_tv ="";
                connectToHost("3");
            }
        });

        imgBack = findViewById(R.id.img_back);
        rlBack = findViewById(R.id.rl_back);
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link_tv ="";
                connectToHost("4");
            }
        });

        rlOk = findViewById(R.id.rl_ok);
        rlOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToHost("23");
            }
        });

        imgTop = findViewById(R.id.img_top);
        rlUp = findViewById(R.id.rl_up);
        rlUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToHost("19");
            }
        });

        imgBottom = findViewById(R.id.img_bottom);
        rlDown = findViewById(R.id.rl_down);
        rlDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToHost("20");
            }
        });

        imgPrev = findViewById(R.id.img_prev);
        rlLeft = findViewById(R.id.rl_left);
        rlLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToHost("21");
            }
        });

        imgNext = findViewById(R.id.img_next);
        rlRight = findViewById(R.id.rl_right);
        rlRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToHost("22");
            }
        });

//        imgMenu = findViewById(R.id.img_menu);
//        imgMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(RemoteActivity.this, "Menu", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        imgCursor = findViewById(R.id.img_cursor);
//        imgCursor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(RemoteActivity.this, "Cursor", Toast.LENGTH_SHORT).show();
//            }
//        });

//        rlMinus = findViewById(R.id.rl_minus);
//        rlMinus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                connectToHost("25");
//            }
//        });
//        rlPlus = findViewById(R.id.rl_plus);
//        rlPlus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                connectToHost("24");
//            }
//        });
    }

    private void connectToHost(String keyCode) {

        try {
            hostAddress = InetAddress.getByName(SelectedServer.host);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        hostPort = iv.parseNullInteger(SelectedServer.port);

        if (hostAddress == null) {
            Log.e(TAG, "Host Address is null");
            return;
        }

        String ipAddress = getMyIPAddress(true);
        JSONObject jsonData = new JSONObject();

        try {
            jsonData.put("request", ServiceUtils.REQUEST_CONNECT_CLIENT);
            jsonData.put("ipAddress", ipAddress);
            jsonData.put("keyCode", keyCode);
            jsonData.put("type", "");
            jsonData.put("fcm_client",SelectedServer.fcm_id);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "can't put request");
            return;
        }
//        Toast.makeText(this, sessionManager.getLink(), Toast.LENGTH_SHORT).show();
//        if(!sessionManager.getLink().equals("")){
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    playVideo(RemoteActivity.this, sessionManager.getLink());
//                }
//            });
//        }else{
//            Log.d(">>>>>>","kosong");
//        }
        new SocketServerTask().execute(jsonData);
    }

    private static String getMyIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        boolean isIPv4 = sAddr.indexOf(':')<0;
                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }

    private class SocketServerTask extends AsyncTask<JSONObject, Void, Void> {
        private JSONObject jsonData;
        private boolean success;

        @Override
        protected Void doInBackground(JSONObject... params) {
            Socket socket = null;
            DataInputStream dataInputStream = null;
            DataOutputStream dataOutputStream = null;
            jsonData = params[0];

            try {

                // Create a new Socket instance and connect to host
                try {
                    socket = new Socket(SelectedServer.host, hostPort);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                dataOutputStream = new DataOutputStream(
                        socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());

                // transfer JSONObject as String to the server
                dataOutputStream.writeUTF(jsonData.toString());
                Log.i(TAG, "waiting for response from host");

                // Thread will wait till server replies
                String response = dataInputStream.readUTF();
//                Log.d("Main a>>>>>",sessionManager.getLink());

                if (response != null ) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if(status.equals("1")){
                            success = true;
                        }else{
                            success = false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    success =false;
                }

            } catch (IOException e) {
                e.printStackTrace();
                success = false;
            } finally {

                // close socket
                if (socket != null) {
                    try {
                        Log.i(TAG, "closing the socket");
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // close input stream
                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // close output stream
                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (success) {
                //Toast.makeText(ClientActivity.this, "Connection Done", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "success remote: "+ result);
            } else {
                Log.d(TAG, "failed remote: "+ result);
                //Toast.makeText(ClientActivity.this, "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void clearConnectedHost() {

        InetAddress hostAddress = null;

        try {
            hostAddress = InetAddress.getByName(SelectedServer.host);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        hostPort = iv.parseNullInteger(SelectedServer.port);

        if (hostAddress == null) {
            Log.e(TAG, "Host Address is null");
            return;
        }

        String ipAddress = getMyIPAddress(true);
        JSONObject jsonData = new JSONObject();

        try {
            jsonData.put("request", ServiceUtils.REQUEST_CONNECT_CLIENT);
            jsonData.put("ipAddress", ipAddress);
            jsonData.put("keyCode", "");
            jsonData.put("type", "clear_connection");
            jsonData.put("fcm_client",SelectedServer.fcm_id);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "can't put request");
            return;
        }

        new SocketServerTask2().execute(jsonData);
    }

    private class SocketServerTask2 extends AsyncTask<JSONObject, Void, Void> {

        private JSONObject jsonData;
        private boolean success;
        private boolean linked = false;

        @Override
        protected Void doInBackground(JSONObject... params) {
            Socket socket = null;
            DataInputStream dataInputStream = null;
            DataOutputStream dataOutputStream = null;
            jsonData = params[0];

            try {

                // Create a new Socket instance and connect to host
                try {
                    socket = new Socket(SelectedServer.host, hostPort);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                dataOutputStream = new DataOutputStream(
                        socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());

                // transfer JSONObject as String to the server
                dataOutputStream.writeUTF(jsonData.toString());
                Log.i(TAG, "waiting for response from host");

                // Thread will wait till server replies
                String response = dataInputStream.readUTF();
                if (response != null ) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.d(">>>>",String.valueOf(jsonObject));
                        String status = jsonObject.getString("status");
                        if(status.equals("1")){
                            success = true;
                            link_tv = jsonObject.getString("link");
                        }else{
                            success = false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    success =false;
                }
//                if (response != null && response.equals("1")) {
//                    success = true;
//                } else {
//                    success = false;
//                    linked = true;
//                }

            } catch (IOException e) {
                e.printStackTrace();
                success = false;
                linked = false;
            } finally {

                // close socket
                if (socket != null) {
                    try {
                        Log.i(TAG, "closing the socket");
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // close input stream
                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // close output stream
                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (success) {
                Log.d(TAG, "Clear Connection succes : "+result);
            } else {
                Log.d(TAG, "Clear Connection failed : "+result);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static void playVideo(final Context context, final String link_tv){
        Log.d(">>>>>",link_tv);
        if(!link_tv.equals("empty")){
            cvPreview.stopPlayback();
            cvPreview.clearAnimation();
            cvPreview.suspend();
            cvPreview.setVideoURI(null);

            RemoteActivity.pbLoading.setVisibility(View.VISIBLE);

            cvPreview.setVisibility(View.VISIBLE);
            llNoteFound.setVisibility(View.GONE);
            if (Looper.myLooper() == null)
            {
                Looper.prepare();
            }
            new Thread(new Runnable() {
                public void run() {

                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {

                                cvPreview.stopPlayback();
                                cvPreview.clearAnimation();
                                cvPreview.suspend();
                                cvPreview.setVideoURI(null);

                                Uri uri = Uri.parse(link_tv);
                                cvPreview.setVideoURI(uri);
                                //vvPlayVideo.setMediaController(mediaController);
                                cvPreview.requestFocus();

                            } catch (Exception e) {
                                // NETWORK ERROR such as Timeout
                                e.printStackTrace();

                                pbLoading.setVisibility(View.GONE);
                                cvPreview.stopPlayback();
                                cvPreview.clearAnimation();
                                cvPreview.suspend();
                                cvPreview.setVideoURI(null);
                                Toast.makeText(context, "Channel sudah tidak tersedia", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }).start();

            cvPreview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {

                    pbLoading.setVisibility(View.GONE);
                    mp.start();

                    fullScreenVideo(context, scaleVideo);
                    mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {

                        @Override
                        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                            mp.start();
                            fullScreenVideo(context, scaleVideo);
                        }
                    });
                }
            });

            cvPreview.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {

                    pbLoading.setVisibility(View.GONE);
                    cvPreview.stopPlayback();
                    cvPreview.clearAnimation();
                    cvPreview.suspend();
                    cvPreview.setVideoURI(null);
                    Toast.makeText(context, "Channel sudah tidak tersedia", Toast.LENGTH_LONG).show();
                    return true;
                }
            });

        }else{
            RemoteActivity.pbLoading.setVisibility(View.GONE);
            cvPreview.setVisibility(View.GONE);
            llNoteFound.setVisibility(View.VISIBLE);
        }
    }

    private static void fullScreenVideo(Context context, double scale)
    {

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) cvPreview.getLayoutParams();

//        if(isFullScreen){
//            scale = 1;
//        }
        double doubleWidth = metrics.widthPixels * scale;
        double doubleHeight = metrics.heightPixels * scale;
        RelativeLayout.LayoutParams newparams = new RelativeLayout.LayoutParams((int) doubleWidth,(int) doubleHeight);
        newparams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        newparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        newparams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        newparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

        cvPreview.setLayoutParams(newparams);
    }


    @Override
    public void onBackPressed() {

        try {
            clearConnectedHost();
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onBackPressed();
    }

}
