package com.github.groupsky.nexus4callfixer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import static android.telephony.TelephonyManager.*;

public class CallMonitorService extends Service {

    private static final String TAG = "SSD";

    public CallMonitorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TELEPHONY MANAGER class object to register one listner
        TelephonyManager tmgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        // Register listener for LISTEN_CALL_STATE
        tmgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

        if (intent != null)
            CallReceiver.completeWakefulIntent(intent);

        return START_STICKY;
    }

    private final PhoneStateListener phoneStateListener = new PhoneStateListener() {
        /**
         * Callback invoked when device call state changes.
         *
         * @see TelephonyManager#CALL_STATE_IDLE
         * @see TelephonyManager#CALL_STATE_RINGING
         * @see TelephonyManager#CALL_STATE_OFFHOOK
         */
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            Log.d(TAG, String.format("call state %d", state));
            if (state == CALL_STATE_IDLE) {
                PackageUtils.enableService();
            } else {
                PackageUtils.disableService();
            }
        }
    };
}
