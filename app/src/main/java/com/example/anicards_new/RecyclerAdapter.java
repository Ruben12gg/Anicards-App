package com.example.anicards_new;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    //criação de referencias
    private List<Animes> mData; //class dos Animes
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    RecyclerAdapter(Context context, ArrayList<Animes> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_card, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        Animes anime = mData.get(position);
        holder.tvTitle.setText(anime.getTitle());
        holder.tvRating.setText(anime.getRating());

        final String animeTitle = anime.getTitle().toString();
        final String launchDate = anime.getLaunchDate();
        final String endDate = anime.getEndDate();
        final String episodeLength = anime.getEpisodeLength();
        final String rating = anime.getRating();
        final String ageRate = anime.getAgeRate();
        final String synopsis = anime.getDesc();
        final String picUrl = anime.getPic();


        Picasso.get().load(anime.getPic()).into(holder.pic);


        holder.resultCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "XPTO");

                Intent i = new Intent(v.getContext(), AnimeActivity.class);
                i.putExtra("title", animeTitle);
                i.putExtra("launchDate", launchDate);
                i.putExtra("endDate", endDate);
                i.putExtra("episodeLength", episodeLength);
                i.putExtra("rating", rating);
                i.putExtra("ageRate", ageRate);
                i.putExtra("synopsis", synopsis);
                i.putExtra("picUrl", picUrl);


                v.getContext().startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvDesc;
        TextView tvRating;
        ImageView pic;
        RelativeLayout resultCard;


        ViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.titleTxt);
            tvRating = itemView.findViewById(R.id.ratingTxt);
            pic = itemView.findViewById(R.id.imageView2);
            resultCard = itemView.findViewById(R.id.resultCard);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
