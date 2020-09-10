package com.coronaid.coronaid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        Intent intent = getIntent();

        FirebaseStorage storage = FirebaseStorage.getInstance();


        String text = intent.getStringExtra("text");
        String date = intent.getStringExtra("date");
        String imgName = intent.getStringExtra("imgName");
        String summary = intent.getStringExtra("summary");

        final ImageView imageIv = findViewById(R.id.currentNewsImg);
        TextView dateTv = findViewById(R.id.currentNewsDate);
        TextView summaryTv = findViewById(R.id.currentNewsSummary);
        TextView textTv = findViewById(R.id.currentNewsText);

        dateTv.setText(date);
        summaryTv.setText(summary);
        textTv.setText(text);

        StorageReference reference = storage.getReference(imgName);
        Glide.with(this)
                .load(reference)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        imageIv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        return false;
                    }
                })
                .into(imageIv);

    }
}
