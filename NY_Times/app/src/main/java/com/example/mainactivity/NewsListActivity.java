package com.example.mainactivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.mainactivity.data.NewsItemClickedCallback;
import com.example.mainactivity.data.NewsRecyclerAdapter;
import com.example.mainactivity.data.network.State;
import com.example.mainactivity.data.network.RestApi;
import com.example.mainactivity.data.network.dto.NewsDTO;
import com.example.mainactivity.data.network.dto.NewsResponse;

import java.io.IOException;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class NewsListActivity extends AppCompatActivity {

    public static final String DEFAULT_SEARCH_REQUEST = "home";
    public static final String[] SECTIONS = {"home",  "arts", "automobiles", "books", "business", "fashion", "food", "health",
            "insider", "magazine", "movies", "national", "nyregion", "obituaries", "opinion", "politics", "realestate",
            "science", "sports", "sundayreview", "technology", "theater", "tmagazine", "travel", "upshot", "world"};

    private NewsRecyclerAdapter adapter;
    private RecyclerView recycler;

    private Toolbar toolbar;
    private AppCompatSpinner spinner;
    private View viewError;
    private TextView textViewError;
    private Button buttonRetry;
    private View viewLoading;
    private View viewNoData;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        setupOnCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupOnStart();
        loadNews(DEFAULT_SEARCH_REQUEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setupOnPause();
        this.compositeDisposable.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void setupOnCreate(){
        findViews();
        this.toolbar.setTitle(R.string.app_title);

        setupRecyclerView();
        setupSpinner();
    }

    private void findViews(){
        this.toolbar = findViewById(R.id.news_list_toolbar);
        this.spinner = findViewById(R.id.spinner);
        this.viewError = findViewById(R.id.frame_error);
        this.textViewError = findViewById(R.id.text_view_error);
        this.buttonRetry = findViewById(R.id.btn_try_again);
        this.viewLoading = findViewById(R.id.frame_loading);
        this.viewNoData = findViewById(R.id.frame_no_data);
    }

    private void setupRecyclerView(){
        this.adapter = new NewsRecyclerAdapter(this,
                new NewsItemClickedCallback<NewsDTO>() {
                    @Override
                    public void onItemClicked(NewsDTO item) {
                        NewsDetailActivity.start(NewsListActivity.this, item.getSubsection(),
                                item.getUrl(), item.getTitle(), item.getPublishedDate(), item.getUrl());  // TODO - replae last geturl on getfulltext
                    }
                });

        this.recycler = findViewById(R.id.news_recycler_view);
        this.recycler.setAdapter(this.adapter);
        if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            this.recycler.setLayoutManager(new GridLayoutManager(this, 1));
        }
        else{
            this.recycler.setLayoutManager(new GridLayoutManager(this, 2));
        }
    }

    private void setupSpinner(){
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(NewsListActivity.this,
                android.R.layout.simple_spinner_item, SECTIONS);
        this.spinner.setAdapter(adapterSpinner);
    }

    private void setupOnStart(){
        // set listeners if needed

        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // считать и передать выбранное поле в loadNews (default = "home")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner.getSelectedItem() != null) {
                    String section = (String) spinner.getSelectedItem();
                    loadNews(section);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                loadNews(DEFAULT_SEARCH_REQUEST);
            }
        });


        this.buttonRetry.setOnClickListener(evt -> onClickButtonRetry());
    }

    private void onClickButtonRetry(){
        loadNews((String) this.spinner.getSelectedItem());
    }

    private void loadNews(@NonNull String section){
        showStateView(State.Loading);

        final Disposable searchDisposable = RestApi.getInstance(section)
                            .getNewsEndpoint().search(section)     // TODO - ?
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> checkResponseAndShowState(i), e -> handleError(e));
        this.compositeDisposable.add(searchDisposable);
    }

    private void checkResponseAndShowState(@NonNull Response<NewsResponse<List<NewsDTO>>> response){
        // failure: not successful response
        if (! response.isSuccessful()){
            showStateView(State.ServerError);
            return;
        }

        // check body
        final NewsResponse<List<NewsDTO>> body_resp = response.body();
        // failure: body - null -> no data
        if(body_resp == null){
            showStateView(State.HasNoData);
            return;
        }

        // get&check data
        final List<NewsDTO> data = body_resp.getResults();

        // failure:
        if (data.isEmpty()){
            showStateView(State.HasNoData);
            return;
        }

        // success
        this.adapter.replaceItems(data);
        showStateView(State.HasData);

    }

    private void handleError(Throwable e){
        if (e instanceof IOException){
            showStateView(State.NetworkError);
        }
        else{
            showStateView(State.ServerError);
        }
    }

    private void showStateView(@NonNull State state){
        switch (state){
            case HasData:
                this.viewLoading.setVisibility(View.GONE);
                this.viewNoData.setVisibility(View.GONE);
                this.viewError.setVisibility(View.GONE);

                this.recycler.setVisibility(View.VISIBLE);
                break;

            case Loading:
                this.viewNoData.setVisibility(View.GONE);
                this.viewError.setVisibility(View.GONE);
                this.recycler.setVisibility(View.GONE);

                this.viewLoading.setVisibility(View.VISIBLE);
                break;

            case HasNoData:
                this.viewLoading.setVisibility(View.GONE);
                this.viewNoData.setVisibility(View.GONE);
                this.recycler.setVisibility(View.GONE);

                this.textViewError.setText(getText(R.string.error_empty_data_text));
                this.viewError.setVisibility(View.VISIBLE);
                break;

            case NetworkError:
                this.viewLoading.setVisibility(View.GONE);
                this.viewNoData.setVisibility(View.GONE);
                this.recycler.setVisibility(View.GONE);

                this.textViewError.setText(getText(R.string.error_network_text));
                this.viewError.setVisibility(View.VISIBLE);
                break;

            case ServerError:
                this.viewLoading.setVisibility(View.GONE);
                this.viewNoData.setVisibility(View.GONE);
                this.recycler.setVisibility(View.GONE);

                this.textViewError.setText(getText(R.string.error_server_text));
                this.viewError.setVisibility(View.VISIBLE);
                break;

            default:
                throw new IllegalArgumentException("Wrong state: " + state);
        }

    }

    private void setupOnPause(){
        // setListener(null) for each from setupOnStert
        this.spinner.setOnItemSelectedListener(null);
        this.buttonRetry.setOnClickListener(null);
    }
}