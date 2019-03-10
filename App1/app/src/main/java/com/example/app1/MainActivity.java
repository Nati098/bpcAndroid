package com.example.app1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button1;
    private EditText editText1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);

        editText1 = (EditText) findViewById(R.id.editText1);
        //editText1.setListener
    }

    @Override
    public void onClick(View v) {
        Intent intent1 = new Intent(this, ViewActivity.class);
        intent1.putExtra("msg", editText1.getText().toString());
        startActivity(intent1);
    }
}
