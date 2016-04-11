package com.example.jerry.crimeintent;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.jerry.crimeintent.Model.Crime;
import com.example.jerry.crimeintent.Model.CrimeLab;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by jerry on 02.04.2016.
 */
public class CrimePagerActivity extends FragmentActivity
    implements CrimeFragment.Callbacks
{
    ViewPager mViewPager;
    ArrayList<Crime> mCrimes;

    private static final String TAG = "CrimePagerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreated");

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        mCrimes = CrimeLab.get(this).getCrimes();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.Id);
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        for (int i = 0; i < mCrimes.size(); i++)
        {
            if(mCrimes.get(i).Id.equals(crimeId)){
                mViewPager.setCurrentItem(i);
                setTitle(mCrimes.get(i).Title);
                break;
            }
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Crime crime = mCrimes.get(position);
                if(crime.Title != null)
                {
                    setTitle(crime.Title);

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
    }
}
