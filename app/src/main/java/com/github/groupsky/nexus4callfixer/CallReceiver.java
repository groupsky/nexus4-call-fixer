package com.github.groupsky.nexus4callfixer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class CallReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, CallMonitorService.class);
        startWakefulService(context, service);
    }
}
