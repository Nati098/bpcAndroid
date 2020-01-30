package com.example.mainactivity.data.network;

import com.example.mainactivity.data.network.dto.NewsDTO;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface NewsEndpoint {
    @Headers({"Accept: application/json",
            "api-key: 0"})
    @GET("svc/topstories/v2/{section}.json")
    Single<Response<NewsResponse<List<NewsDTO>>>> search(@Path("section") String section);
                                                         //@Header("api-key") @NonNull String apiKey);

}
