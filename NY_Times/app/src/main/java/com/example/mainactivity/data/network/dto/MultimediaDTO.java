package com.example.mainactivity.data.network.dto;

import com.google.gson.annotations.SerializedName;

class MultimediaDTO {

    @SerializedName("url")
    private String url;

    @SerializedName("format")
    private String format;

    public String getUrl(){
        return this.url;
    }

    public String getFormat(){
        return this.format;
    }

}
