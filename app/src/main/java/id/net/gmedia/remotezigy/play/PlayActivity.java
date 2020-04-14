package id.net.gmedia.remotezigy.play;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import id.net.gmedia.remotezigy.R;
import id.net.gmedia.remotezigy.Utils.CustomVideoView;
import id.net.gmedia.remotezigy.tv.ChannelModel;

public class PlayActivity extends AppCompatActivity {

    ChannelModel item;
    public static String CHANNEL_ITEM="channel_item";
    private Gson gson = new Gson();
    CustomVideoView cvPlay;
    ProgressBar pbLoading;
    private static double scaleVideo = 1;

    private static boolean isFullScreen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play);
        String channel_item = getIntent().getStringExtra(CHANNEL_ITEM);
        item = gson.fromJson(channel_item, ChannelModel.class);
        initUi();
    }

    private void initUi(){
        cvPlay = findViewById(R.id.cv_play);
        pbLoading = findViewById(R.id.pb_loading);
        if(!item.getLink().equals("")){
            playVideo(this,item.getNama(),item.getLink());
        }
    }


    public void playVideo(final Context context, final String nama, final String url){

        cvPlay.stopPlayback();
        cvPlay.clearAnimation();
        cvPlay.suspend();
        cvPlay.setVideoURI(null);

        pbLoading.setVisibility(View.VISIBLE);

        if(false){

        }else{
            cvPlay.setVisibility(View.VISIBLE);
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

                                cvPlay.stopPlayback();
                                cvPlay.clearAnimation();
                                cvPlay.suspend();
                                cvPlay.setVideoURI(null);

                                Uri uri = Uri.parse(url);
                                cvPlay.setVideoURI(uri);
                                //vvPlayVideo.setMediaController(mediaController);
                                cvPlay.requestFocus();

                            } catch (Exception e) {
                                // NETWORK ERROR such as Timeout
                                e.printStackTrace();

                                pbLoading.setVisibility(View.GONE);
                                cvPlay.stopPlayback();
                                cvPlay.clearAnimation();
                                cvPlay.suspend();
                                cvPlay.setVideoURI(null);
                                Toast.makeText(context, "Channel sudah tidak tersedia", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }).start();

            cvPlay.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

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

            cvPlay.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {

                    pbLoading.setVisibility(View.GONE);
                    cvPlay.stopPlayback();
                    cvPlay.clearAnimation();
                    cvPlay.suspend();
                    cvPlay.setVideoURI(null);
                    Toast.makeText(context, "Channel sudah tidak tersedia", Toast.LENGTH_LONG).show();
                    return true;
                }
            });

        }
    }

    private void fullScreenVideo(Context context, double scale)
    {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) cvPlay.getLayoutParams();

        if(isFullScreen){
            scale = 1;
        }
        double doubleWidth = metrics.widthPixels * scale;
        double doubleHeight = metrics.heightPixels * scale;
        RelativeLayout.LayoutParams newparams = new RelativeLayout.LayoutParams((int) doubleWidth,(int) doubleHeight);
        newparams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        newparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        newparams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        newparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

        cvPlay.setLayoutParams(newparams);
    }

}
