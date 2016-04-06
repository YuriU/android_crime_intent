package com.example.jerry.crimeintent;

import android.support.v4.app.Fragment;

/**
 * Created by jerry on 02.04.2016.
 */
public class CrimeListActivity extends SingleFragmentUtility {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}


