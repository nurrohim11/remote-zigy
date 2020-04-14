package id.net.gmedia.remotezigy.tv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.id.gmedia.coremodul.ApiVolley;
import co.id.gmedia.coremodul.AppRequestCallback;
import id.net.gmedia.remotezigy.R;
import id.net.gmedia.remotezigy.Utils.Url;

public class ChannelActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView rvChannel;
    List<ChannelModel> channelModels = new ArrayList<>();
    ChannelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        initUi();

        adapter = new ChannelAdapter(this, channelModels);

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
//        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rvChannel.setLayoutManager(gridLayoutManager);
        rvChannel.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(10), true));
        rvChannel.setItemAnimator(new DefaultItemAnimator());
        rvChannel.setAdapter(adapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.padding_channel);
        rvChannel.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        getListChannel();

    }

    private void initUi(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rvChannel = findViewById(R.id.rv_channel);
    }

    private void getListChannel() {
        JSONObject jbody = new JSONObject();

        new ApiVolley(this, jbody, "GET", Url.url_channel,
                new AppRequestCallback(new AppRequestCallback.ResponseListener() {
                    @Override
                    public void onSuccess(String response, String message) {
                        channelModels.clear();
                        try{
                            JSONArray obj = new JSONArray(response);
                            for(int i = 0; i < obj.length(); i++){
                                JSONObject j = obj.getJSONObject(i);
                                ChannelModel m = new ChannelModel(
                                        j.getString("id"),
                                        j.getString("icon"),
                                        j.getString("nama"),
                                        j.getString("link")
                                );
                                channelModels.add(m);
                            }
                            adapter.notifyDataSetChanged();
                        }
                        catch (JSONException e){
                            Toast.makeText(getApplicationContext(), "Error item", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onEmpty(String message) {

                        channelModels.clear();
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFail(String message) {
                        Toast.makeText(getApplicationContext(), "Failed item Live Streaming", Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }

    // ===================================== Beautify Grid ================================

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            }
        }
    }
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom

            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing;
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
