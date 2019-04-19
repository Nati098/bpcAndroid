package com.example.mainactivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.mainactivity.data.local.DataUtils;
import com.example.mainactivity.data.local.NewsItem;
import com.example.mainactivity.data.network.RestApi;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class NewsListActivity extends AppCompatActivity {

    public static final String DEFAULT_SEARCH_REQUEST = "home";

    private NewsRecyclerAdapter adapter;
    private RecyclerView recycler;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        setupOnCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupOnStart();
        loadNews();
    }

    @Override
    protected void onPause() {
        super.onPause();
        setupOnPause();
        this.compositeDisposable.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void setupOnCreate(){
        findViews();
        //toolbar - TODO
        List<NewsItem> newsItems = DataUtils.generateNews();
        //setup Adapter & RecyclerView
        this.adapter = new NewsRecyclerAdapter(this,newsItems,
                new NewsItemClickedCallback() {
                    @Override
                    public void onItemClicked(NewsItem item) {
                        NewsDetailActivity.start(NewsListActivity.this, item.getCategory().getName(),
                                item.getImageUrl(), item.getTitle(), item.getPublishDate().toString(), item.getFullText());
                    }
                });

        this.recycler = findViewById(R.id.news_recycler_view);
        this.recycler.setAdapter(this.adapter);
        if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            this.recycler.setLayoutManager(new GridLayoutManager(this, 1));
        }
        else{
            this.recycler.setLayoutManager(new GridLayoutManager(this, 2));
        }
    }

    private void findViews(){
        // TODO
    }

    private void setupOnStart(){
        // set listeners if needed - TODO
    }

    private void loadNews(@NonNull String section){
        //showState - TODO
        final Disposable searchDisposable = RestApi.getInstance(section)
                            .getNewsEndpoint().search(section)     // TODO - ?
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        this.compositeDisposable.add(searchDisposable);
    }

    private void setupOnPause(){
        // setListener(null) for each from setupOnStert- TODO
    }
}