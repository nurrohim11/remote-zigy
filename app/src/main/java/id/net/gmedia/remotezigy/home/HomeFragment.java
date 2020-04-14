package id.net.gmedia.remotezigy.home;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.net.gmedia.remotezigy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    View view;
//    RecyclerView rvChannel;
//    List<ChannelModel> channelModels = new ArrayList<>();
//    ChannelAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initUi();

//        adapter = new ChannelAdapter(getContext(), channelModels);
//
////        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
////        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
//        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
//        rvChannel.setLayoutManager(gridLayoutManager);
//        rvChannel.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(10), true));
//        rvChannel.setItemAnimator(new DefaultItemAnimator());
//        rvChannel.setAdapter(adapter);
//        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.padding_channel);
//        rvChannel.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
//        getListChannel();
        return view;
    }

    private void initUi(){
//        rvChannel = view.findViewById(R.id.rv_channel);
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
