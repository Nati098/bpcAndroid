package com.example.mainactivity.data.network;

import java.io.IOException;
import java.util.logging.Logger;

import io.reactivex.annotations.NonNull;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class ApiKeyInterceptor implements Interceptor {

    private static final String PARAM_API_KEY = "api-key";
    private static final String PARAM_SECTION = "section";
    private final String apiKey;
    private String section;

    private ApiKeyInterceptor(String apiKey, String section) {
        this.apiKey = apiKey;
        this.section = section;
    }

    public static Interceptor create(@NonNull String apiKey, @NonNull String section){
        return new ApiKeyInterceptor(apiKey, section);
    }


    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final Request requestNoKey = chain.request();
        final HttpUrl url = requestNoKey.url()
                .newBuilder().build();

        //Headers headers = requestNoKey.headers().newBuilder().set(PARAM_API_KEY, this.apiKey).build();
        //System.out.println("REQUEEEEEEEEEEEEEEEEST "+url.toString()+"  "+headers);

        final Request requestWithKey = requestNoKey.newBuilder()
                .url(url)
                //.headers(headers)
                .header(PARAM_API_KEY, this.apiKey)
                .build();
        System.out.println("AFTER requestWithKey headers "+requestWithKey.headers().toString());
        //System.out.println("AFTER requestWithKey "+requestWithKey.body().toString());

        return chain.proceed(requestWithKey);
    }
}
