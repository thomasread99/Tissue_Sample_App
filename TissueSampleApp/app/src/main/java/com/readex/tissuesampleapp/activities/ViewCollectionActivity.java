package com.readex.tissuesampleapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.readex.tissuesampleapp.MainActivity;
import com.readex.tissuesampleapp.adapters.DatabaseAdapter;
import com.readex.tissuesampleapp.R;
import com.readex.tissuesampleapp.models.Sample;
import com.readex.tissuesampleapp.adapters.SampleAdapter;

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

    public void newSample(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        Intent intent = new Intent(ViewCollectionActivity.this, AddSampleActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    public void deleteCollection(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Collection")
                .setMessage("Are you sure you want to delete this collection?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.deleteCollection(id);
                        Intent intent = new Intent(ViewCollectionActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void goBack(View view) {
        Intent intent = new Intent(ViewCollectionActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ViewCollectionActivity.this, MainActivity.class);
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