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

    private DatabaseAdapter adapter;

    private ListView lstCollections;

    private ArrayList<Integer> ids = new ArrayList<>();
    private ArrayList<Collection> displayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new DatabaseAdapter(this);
        adapter.open();

        lstCollections = findViewById(R.id.lstCollections);

        populateListView();
    }

    public void populateListView() {
        lstCollections.setAdapter(null);
        ids.clear();
        displayList.clear();

        Cursor cursor = adapter.getAllCollections();

        if (cursor.moveToFirst()) {
            do {
                ids.add(cursor.getInt(0));
                displayList.add(new Collection(cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }

        CollectionAdapter adapter = new CollectionAdapter(this, displayList);

        lstCollections.setAdapter(adapter);

        lstCollections.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewCollection(Integer.toString(ids.get(position)));
            }
        });
    }

    public void viewCollection(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        Intent intent = new Intent(MainActivity.this, ViewCollectionActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    public void newCollection(View view) {
        Intent intent = new Intent(MainActivity.this, AddCollectionActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.close();
    }
}