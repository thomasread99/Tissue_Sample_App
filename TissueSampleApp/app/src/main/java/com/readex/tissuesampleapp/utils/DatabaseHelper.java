package com.readex.tissuesampleapp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String CREATE_COLLECTIONS_TABLE = "CREATE TABLE collections (\n" +
            "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
            "disease_term VARCHAR(128) NOT NULL,\n" +
            "title VARCHAR(128) NOT NULL);";

    private static final String CREATE_SAMPLES_TABLE = "CREATE TABLE samples (\n" +
            "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
            "collection_id INT NOT NULL,\n" +
            "donor_count INT NOT NULL,\n" +
            "material_type VARCHAR(128) NOT NULL,\n" +
            "last_updated DATETIME NOT NULL,\n" +
            "CONSTRAINT fk FOREIGN KEY (collection_id) REFERENCES collections (_id));";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context) {
        super(context, "tissueSampleDB", null, 8);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COLLECTIONS_TABLE);
        db.execSQL(CREATE_SAMPLES_TABLE);

        db.execSQL("INSERT INTO collections (disease_term, title) " +
                "VALUES " +
                "('" + "Cirrhosis of liver" + "','" + "Mothers Pregnancy Samples" + "')");
        db.execSQL("INSERT INTO collections (disease_term, title) " +
                "VALUES " +
                "('" + "Malignant tumour of breast" + "','" + "Phase II multicentre study" + "')");
        db.execSQL("INSERT INTO collections (disease_term, title) " +
                "VALUES " +
                "('" + "Fit and well" + "','" + "Lymphoblastoid cell lines" + "')");
        db.execSQL("INSERT INTO collections (disease_term, title) " +
                "VALUES " +
                "('" + "Chronic fatigue syndrome" + "','" + "Samples available include ME/CFS Cases" + "')");
        db.execSQL("INSERT INTO collections (disease_term, title) " +
                "VALUES " +
                "('" + "Malignant tumour of breast" + "','" + "A randomised placebo-controlled trial" + "')");

        db.execSQL("INSERT INTO samples (collection_id, donor_count, material_type, last_updated) " +
                "VALUES " +
                "('" + "4" + "','" + "90210" + "','" + "Cerebrospinal fluid" + "','" + "2019-06-03" + "')");
        db.execSQL("INSERT INTO samples (collection_id, donor_count, material_type, last_updated) " +
                "VALUES " +
                "('" + "2" + "','" + "512" + "','" + "Cerebrospinal fluid" + "','" + "2019-03-08" + "')");
        db.execSQL("INSERT INTO samples (collection_id, donor_count, material_type, last_updated) " +
                "VALUES " +
                "('" + "2" + "','" + "7777" + "','" + "Core biopsy" + "','" + "2019-05-04" + "')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
