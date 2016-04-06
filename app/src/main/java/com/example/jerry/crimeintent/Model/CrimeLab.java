package com.example.jerry.crimeintent.Model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by jerry on 02.04.2016.
 */
public class CrimeLab {
    private static final String TAG = "CrimeLab";
    private static final String FILE_NAME = "crimes.json";

    private ArrayList<Crime> mCrimes;
    private CriminalIntentJSONSerializer mSerializer;

    private static CrimeLab sCrimeLab;
    private Context mAppContext;

    private CrimeLab(Context appContext)
    {
        mAppContext = appContext;
        mSerializer = new CriminalIntentJSONSerializer(appContext, FILE_NAME);
        try{
            mCrimes = mSerializer.loadCrimes();
        }
        catch (Exception e){
            mCrimes = new ArrayList<Crime>();
            Log.e(TAG, "Error loading crimes", e);
        }

        /*for (int i = 0; i < 100; i++){
            Crime c = new Crime();
            c.Title = "Crime #" + i;
            c.Solved = i % 2 == 0;
            mCrimes.add(c);
        }*/
    }

    public static CrimeLab get(Context c)
    {
        if(sCrimeLab == null)
        {
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes()
    {
        return mCrimes;
    }

    public void addCrime(Crime c)
    {
        mCrimes.add(c);
    }

    public Crime getCrime(UUID id)
    {
        for (Crime c : mCrimes){
            if(c.Id.equals(id))
                return c;
        }
        return null;
    }

    public void deleteCrime(Crime c){
        mCrimes.remove(c);
    }

    public boolean saveCrimes()
    {
        try{
            mSerializer.saveCrimes(mCrimes);
            Log.d(TAG, "crimes saved to file");
            return true;
        }
        catch (Exception e)
        {
            Log.e(TAG, "Errpr saving crimes", e);
            return false;
        }
    }
}
