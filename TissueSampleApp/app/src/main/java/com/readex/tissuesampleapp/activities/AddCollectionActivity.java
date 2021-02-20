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

public class AddCollectionActivity extends AppCompatActivity {

    //Adapters
    private DatabaseAdapter adapter;

    //UI elements
    private EditText edtTitle, edtDiseaseTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_collection);

        //Instantiate the database adapter
        adapter = new DatabaseAdapter(this);
        adapter.open();

        //Instantiate the UI elements
        edtTitle = findViewById(R.id.edtTitle);
        edtDiseaseTerm = findViewById(R.id.edtDiseaseTerm);
    }

    /**
     * Save the new collection to the database on button press
     * @param view
     */
    public void saveCollection(View view) {
        //Check that all the fields have been filled in
        if (edtTitle.getText().toString().matches("") || edtDiseaseTerm.getText().toString().matches("")) {
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
            //Save the new collection to the database
            adapter.addCollection(edtTitle.getText().toString(), edtDiseaseTerm.getText().toString());

            //Return to the main activity
            Intent intent = new Intent(AddCollectionActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(0, 0);
        }
    }

    /**
     * Return to the main activity on button press
     * @param view
     */
    public void goBack(View view) {
        //Return to the main activity
        Intent intent = new Intent(AddCollectionActivity.this, MainActivity.class);
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
        Intent intent = new Intent(AddCollectionActivity.this, MainActivity.class);
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