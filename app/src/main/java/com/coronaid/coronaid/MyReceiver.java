package com.coronaid.coronaid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String lang = intent.getStringExtra("lang");
        Intent intent1 = new Intent(context, MyNewIntentService.class);
        intent1.putExtra("lang", lang);
        context.startService(intent1);
    }
}