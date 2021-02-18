package com.readex.tissuesampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewCollectionActivity extends AppCompatActivity {

    private DatabaseAdapter adapter;

    private TextView txtTitle;
    private ListView lstSamples;

    private String id, title;
    private ArrayList<Sample> samples = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_collection);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");

        adapter = new DatabaseAdapter(this);
        adapter.open();

        txtTitle = findViewById(R.id.txtTitle);
        lstSamples = findViewById(R.id.lstSamples);

        populateActivity();
    }

    private void populateActivity() {
        lstSamples.setAdapter(null);
        samples.clear();

        Cursor cursor = adapter.getCollection(id);

        if (cursor.moveToFirst()) {
            do {
                title = cursor.getString(2);
                txtTitle.setText(title);
            }
            while (cursor.moveToNext());
        }

        Cursor cursor1 = adapter.getCollectionSamples(id);
        if (cursor1.moveToFirst()) {
            do {
                samples.add(new Sample(cursor1.getString(1), cursor1.getInt(2), cursor1.getString(3)));
            }
            while (cursor1.moveToNext());
        }

        SampleAdapter adapter = new SampleAdapter(this, samples);

        lstSamples.setAdapter(adapter);
    }

}