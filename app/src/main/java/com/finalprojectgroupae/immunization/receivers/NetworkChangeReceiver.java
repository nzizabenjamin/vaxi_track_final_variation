package com.finalprojectgroupae.immunization.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.finalprojectgroupae.immunization.utils.NetworkUtils;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private static final String TAG = "NetworkChangeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            Log.i(TAG, "Network connected. Triggering sync...");

            // Trigger sync service
            Intent syncIntent = new Intent(context,
                    com.finalprojectgroupae.immunization.services.sync.BackgroundSyncService.class);
            syncIntent.setAction("ACTION_SYNC_DATA");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                context.startForegroundService(syncIntent);
            } else {
                context.startService(syncIntent);
            }
        } else {
            Log.i(TAG, "Network disconnected");
        }
    }
}