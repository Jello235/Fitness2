package com.example.fitness2;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class FitnessSetFragement extends AppCompatActivity {

    private static final String TAG = "FitnessSetFragement";
    FitnessChoice fitnessChoice;
    Spinner partSpinner;
    Spinner intensitySpinner;
    Button search;
    String part;
    String intensity;
    RecyclerView fitnessChoiceRecycleView;
    FirebaseFirestore fitStoreRef = FirebaseFirestore.getInstance();
    FitnessList listadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firness_set_fragment);

        partSpinner = findViewById(R.id.part_spinner);
        ArrayAdapter<CharSequence> partAdapter = ArrayAdapter.createFromResource(this, R.array.part, android.R.layout.simple_spinner_item);
        partAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partSpinner.setAdapter(partAdapter);

        intensitySpinner = findViewById(R.id.intensity_spinner);
        ArrayAdapter<CharSequence> intensityAdapter = ArrayAdapter.createFromResource(this, R.array.intensity, android.R.layout.simple_spinner_item);
        intensityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intensitySpinner.setAdapter(intensityAdapter);

        search = findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                part = partSpinner.getSelectedItem().toString();
                intensity = intensitySpinner.getSelectedItem().toString();
                search(intensity,part);
            }
        });

        setUpRecyclerView();

    }
    private void setUpRecyclerView(){
        Query query = fitStoreRef.collection("Exercise Choice").orderBy("description",Query.Direction.ASCENDING);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "no error");
                }  else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        FirestoreRecyclerOptions<FitnessChoice> options = new FirestoreRecyclerOptions
                .Builder<FitnessChoice>()
                .setQuery(query,FitnessChoice.class)
                .build();

        listadapter = new FitnessList(options);
        listadapter.updateOptions(options);

        fitnessChoiceRecycleView = findViewById(R.id.exercise_list_recycler_view);
        fitnessChoiceRecycleView.setLayoutManager(new LinearLayoutManager(this));
        fitnessChoiceRecycleView.setAdapter(listadapter);
    }

    private void search(String intensity, String part){
        Query query = fitStoreRef.collection("Exercise Choice").whereEqualTo("intensity",intensity).whereEqualTo("part",part);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "no error");
                }  else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        FirestoreRecyclerOptions<FitnessChoice> options = new FirestoreRecyclerOptions
                .Builder<FitnessChoice>()
                .setQuery(query,FitnessChoice.class)
                .build();

        listadapter.updateOptions(options);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(listadapter != null){
            listadapter.stopListening();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        listadapter.startListening();
    }
}
