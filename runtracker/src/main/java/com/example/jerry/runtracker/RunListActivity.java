package com.example.jerry.runtracker;

import android.support.v4.app.Fragment;

import com.example.jerry.common.Activities.SingleFragmentActivity;

/**
 * Created by jerry on 07.08.2016.
 */
public class RunListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new RunListFragment();
    }
}
