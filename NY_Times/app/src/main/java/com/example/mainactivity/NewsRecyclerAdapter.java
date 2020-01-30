package com.example.mainactivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.mainactivity.glidemodule.GlideApp;

import com.bumptech.glide.Glide;
import com.example.mainactivity.debounced.DebouncedClickListener;

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
        View v = this.inflater.inflate(R.layout.list_item_news, parent, false);
        Log.i("DebouncedClickListener", Integer.toHexString(System.identityHashCode(v))+": creating ViewHolder");
        return new ViewHolder(
                v,
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
            this.photo = itemView.findViewById(R.id.news_img);
            this.category = itemView.findViewById(R.id.category_text);
            this.title = itemView.findViewById(R.id.title_text);
            this.preview = itemView.findViewById(R.id.preview_text);
            this.publishDdate = itemView.findViewById(R.id.date_text);

            itemView.setOnClickListener(new DebouncedClickListener(this){

                public void onItemClick(View v){
                    itemClickedCallback.onItemClicked(item);
                }
            });
        }

        // binds the view holder to its data; replace the contents of a view
        public void bind(Context context, NewsItem item){

            Glide.with(context).load(item.getImageUrl()).into(ViewHolder.this.photo);

            this.category.setText(item.getCategory().getName());
            this.title.setText(item.getTitle());
            this.preview.setText(item.getPreviewText());
            this.publishDdate.setText(item.getPublishDate().toString());
            this.item = item;
        }

    }

}
