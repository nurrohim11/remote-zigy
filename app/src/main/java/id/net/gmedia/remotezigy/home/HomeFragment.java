package id.net.gmedia.remotezigy.home;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rd.PageIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import co.id.gmedia.coremodul.ApiVolley;
import co.id.gmedia.coremodul.AppRequestCallback;
import id.net.gmedia.remotezigy.R;
import id.net.gmedia.remotezigy.Utils.SessionManager;
import id.net.gmedia.remotezigy.Utils.Url;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    View view;
    ViewPager viewPager;
    PageIndicatorView pageIndicatorView;
    List<PromoModel> promoModels = new ArrayList<>();
    PromoAdapter adapter;
    private int count_slider= 0;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    SessionManager sessionManager;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initUi();
        sessionManager = new SessionManager(getContext());

        adapter = new PromoAdapter(getContext(), promoModels);
        initDataPromo();
        initPromo();
        return view;
    }

    private void initUi(){
        viewPager = view.findViewById(R.id.pager);
        pageIndicatorView = view.findViewById(R.id.pageIndicatorView);
    }

    private void initDataPromo(){
        JSONObject jBody = new JSONObject();
        count_slider =0;

        new ApiVolley(getContext(), jBody, "GET", Url.url_promo,
                new AppRequestCallback(new AppRequestCallback.ResponseListener() {
                    @Override
                    public void onSuccess(String response, String message) {
                        promoModels.clear();
                        try{
                            JSONArray obj = new JSONArray(response);
                            sessionManager.saveCountSlider(obj.length());
                            for(int i = 0; i < obj.length(); i++){
                                JSONObject d = obj.getJSONObject(i);
                                PromoModel s = new PromoModel(
                                        d.getString("id")
                                        ,d.getString("image")
                                        ,d.getString("url")
                                );
                                promoModels.add(s);
                            }
                            adapter.notifyDataSetChanged();
                        }
                        catch (JSONException e){
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onEmpty(String message) {

                        promoModels.clear();
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFail(String message) {
                    }
                })
        );
    }

    private void initPromo(){

        viewPager.setAdapter(adapter);

        pageIndicatorView.setViewPager(viewPager);

        final float density = getResources().getDisplayMetrics().density;

        pageIndicatorView.setRadius(5 * density);

        NUM_PAGES = sessionManager.getCountSlider();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 6000, 4000);

        // Pager listener over indicator
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                pageIndicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {/*empty*/}
        });
    }

}
