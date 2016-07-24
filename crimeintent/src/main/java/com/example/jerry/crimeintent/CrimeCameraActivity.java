package com.example.jerry.crimeintent;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by jerry on 10.04.2016.
 */
public class CrimeCameraActivity extends SingleFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        return new CrimeCameraFragment();
    }
}
