package com.example.mainactivity.data.network;

import java.util.List;

import androidx.annotation.Nullable;

public class NewsResponse<T> {

    private T results;

    @Nullable
    public T getResults(){
        return this.results;
    }

}
