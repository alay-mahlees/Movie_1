package com.example.ropot.movie_1;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends FragmentActivity {

    public static final String BASE_URL = "https://api.themoviedb.org";
    public static final String API_KEY = "Replace you API KEY here, thanks!";
    public static final String LANGUAGE = "en-US";
    public static final int PAGE = 1;
    public String category;

    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    public  static List<MoviesModule.ResultsBean> listOfResults;
    ArrayList<String> myList;
    PosterAdapter posterAdapter;
    RetrofitInterface retrofitInterfaceInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);

        gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        category = new String("popular");



        Retrofit myRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        retrofitInterfaceInstance = myRetrofit.create(RetrofitInterface.class);

        queryString(category);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.popular:

                queryString("popular");

                return true;

            case R.id.top_rated:
                queryString("top_rated");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }





    public void queryString(String mCategory){

        myList.clear();

        Call<MoviesModule> call = retrofitInterfaceInstance.listMovies(mCategory, API_KEY, LANGUAGE, PAGE);
        call.enqueue(new Callback<MoviesModule>() {
            @Override
            public void onResponse(Call<MoviesModule> call, Response<MoviesModule> response) {

                if (response.isSuccessful()) {


                    MoviesModule catchRespons = response.body();
                    listOfResults = catchRespons.getResults();
                    Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();


                    for (int i = 0; i < listOfResults.size(); i++) {

                        String posterPath = listOfResults.get(i).getPoster_path();
                        myList.add(posterPath);


                    }
                    posterAdapter = new PosterAdapter(MainActivity.this, myList);
                    recyclerView.setAdapter(posterAdapter);




                } else {

                    try {
                        Toast.makeText(MainActivity.this, response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }




            }

            @Override
            public void onFailure(Call<MoviesModule> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Other error: : " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        return;
    }

}
