package com.lanlengran.photoclock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DevicePolicyManager mDPM= LockUtils.checkPermissionDPM(this);
        if (mDPM!=null) {
            startActivity(new Intent(this, LockClockActivity.class));
            finish();
        }
    }
}