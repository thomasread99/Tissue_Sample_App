package com.readex.tissuesampleapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.readex.tissuesampleapp.MainActivity;
import com.readex.tissuesampleapp.R;
import com.readex.tissuesampleapp.adapters.DatabaseAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddSampleActivity extends AppCompatActivity {

    //Adapters
    private DatabaseAdapter adapter;

    //UI elements
    private EditText edtDonorCount, edtMaterialType;

    //Variables
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sample);

        //Get the collection ID from the bundle
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");

        //Instantiate the database adapter
        adapter = new DatabaseAdapter(this);
        adapter.open();

        //Instantiate the UI elements
        edtDonorCount = findViewById(R.id.edtDonorCount);
        edtMaterialType = findViewById(R.id.edtMaterialType);
    }

    /**
     * Save the new sample to the database
     * @param view
     */
    public void saveSample(View view) {
        //Check that all the fields have been filled in
        if (edtDonorCount.getText().toString().matches("") || edtMaterialType.getText().toString().matches("")) {
            new AlertDialog.Builder(this)
                    .setTitle("Missing Fields")
                    .setMessage("Please fill in all boxes before saving")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else {
            //Get the current date and format it like yyyy-MM-dd
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = dateFormat.format(date);

            //Save the new sample to the database
            adapter.addSample(id, edtDonorCount.getText().toString(), edtMaterialType.getText().toString(), strDate);

            //Put the collection ID in a bundle
            Bundle bundle = new Bundle();
            bundle.putString("id", id);

            //Return to the view collection activity with the collection ID
            Intent intent = new Intent(AddSampleActivity.this, ViewCollectionActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
            overridePendingTransition(0, 0);
        }
    }

    /**
     * Return to the view collection activity on button press
     * @param view
     */
    public void goBack(View view) {
        //Put the collection ID in a bundle
        Bundle bundle = new Bundle();
        bundle.putString("id", id);

        //Return to the view collection activity with the collection ID
        Intent intent = new Intent(AddSampleActivity.this, ViewCollectionActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    /**
     * Return to the view collection activity on back button press
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Put the collection ID in a bundle
        Bundle bundle = new Bundle();
        bundle.putString("id", id);

        //Return to the view collection activity with the collection ID
        Intent intent = new Intent(AddSampleActivity.this, ViewCollectionActivity.class);
        intent.putExtras(bundle);
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