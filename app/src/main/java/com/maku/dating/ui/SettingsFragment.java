package com.maku.dating.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.maku.dating.R;
import com.maku.dating.ui.interfaces.IMainActivity;
import com.maku.dating.ui.util.Preferences;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{


    private static final String TAG = "SettingsFragment";

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        Log.d(TAG, "onItemSelected: clicked.");

        switch (adapterView.getId()){

            case R.id.gender_spinner:
                Log.d(TAG, "onItemSelected: selected a gender: " + (String)adapterView.getItemAtPosition(pos));
                mSelectedGender = (String)adapterView.getItemAtPosition(pos);
                break;

            case R.id.interested_in_spinner:
                Log.d(TAG, "onItemSelected: selected an interest: " + (String)adapterView.getItemAtPosition(pos));
                mSelectedInterest = (String)adapterView.getItemAtPosition(pos);
                break;

            case R.id.relationship_status_spinner:
                Log.d(TAG, "onItemSelected: selected a status: " + (String)adapterView.getItemAtPosition(pos));
                mSelectedStatus = (String)adapterView.getItemAtPosition(pos);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d(TAG, "onNothingSelected: nothing is selected.");
    }

    //constants
    private static final int NEW_PHOTO_REQUEST = 3567;
    private static final int VERIFY_PERMISSIONS_REQUEST = 1235;

    //widgets
    private TextView mFragmentHeading;
    private RelativeLayout mBackArrow;
    private EditText mName;
    private Spinner mGenderSpinner, mInterestedInSpinner, mStatusSpinner;
    private CircleImageView mProfileImage;
    private Button mSave;

    //vars
    private IMainActivity mInterface;
    private String mSelectedGender, mSelectedInterest, mSelectedStatus, mSelectedImageUrl;
    private Boolean mPermissionsChecked = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Log.d(TAG, "onCreateView: started.");
        mBackArrow = view.findViewById(R.id.back_arrow);
        mFragmentHeading = view.findViewById(R.id.fragment_heading);
        mName = view.findViewById(R.id.name);
        mGenderSpinner = view.findViewById(R.id.gender_spinner);
        mInterestedInSpinner = view.findViewById(R.id.interested_in_spinner);
        mStatusSpinner = view.findViewById(R.id.relationship_status_spinner);
        mProfileImage = view.findViewById(R.id.profile_image);
        mSave = view.findViewById(R.id.btn_save);

        mProfileImage.setOnClickListener(this);
        mSave.setOnClickListener(this);

        checkPermissions();
        setBackgroundImage(view);
        initToolbar();
        getSavedPreferences();


        return view;
    }

    private void getSavedPreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String name = preferences.getString(Preferences.NAME, "");
        mName.setText(name);

        mSelectedGender = preferences.getString(Preferences.GENDER, getString(R.string.gender_none));
        String[] genderArray = getActivity().getResources().getStringArray(R.array.gender_array);
        for(int i = 0; i < genderArray.length; i++){
            if(genderArray[i].equals(mSelectedGender)){
                mGenderSpinner.setSelection(i, false);
            }
        }

        mSelectedInterest = preferences.getString(Preferences.INTERESTED_IN, getString(R.string.interested_in_anyone));
        String[] interestArray = getActivity().getResources().getStringArray(R.array.interested_in_array);
        for(int i = 0; i < interestArray.length; i++){
            if(interestArray[i].equals(mSelectedInterest)){
                mInterestedInSpinner.setSelection(i, false);
            }
        }

        mSelectedStatus = preferences.getString(Preferences.RELATIONSHIP_STATUS, getString(R.string.status_looking));
        String[] statusArray = getActivity().getResources().getStringArray(R.array.relationship_status_array);
        for(int i = 0; i < statusArray.length; i++){
            if(statusArray[i].equals(mSelectedStatus)){
                mStatusSpinner.setSelection(i, false);
            }
        }

        mSelectedImageUrl = preferences.getString(Preferences.PROFILE_IMAGE, "");
        if(!mSelectedImageUrl.equals("")){
            Glide.with(getActivity())
                    .load(mSelectedImageUrl)
                    .into(mProfileImage);
        }
//
        mGenderSpinner.setOnItemSelectedListener(this);
        mInterestedInSpinner.setOnItemSelectedListener(this);
        mStatusSpinner.setOnItemSelectedListener(this);

        Log.d(TAG, "getSavedPreferences: name: " + name);
        Log.d(TAG, "getSavedPreferences: gender: " + mSelectedGender);
        Log.d(TAG, "getSavedPreferences: interested in: " + mSelectedInterest);
        Log.d(TAG, "getSavedPreferences: status: " + mSelectedStatus);
    }


    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: clicked.");

        if(view.getId() == R.id.back_arrow){
            Log.d(TAG, "onClick: navigating back.");
            mInterface.onBackPressed();
        }

        if(view.getId() == R.id.btn_save){
            Log.d(TAG, "onClick: saving.");
            savePreferences();
        }

        if(view.getId() == R.id.profile_image){
            Log.d(TAG, "onClick: opening activity to choose a photo.");
            if(mPermissionsChecked){
                Intent intent = new Intent(getActivity(), ChoosePhotoActivity.class);
                startActivityForResult(intent, NEW_PHOTO_REQUEST);
            }
            else{
                checkPermissions();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");

        if(requestCode == NEW_PHOTO_REQUEST) {
            Log.d(TAG, "onActivityResult: received an activity result from photo request.");
            if (data != null) {
                if (data.hasExtra(getString(R.string.intent_new_gallery_photo))) {
                    Glide.with(getActivity())
                            .load(data.getStringExtra(getString(R.string.intent_new_gallery_photo)))
                            .into(mProfileImage);
                    mSelectedImageUrl = data.getStringExtra(getString(R.string.intent_new_gallery_photo));
                }
                else if (data.hasExtra(getString(R.string.intent_new_camera_photo))) {
                    Glide.with(getActivity())
                            .load(data.getStringExtra(getString(R.string.intent_new_camera_photo)))
                            .into(mProfileImage);
                    mSelectedImageUrl = data.getStringExtra(getString(R.string.intent_new_camera_photo));
                }
            }
        }
    }

    private void checkPermissions() {
        final boolean cameraGranted =
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED;
        final boolean storageGranted =
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED;


        String[] perms = null;
        if (cameraGranted) {
            if (storageGranted) {
                mPermissionsChecked = true;
            }
            else{
                perms = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            }
        } else {
            if (!storageGranted) {
                perms = new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            } else {
                perms = new String[] {Manifest.permission.CAMERA};
            }
        }

        if (perms != null) {
            ActivityCompat.requestPermissions(getActivity(), perms, VERIFY_PERMISSIONS_REQUEST);
            mPermissionsChecked = false;
        }
    }

    private void savePreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();

        String name = mName.getText().toString();
        if(!name.equals("")){
            editor.putString(Preferences.NAME, name);
            editor.apply();
        }
        else{
            Toast.makeText(getActivity(), "Enter your name", Toast.LENGTH_SHORT).show();
        }

        editor.putString(Preferences.GENDER, mSelectedGender);
        editor.apply();

        editor.putString(Preferences.INTERESTED_IN, mSelectedInterest);
        editor.apply();

        editor.putString(Preferences.RELATIONSHIP_STATUS, mSelectedStatus);
        editor.apply();

        if(!mSelectedImageUrl.equals("")){
            editor.putString(Preferences.PROFILE_IMAGE, mSelectedImageUrl);
            editor.apply();
        }

        Toast.makeText(getActivity(), "saved", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "savePreferences: name: " + name);
        Log.d(TAG, "savePreferences: gender: " + mSelectedGender);
        Log.d(TAG, "savePreferences: interested in: " + mSelectedInterest);
        Log.d(TAG, "savePreferences: status: " + mSelectedStatus);
    }



    private void setBackgroundImage(View view){
        ImageView backgroundView = view.findViewById(R.id.background);
        Glide.with(getActivity())
                .load(Resources.BACKGROUND_HEARTS)
                .into(backgroundView);

    }

    private void initToolbar(){
        Log.d(TAG, "initToolbar: initializing toolbar.");
        mBackArrow.setOnClickListener(this);
        mFragmentHeading.setText(getString(R.string.tag_fragment_settings));

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mInterface = (IMainActivity) getActivity();
    }

}
