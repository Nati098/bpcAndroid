package com.example.app1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewActivity extends AppCompatActivity {

    public static final String MSG_LABEL = "msg";
    public static final String EMAIL_ERROR_LABEL = "Can't find Email app";

    private Button button1;
    private TextView textObject1;
    private String msg;

    public static void start(Activity activity, String msg){
        Intent intent1 = new Intent(activity, ViewActivity.class);
        intent1.putExtra(MSG_LABEL, msg);
        activity.startActivity(intent1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        msg = getIntent().getStringExtra(MSG_LABEL);
        textObject1 = findViewById(R.id.textView2);
        textObject1.setText(msg);  //getExtras().getString("msg")

        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toEmailActivity();
            }
        });
    }


    private void toEmailActivity() {
        Intent intent1 = new Intent(Intent.ACTION_SEND);
        if (intent1.resolveActivity(getPackageManager()) == null){
            button1.setEnabled(false);
            Toast.makeText(getApplicationContext(), EMAIL_ERROR_LABEL, Toast.LENGTH_LONG).show();
        }
        else{
            intent1.setData(Uri.parse("mailto:"));
            intent1.setType("*/*");
            intent1.putExtra(Intent.EXTRA_EMAIL, "chicken000@mail.ru");
            intent1.putExtra(Intent.EXTRA_SUBJECT, "App1 test");
            intent1.putExtra(Intent.EXTRA_TEXT, msg);

            startActivity(intent1);
        }


    }
}
