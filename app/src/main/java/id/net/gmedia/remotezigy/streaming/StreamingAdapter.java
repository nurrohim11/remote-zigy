package id.net.gmedia.remotezigy.streaming;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import id.net.gmedia.remotezigy.R;
import id.net.gmedia.remotezigy.Utils.Utils;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class StreamingAdapter extends RecyclerView.Adapter<StreamingAdapter.ViewHolder>  {

    private Context mContext;
    private List<StreamingModel> musicModels;
    public static int selectedPosition =-1;

    public StreamingAdapter(Context mContext, List<StreamingModel> musicModels) {
        this.mContext = mContext;
        this.musicModels = musicModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_streaming, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final List<String> installedPackages = Utils.getInstalledAppsPackageNameList(mContext);
        final StreamingModel m= musicModels.get(position);
        Glide.with(mContext)
                .load(m.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)
                .override(400, 400)
                .centerCrop()
                .transform(new RoundedCornersTransformation(30,0))
                .into(holder.imgMusic);
        holder.tvTitle.setText(m.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(installedPackages.contains(m.getM_package())){
                    Intent launchIntent = mContext.getPackageManager().getLaunchIntentForPackage(m.getM_package());
                    mContext.startActivity( launchIntent );
                }else {
                    if(m.getM_package().isEmpty()){
                        if(m.getUrl_playstore().isEmpty()){
                            if(m.getUrl_web().isEmpty()){
                                Toast.makeText(mContext, "Paket tidak ditemukan !!..", Toast.LENGTH_SHORT).show();
                            }else{
                                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                                httpIntent.setData(Uri.parse(m.getUrl_web()));
                                mContext.startActivity(httpIntent);
                            }
                        }else{
                            try {
                                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+m.getM_package())));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+m.getM_package())));
                            }
                        }
                    }else{
                        try {
                            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+m.getM_package())));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+m.getM_package())));
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return musicModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMusic;
        TextView tvTitle;
        RelativeLayout rlImage;
        LinearLayout llKonten;

        public ViewHolder(View itemView) {
            super(itemView);
            imgMusic = itemView.findViewById(R.id.img_usic);
            tvTitle = itemView.findViewById(R.id.tv_title);
            rlImage =itemView.findViewById(R.id.rl_image);
            llKonten = itemView.findViewById(R.id.ll_konten);
        }
    }
}

