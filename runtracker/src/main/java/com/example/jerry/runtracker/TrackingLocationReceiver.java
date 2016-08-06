package com.example.jerry.runtracker;

import android.content.Context;
import android.location.Location;

import com.example.jerry.runtracker.Model.RunManager;

/**
 * Created by jerry on 06.08.2016.
 */
public class TrackingLocationReceiver extends LocationReceiver {
    @Override
    protected void onLocationReceived(Context context, Location loc) {
        RunManager.get(context).insertLocation(loc);
    }
}
