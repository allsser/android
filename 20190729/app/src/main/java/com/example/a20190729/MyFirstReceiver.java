package com.example.a20190729;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyFirstReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getExtras().getString("broadcastMSG");
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
