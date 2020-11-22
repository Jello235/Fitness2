package com.example.fitness2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class FitnessMainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.fitness2.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fitness_main_activity);

    }

    public void newExercise(View view){
        Intent intent = new Intent(this,FitnessSetFragement.class);
        startActivity(intent);
    }
}

