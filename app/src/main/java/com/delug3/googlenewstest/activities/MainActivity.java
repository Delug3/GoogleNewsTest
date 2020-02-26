package com.delug3.googlenewstest.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.delug3.googlenewstest.R;
import com.delug3.googlenewstest.adapters.ArticlesListAdapter;
import com.delug3.googlenewstest.services.ApiBase;
import com.delug3.googlenewstest.models.Articles;
import com.delug3.googlenewstest.responses.ArticlesResponse;
import com.delug3.googlenewstest.services.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ArticlesListAdapter.ItemClickListener{

    private static final String TAG = "HEADLINES";
    private RecyclerView recyclerViewArticles;
    private ArticlesListAdapter articlesListAdapter;

    List<Articles> articlesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_articles);

        recyclerViewArticles = findViewById(R.id.recycler_view_articles);

        articlesListAdapter= new ArticlesListAdapter(articlesList);

        recyclerViewArticles.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewArticles.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerViewArticles.setHasFixedSize(true);
        recyclerViewArticles.setAdapter(articlesListAdapter);

        articlesListAdapter.setClickListener(this);


        getData();

    }

    private void getData()
    {


        RetrofitService service = ApiBase.getClient().create(RetrofitService.class);
        Call<ArticlesResponse> articlesResponseCall= service.obtainArticlesList("us", RetrofitService.API_KEY);

        articlesResponseCall.enqueue(new Callback<ArticlesResponse>() {
            @Override
            public void onResponse(Call<ArticlesResponse> call, Response<ArticlesResponse> response) {

                if (response.isSuccessful()) {

                    ArticlesResponse articlesResponse = response.body();
                    ArrayList<Articles> listArticles = articlesResponse.getArticles();

                    //Test articles list
                    for (int i = 0; i < listArticles.size(); i++)
                    {
                        Articles a = listArticles.get(i);
                        Log.e(TAG, "Title: " + a.getTitle());
                        Log.e(TAG, "Description: " + a.getDescription());
                    }

                    articlesListAdapter.addArticlesList(listArticles);



                }else{

                    Log.e(TAG, "onResponse: " + response.errorBody());


                }
            }

            @Override
            public void onFailure(Call<ArticlesResponse> call, Throwable t) {

                Log.e(TAG,"onFailure"+ t.getMessage());

            }
        });

    }


    @Override
    public void onItemClick(View view, int position) {
        Intent i = new Intent(MainActivity.this, ArticlesDetailsActivity.class);
        i.putExtra("ARTICLE_TITLE", articlesList.get(position).getTitle());
        i.putExtra("ARTICLE_CONTENT", articlesList.get(position).getContent());
        i.putExtra("ARTICLE_IMAGE", articlesList.get(position).getUrlToImage());
        i.putExtra("ARTICLE_DATE", articlesList.get(position).getPublishedAt());
        startActivity(i);
    }




}


