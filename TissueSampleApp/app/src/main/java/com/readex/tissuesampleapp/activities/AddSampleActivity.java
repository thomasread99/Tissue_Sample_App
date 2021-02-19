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

import java.util.Calendar;

public class AddSampleActivity extends AppCompatActivity {

    private DatabaseAdapter adapter;

    private EditText edtDonorCount, edtMaterialType;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sample);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");

        adapter = new DatabaseAdapter(this);
        adapter.open();

        edtDonorCount = findViewById(R.id.edtDonorCount);
        edtMaterialType = findViewById(R.id.edtMaterialType);
    }

    public void saveSample(View view) {
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
            adapter.addSample(id, edtDonorCount.getText().toString(), edtMaterialType.getText().toString(), Calendar.getInstance().getTime().toString());

            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            Intent intent = new Intent(AddSampleActivity.this, ViewCollectionActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
            overridePendingTransition(0, 0);
        }
    }

    public void goBack(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        Intent intent = new Intent(AddSampleActivity.this, ViewCollectionActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        Intent intent = new Intent(AddSampleActivity.this, ViewCollectionActivity.class);
        intent.putExtras(bundle);
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