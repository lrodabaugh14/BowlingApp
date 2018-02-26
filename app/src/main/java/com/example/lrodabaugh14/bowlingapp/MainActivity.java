package com.example.lrodabaugh14.bowlingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;

public class MainActivity extends AppCompatActivity {
    Database db = new Database();
    EditText txtTourney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Database db = new Database();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtTourney = findViewById(R.id.etTourney);
    }
    public void onNewTournament(View view){
        db.addNewTournament(txtTourney.getText().toString());
        startActivity(new Intent(MainActivity.this, GameViewer.class));
    }
}
