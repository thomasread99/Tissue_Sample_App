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

    private DatabaseAdapter adapter;

    private EditText edtTitle, edtDiseaseTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_collection);

        adapter = new DatabaseAdapter(this);
        adapter.open();

        edtTitle = findViewById(R.id.edtTitle);
        edtDiseaseTerm = findViewById(R.id.edtDiseaseTerm);
    }

    public void saveCollection(View view) {
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
            adapter.addCollection(edtTitle.getText().toString(), edtDiseaseTerm.getText().toString());

            Intent intent = new Intent(AddCollectionActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(0, 0);
        }
    }

    public void goBack(View view) {
        Intent intent = new Intent(AddCollectionActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddCollectionActivity.this, MainActivity.class);
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