package com.readex.tissuesampleapp.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.readex.tissuesampleapp.utils.DatabaseHelper;
import com.readex.tissuesampleapp.database.TissueSampleProviderContract;

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

    public void addCollection(String title, String diseaseTerm) {
        ContentValues values = new ContentValues();
        values.put(TissueSampleProviderContract.TITLE, title);
        values.put(TissueSampleProviderContract.DISEASE_TERM, diseaseTerm);

        context.getContentResolver().insert(TissueSampleProviderContract.COLLECTIONS_URI, values);
    }

    public void addSample(String collectionID, String donorCount, String materialType, String lastUpdated) {
        ContentValues values = new ContentValues();
        values.put(TissueSampleProviderContract.COLLECTION_ID, collectionID);
        values.put(TissueSampleProviderContract.DONOR_COUNT, donorCount);
        values.put(TissueSampleProviderContract.MATERIAL_TYPE, materialType);
        values.put(TissueSampleProviderContract.LAST_UPDATED, lastUpdated);

        context.getContentResolver().insert(TissueSampleProviderContract.SAMPLES_URI, values);
    }

    public void deleteCollection(String id) {
        String collectionId[] = { id };
        context.getContentResolver().delete(TissueSampleProviderContract.COLLECTIONS_URI, "_id = ?", collectionId);
        context.getContentResolver().delete(TissueSampleProviderContract.SAMPLES_URI, "collection_id = ?", collectionId);
    }
}
