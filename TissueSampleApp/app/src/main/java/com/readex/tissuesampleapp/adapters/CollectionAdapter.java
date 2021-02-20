package com.readex.tissuesampleapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.readex.tissuesampleapp.models.Collection;
import com.readex.tissuesampleapp.R;

import java.util.ArrayList;

public class CollectionAdapter extends ArrayAdapter<Collection> {

    /**
     * Constructor for the adapter
     * @param context
     * @param collections
     */
    public CollectionAdapter(Context context, ArrayList<Collection> collections) {
        super(context, 0, collections);
    }

    /**
     * Populate each element of the custom list view
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Get the collection item at specified position
        Collection collection = getItem(position);

        if (convertView == null) {
            //Get the collection list item UI element
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.collection_list_item, parent, false);
        }

        //Instantiate the UI elements in the list item
        TextView title = convertView.findViewById(R.id.txtTitle);
        TextView diseaseTerm = convertView.findViewById(R.id.txtDiseaseTerm);

        title.setText(collection.getTitle());
        diseaseTerm.setText(collection.getDiseaseTerm());

        return convertView;
    }
}
