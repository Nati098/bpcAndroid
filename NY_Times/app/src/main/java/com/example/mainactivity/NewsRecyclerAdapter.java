package com.example.mainactivity;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {

    private List<NewsItem> newsItems;
    private Context context;
    private LayoutInflater inflater;
    private final NewsItemClickedCallback itemClickedCallback;

    public NewsRecyclerAdapter(Context context, List<NewsItem> newsItems, NewsItemClickedCallback callback){
        this.context = context;
        this.newsItems = newsItems;
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

    public NewsItem getItem(int position) {
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


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView photo;
        public final TextView category;
        public final TextView title;
        public final TextView preview;
        public final TextView publishDdate;

        private NewsItem item;

        public ViewHolder(View itemView, final NewsItemClickedCallback itemClickedCallback) {
            super(itemView);
            photo = itemView.findViewById(R.id.news_img);
            category = itemView.findViewById(R.id.category_text);
            title = itemView.findViewById(R.id.title_text);
            preview = itemView.findViewById(R.id.preview_text);
            publishDdate = itemView.findViewById(R.id.date_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickedCallback.onItemClicked(item);
                }
            });
        }

        // binds the view holder to its data; replace the contents of a view
        public void bind(Context context, NewsItem item){
            Glide.with(context).load(item.getImageUrl()).into(this.photo);

            this.category.setText(item.getCategory().getName());
            this.title.setText(item.getTitle());
            this.preview.setText(item.getPreviewText());
            this.publishDdate.setText(item.getPublishDate().toString());
            this.item = item;
        }

    }

}
