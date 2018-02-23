package com.example.lrodabaugh14.bowlingapp;

import android.os.Looper;
import android.text.format.DateFormat;
import android.widget.ArrayAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.os.Handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Database {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = firebaseDatabase.getReference();

    Database() {

    }
    public boolean LoginAttempt(String username, String password) throws InterruptedException {

        DatabaseReference user = dbRef.child(username);
        List<Object> userInfo = getDbInfo(user);

        boolean matching = false;
        Thread.sleep(1000);
        if (!userInfo.isEmpty()) {
            HashMap<String, Object> hashMap = (HashMap<String, Object>) userInfo.get(0);
            String strPassword = (String) hashMap.get("Password");
            matching = strPassword.equals(password);
        }

        return matching;
    }
    public List<Object> getDbInfo(DatabaseReference dbRef) {
        final List<Object> info = new ArrayList();
        dbRef.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        info.add(dataSnapshot.getValue());
                        return;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError){

                    }
                }

        );
        return info;
    }
}