package com.example.mainactivity.data.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import io.reactivex.annotations.NonNull;

@Entity
public class NewsEntity {

    public NewsEntity(){}

    @NonNull
    @PrimaryKey
    private int id;

    @NonNull
    private String section;

    private String subsection;

    @NonNull
    private String title;

    private String _abstract;

    private String url;

    @NonNull
    private String publishedDate;

    private String multimedia_url;

    private String multimedia_format;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstract() {
        return _abstract;
    }

    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getMultimedia_url() {
        return multimedia_url;
    }

    public void setMultimedia_url(String multimedia_url) {
        this.multimedia_url = multimedia_url;
    }

    public String getMultimedia_format() {
        return multimedia_format;
    }

    public void setMultimedia_format(String multimedia_format) {
        this.multimedia_format = multimedia_format;
    }
}
