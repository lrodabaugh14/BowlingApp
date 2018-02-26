package com.example.lrodabaugh14.bowlingapp;

import android.graphics.Color;
import android.graphics.Picture;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameViewer extends AppCompatActivity {
    Database db = new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_viewer);
        getGames();
    }
    public void populateGames(){
        // Loop for ten iterations.

        List<Object> Games = (List<Object>) AppUtil.lstTourneyGameScores.get(0);


        LinearLayout ll = (LinearLayout) findViewById(R.id.llGames);
        ll.removeAllViews();

        // Create a LinearLayout element
        for(int i = 0; i< Games.size(); i++) {

            Button b = new Button(getBaseContext());
            b.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT,0f));
            b.setText("Edit Game");

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onButtonClick();
                }
            });

            LinearLayout w = new LinearLayout(getBaseContext());
            w.setOrientation(LinearLayout.HORIZONTAL);
            w.setPadding(16,16,16,16);
            // Add text
            TextView tv = new TextView(getBaseContext());
            tv.setTextSize(24);
            tv.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
//            tv.layout(Wider);

            b.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f));



            // DO SOME FORMATTING stuff
            tv.setText(Games.get(i).toString());

//            tv.setId(Integer.getInteger(key));
            w.addView(tv);
            w.addView(b);
            // Add the LinearLayout element to the ScrollView
            ll.addView(w);
        }
    }

    public void onButtonClick(){


    }
    public void getGames() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = firebaseDatabase.getReference();
        DatabaseReference gameScores = dbRef.child(AppUtil.strUsername)
                .child("Tournaments").child(AppUtil.strTournament).child("GameScores");
        getGameInfo(gameScores);


    }


    public void getGameInfo(DatabaseReference dbRef) {
        final List<Object> info = new ArrayList();
        dbRef.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        info.add(dataSnapshot.getValue());
                        AppUtil.lstTourneyGameScores = info;
                        populateGames();

                        return;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError){

                    }
                }

        );
//        return info;
    }
}
