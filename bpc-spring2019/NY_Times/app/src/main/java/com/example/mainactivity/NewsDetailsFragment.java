package com.example.mainactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class NewsDetailsFragment extends Fragment {

    private static final String ARGS_IMG_URL = "args:img_url";
    private static final String ARGS_TITLE = "args:title";
    private static final String ARGS_SUBSECTION = "args:subsection";
    private static final String ARGS_DATE = "args:date";
    private static final String ARGS_FULL_TEXT = "args:full_text";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_news_details, container, false);

        String url = savedInstanceState.getString(ARGS_IMG_URL);
        if ((url != null) && (! url.equals(""))){
            Glide.with(this).load(url)
                    .into((ImageView) view.findViewById(R.id.news_details_photo));
        }
        else{
            ((ImageView) view.findViewById(R.id.news_details_photo)).setImageResource(R.drawable.empty_img);
        }

        Toolbar toolbar = view.findViewById(R.id.news_details_toolbar);

        TextView toolTitle = (TextView) toolbar.findViewById(R.id.news_details_toolbar_title);
        toolTitle.setText(savedInstanceState.getString(ARGS_SUBSECTION));

        TextView title = view.findViewById(R.id.news_details_title);
        title.setText(savedInstanceState.getString(ARGS_TITLE));

        TextView date = view.findViewById(R.id.news_details_date);
        date.setText(savedInstanceState.getString(ARGS_DATE));

        TextView full_text = view.findViewById(R.id.news_details_full_text);
        full_text.setText(savedInstanceState.getString(ARGS_FULL_TEXT));

        return view;
    }

    public static NewsDetailsFragment getInstance(String category, String imgUrl, String title, String publishDdate, String full_text){
        NewsDetailsFragment ndf = new NewsDetailsFragment();
        Bundle bundle = new Bundle();

        bundle.putString(ARGS_IMG_URL, imgUrl);
        bundle.putString(ARGS_TITLE, title);
        bundle.putString(ARGS_DATE, publishDdate);
        bundle.putString(ARGS_FULL_TEXT, full_text);
        bundle.putString(ARGS_SUBSECTION, category);

        ndf.setArguments(bundle);

        return ndf;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
