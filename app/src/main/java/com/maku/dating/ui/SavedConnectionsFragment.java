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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.maku.dating.R;
import com.maku.dating.ui.adapters.MainRcyclerviewAdapter;
import com.maku.dating.ui.models.User;
import com.maku.dating.ui.util.Preferences;
import com.maku.dating.ui.util.Users;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SavedConnectionsFragment extends Fragment {

    private static final String TAG = "SavedConnFragment";

    //constants
    private static final int NUM_GRID_COLUMNS = 2;

    //widgets

    private MainRcyclerviewAdapter mRecyclerViewAdapter;
    private RecyclerView mRecyclerView;

    //vars
    private ArrayList<User> mUsers = new ArrayList<>();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved_connections, container, false);
        Log.d(TAG, "onCreateView: started.");

        mRecyclerView = view.findViewById(R.id.recycler_view);

        getConnections();

        return view;
    }

    private void getConnections() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> savedNames = preferences.getStringSet(Preferences.SAVED_CONNECTIONS, new HashSet<String>());

        Users users = new Users();
        if(mUsers != null){
            mUsers.clear();
        }
        for(User user: users.USERS){
            if(savedNames.contains(user.getName())){
                mUsers.add(user);
            }
        }
        if(mRecyclerViewAdapter == null){
            initRecyclerView();
        }
    }

    private void initRecyclerView() {

        Log.d(TAG, "initRecyclerView: init recyclerview.");
        mRecyclerViewAdapter = new MainRcyclerviewAdapter(mUsers, getActivity());
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_GRID_COLUMNS, LinearLayoutManager.VERTICAL);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }


}
