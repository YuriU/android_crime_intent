package com.example.jerry.draganddraw.Model;

import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by jerry on 04.08.2016.
 */
public class Box {
    private PointF mOrigin;
    private PointF mCurrent;

    public Box(PointF origin){
        mOrigin = mCurrent = origin;
    }

    public void setmCurrent(PointF current){
        mCurrent = current;
    }

    public PointF getOrigin()
    {
        return mOrigin;
    }

    public PointF getmCurrent()
    {
        return mCurrent;
    }
}
