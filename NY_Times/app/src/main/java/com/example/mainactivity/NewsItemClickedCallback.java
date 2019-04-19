package com.example.mainactivity;

import com.example.mainactivity.data.local.NewsItem;

public interface NewsItemClickedCallback {
    void onItemClicked(NewsItem item);
}
