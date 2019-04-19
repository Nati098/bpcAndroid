package com.example.mainactivity.data.network.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsDTO {

    @SerializedName("section")
    private String section;

    @SerializedName("subsection")
    public String subsection;

    @SerializedName("title")
    private String title;

    @SerializedName("abstract")
    private String _abstract;

    @SerializedName("url")
    private String url;

    @SerializedName("item_type")
    private String itemType;

    @SerializedName("published_date")
    private String publishedDate;

    @SerializedName("multimedia")
    private List<MultimediaDTO> multimedia;

    public String getUrl() {
        return this.multimedia.get(2).getUrl();
    }


}
