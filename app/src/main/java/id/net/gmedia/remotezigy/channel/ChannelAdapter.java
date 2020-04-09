package id.net.gmedia.remotezigy.channel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

import id.net.gmedia.remotezigy.R;
import id.net.gmedia.remotezigy.play.PlayActivity;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ChannelAdapter  extends RecyclerView.Adapter<ChannelAdapter.MyViewHolder> {

    Context context;
    List<ChannelModel> channelModels;

    public ChannelAdapter(Context context, List<ChannelModel> models){
        this.channelModels = models;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_channel, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChannelAdapter.MyViewHolder holder, int position) {
        final ChannelModel m = channelModels.get(position);

        Glide.with(context)
                .load(m.getIcon())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .override(500, 500)
//                .centerCrop()
//                .apply(RequestOptions.circleCropTransform())
                .transform(new RoundedCornersTransformation(30,0))
                .into(holder.imgChannel);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayActivity.class);
                intent.putExtra(PlayActivity.CHANNEL_ITEM, new Gson().toJson(m));

                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return channelModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgChannel;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgChannel = itemView.findViewById(R.id.img_channel);
        }
    }
}
