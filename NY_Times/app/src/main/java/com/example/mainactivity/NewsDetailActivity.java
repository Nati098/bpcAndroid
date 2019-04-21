package com.example.mainactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class NewsDetailActivity extends AppCompatActivity {

    public static void start(Activity activity, String category, String imgUrl, String title, String publishDdate, String full_text) {
        Intent intent1 = new Intent(activity, NewsDetailActivity.class);
        intent1.putExtra("img_url", imgUrl);
        intent1.putExtra("title", title);
        intent1.putExtra("date", publishDdate);
        intent1.putExtra("full_text", full_text);
        intent1.putExtra("subsection", category);

        activity.startActivity(intent1);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        Glide.with(this).load(getIntent().getStringExtra("img_url"))
                .into((ImageView) findViewById(R.id.news_details_photo));

        Toolbar toolbar = findViewById(R.id.news_details_toolbar);
        TextView toolTitle = (TextView) toolbar.findViewById(R.id.news_details_toolbar_title);
        toolTitle.setText(getIntent().getStringExtra("subsection"));

        TextView title = findViewById(R.id.news_details_title);
        title.setText(getIntent().getStringExtra("title"));

        TextView date = findViewById(R.id.news_details_date);
        date.setText(getIntent().getStringExtra("date"));

        TextView full_text = findViewById(R.id.news_details_full_text);
        full_text.setText(getIntent().getStringExtra("full_text"));

    }
}
