package com.maku.dating.ui.util;

import android.net.Uri;

import com.maku.dating.R;
import com.maku.dating.ui.models.User;

public class Users {
    public User[] USERS = {
            James, Elizabeth, Robert, Carol, Jennifer, Susan, Michael, William, Karen, Joseph, Nancy,
            Charles, Matthew, Sarah, Jessica, Donald, Mary, Paul,  Patricia,   Linda, Steve
    };


    /*
            Men
     */
    public static final User James = new User(Uri.parse("android.resource://com.maku.dating/" + R.drawable.james).toString(),
            "James", "Male","Female", "Looking");

    public static final User Robert = new User(Uri.parse("android.resource://com.maku.dating/" + R.drawable.robert).toString(),
            "Robert", "Male","Female", "Looking");

    public static final User Michael = new User(Uri.parse("android.resource://com.maku.dating/" + R.drawable.michael).toString(),
            "Michael", "Male","Female", "Looking");

    public static final User William = new User(Uri.parse("android.resource://com.maku.dating/" + R.drawable.william).toString(),
            "William", "Male","Female", "Not Looking");

    public static final User Joseph = new User(Uri.parse("android.resource://com.maku.dating/" + R.drawable.joseph).toString(),
            "Joseph", "Male","Female", "Looking");

    public static final User Charles = new User(Uri.parse("android.resource://com.maku.dating/" + R.drawable.charles).toString(),
            "Charles", "Male","Female", "Looking");

    public static final User Matthew = new User(Uri.parse("android.resource://com.maku.dating/" + R.drawable.mattew).toString(),
            "Matthew", "Male","Female", "Looking");

    public static final User Donald = new User(Uri.parse("android.resource://com.maku.dating/" + R.drawable.donald).toString(),
            "Donald", "Male","Female", "Looking");

    public static final User Paul = new User(Uri.parse("android.resource://com.maku.dating/" + R.drawable.paul).toString(),
            "Paul", "Male","Female", "Looking");

    public static final User Steve = new User(Uri.parse("android.resource://com.maku.dating/" + R.drawable.steve).toString(),
            "Steve", "Male","Female", "Looking");


    /*
            Females
     */
    public static final User Mary = new User(Uri.parse("android.resource://com.maku.dating/" + R.drawable.mary).toString(),
            "Mary", "Female","Male", "Looking");

    public static final User Patricia = new User(Uri.parse("android.resource://com.maku.dating/" + R.drawable.patricia).toString(),
            "Patricia", "Female","Male", "Looking");

    public static final User Jennifer = new User(Uri.parse("android.resource://com.maku.dating/" + R.drawable.jennifer).toString(),
            "Jennifer", "Female","Male", "Looking");

    public static final User Elizabeth = new User(Uri.parse("android.resource://com.maku.dating/" + R.drawable.elizabeth).toString(),
            "Elizabeth", "Female","Male", "Looking");

    public static final User Linda = new User(Uri.parse("android.resource://com.maku.dating/" + R.drawable.linda).toString(),
            "Linda", "Female","Male", "Looking");

    public static final User Susan = new User(Uri.parse("android.resource://com.maku.dating/" + R.drawable.susan).toString(),
            "Susan", "Female","Male", "Looking");

    public static final User Jessica = new User(Uri.parse("android.resource://com.maku.dating/" + R.drawable.jessica).toString(),
            "Jessica", "Female","Male", "Looking");

    public static final User Sarah = new User(Uri.parse("android.resource://com.maku.dating/" + R.drawable.sarah).toString(),
            "Sarah", "Female","Male", "Looking");

    public static final User Karen = new User(Uri.parse("android.resource://com.maku.dating/" + R.drawable.karen).toString(),
            "Karen", "Female","Male", "Looking");

    public static final User Nancy = new User(Uri.parse("android.resource://com.maku.dating/" + R.drawable.nancy).toString(),
            "Nancy", "Female","Male", "Looking");

    public static final User Carol = new User(Uri.parse("android.resource://com.maku.dating/" + R.drawable.carol).toString(),
            "Carol", "Do Not Identify","Anyone", "Looking");
}


