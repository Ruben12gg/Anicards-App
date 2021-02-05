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
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ToWatchAdapter extends RecyclerView.Adapter<ToWatchAdapter.ViewHolder> {

    //criação de referencias
    private List<AnimeToWatch> mData; //class dos AnimeLiked
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    ToWatchAdapter(Context context, List<AnimeToWatch> data05) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data05;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_card_collections, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        AnimeToWatch anime = mData.get(position);
        holder.tvTitle.setText(anime.getTitle());
        holder.tvdate.setText(anime.getDate());

        final String animeTitle = anime.getTitle().toString();
        final String rating = anime.getRating();
        final String picUrl = anime.getPic();
        final String date = anime.getDate();


        Picasso.get().load(picUrl).into(holder.pic);


        holder.resultCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "XPTO");

            }
        });

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvRating;
        TextView tvdate;
        ImageView pic;
        RelativeLayout resultCard;


        ViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.titleTxtColl00);
            tvdate = itemView.findViewById(R.id.dateTxtColl);
            pic = itemView.findViewById(R.id.imageView2Coll);
            resultCard = itemView.findViewById(R.id.resultCardColl);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // Método utilitário para obter o nome
    String getName(int id) {
        return mData.get(id).getTitle();
    }


    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

