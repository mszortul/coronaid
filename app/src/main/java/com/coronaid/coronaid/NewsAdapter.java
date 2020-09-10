package com.coronaid.coronaid;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<NewsPost> newsPostArrayList;
    private RecyclerViewClickListener recyclerViewClickListener;

    NewsAdapter(Context context, ArrayList<NewsPost> newsPostArrayList, RecyclerViewClickListener listener) {
        this.context = context;
        this.newsPostArrayList = newsPostArrayList;
        this.recyclerViewClickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View newsPostView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_feed, parent, false);
        return new NewsFeedViewHolder(newsPostView, recyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final NewsPost newsPost = newsPostArrayList.get(position);
        if (holder instanceof NewsFeedViewHolder) {
            final NewsFeedViewHolder rowViewHolder = (NewsFeedViewHolder) holder;
            if (newsPost.getVlink().isEmpty()) {
                rowViewHolder.link.setText(R.string.sample_link);
            } else {
                rowViewHolder.link.setText(newsPost.getVlink());
            }
            rowViewHolder.summary.setText(newsPost.getSummary());
            rowViewHolder.date.setText(newsPost.getDate());
            Glide.with(context)
                    .load(newsPost.getImgRef())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            rowViewHolder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            return false;
                        }
                    })
                    .into(rowViewHolder.image);
        }

    }

    @Override
    public int getItemCount() {
        return newsPostArrayList.size();
    }

    public static class NewsFeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView summary, date, link;
        ImageView image;
        private RecyclerViewClickListener clickListener;

        NewsFeedViewHolder(View view, RecyclerViewClickListener recyclerViewClickListener) {
            super(view);
            clickListener = recyclerViewClickListener;

            image = (ImageView) view.findViewById(R.id.newsImage);
            link = (TextView) view.findViewById(R.id.newsLink);
            date = (TextView) view.findViewById(R.id.newsDate);
            summary = (TextView) view.findViewById(R.id.newsSummary);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }
}
