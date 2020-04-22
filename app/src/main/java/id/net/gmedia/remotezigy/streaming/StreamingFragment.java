package id.net.gmedia.remotezigy.streaming;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import id.net.gmedia.remotezigy.tv.ChannelAdapter;
import id.net.gmedia.remotezigy.tv.ChannelModel;
import id.net.gmedia.remotezigy.tv.TvFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class StreamingFragment extends Fragment {

    List<StreamingModel> model = new ArrayList<>();
    View view;
    StreamingAdapter adapter;
    RecyclerView rvStreaming;

    public StreamingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_streaming, container, false);
        rvStreaming = view.findViewById(R.id.rv_streaming);

        adapter = new StreamingAdapter(getContext(), model);

        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rvStreaming.setLayoutManager(gridLayoutManager);
        rvStreaming.setAdapter(adapter);
        getListStreaming();
        return  view;
    }

    private void getListStreaming() {
        JSONObject jbody = new JSONObject();

        new ApiVolley(getContext(), jbody, "POST", Url.url_streaming,
                new AppRequestCallback(new AppRequestCallback.ResponseListener() {
                    @Override
                    public void onSuccess(String response, String message) {
                        model.clear();
                        try{
                            JSONArray obj = new JSONArray(response);
                            for(int i = 0; i < obj.length(); i++){
                                JSONObject j = obj.getJSONObject(i);
                                StreamingModel m = new StreamingModel(
                                        j.getString("id"),
                                        j.getString("title"),
                                        j.getString("icon"),
                                        j.getString("url"),
                                        j.getString("kategori"),
                                        j.getString("package"),
                                        j.getString("url_playstore"),
                                        j.getString("url_web")
                                );
                                model.add(m);
                            }
                            adapter.notifyDataSetChanged();
                        }
                        catch (JSONException e){
                            Toast.makeText(getContext(), "Error item", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onEmpty(String message) {

                        model.clear();
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFail(String message) {
                        Toast.makeText(getContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
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
