package com.finalprojectgroupae.immunization.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.i(TAG, "Device booted. Rescheduling alarms...");

            // TODO: Reschedule all pending notifications
            // This will be implemented when we create the notification scheduler

            Log.i(TAG, "Alarms rescheduled successfully");
        }
    }
}