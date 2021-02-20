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

    //Adapters
    private DatabaseAdapter adapter;

    //UI elements
    private TextView txtTitle;
    private ListView lstSamples;

    //Variables
    private String id, title;
    private ArrayList<Sample> samples = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_collection);

        //Get the collection ID from the bundle
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");

        //Instantiate the database adapter
        adapter = new DatabaseAdapter(this);
        adapter.open();

        //Instantiate the UI elements
        txtTitle = findViewById(R.id.txtTitle);
        lstSamples = findViewById(R.id.lstSamples);

        populateActivity();
    }

    /**
     * Populate the UI elements in the activity with the information for the collection
     */
    private void populateActivity() {
        //Clear the list view
        lstSamples.setAdapter(null);
        samples.clear();

        //Get the specific collection
        Cursor cursor = adapter.getCollection(id);
        if (cursor.moveToFirst()) {
            do {
                //Set the text view to the collection title
                title = cursor.getString(2);
                txtTitle.setText(title);
            }
            while (cursor.moveToNext());
        }

        //Get the samples associated with the collection
        Cursor cursor1 = adapter.getCollectionSamples(id);
        if (cursor1.moveToFirst()) {
            do {
                //Add each sample to a list of samples
                samples.add(new Sample(cursor1.getString(1), cursor1.getInt(2), cursor1.getString(3)));
            }
            while (cursor1.moveToNext());
        }

        //Instantiate a new sample adapter
        SampleAdapter adapter = new SampleAdapter(this, samples);
        //Set the adapter of the list view
        lstSamples.setAdapter(adapter);
    }

    /**
     * Launch the add sample activity
     * @param view
     */
    public void newSample(View view) {
        //Put the collection ID in a bundle
        Bundle bundle = new Bundle();
        bundle.putString("id", id);

        //Launch the add sample activity with the collection ID
        Intent intent = new Intent(ViewCollectionActivity.this, AddSampleActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    /**
     * Delete the collection and its associated samples from the database
     * @param view
     */
    public void deleteCollection(View view) {
        //Prompt the user to confirm they want to delete
        new AlertDialog.Builder(this)
                .setTitle("Delete Collection")
                .setMessage("Are you sure you want to delete this collection?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Delete the collection and its associated samples
                        adapter.deleteCollection(id);

                        //Return to the main activity
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

    /**
     * Return to the main activity on button press
     * @param view
     */
    public void goBack(View view) {
        //Return to the main activity
        Intent intent = new Intent(ViewCollectionActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    /**
     * Return to the main activity on back button press
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Return to the main activity
        Intent intent = new Intent(ViewCollectionActivity.this, MainActivity.class);
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