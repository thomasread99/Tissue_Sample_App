package com.readex.tissuesampleapp.activities;

import androidx.appcompat.app.AppCompatActivity;

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
        adapter.addCollection(edtTitle.getText().toString(), edtDiseaseTerm.getText().toString());

        Intent intent = new Intent(AddCollectionActivity.this, MainActivity.class);
        startActivity(intent);
    }
}