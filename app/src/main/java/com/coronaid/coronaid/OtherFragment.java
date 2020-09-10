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
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;


import java.util.Locale;

import static com.coronaid.coronaid.LanguageActivity.LANGUAGE;
import static com.coronaid.coronaid.LanguageActivity.SHARED_PREFS;
import static com.coronaid.coronaid.MainActivity.DARKMODE;
import static com.coronaid.coronaid.MainActivity.NOTIFICATION;

public class OtherFragment extends Fragment {
    public OtherFragment() {}

    ConstraintLayout opt1, opt2, opt3;
    TextView opt1Text, opt2Text, opt3Text;
    View rootView;

    SharedPreferences sharedPreferences;
    String currLang;
    Boolean currMode;
    Boolean currNotif;

    Switch notSwitch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        currLang = sharedPreferences.getString(LANGUAGE, (String) Locale.getDefault().getLanguage());
        currMode = sharedPreferences.getBoolean(DARKMODE, false);
        currNotif = sharedPreferences.getBoolean(NOTIFICATION, true);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_other, container, false);
            opt1 = rootView.findViewById(R.id.opt1);
            opt2 = rootView.findViewById(R.id.opt2);

            opt1Text = rootView.findViewById(R.id.opt1text);
            opt2Text = rootView.findViewById(R.id.opt2text);

            notSwitch = (Switch) rootView.findViewById(R.id.opt2switch);

            if (currNotif) {
                notSwitch.setChecked(true);
            } else {
                notSwitch.setChecked(false);
            }
            notSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        editor.putBoolean(NOTIFICATION, true);
                        editor.apply();
                        Log.i("notif pref", "true");
                    } else {
                        editor.putBoolean(NOTIFICATION, false);
                        editor.apply();
                        Log.i("notif pref", "false");
                    }
                }
            });

            if (currLang.equals("tr")) {
                opt1Text.setText(R.string.option_language_tr);
                opt2Text.setText(R.string.option_notification_tr);
            } else {
                opt1Text.setText(R.string.option_language);
                opt2Text.setText(R.string.option_notification);
            }

            opt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LanguageActivity.class);
                    startActivity(intent);
                }
            });

        } else {
            // update language
            opt1Text = rootView.findViewById(R.id.opt1text);
            opt2Text = rootView.findViewById(R.id.opt2text);

            notSwitch = (Switch) rootView.findViewById(R.id.opt2switch);

            if (currNotif) {
                notSwitch.setChecked(true);
            } else {
                notSwitch.setChecked(false);
            }
            notSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        editor.putBoolean(NOTIFICATION, true);
                        editor.apply();

                        Log.i("notif pref", "true");

                    } else {
                        editor.putBoolean(NOTIFICATION, false);
                        editor.apply();
                        Log.i("notif pref", "false");
                    }
                }
            });

            if (currLang.equals("tr")) {
                opt1Text.setText(R.string.option_language_tr);
                opt2Text.setText(R.string.option_notification_tr);
            } else {
                opt1Text.setText(R.string.option_language);
                opt2Text.setText(R.string.option_notification);
            }
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        currLang = sharedPreferences.getString(LANGUAGE, (String) Locale.getDefault().getLanguage());
        currMode = sharedPreferences.getBoolean(DARKMODE, false);

        opt1Text = rootView.findViewById(R.id.opt1text);
        opt2Text = rootView.findViewById(R.id.opt2text);

        if (currLang.equals("tr")) {
            opt1Text.setText(R.string.option_language_tr);
            opt2Text.setText(R.string.option_notification_tr);
        } else {
            opt1Text.setText(R.string.option_language);
            opt2Text.setText(R.string.option_notification);
        }
    }
}
