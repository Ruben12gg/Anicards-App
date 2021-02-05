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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    //criação de referencias
    private List<AnimeHome> mData; //class dos AnimeLiked
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    HomeAdapter(Context context, List<AnimeHome> data06) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data06;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_card_home, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        AnimeHome anime = mData.get(position);
        holder.tvdate.setText(anime.getDate());
        holder.tvActivity.setText(anime.getActivity());
        holder.tvAuthor.setText(anime.getAuthor());

        final String picUrl = anime.getPic();
        final String date = anime.getDate();
        final String activity = anime.getActivity();
        final String pfp = anime.getPfp();


        Picasso.get().load(picUrl).into(holder.pfp);
        Picasso.get().load(pfp).into(holder.pic);


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

        TextView tvdate;
        TextView tvAuthor;
        TextView tvActivity;
        ImageView pic;
        ImageView pfp;
        RelativeLayout resultCard;


        ViewHolder(View itemView) {
            super(itemView);

            tvdate = itemView.findViewById(R.id.time);
            tvAuthor = itemView.findViewById(R.id.pfName);
            tvActivity = itemView.findViewById(R.id.activity);
            pic = itemView.findViewById(R.id.imageView2Home);
            pfp = itemView.findViewById(R.id.pfp);
            resultCard = itemView.findViewById(R.id.resultCardHome);
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

