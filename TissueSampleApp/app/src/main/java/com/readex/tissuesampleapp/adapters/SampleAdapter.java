package com.readex.tissuesampleapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.readex.tissuesampleapp.R;
import com.readex.tissuesampleapp.models.Sample;

import java.util.ArrayList;


public class SampleAdapter extends ArrayAdapter<Sample> {

    public SampleAdapter(Context context, ArrayList<Sample> samples) {
        super(context, 0, samples);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Sample sample = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sample_list_item, parent, false);
        }

        TextView txtMaterialType = convertView.findViewById(R.id.txtMaterialType);
        TextView txtDonorCount = convertView.findViewById(R.id.txtDonorCount);
        TextView txtLastUpdated = convertView.findViewById(R.id.txtLastUpdated);

        txtMaterialType.setText(sample.getMaterialType());
        txtDonorCount.setText("Donor Count: " + sample.getDonorCount());
        txtLastUpdated.setText(sample.getLastUpdated());

        return convertView;
    }
}
