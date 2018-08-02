package com.example.ropot.movie_1;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import com.example.ropot.movie_1.databinding.ActivityDetailBinding;
import com.squareup.picasso.Picasso;


import static com.example.ropot.movie_1.MainActivity.listOfResults;


public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDetailBinding mBinding;
        //setContentView(R.layout.activity_detail);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        int position = getIntent().getIntExtra("position", 0);


        String url =  getString(R.string.poster_base_path)  +  listOfResults.get(position).getPoster_path();
        Uri uri = Uri.parse(url);

        Toast.makeText(DetailActivity.this, url, Toast.LENGTH_LONG).show();

        mBinding.originalTitle.setText(listOfResults.get(position).getTitle());
        mBinding.overview.setText(listOfResults.get(position).getOverview());
        mBinding.releaseDate.setText(listOfResults.get(position).getRelease_date());
        mBinding.voteAverage.setText(Double.toString(listOfResults.get(position).getVote_average()));
        //mBinding.thumbnail.setImageURI(uri);

        Picasso.get().load(uri).into(mBinding.thumbnail);





    }
}
