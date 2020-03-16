package id.net.gmedia.remotezigy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class RemoteActivity extends AppCompatActivity {
    LinearLayout llPreview;
    RelativeLayout rlOk;
    ImageView imgPower, imgHome, imgBack, imgTop, imgBottom, imgPrev, imgNext, imgMenu, imgCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initUi();
    }

    private void initUi(){
        llPreview = findViewById(R.id.ll_preview);
        imgPower = findViewById(R.id.img_power);
        imgPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RemoteActivity.this, "Power", Toast.LENGTH_SHORT).show();
            }
        });

        imgHome = findViewById(R.id.img_home);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RemoteActivity.this, "HOme", Toast.LENGTH_SHORT).show();
            }
        });

        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RemoteActivity.this, "Back", Toast.LENGTH_SHORT).show();
            }
        });

        rlOk = findViewById(R.id.rl_ok);
        rlOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RemoteActivity.this, "OK", Toast.LENGTH_SHORT).show();
            }
        });

        imgTop = findViewById(R.id.img_top);
        imgTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RemoteActivity.this, "Top", Toast.LENGTH_SHORT).show();
            }
        });

        imgBottom = findViewById(R.id.img_bottom);
        imgBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RemoteActivity.this, "Bottom", Toast.LENGTH_SHORT).show();
            }
        });

        imgPrev = findViewById(R.id.img_prev);
        imgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RemoteActivity.this, "Previous", Toast.LENGTH_SHORT).show();
            }
        });

        imgNext = findViewById(R.id.img_next);
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RemoteActivity.this, "Next", Toast.LENGTH_SHORT).show();
            }
        });

        imgMenu = findViewById(R.id.img_menu);
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RemoteActivity.this, "Menu", Toast.LENGTH_SHORT).show();
            }
        });

        imgCursor = findViewById(R.id.img_cursor);
        imgCursor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RemoteActivity.this, "Cursor", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
