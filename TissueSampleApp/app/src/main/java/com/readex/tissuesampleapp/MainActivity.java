package com.readex.tissuesampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.readex.tissuesampleapp.activities.AddCollectionActivity;
import com.readex.tissuesampleapp.activities.ViewCollectionActivity;
import com.readex.tissuesampleapp.adapters.CollectionAdapter;
import com.readex.tissuesampleapp.adapters.DatabaseAdapter;
import com.readex.tissuesampleapp.models.Collection;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Adapters
    private DatabaseAdapter adapter;

    //UI elements
    private ListView lstCollections;

    //Variables
    private ArrayList<Integer> ids = new ArrayList<>();
    private ArrayList<Collection> displayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instantiate the database adapter
        adapter = new DatabaseAdapter(this);
        adapter.open();

        //Instantiate the UI elements
        lstCollections = findViewById(R.id.lstCollections);

        populateListView();
    }

    /**
     * Populate the list view with all the collections
     */
    public void populateListView() {
        //Clear the initial list view
        lstCollections.setAdapter(null);
        ids.clear();
        displayList.clear();

        //Get all the collections
        Cursor cursor = adapter.getAllCollections();
        if (cursor.moveToFirst()) {
            do {
                //Add each ID to a list of IDs
                ids.add(cursor.getInt(0));
                //Add each collection to a list of collections
                displayList.add(new Collection(cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }

        //Initiate a new collection adapter
        CollectionAdapter adapter = new CollectionAdapter(this, displayList);
        //Set the adapter of the list view
        lstCollections.setAdapter(adapter);

        //Set an on item click listener to each value in the list view
        lstCollections.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewCollection(Integer.toString(ids.get(position)));
            }
        });
    }

    /**
     * Launch the view collection activity for the collection pressed
     * @param id
     */
    public void viewCollection(String id) {
        //Put the collection ID in a bundle
        Bundle bundle = new Bundle();
        bundle.putString("id", id);

        //Launch the view collection activity with the collection ID
        Intent intent = new Intent(MainActivity.this, ViewCollectionActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    /**
     * Launch the add collection activity
     * @param view
     */
    public void newCollection(View view) {
        //Launch the add collection activity
        Intent intent = new Intent(MainActivity.this, AddCollectionActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    /**
     * Close the adapter when the activity is destroyed
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.close();
    }
}