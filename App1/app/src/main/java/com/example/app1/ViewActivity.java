package com.example.app1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button1;
    private TextView textObject1;
    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view);

        msg = getIntent().getStringExtra("msg");
        textObject1 = (TextView) findViewById(R.id.textView2);
        textObject1.setText(msg);  //getExtras().getString("msg")

        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent1 = new Intent(Intent.ACTION_SEND);
        if (intent1.resolveActivity(getPackageManager()) == null){
            button1.setEnabled(false);
            Toast.makeText(getApplicationContext(), "Can't find Email app", Toast.LENGTH_LONG).show();
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
