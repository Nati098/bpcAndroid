package com.example.mainactivity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class NewsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        RecyclerView rv = findViewById(R.id.news_recycler_view);

        if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            //rv.setLayoutManager(new LinearLayoutManager(this));
            rv.setLayoutManager(new GridLayoutManager(this, 1));
        }
        else{
            rv.setLayoutManager(new GridLayoutManager(this, 2));
        }


        rv.setAdapter(new NewsRecyclerAdapter(this, DataUtils.generateNews(),
                new NewsItemClickedCallback() {
                    @Override
                    public void onItemClicked(NewsItem item) {
                        NewsDetailActivity.start(NewsListActivity.this, item.getCategory().toString(),
                                item.getImageUrl(), item.getTitle(), item.getPublishDate().toString(), item.getFullText());
                    }
                }));


    }

}