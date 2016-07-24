package com.example.jerry.crimeintent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.jerry.crimeintent.Model.Crime;
import com.example.jerry.crimeintent.Model.CrimeLab;
import com.example.jerry.crimeintent.Model.Photo;
import com.example.jerry.crimeintent.Model.PictureUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by jerry on 30.03.2016.
 */
public class CrimeFragment extends Fragment {
    private static final String TAG = "CrimeFragment";

    public static final String EXTRA_CRIME_ID = "com.example.jerry.crimeintent.crime_id";
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_IMAGE = "image";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;
    private static final int REQUEST_CONTACT = 2;

    private Crime crime;
    private EditText TitleEdit;
    private Button DateButton;
    private CheckBox SolvedCheckBox;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private Button mSuspectButton;
    private Callbacks mCallBacks;

    public interface Callbacks{
        void onCrimeUpdated(Crime crime);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallBacks = (Callbacks)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBacks = null;
    }

    public static CrimeFragment newInstance(UUID crimeId)
    {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
        crime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Override
    public void onStart() {
        super.onStart();
        showPhoto();
    }

    @Override
    public void onStop() {
        super.onStop();
        PictureUtils.cleanImageView(mPhotoView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                /*Intent i = new Intent(getActivity(), CrimeListActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);*/
                if(NavUtils.getParentActivityName(getActivity()) != null)
                {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            if(NavUtils.getParentActivityName(getActivity()) != null)
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TitleEdit = (EditText)v.findViewById(R.id.crime_title);
        TitleEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                crime.Title = s.toString();
                //getActivity().setTitle(crime.Title);
                mCallBacks.onCrimeUpdated(crime);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        TitleEdit.setText(crime.Title);
        //getActivity().setTitle(crime.Title);



        DateButton = (Button)v.findViewById(R.id.crime_date);
        DateButton.setText(crime.Date.toString());
        DateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(crime.Date);
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        SolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        SolvedCheckBox.setChecked(crime.Solved);
        SolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime.Solved = isChecked;
                if (crime.Solved) {
                    returnResult();
                }
                mCallBacks.onCrimeUpdated(crime);
            }
        });

        mPhotoButton = (ImageButton)v.findViewById(R.id.crime_image_buttom);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CrimeCameraActivity.class);
                startActivityForResult(i, REQUEST_PHOTO);
            }
        });

        PackageManager pm = getActivity().getPackageManager();
        if(!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) &&!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)){
            mPhotoButton.setEnabled(false);
        }

        mPhotoView = (ImageView)v.findViewById(R.id.crime_imageView);
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo p = crime.Photo;
                if(p == null)
                    return;

                FragmentManager fm = getActivity().getSupportFragmentManager();
                String path = getActivity().getFileStreamPath(p.getFileName()).getAbsolutePath();
                ImageFragment.newInstance(path).show(fm, DIALOG_IMAGE);
            }
        });

        Button reportButton = (Button)v.findViewById(R.id.crime_reportButton);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));
                i = Intent.createChooser(i, getString(R.string.send_report));
                startActivity(i);
            }
        });

        mSuspectButton = (Button)v.findViewById(R.id.crime_suspectButton);
        mSuspectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i, REQUEST_CONTACT);
            }
        });

        if(crime.Suspect != null) {
            mSuspectButton.setText(crime.Suspect);
        }

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) return;

        if(requestCode == REQUEST_DATE) {
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            crime.Date = date;
            mCallBacks.onCrimeUpdated(crime);
            DateButton.setText(crime.Date.toString());
        } else if(requestCode == REQUEST_PHOTO) {
            String filename = data.getStringExtra(CrimeCameraFragment.EXTRA_PHOTO_FILENAME);
            if(filename != null){
                Photo p = new Photo(filename);
                crime.Photo = p;
                mCallBacks.onCrimeUpdated(crime);
                showPhoto();
            }
        } else if(requestCode == REQUEST_CONTACT){
            Uri contactUri = data.getData();

            String[] queryFields = new String[] {
                    ContactsContract.Contacts.DISPLAY_NAME
            };

            Cursor c = getActivity().getContentResolver().query(contactUri, queryFields, null, null, null);

            if(c.getCount() == 0){
                c.close();
                return;
            }

            c.moveToFirst();
            String suspect = c.getString(0);
            crime.Suspect = suspect;
            mCallBacks.onCrimeUpdated(crime);
            mSuspectButton.setText(suspect);
            c.close();
        }
    }

    public void returnResult()
    {
        getActivity().setResult(Activity.RESULT_OK, null);
    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.get(getActivity()).saveCrimes();
    }

    private void showPhoto(){
        Photo p = crime.Photo;
        BitmapDrawable b = null;
        if(p != null)
        {
            String path = getActivity().getFileStreamPath(p.getFileName()).getAbsolutePath();
            b = PictureUtils.getScaledDrawable(getActivity(), path);
        }
        mPhotoView.setImageDrawable(b);
    }

    private String getCrimeReport(){
        String solvedString = null;
        if(crime.Solved)
            solvedString = getString(R.string.crime_report_solved);
        else
            solvedString = getString(R.string.crime_report_unsolved);

        String dateFormat = "EEE, MM dd";

        String dateString = new SimpleDateFormat(dateFormat).format(crime.Date).toString();

        String suspect = crime.Suspect;
        if(suspect == null) {
            suspect = getString(R.string.crime_report_no_suspect);
        } else {
            suspect = getString(R.string.crime_report_suspect, suspect);
        }

        String report = getString(R.string.crime_report, crime.Title, dateString, solvedString, suspect);
        return report;
    }
}
