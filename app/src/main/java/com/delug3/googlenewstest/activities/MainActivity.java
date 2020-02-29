package com.delug3.googlenewstest.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.delug3.googlenewstest.R;
import com.delug3.googlenewstest.adapters.ArticlesAdapter;
import com.delug3.googlenewstest.listeners.RecyclerTouchListener;
import com.delug3.googlenewstest.services.ApiBase;
import com.delug3.googlenewstest.models.Articles;
import com.delug3.googlenewstest.responses.ArticlesResponse;
import com.delug3.googlenewstest.services.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ArticlesAdapter.ArticlesAdapterListener {

    private static final String TAG = "HEADLINES";
    RecyclerView recyclerViewArticles;
    ArticlesAdapter articlesAdapter;
    SearchView searchViewArticles;
    List<Articles> articlesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_articles);

        setUpRecyclerView();

        getData();

        recyclerViewArticles.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), new RecyclerTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                final Articles articles = (Articles) articlesList.get(position);
                Intent intent = new Intent(MainActivity.this,ArticlesDetailsActivity.class);
                intent.putExtra("ARTICLE_TITLE",articles.getTitle());
                startActivity(intent);
            }
        }));
    }

    private void setUpRecyclerView()
    {

        recyclerViewArticles = findViewById(R.id.recycler_view_articles);
        recyclerViewArticles.setHasFixedSize(true);
        recyclerViewArticles.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerViewArticles.setLayoutManager(new LinearLayoutManager(this));

        articlesList = new ArrayList<>();

        articlesAdapter = new ArticlesAdapter(this,articlesList,this);


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

                    articlesList.addAll(listArticles);

                    articlesAdapter.notifyDataSetChanged();
                    recyclerViewArticles.setAdapter(articlesAdapter);

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


    /*@Override
    public void onItemClick(View view, int position) {
        Intent i = new Intent(MainActivity.this, ArticlesDetailsActivity.class);
        i.putExtra("ARTICLE_TITLE", articlesList.get(position).getTitle());
        i.putExtra("ARTICLE_CONTENT", articlesList.get(position).getContent());
        i.putExtra("ARTICLE_IMAGE", articlesList.get(position).getUrlToImage());
        i.putExtra("ARTICLE_DATE", articlesList.get(position).getPublishedAt());
        startActivity(i);
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchViewArticles = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchViewArticles.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchViewArticles.setMaxWidth(Integer.MAX_VALUE);

        searchViewArticles.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                articlesAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchViewArticles.isIconified()) {
            searchViewArticles.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onArticleSelected(Articles articles) {
           }

}


