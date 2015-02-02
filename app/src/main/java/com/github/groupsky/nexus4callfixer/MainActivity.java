package com.github.groupsky.nexus4callfixer;

import android.app.Activity;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stericson.RootShell.RootShell;

import static android.content.pm.PackageManager.*;
import static android.view.View.*;
import static com.github.groupsky.nexus4callfixer.R.id;
import static com.github.groupsky.nexus4callfixer.R.string.*;

public class MainActivity extends Activity implements OnClickListener {

    private static final String TAG = "SSD";
    Button btnEnable;
    Button btnDisable;
    ComponentName br;
    ComponentName ss;
    TextView lblStatusSyncService;
    TextView lblStatusBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEnable = (Button)findViewById(android.R.id.button1);
        btnEnable.setOnClickListener(this);
        btnDisable = (Button)findViewById(android.R.id.button2);
        btnDisable.setOnClickListener(this);
        lblStatusSyncService = (TextView)findViewById(id.status_sync_service);
        lblStatusBroadcastReceiver = (TextView)findViewById(id.status_broadcast_receiver);

        br = new ComponentName(this, CallReceiver.class);
        ss = new ComponentName("com.google.android.gms", "com.google.android.gms.checkin.CheckinService");
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateState();
    }

    private boolean displayState(ComponentName component, TextView view, boolean defaultEnabled) {
        int state = getPackageManager().getComponentEnabledSetting(component);
        Log.d(TAG, component+" state "+state);
        boolean enabled = state == COMPONENT_ENABLED_STATE_ENABLED || defaultEnabled && state == COMPONENT_ENABLED_STATE_DEFAULT;
        view.setText(enabled?status_enabled:status_disabled);
        return enabled;
    }

    private void updateState() {
        Log.d(TAG, "display broadcast receiver status");
        if (displayState(br, lblStatusBroadcastReceiver, false)) {
            btnEnable.setVisibility(GONE);
            btnDisable.setVisibility(VISIBLE);
        } else {
            btnEnable.setVisibility(VISIBLE);
            btnDisable.setVisibility(GONE);
        }
        Log.d(TAG, "display sync service status");
        displayState(ss, lblStatusSyncService, true);
        Log.d(TAG, "done");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case android.R.id.button1:
                enable();
                break;
            case android.R.id.button2:
                disable();
                break;
        }
        updateState();
    }

    private void modify(int state) {
        getPackageManager().setComponentEnabledSetting(br, state, DONT_KILL_APP);
        Log.d(TAG, String.format("broadcast receiver set to %d", state));
    }

    private void enable() {
        if (!RootShell.isAccessGiven()) {
            Toast.makeText(this, "Root access is required to modify SyncService!", Toast.LENGTH_SHORT).show();
        } else
            modify(COMPONENT_ENABLED_STATE_ENABLED);
    }

    private void disable() {
        modify(COMPONENT_ENABLED_STATE_DISABLED);
    }
}
