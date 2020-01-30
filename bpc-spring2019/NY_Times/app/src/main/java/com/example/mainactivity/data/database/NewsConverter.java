package com.example.mainactivity.data.database;

import android.content.Context;

import com.example.mainactivity.data.network.dto.NewsDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Single;


public class NewsConverter {

    private final Context context;

    public NewsConverter(Context context) {
        this.context = context;
    }

    public Completable toDatabase(final List<NewsDTO> items) {
        return Completable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                AppDatabase db = AppDatabase.getAppDatabase(context);
                db.newsDao().deleteAll();
                db.newsDao().insertAll(fromDTO(items).toArray(new NewsEntity[items.size()]));
                return null;
            }
        });
    }

    public Single<List<NewsEntity>> fromDatabase() {
        return Single.fromCallable(new Callable<List<NewsEntity>>() {
            @Override
            public List<NewsEntity> call() throws Exception {
                return AppDatabase.getAppDatabase(context).newsDao().getAll();
            }
        });
    }

    public List<NewsEntity> fromDTO(List<NewsDTO> items){
        ArrayList<NewsEntity> buf = new ArrayList<>();

        for (int i=0; i < items.size(); i++){
            NewsDTO it = items.get(i);
            NewsEntity en = new NewsEntity();

            en.setId(i);
            en.setSection(it.getSection());
            en.setSubsection(it.getSubsection());
            en.setTitle(it.getTitle());
            en.setAbstract(it.getAbstract());
            en.setPublishedDate(it.getPublishedDate());
            en.setUrl(it.getUrl());
            en.setMultimedia_url(it.getMultimediaUrl());
            en.setMultimedia_format(it.getMultimediaFormat());

            buf.add(en);
        }

        return buf;
    }

}
