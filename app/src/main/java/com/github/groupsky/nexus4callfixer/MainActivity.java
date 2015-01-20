package com.github.groupsky.nexus4callfixer;

import android.content.ComponentName;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.stericson.RootShell.RootShell;

import static android.content.pm.PackageManager.*;
import static android.view.View.*;

public class MainActivity extends ActionBarActivity implements OnClickListener {

    private static final String TAG = "SSD";
    Button btnEnable;
    Button btnDisable;
    ComponentName br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEnable = (Button)findViewById(android.R.id.button1);
        btnEnable.setOnClickListener(this);
        btnDisable = (Button)findViewById(android.R.id.button2);
        btnDisable.setOnClickListener(this);

        br = new ComponentName(this, CallReceiver.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateState();
    }

    private void updateState() {
        int state = getPackageManager().getComponentEnabledSetting(br);
        boolean enabled = state == COMPONENT_ENABLED_STATE_ENABLED;
        Log.d(TAG, String.format("broadcast receiver state %d: %b", state, enabled));
        btnEnable.setVisibility(enabled?GONE:VISIBLE);
        btnDisable.setVisibility(enabled?VISIBLE:GONE);
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
