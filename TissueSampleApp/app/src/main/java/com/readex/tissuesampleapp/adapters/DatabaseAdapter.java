package com.readex.tissuesampleapp.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.readex.tissuesampleapp.utils.DatabaseHelper;
import com.readex.tissuesampleapp.database.TissueSampleProviderContract;

public class DatabaseAdapter {

    //Database declarations
    private DatabaseHelper databaseHelper;
    public SQLiteDatabase db;
    private Context context;

    /**
     * Empty constructor
     */
    public DatabaseAdapter(){}

    /**
     * Constructor to assign the context
     * @param context
     */
    public DatabaseAdapter(Context context) {
        this.context = context;
    }

    /**
     * Open the connection to the database
     * @return
     * @throws SQLException
     */
    public DatabaseAdapter open() throws SQLException {
        //Get a writeable database object that can be read from and written to
        databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
        return this;
    }

    /**
     * Close the connection to the database
     */
    public void close() {
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }

    /**
     * Get all the collections in the database
     * @return
     */
    public Cursor getAllCollections() {
        //Fields to return
        String projection[] = {"_id", "disease_term", "title"};
        //Get all the collections
        Cursor cursor = context.getContentResolver().query(TissueSampleProviderContract.COLLECTIONS_URI, projection, null, null, null);

        return cursor;
    }

    /**
     * Get a specific collection from the database
     * @param id
     * @return
     */
    public Cursor getCollection(String id) {
        //Fields to return
        String projection[] = {"_id", "disease_term", "title"};
        //Get specific collection
        Cursor cursor = context.getContentResolver().query(TissueSampleProviderContract.COLLECTIONS_URI, projection, "_id = ?", new String[] {id}, null);

        return cursor;
    }

    /**
     * Get all samples associated with a specific collection
     * @param id
     * @return
     */
    public Cursor getCollectionSamples(String id) {
        //Parameter field
        String[] collectionId = { id };
        //Get all associate samples using a raw SQL query rather than content provider
        Cursor cursor = db.rawQuery("SELECT c._id as collection_id, s.material_type, s.donor_count, s.last_updated " +
                "FROM collections c " +
                "JOIN samples s on (c._id = s.collection_id) " +
                "WHERE c._id == ?", collectionId);

        return cursor;
    }

    /**
     * Save a new collection to the database
     * @param title
     * @param diseaseTerm
     */
    public void addCollection(String title, String diseaseTerm) {
        //Put the values to save in an object
        ContentValues values = new ContentValues();
        values.put(TissueSampleProviderContract.TITLE, title);
        values.put(TissueSampleProviderContract.DISEASE_TERM, diseaseTerm);

        //Save the new collection
        context.getContentResolver().insert(TissueSampleProviderContract.COLLECTIONS_URI, values);
    }

    /**
     * Add a new sample to the database, for a specific collection
     * @param collectionID
     * @param donorCount
     * @param materialType
     * @param lastUpdated
     */
    public void addSample(String collectionID, String donorCount, String materialType, String lastUpdated) {
        //Put the values to save in an object
        ContentValues values = new ContentValues();
        values.put(TissueSampleProviderContract.COLLECTION_ID, collectionID);
        values.put(TissueSampleProviderContract.DONOR_COUNT, donorCount);
        values.put(TissueSampleProviderContract.MATERIAL_TYPE, materialType);
        values.put(TissueSampleProviderContract.LAST_UPDATED, lastUpdated);

        //Save the new sample
        context.getContentResolver().insert(TissueSampleProviderContract.SAMPLES_URI, values);
    }

    /**
     * Delete a collection and its associated samples
     * @param id
     */
    public void deleteCollection(String id) {
        //Parameter field
        String collectionId[] = { id };
        //Delete the specific collection
        context.getContentResolver().delete(TissueSampleProviderContract.COLLECTIONS_URI, "_id = ?", collectionId);
        //Delete the samples with the same collection ID
        context.getContentResolver().delete(TissueSampleProviderContract.SAMPLES_URI, "collection_id = ?", collectionId);
    }
}
