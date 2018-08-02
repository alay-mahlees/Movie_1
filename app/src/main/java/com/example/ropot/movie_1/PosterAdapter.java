package com.example.ropot.movie_1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;


public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.MyHolder> {


    private Context context;
    private ArrayList<String> Poster;




    public PosterAdapter(Context context, ArrayList<String> poster) {
        this.context = context;
        Poster = poster;

    }


    @NonNull
    @Override

    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout, null);
        MyHolder myHolder = new MyHolder(layout);

        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {




        String url = context.getString(R.string.poster_base_path) + Poster.get(position);
        Uri uri = Uri.parse(url);
        Picasso.get().load(uri)
                .resize(400, 500)
                .centerCrop()
               .into(holder.posterHolder);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
                Toast.makeText(context, "inner toast " + position, Toast.LENGTH_LONG).show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return Poster.size();
    }


    public static class MyHolder extends RecyclerView.ViewHolder {

        ImageView posterHolder;

        public MyHolder(View itemView) {
            super(itemView);
            posterHolder = itemView.findViewById(R.id.imageView);
        }

        /*public void bind(final String item) {

        }*/
    }
}
