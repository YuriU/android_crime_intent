package com.example.jerry.draganddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.jerry.draganddraw.Model.Box;

import java.util.ArrayList;

/**
 * Created by jerry on 04.08.2016.
 */
public class BoxDrawingView extends View {
    public static final String TAG = "BoxDrawingView";

    private Box mCurrentBox;
    private ArrayList<Box> mBoxes = new ArrayList<Box>();
    private Paint mBoxPaint;
    private Paint mBackGroundPaint;

    public BoxDrawingView(Context context) {
        this(context, null);
    }

    public BoxDrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        mBoxPaint = new Paint();
        mBoxPaint.setColor(0x22ff0000);

        mBackGroundPaint = new Paint();
        mBackGroundPaint.setColor(0xfff8efe0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF curr = new PointF(event.getX(), event.getY());

        Log.i(TAG, "Received event at x = " + curr.x + ", y = " + curr.y + ":");

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "  ACTION_DOWN");
                mCurrentBox = new Box(curr);
                mBoxes.add(mCurrentBox);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "  ACTION_MOVE");
                if(mCurrentBox != null){
                    mCurrentBox.setmCurrent(curr);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "  ACTION_UP");
                mCurrentBox = null;
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "  ACTION_CANCEL");
                mCurrentBox = null;
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(mBackGroundPaint);

        for (Box box : mBoxes){
            float left = Math.min(box.getOrigin().x, box.getmCurrent().x);
            float right = Math.max(box.getOrigin().x, box.getmCurrent().x);
            float top = Math.min(box.getOrigin().y, box.getmCurrent().y);
            float bottom = Math.max(box.getOrigin().y, box.getmCurrent().y);

            canvas.drawRect(left, top, right, bottom, mBoxPaint);
        }
    }
}