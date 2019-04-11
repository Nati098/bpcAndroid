package com.example.mainactivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.disposables.Disposable;


public class NewsListActivity extends AppCompatActivity {

    //private NewsRecyclerAdapter nra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        RecyclerView rv = findViewById(R.id.news_recycler_view);

        /*
        this.nra = new NewsRecyclerAdapter(this, DataUtils.generateNews(),
                new NewsItemClickedCallback() {
                    @Override
                    public void onItemClicked(NewsItem item) {
                        NewsDetailActivity.start(NewsListActivity.this, item.getCategory().getName(),
                                item.getImageUrl(), item.getTitle(), item.getPublishDate().toString(), item.getFullText());
                    }
                });
         */

        rv.setAdapter(new NewsRecyclerAdapter(this, DataUtils.generateNews(),
                new NewsItemClickedCallback() {
                    @Override
                    public void onItemClicked(NewsItem item) {
                        NewsDetailActivity.start(NewsListActivity.this, item.getCategory().getName(),
                                item.getImageUrl(), item.getTitle(), item.getPublishDate().toString(), item.getFullText());
                    }
                }));

        if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            //rv.setLayoutManager(new LinearLayoutManager(this));
            rv.setLayoutManager(new GridLayoutManager(this, 1));
        }
        else{
            rv.setLayoutManager(new GridLayoutManager(this, 2));
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        //this.nra.onStop();
    }

}