package com.example.bakingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class StepsAdapter extends ArrayAdapter<Step> {
    public StepsAdapter(@NonNull Context context, ArrayList<Step> resource) {
        super(context, 0, resource);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View stepView = convertView;
        if(stepView == null){
            stepView = LayoutInflater.from(getContext()).inflate(R.layout.steps_list_item, parent, false);
        }
        Step step = getItem(position);
        TextView stepTextView = stepView.findViewById(R.id.step);
        stepTextView.setText(step.getShortDescription());

        return stepView;
    }
}
