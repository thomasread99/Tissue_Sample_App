package com.readex.tissuesampleapp;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseAdapter {

    private DatabaseHelper databaseHelper;
    public SQLiteDatabase db;
    private Context context;

    public DatabaseAdapter(){}

    public DatabaseAdapter(Context context) {
        this.context = context;
    }

    public DatabaseAdapter open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }

    public Cursor getAllCollections() {
        String projection[] = {"_id", "disease_term", "title"};
        Cursor cursor = context.getContentResolver().query(TissueSampleProviderContract.COLLECTIONS_URI, projection, null, null, null);

        return cursor;
    }

    public Cursor getCollection(String id) {
        String projection[] = {"_id", "disease_term", "title"};
        Cursor cursor = context.getContentResolver().query(TissueSampleProviderContract.COLLECTIONS_URI, projection, "_id = ?", new String[] {id}, null);
        return cursor;
    }

    public Cursor getCollectionSamples(String id) {
        String[] collectionId = { id };
        Cursor cursor = db.rawQuery("SELECT c._id as collection_id, s.material_type, s.donor_count, s.last_updated " +
                "FROM collections c " +
                "JOIN samples s on (c._id = s.collection_id) " +
                "WHERE c._id == ?", collectionId);

        return cursor;
    }
}
