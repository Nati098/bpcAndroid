package com.example.mainactivity.data.network.dto;

import java.util.List;

import androidx.annotation.Nullable;

public class NewsResponse<T> {

    private List<T> results;

    @Nullable
    public List<T> getResults(){
        return this.results;
    }


}
