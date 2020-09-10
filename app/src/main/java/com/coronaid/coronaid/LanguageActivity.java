package com.coronaid.coronaid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class LanguageActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String LANGUAGE = "LANGUAGE";
    public static final String DARKMODE = "DARKMODE";

    ImageView trCheck, enCheck;
    ConstraintLayout trLayout, enLayout;
    TextView langHeaderText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String currLang = sharedPreferences.getString(LANGUAGE, (String) Locale.getDefault().getLanguage());
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        trCheck = findViewById(R.id.lang1check);
        enCheck = findViewById(R.id.lang2check);

        trLayout = findViewById(R.id.lang1);
        enLayout = findViewById(R.id.lang2);
        langHeaderText = findViewById(R.id.langHeaderText);

        if (currLang.equals("tr")) {
            trCheck.setVisibility(View.VISIBLE);
            langHeaderText.setText(R.string.lang_header_tr);
        } else {
            enCheck.setVisibility(View.VISIBLE);
            langHeaderText.setText(R.string.lang_header);
        }

        trLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trCheck.setVisibility(View.VISIBLE);
                enCheck.setVisibility(View.INVISIBLE);
                editor.putString(LANGUAGE, "tr");
                editor.apply();
            }
        });

        enLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enCheck.setVisibility(View.VISIBLE);
                trCheck.setVisibility(View.INVISIBLE);
                editor.putString(LANGUAGE, "en");
                editor.apply();
            }
        });

    }
}
