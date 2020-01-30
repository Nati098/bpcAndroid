package com.example.mainactivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class NewsListActivity extends AppCompatActivity {

    private Disposable disposable;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        this.progressBar = findViewById(R.id.progressBarList);
        this.progressBar.setVisibility(View.VISIBLE);

        //final List<NewsItem> newsItems;
        this.disposable = Single.just(new DataUtils()).map(du -> {
            Thread.sleep(2000);
            return du.generateNews();})
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(news -> runRecyclerView(news));


    }

    private void runRecyclerView(List<NewsItem> newsItems){
        this.progressBar.setVisibility(View.GONE);
        System.out.println("\n\nNow it's a " + Thread.currentThread().getName()+"\n\n");

        RecyclerView rv = findViewById(R.id.news_recycler_view);
        rv.setAdapter(new NewsRecyclerAdapter(this, newsItems,
                new NewsItemClickedCallback() {
                    @Override
                    public void onItemClicked(NewsItem item) {
                        NewsDetailActivity.start(NewsListActivity.this, item.getCategory().getName(),
                                item.getImageUrl(), item.getTitle(), item.getPublishDate().toString(), item.getFullText());
                    }
                }));

        if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            rv.setLayoutManager(new GridLayoutManager(this, 1));
        }
        else{
            rv.setLayoutManager(new GridLayoutManager(this, 2));
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        this.disposable.dispose();
        System.out.println("Disposed");
    }
}