package com.example.jerry.nerdlauncher;

import android.support.v4.app.Fragment;

import com.example.jerry.common.Activities.SingleFragmentActivity;

/**
 * Created by jerry on 12.04.2016.
 */
public class NerdLauncherActivity extends SingleFragmentActivity
{
    @Override
    protected Fragment createFragment() {
        return new NerdLauncherFragment();
    }
}
