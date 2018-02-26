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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.example.lrodabaugh14.bowlingapp.GameViewer;

public class Database {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = firebaseDatabase.getReference();
    List<Object> lstScores = new ArrayList();

    public Database() {

    }
    public boolean LoginAttempt(String username, String password) throws InterruptedException {

        DatabaseReference user = dbRef.child(username);
        List<Object> userInfo = getDbInfo(user);
        AppUtil.userInfo = userInfo;

        boolean matching = false;
        Thread.sleep(1000);
        if (!userInfo.isEmpty()) {
            AppUtil.strUsername = username;
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

    public void addNewTournament(String strTourneyName){
        DatabaseReference dbRefTournaments = dbRef.child(AppUtil.strUsername).child("Tournaments").child(strTourneyName);
        HashMap<String, Object> tourneyInfo = new HashMap();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        tourneyInfo.put("CreatedAt", dateFormat.format(date).toString());
        ArrayList<Integer> gameScores = new ArrayList(3);
        gameScores.add(0,0);
        gameScores.add(1,0);
        gameScores.add(2,0);
        tourneyInfo.put("GameScores", gameScores);
        AppUtil.strTournament = strTourneyName;
        dbRefTournaments.setValue(tourneyInfo);
    }
    
//
//    public void getGames() throws InterruptedException {
//        DatabaseReference gameScores = dbRef.child(AppUtil.strUsername)
//                .child("Tournaments").child(AppUtil.strTournament).child("Game Scores");
//        lstScores = getGameInfo(gameScores);
//
//        Thread.sleep(2000);
//        if (!lstScores.isEmpty()) {
//            AppUtil.lstTourneyGameScores = lstScores;
//        }
//
//
//    }
//
//
//    public List<Object> getGameInfo(DatabaseReference dbRef) {
//        final List<Object> info = new ArrayList();
//        dbRef.addValueEventListener(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        info.add(dataSnapshot.getValue());
//                        return;
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError){
//
//                    }
//                }
//
//        );
//        return info;
//    }
//

}