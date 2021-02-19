package com.readex.tissuesampleapp.activities;

import androidx.appcompat.app.AppCompatActivity;

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
        adapter.addSample(id, edtDonorCount.getText().toString(), edtMaterialType.getText().toString(), Calendar.getInstance().getTime().toString());

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        Intent intent = new Intent(AddSampleActivity.this, ViewCollectionActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}