package com.coronaid.coronaid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import static com.coronaid.coronaid.HomeFragment.SHARED_PREFS;
import static com.coronaid.coronaid.LanguageActivity.DARKMODE;
import static com.coronaid.coronaid.LanguageActivity.LANGUAGE;



public class NewsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<NewsPost> newsPostArrayList = new ArrayList<>();
    private NewsAdapter newsAdapter;
    ArrayList<Integer> adPositions = new ArrayList<>();

    FirebaseDatabase database;
    FirebaseStorage storage;
    DatabaseReference newsReference;
    DatabaseReference batchReference;
    DatabaseReference idReference;


    Integer lastBatch;
    Integer downloadedNews;

    public NewsFragment() { }

    String currLang;
    Boolean currMode;

    View rootView;
    String langHist;

    Integer lastAdded;

    Integer currentAdIndex;

    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        currLang = sharedPreferences.getString(LANGUAGE, (String) Locale.getDefault().getLanguage());
        currMode = sharedPreferences.getBoolean(DARKMODE, false);

        lastAdded = 0;
        currentAdIndex = 2;

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        if (currLang.equals("tr")) {
            newsReference = database.getReference("tr_news");
            batchReference = database.getReference("tr_news_batch_id_last");
            idReference = database.getReference("tr_news_id_last");
        } else {
            newsReference = database.getReference("eng_news");
            batchReference = database.getReference("eng_news_batch_id_last");
            idReference = database.getReference("eng_news_id_last");
        }


        if (rootView == null || !langHist.equals(currLang)) {

            adPositions.clear();

            context = getActivity().getApplicationContext();
            langHist = currLang;

            rootView = inflater.inflate(R.layout.fragment_news, container, false);
            downloadedNews = 0;

            recyclerView = rootView.findViewById(R.id.newsFragmentRecyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);

            RecyclerViewClickListener listener = new RecyclerViewClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Log.i("Pressed to position", Integer.toString(position));
                    NewsPost temp = (NewsPost) newsPostArrayList.get(position);

                    String newsId = temp.getId();
                    String newsVlink = temp.getVlink();
                    String newsSummary = temp.getSummary();
                    String newsDate = temp.getDate();
                    String newsOlink = temp.getOlink();
                    String newsText = temp.getText();
                    String newsSource = temp.getSource();
                    String newsImgName = temp.getImgName();

                    if (!newsOlink.startsWith("http://www.") && !newsOlink.startsWith("https://www.")) {
                        newsOlink = "http://www." + newsOlink;
                    }

                    if (newsSource.equals("ext")) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsOlink));
                        startActivity(browserIntent);
                    } else {
                        Intent intent = new Intent(getActivity(), NewsActivity.class);
                        intent.putExtra("text", newsText);
                        intent.putExtra("date", newsDate);
                        intent.putExtra("summary", newsSummary);
                        intent.putExtra("imgName", newsImgName);
                        startActivity(intent);
                    }
                }
            };

            newsAdapter = new NewsAdapter(getActivity(), newsPostArrayList, listener);
            recyclerView.setAdapter(newsAdapter);

            recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager, newsAdapter) {
                @Override
                public void onLoadMore(int current_page) {
                    Log.i("LoadMore", "executed");
                    if (lastBatch >= 0) {
                        Log.i("Batch download scroll", lastBatch.toString());
                        newsReference.child(lastBatch.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ArrayList<NewsPost> temp = new ArrayList<>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    NewsData curr = ds.getValue(NewsData.class);
                                    downloadedNews++;
                                    String date, vlink, summary, id, olink, text, source, img_name;

                                    date = curr.getDate();
                                    olink = curr.getOut_link();
                                    vlink = curr.getVisible_link();
                                    summary = curr.getSummary();
                                    id = curr.getId();
                                    text = curr.getText();
                                    source = curr.getSource();
                                    img_name = curr.getImg_name();

                                    Log.i(downloadedNews.toString(), "id:" + curr.getId());

                                    StorageReference imgRef = storage.getReference(img_name);

                                    NewsPost post = new NewsPost(date, id, vlink, summary, imgRef, olink, source, text, img_name);

                                    temp.add(post);
                                }

                                Collections.sort(temp, new Comparator<NewsPost>() {
                                    @Override
                                    public int compare(NewsPost o1, NewsPost o2) {
                                        Integer o1id = Integer.parseInt(o1.getId());
                                        Integer o2id = Integer.parseInt(o2.getId());
                                        return o2id - o1id;
                                    }
                                });

                                newsPostArrayList.addAll(temp);
                                newsAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        lastBatch--;
                    }
                }
            });

            firstUpdate();
        }
        return rootView;
    }

    public void firstUpdate() {
        newsPostArrayList.clear();
        batchReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lastBatch = dataSnapshot.getValue(Integer.class);
                if (lastBatch >= 0) {
                    Log.i("Batch download: ", lastBatch.toString());
                    newsReference.child(lastBatch.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<NewsPost> temp = new ArrayList<>();
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                NewsData curr = ds.getValue(NewsData.class);
                                downloadedNews++;
                                String date, vlink, summary, id, olink, text, source, img_name;

                                date = curr.getDate();
                                olink = curr.getOut_link();
                                vlink = curr.getVisible_link();
                                summary = curr.getSummary();
                                id = curr.getId();
                                text = curr.getText();
                                source = curr.getSource();
                                img_name = curr.getImg_name();

                                StorageReference imgRef = storage.getReference(img_name);

                                NewsPost post = new NewsPost(date, id, vlink, summary, imgRef, olink, source, text, img_name);

                                temp.add(post);
                            }

                            Collections.sort(temp, new Comparator<NewsPost>() {
                                @Override
                                public int compare(NewsPost o1, NewsPost o2) {
                                    Integer o1id = Integer.parseInt(o1.getId());
                                    Integer o2id = Integer.parseInt(o2.getId());
                                    return o2id - o1id;
                                }
                            });

                            newsPostArrayList.addAll(temp);
                            newsAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.i("first batch cancelled", databaseError.toException().toString());
                        }
                    });

                    lastBatch--;
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("Batch id cancelled", databaseError.toException().toString());
            }
        });
    }

}




