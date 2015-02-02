package com.github.groupsky.nexus4callfixer;

import android.util.Log;

import com.stericson.RootShell.RootShell;
import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by groupsky on 20.01.15.
 */
public class PackageUtils {

    private static final String TAG = "SSD";

    public static void enableService() {
//        modifyService("unblock");
        modifyService("enable");
    }

    public static void disableService() {
        modifyService("disable");
//        modifyService("block");
    }

    private static boolean modifyService(String operation) {
        if (!RootShell.isAccessGiven()) return false;
        try {
            Log.d(TAG, String.format("Service changed to %s", operation));
            RootShell.getShell(true).add(new Command(0, "pm " + operation + " com.google.android.gms/com.google.android.gms.checkin.CheckinService"));
            return true;
        } catch (IOException e) {
            Log.e(TAG, "error while trying to " + operation + " service", e);
        } catch (TimeoutException e) {
            Log.e(TAG, "timeout waiting to " + operation + " service", e);
        } catch (RootDeniedException e) {
            Log.w(TAG, "root denied when trying to " + operation + " service", e);
        }
        return false;
    }

}
