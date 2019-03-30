package com.example.mainactivity;

import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {

    private static final String addressVk = "https://vk.com/";
    private static final String addressGithub = "https://github.com";
    private static final String addressInstagram = "https://www.instagram.com/";

    private void initComponents(){
        TextView descript = this.findViewById(R.id.textView);
        descript.setText(R.string.description);

        Button sendButton = this.findViewById(R.id.button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButton();
            }
        });

        ImageView vk = this.findViewById(R.id.imageView4);
        vk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBrowser(addressVk);
            }
        });

        ImageView git = this.findViewById(R.id.imageView5);
        git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBrowser(addressGithub);
            }
        });

        ImageView inst = this.findViewById(R.id.imageView6);
        inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBrowser(addressInstagram);
            }
        });
    }

    private void setSigntureConstraint(){

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.mainLayout);

        TextView sign = new TextView(this);
        sign.setText(R.string.signature);
        sign.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        sign.setTextColor(ContextCompat.getColor(this, R.color.colorSignature));

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);

        params.topToBottom = R.id.imageView5;
        params.topMargin = 8;
        params.bottomMargin = 8;
        params.leftMargin = 8;
        params.rightMargin = 8;

        layout.addView(sign, params);
    }

    private void setSigntureRelative(){

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainLayout);

        TextView sign = new TextView(this);
        sign.setText(R.string.signature);
        sign.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        sign.setTextColor(ContextCompat.getColor(this, R.color.colorSignature));

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);

        params.addRule(RelativeLayout.BELOW, R.id.button);
        params.addRule(RelativeLayout.ALIGN_RIGHT, R.id.button);

        layout.addView(sign, params);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initComponents();

        ViewGroup vg = (ViewGroup) findViewById(R.id.mainLayout);
        if (vg instanceof ConstraintLayout) {
            setSigntureConstraint();
        }
        if (vg instanceof RelativeLayout){

        }
    }

    private void onClickButton(){
        EditText editText1 = this.findViewById(R.id.editText3);
        if (! editText1.getText().equals("")){
            Intent intent1 = new Intent(Intent.ACTION_SEND);

            intent1.setData(Uri.parse("mailto:"));
            intent1.setType("*/*");
            intent1.putExtra(Intent.EXTRA_EMAIL, editText1.getText());

            startActivity(intent1);
        }
        else{
            Toast.makeText(getApplicationContext(), R.string.email_error, Toast.LENGTH_LONG).show();
        }
    }

    private void goToBrowser(String address) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(address));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), R.string.browser_error, Toast.LENGTH_LONG).show();
        }
    }
}

