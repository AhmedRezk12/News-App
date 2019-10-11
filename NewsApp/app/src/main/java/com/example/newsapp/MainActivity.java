package com.example.newsapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{

    private static final int NEWS_LOADER_ID = 1;
    ArrayList<News> news = new ArrayList<>();
    NewsAdapter mAdapter;
    ListView newsListView;
    TextView mEmptyState;
    ProgressBar mProgressBar;

    public static final String LOG_TAG = MainActivity.class.getName();
    private static final String GUARDIAN_API_URL =
            "https://content.guardianapis.com/search";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyState = (TextView) findViewById(R.id.empty_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager  = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);

        } else {
            mProgressBar.setVisibility(View.GONE);
            mEmptyState.setText(R.string.no_internet_connection);
        }

        newsListView = (ListView) findViewById(R.id.list);

        newsListView.setEmptyView(mEmptyState);

        mAdapter = new NewsAdapter(this, news);
        newsListView.setAdapter(mAdapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String url = news.get(i).getWeb_Url();

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });


    }

    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {

        Uri Uris = Uri.parse(GUARDIAN_API_URL);
        Uri.Builder uriBuilder = Uris.buildUpon();


        uriBuilder.appendQueryParameter("api-key","1386c4fe-d0c4-4bea-8e95-47c9cd4c538e");
        uriBuilder.appendQueryParameter("show-tags","contributor");

        return new NewsLoader(this,uriBuilder.toString() );
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        mAdapter.clear();
        if(news == null) {
            return;
        }
        mAdapter.addAll(news);

        mProgressBar.setVisibility(View.GONE);
        mEmptyState.setText(R.string.no_news_found);

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }
}
