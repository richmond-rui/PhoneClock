package com.lanlengran.photoclock;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import static android.content.Context.KEYGUARD_SERVICE;

public class LockUtils {

    public static boolean isChanging(Context context) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        // 是否在充电
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        return isCharging;
    }


    public static DevicePolicyManager checkPermissionDPM(Context context) {
        ComponentName mAdminName = new ComponentName(context, AdminManageReceiver.class);
        //获取设备管理器
        DevicePolicyManager mDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        //如果还没有激活设备管理器，则直接进入到设备管理器界面
        if (!mDPM.isAdminActive(mAdminName)) {
            showAdminManage(mAdminName, context);
            return null;
        }
        //如果已经有了设备管理器的权限，则锁定屏幕
        if (mDPM.isAdminActive(mAdminName)) {
            return mDPM;
        }
        return null;
    }

    public static void lockDevice(Context context) {
        //如果已经有了设备管理器的权限，则锁定屏幕
        DevicePolicyManager mDPM = checkPermissionDPM(context);
        if (mDPM != null) {
            mDPM.lockNow();
        }
    }


    private static void showAdminManage(ComponentName mAdminName, Context context) {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mAdminName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, R.string.app_name);
        context.startActivity(intent);
    }

    public static void wakeUpAndUnlock(Activity activity) {
        // 获取电源管理器对象
        PowerManager pm = (PowerManager) activity.getApplicationContext()
                .getSystemService(Context.POWER_SERVICE);
        boolean screenOn = pm.isScreenOn();
        if (!screenOn) {
            // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(
                    PowerManager.ACQUIRE_CAUSES_WAKEUP |
                            PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
            wl.acquire(10000); // 点亮屏幕
            wl.release(); // 释放
        }
        // 屏幕解锁
        KeyguardManager keyguardManager = (KeyguardManager) activity.getApplicationContext()
                .getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("unLock");
        // 屏幕锁定
        keyguardLock.reenableKeyguard();
        keyguardLock.disableKeyguard(); // 解锁
        unLockScreen(activity);
    }

    private static void unLockScreen(Activity activity) {
        final Window win = activity.getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
    }
}
