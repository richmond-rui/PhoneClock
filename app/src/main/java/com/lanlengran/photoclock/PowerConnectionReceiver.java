package com.lanlengran.photoclock;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

public class PowerConnectionReceiver extends BroadcastReceiver {
    private static final String TAG = "PowerConnectionReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startActivity(new Intent(context,UnlockActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }



}
