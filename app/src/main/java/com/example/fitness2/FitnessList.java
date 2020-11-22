package com.example.fitness2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class FitnessList extends FirestoreRecyclerAdapter<FitnessChoice, FitnessList.FitnessViewHolder> {

    public FitnessList(@NonNull FirestoreRecyclerOptions<FitnessChoice> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FitnessViewHolder holder, int position, @NonNull FitnessChoice model) {
        holder.Description.setText(model.getDescription());
    }

    @NonNull
    @Override
    public FitnessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.list_item,
                parent, false);
        return new FitnessViewHolder(view);
    }

    class FitnessViewHolder extends RecyclerView.ViewHolder{

        TextView Description;

        FitnessViewHolder(View view){
            super(view);
            Description = view.findViewById(R.id.Description);
        }
    }

}
