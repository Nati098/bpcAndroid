package com.example.mainactivity;

import android.content.Context;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mainactivity.data.network.dto.NewsDTO;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private final NewsItemClickedCallback<NewsDTO> itemClickedCallback;

    private List<NewsDTO> newsItems = new ArrayList<>();

    public NewsRecyclerAdapter(Context context, NewsItemClickedCallback<NewsDTO> callback){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.itemClickedCallback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                this.inflater.inflate(R.layout.list_item_news, parent, false),
                itemClickedCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(this.context, this.newsItems.get(position));
    }

    public NewsDTO getItem(int position) {
        return this.newsItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return this.newsItems.size();
    }

    public void replaceItems(@NonNull List<NewsDTO> items) {
        this.newsItems.clear();
        this.newsItems.addAll(items);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView photo;
        public final TextView subsection;
        public final TextView title;
        public final TextView preview;
        public final TextView publishedDate;

        ProgressBar progressBar;

        private NewsDTO item;

        public ViewHolder(View itemView, final NewsItemClickedCallback<NewsDTO> itemClickedCallback) {
            super(itemView);
            this.photo = itemView.findViewById(R.id.news_img);
            this.subsection = itemView.findViewById(R.id.category_text);
            this.title = itemView.findViewById(R.id.title_text);
            this.preview = itemView.findViewById(R.id.preview_text);
            this.publishedDate = itemView.findViewById(R.id.date_text);
            this.progressBar = itemView.findViewById(R.id.news_item_progress);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickedCallback.onItemClicked(item);
                }
            });
        }

        // binds the view holder to its data; replace the contents of a view
        public void bind(Context context, NewsDTO item){
            this.progressBar.setVisibility(View.VISIBLE);
            Glide.with(context).load(item.getUrl())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            System.err.println("Fail to load image: ViewHolder, bind");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(ViewHolder.this.photo);

            this.subsection.setText(item.getSubsection());
            this.title.setText(item.getTitle());
            this.preview.setText(item.getAbstract());
            this.publishedDate.setText(item.getPublishedDate());
            this.item = item;
        }

    }

}
