package com.readex.tissuesampleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CollectionAdapter extends ArrayAdapter<Collection> {

    public CollectionAdapter(Context context, ArrayList<Collection> collections) {
        super(context, 0, collections);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Collection collection = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.collection_list_item, parent, false);
        }

        TextView title = convertView.findViewById(R.id.txtTitle);
        TextView diseaseTerm = convertView.findViewById(R.id.txtDiseaseTerm);

        title.setText(collection.getTitle());
        diseaseTerm.setText(collection.getDiseaseTerm());

        return convertView;
    }
}
