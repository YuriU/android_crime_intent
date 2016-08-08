package com.example.jerry.runtracker.Loaders;

import android.content.Context;

import com.example.jerry.runtracker.Model.Run;
import com.example.jerry.runtracker.Model.RunManager;

/**
 * Created by jerry on 08.08.2016.
 */
public class RunLoader extends DataLoader<Run>{
    private long mRunId;
    public RunLoader(Context context, long runId) {
        super(context);
        mRunId = runId;
    }

    @Override
    public Run loadInBackground() {
        return RunManager.get(getContext()).getRun(mRunId);
    }
}
