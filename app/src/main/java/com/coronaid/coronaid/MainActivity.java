package com.coronaid.coronaid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;


enum fragname {
    home,
    map,
    news,
    other
}

public class MainActivity extends AppCompatActivity {

    HomeFragment homeFragment = new HomeFragment();
    WorldMapFragment worldMapFragment = new WorldMapFragment();
    NewsFragment newsFragment = new NewsFragment();
    OtherFragment otherFragment = new OtherFragment();


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String LANGUAGE = "LANGUAGE";
    public static final String DARKMODE = "DARKMODE";
    public static final String NOTIFICATION = "NOTIFICATION";


    ImageView homeToolbar;
    ImageView mapToolbar;
    ImageView newsToolbar;
    ImageView otherToolbar;

    TextView homeText;
    TextView mapText;
    TextView newsText;
    TextView otherText;

    Integer fragNo;

    int NOTIFICATION_REMINDER_NIGHT = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragNo = 0;

        String ln = Locale.getDefault().getLanguage();

        Log.i("Current language", ln);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String currLang = sharedPreferences.getString(LANGUAGE, (String) Locale.getDefault().getLanguage());
        Boolean currMode = sharedPreferences.getBoolean(DARKMODE, false);
        Boolean currNotif = sharedPreferences.getBoolean(NOTIFICATION, true);


        if (currNotif) {
            Intent notifyIntent = new Intent(this, MyReceiver.class);
            notifyIntent.putExtra("lang", currLang);
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);

            if (hour >= 20) {
                calendar.add(Calendar.DATE, 1);
            }

            calendar.set(Calendar.HOUR_OF_DAY, 20);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Log.i("schedule time", calendar.toString());
            PendingIntent pendingIntent = PendingIntent.getBroadcast
                    (this, NOTIFICATION_REMINDER_NIGHT, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,  calendar.getTimeInMillis(),
                    0, pendingIntent);
            Log.i("Scheduled notification", "created");
        } else {
            Intent notifyIntent = new Intent(this, MyReceiver.class);
            notifyIntent.putExtra("lang", currLang);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_REMINDER_NIGHT, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
            Log.i("Scheduled notification", "cancelled");
        }

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        homeToolbar = findViewById(R.id.homeToolbar);
        mapToolbar = findViewById(R.id.mapToolbar);
        newsToolbar = findViewById(R.id.newsToolbar);
        otherToolbar = findViewById(R.id.otherToolbar);

        homeText = findViewById(R.id.homeToolbarText);
        mapText = findViewById(R.id.mapToolbarText);
        newsText = findViewById(R.id.newsToolbarText);
        otherText = findViewById(R.id.otherToolbarText);

        if (currLang.equals("tr")) {
            homeText.setText(R.string.toolbar_dashboard_tr);
            mapText.setText(R.string.toolbar_map_tr);
            newsText.setText(R.string.toolbar_news_tr);
            otherText.setText(R.string.toolbar_other_tr);
        } else {
            homeText.setText(R.string.toolbar_dashboard);
            mapText.setText(R.string.toolbar_map);
            newsText.setText(R.string.toolbar_news);
            otherText.setText(R.string.toolbar_other);
        }

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        switch (fragNo) {
            case 0:
                fragmentTransaction.replace(R.id.mainContainer, homeFragment);
                changeToolbarColors(fragname.home);
                break;
            case 1:
                fragmentTransaction.replace(R.id.mainContainer, worldMapFragment);
                changeToolbarColors(fragname.map);
                break;
            case 2:
                fragmentTransaction.replace(R.id.mainContainer, newsFragment);
                changeToolbarColors(fragname.news);
                break;
            case 3:
                fragmentTransaction.replace(R.id.mainContainer, otherFragment);
                changeToolbarColors(fragname.other);
                break;
        }
        fragmentTransaction.commit();
    }

    public void changeToolbarColors(fragname fname) {
        switch (fname) {
            case home:
                homeToolbar.setImageResource(R.drawable.home_pink_clicked_24);
                mapToolbar.setImageResource(R.drawable.location_pink_24);
                newsToolbar.setImageResource(R.drawable.news_pink_24);
                otherToolbar.setImageResource(R.drawable.other_pink_24);
                homeText.setTextColor(getResources().getColor(R.color.app_logo_color));
                mapText.setTextColor(getResources().getColor(R.color.app_logo_color_nclick));
                newsText.setTextColor(getResources().getColor(R.color.app_logo_color_nclick));
                otherText.setTextColor(getResources().getColor(R.color.app_logo_color_nclick));
                break;
            case map:
                homeToolbar.setImageResource(R.drawable.home_pink_24);
                mapToolbar.setImageResource(R.drawable.location_pink_clicked_24);
                newsToolbar.setImageResource(R.drawable.news_pink_24);
                otherToolbar.setImageResource(R.drawable.other_pink_24);
                homeText.setTextColor(getResources().getColor(R.color.app_logo_color_nclick));
                mapText.setTextColor(getResources().getColor(R.color.app_logo_color));
                newsText.setTextColor(getResources().getColor(R.color.app_logo_color_nclick));
                otherText.setTextColor(getResources().getColor(R.color.app_logo_color_nclick));
                break;
            case news:
                homeToolbar.setImageResource(R.drawable.home_pink_24);
                mapToolbar.setImageResource(R.drawable.location_pink_24);
                newsToolbar.setImageResource(R.drawable.news_pink_clicked_24);
                otherToolbar.setImageResource(R.drawable.other_pink_24);
                homeText.setTextColor(getResources().getColor(R.color.app_logo_color_nclick));
                mapText.setTextColor(getResources().getColor(R.color.app_logo_color_nclick));
                newsText.setTextColor(getResources().getColor(R.color.app_logo_color));
                otherText.setTextColor(getResources().getColor(R.color.app_logo_color_nclick));
                break;
            case other:
                homeToolbar.setImageResource(R.drawable.home_pink_24);
                mapToolbar.setImageResource(R.drawable.location_pink_24);
                newsToolbar.setImageResource(R.drawable.news_pink_24);
                otherToolbar.setImageResource(R.drawable.other_pink_clicked_24);
                homeText.setTextColor(getResources().getColor(R.color.app_logo_color_nclick));
                mapText.setTextColor(getResources().getColor(R.color.app_logo_color_nclick));
                newsText.setTextColor(getResources().getColor(R.color.app_logo_color_nclick));
                otherText.setTextColor(getResources().getColor(R.color.app_logo_color));
                break;
        }
    }

    public void toolbarClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        switch (v.getId()) {
            case R.id.homeToolbarLayout:
                changeToolbarColors(fragname.home);
                fragNo = 0;
                fragmentTransaction.replace(R.id.mainContainer, homeFragment);
                fragmentTransaction.commit();
                break;

            case R.id.mapToolbarLayout:
                changeToolbarColors(fragname.map);
                fragNo = 1;
                fragmentTransaction.replace(R.id.mainContainer, worldMapFragment);
                fragmentTransaction.commit();
                break;
            case R.id.newsToolbarLayout:
                changeToolbarColors(fragname.news);
                fragNo = 2;
                fragmentTransaction.replace(R.id.mainContainer, newsFragment);
                fragmentTransaction.commit();
                break;
            case R.id.otherToolbarLayout:
                changeToolbarColors(fragname.other);
                fragNo = 3;
                fragmentTransaction.replace(R.id.mainContainer, otherFragment);
                fragmentTransaction.commit();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String currLang = sharedPreferences.getString(LANGUAGE, (String) Locale.getDefault().getLanguage());
        Boolean currMode = sharedPreferences.getBoolean(DARKMODE, false);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        homeToolbar = findViewById(R.id.homeToolbar);
        mapToolbar = findViewById(R.id.mapToolbar);
        newsToolbar = findViewById(R.id.newsToolbar);
        otherToolbar = findViewById(R.id.otherToolbar);

        homeText = findViewById(R.id.homeToolbarText);
        mapText = findViewById(R.id.mapToolbarText);
        newsText = findViewById(R.id.newsToolbarText);
        otherText = findViewById(R.id.otherToolbarText);

        if (currLang.equals("tr")) {
            homeText.setText(R.string.toolbar_dashboard_tr);
            mapText.setText(R.string.toolbar_map_tr);
            newsText.setText(R.string.toolbar_news_tr);
            otherText.setText(R.string.toolbar_other_tr);
        } else {
            homeText.setText(R.string.toolbar_dashboard);
            mapText.setText(R.string.toolbar_map);
            newsText.setText(R.string.toolbar_news);
            otherText.setText(R.string.toolbar_other);
        }


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        switch (fragNo) {
            case 0:
                fragmentTransaction.replace(R.id.mainContainer, homeFragment);
                changeToolbarColors(fragname.home);
                break;
            case 1:
                fragmentTransaction.replace(R.id.mainContainer, worldMapFragment);
                changeToolbarColors(fragname.map);
                break;
            case 2:
                fragmentTransaction.replace(R.id.mainContainer, newsFragment);
                changeToolbarColors(fragname.news);
                break;
            case 3:
                fragmentTransaction.replace(R.id.mainContainer, otherFragment);
                changeToolbarColors(fragname.other);
                break;
        }
        fragmentTransaction.commit();
    }
}
