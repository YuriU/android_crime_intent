package com.example.jerry.runtracker.Loaders;

import android.content.Context;
import android.location.Location;

import com.example.jerry.runtracker.Model.RunManager;

/**
 * Created by jerry on 08.08.2016.
 */
public class LastLocationLoader extends DataLoader<Location> {
    private long mRunId;

    public LastLocationLoader(Context context, long runId) {
        super(context);
        mRunId = runId;
    }

    @Override
    public Location loadInBackground() {
        return RunManager.get(getContext()).getLastLocationForRun(mRunId);
    }
}
