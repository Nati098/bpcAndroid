package com.example.mainactivity.data.database;

import android.content.Context;

import java.util.List;


public class NewsConverter {

    private final Context context;

    public NewsConverter(Context context) {
        this.context = context;
    }

    void toDatabase(final List<NewsEntity> items) {
        AppDatabase db = AppDatabase.getAppDatabase(context);
        db.newsDao().deleteAll();
        db.newsDao().insertAll(items.toArray(new NewsEntity[items.size()]));
    }

    List<NewsEntity> fromDatabase() {
        return AppDatabase.getAppDatabase(context).newsDao().getAll();
    }

}
