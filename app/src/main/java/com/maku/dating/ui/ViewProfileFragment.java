package com.maku.dating.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.maku.dating.R;
import com.maku.dating.ui.interfaces.IMainActivity;
import com.maku.dating.ui.models.User;
import com.maku.dating.ui.util.Preferences;

import java.util.HashSet;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewProfileFragment extends Fragment implements OnLikeListener, View.OnClickListener{

    private static final String TAG = "ViewProfileFragment";

    //widgets
    private TextView mFragmentHeading, mName, mGender, mInterestedIn, mStatus;
    private LikeButton mLikeButton;
    private RelativeLayout mBackArrow;
    private CircleImageView mProfileImage;
    private IMainActivity mInterface;

    //vars
    private User mUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            mUser = bundle.getParcelable(getString(R.string.intent_user));
            Log.d(TAG, "onCreate: got incoming bundle: " + mUser.getName());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_profile, container, false);
        Log.d(TAG, "onCreateView: started.");
        mBackArrow = view.findViewById(R.id.back_arrow);
        mFragmentHeading = view.findViewById(R.id.fragment_heading);
        mProfileImage = view.findViewById(R.id.profile_image);
        mLikeButton = view.findViewById(R.id.heart_button);
        mName = view.findViewById(R.id.name);
        mGender = view.findViewById(R.id.gender);
        mInterestedIn = view.findViewById(R.id.interested_in);
        mStatus = view.findViewById(R.id.status);


        mLikeButton.setOnLikeListener(this);
        checkIfConnected();
        setBackgroundImage(view);
        init();

        return view;
    }

    private void checkIfConnected() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> savedNames = preferences.getStringSet(Preferences.SAVED_CONNECTIONS, new HashSet<String>());
        if(savedNames.contains(mUser.getName())){
            Log.d(TAG, "checkIfConnected: liked.");
            mLikeButton.setLiked(true);
        }
        else{
            Log.d(TAG, "checkIfConnected: not liked.");
            mLikeButton.setLiked(false);
        }
    }

    private void init(){
        Log.d(TAG, "init: initializing " + getString(R.string.tag_fragment_view_profile));
        if(mUser != null){
            Glide.with(getActivity())
                    .load(mUser.getProfile_image())
                    .into(mProfileImage);

            mName.setText(mUser.getName());
            mGender.setText(mUser.getGender());
            mInterestedIn.setText(mUser.getInterested_in());
            mStatus.setText(mUser.getStatus());
        }
    }

    private void setBackgroundImage(View view){
        ImageView backgroundView = view.findViewById(R.id.background);
        Glide.with(getActivity())
                .load(Resources.BACKGROUND_HEARTS)
                .into(backgroundView);
    }

    @Override
    public void liked(LikeButton likeButton) {
        Log.d(TAG, "liked: liked");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();

        Set<String> savedNames = preferences.getStringSet(Preferences.SAVED_CONNECTIONS, new HashSet<String>());
        savedNames.add(mUser.getName());
        editor.putStringSet(Preferences.SAVED_CONNECTIONS, savedNames);
        editor.commit();

    }

    @Override
    public void unLiked(LikeButton likeButton) {
        Log.d(TAG, "unLiked: unliked.");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();

        Set<String> savedNames = preferences.getStringSet(Preferences.SAVED_CONNECTIONS, new HashSet<String>());
        savedNames.remove(mUser.getName());
        editor.remove(Preferences.SAVED_CONNECTIONS);
        editor.commit();
        editor.putStringSet(Preferences.SAVED_CONNECTIONS, savedNames);
        editor.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called.");
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.back_arrow){
            Log.d(TAG, "onClick: navigating back.");
            mInterface.onBackPressed();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mInterface = (IMainActivity) getActivity();
    }
}
