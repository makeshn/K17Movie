package com.example.android.newsapp;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.android.newsapp.adapter.HomeNewsAdapter;
import com.example.android.newsapp.model.GetArticleResponse;
import com.example.android.newsapp.model.NewsStore;
import com.example.android.newsapp.networking.NewsAPI;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {
    private RecyclerView newsRecyclerView;
    private HomeNewsAdapter homeNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing Views
        newsRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerview);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Call<GetArticleResponse> call = NewsAPI.getApi().getArticles("reuters", "top");
        call.enqueue(new Callback<GetArticleResponse>() {
            @Override
            public void onResponse(Call<GetArticleResponse> call, Response<GetArticleResponse> response) {
                GetArticleResponse getArticlesResponse = response.body();
                NewsStore.setArticles(getArticlesResponse.getArticles());
                homeNewsAdapter = new HomeNewsAdapter(getArticlesResponse.getArticles());
                newsRecyclerView.setAdapter(homeNewsAdapter);
            }

            @Override
            public void onFailure(Call<GetArticleResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error " + t.getLocalizedMessage(), LENGTH_SHORT).show();
            }
        });
    }

}
