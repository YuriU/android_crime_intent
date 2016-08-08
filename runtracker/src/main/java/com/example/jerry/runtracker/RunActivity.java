package com.example.jerry.runtracker;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jerry.common.Activities.SingleFragmentActivity;

public class RunActivity extends SingleFragmentActivity {

    public static final String EXTRA_RUN_ID = "com.example.jerry.runtracker.run_id";

    @Override
    protected Fragment createFragment() {
        long runId = getIntent().getLongExtra(EXTRA_RUN_ID, -1);
        if(runId != -1)
            return RunFragment.newInstance(runId);
        else
            return new RunFragment();
    }
}
