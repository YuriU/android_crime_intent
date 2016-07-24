package com.example.jerry.remotecontrol;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jerry.common.Activities.SingleFragmentActivity;

public class RemoteControlActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new RemoteControlFragment();
    }
}
