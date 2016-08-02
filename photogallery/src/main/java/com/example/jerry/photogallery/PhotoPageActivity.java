package com.example.jerry.photogallery;

import android.support.v4.app.Fragment;

import com.example.jerry.common.Activities.SingleFragmentActivity;

/**
 * Created by jerry on 02.08.2016.
 */
public class PhotoPageActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new PhotoPageFragment();
    }
}
