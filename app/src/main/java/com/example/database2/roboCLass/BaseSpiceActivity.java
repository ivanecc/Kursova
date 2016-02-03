package com.example.database2.roboCLass;

/**
 * Created by root on 29.04.15.
 */
import android.support.v7.app.ActionBarActivity;


import com.octo.android.robospice.SpiceManager;
import com.example.database2.entity.SampleSpiceService;


public abstract class BaseSpiceActivity extends ActionBarActivity {
    private SpiceManager spiceManager = new SpiceManager(SampleSpiceService.class);

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    protected SpiceManager getSpiceManager() {
        return spiceManager;
    }

}