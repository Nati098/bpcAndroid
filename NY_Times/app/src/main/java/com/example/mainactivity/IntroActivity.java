package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private static SharedPreferences prefs;
    private static final String PREF_NAME = "tutorialwing-shared-preference";
    private static final String IS_FIRST_TIME_LAUNCH = "FIRST_TIME_LAUNCH";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        btn = findViewById(R.id.intro_button);
        btn.setOnClickListener(this);
           /*     new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().putBoolean(IS_FIRST_TIME_LAUNCH, true).apply();
                System.out.println("In onClick!");
                startSecondActivity();
            }
        });*/


        if (needToShowIntro()) {
            setContentView(R.layout.activity_intro);
            Disposable disposable = Completable.complete()
                    .delay(1, TimeUnit.SECONDS)
                    .subscribe(this::startSecondActivity);
            this.compositeDisposable.add(disposable);
        } else {
            startSecondActivity();
        }
    }

    private boolean needToShowIntro() {
        return (prefs == null) || prefs.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    private void startSecondActivity() {
        startActivity(new Intent(this, NewsListActivity.class));
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.compositeDisposable.dispose();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.intro_button) {
            prefs.edit().putBoolean(IS_FIRST_TIME_LAUNCH, true).apply();
            System.out.println("In activity onClick");
            startSecondActivity();
        }
    }


}
