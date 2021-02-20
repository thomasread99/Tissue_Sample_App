package com.readex.tissuesampleapp.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.readex.tissuesampleapp.adapters.DatabaseAdapter;

public class TissueSampleProvider extends ContentProvider {

    //Database declarations
    private DatabaseAdapter adapter = null;
    private static final UriMatcher uriMatcher;

    //Instantiate the URI Matcher using the content provider contract
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(TissueSampleProviderContract.AUTHORITY, "collections", 1);
        uriMatcher.addURI(TissueSampleProviderContract.AUTHORITY, "collections/#", 2);
        uriMatcher.addURI(TissueSampleProviderContract.AUTHORITY, "samples", 3);
        uriMatcher.addURI(TissueSampleProviderContract.AUTHORITY, "samples/#", 4);
        uriMatcher.addURI(TissueSampleProviderContract.AUTHORITY, "*", 7);
    }

    /**
     * Instantiate a database adapter object on create
     * @return
     */
    @Override
    public boolean onCreate() {
        this.adapter = new DatabaseAdapter(this.getContext());
        return true;
    }

    /**
     * Query the database depending on the case specified
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        //Instantiate a database adapter object
        DatabaseAdapter adapter1 = new DatabaseAdapter(getContext());
        adapter1.open();

        switch (uriMatcher.match(uri)) {
            case 1:
                //Query the collections table
                return adapter1.db.query("collections", projection, selection, selectionArgs, null, null, sortOrder);
            case 2:
            case 4:
                selection = "_id = " + uri.getLastPathSegment();
            case 3:
                //Query the samples table
                return adapter1.db.query("samples", projection, selection, selectionArgs, null, null, sortOrder);
            default:
                return null;
        }
    }

    /**
     * Get the type of the content being returned
     * @param uri
     * @return
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        String contentType;

        if (uri.getLastPathSegment() == null) {
            contentType = "vnd.android.cursor.dir/TissueSampleProvider.data.text";
        }
        else {
            contentType = "vnd.android.cursor.item/TissueSampleProvider.data.text";
        }
        return contentType;
    }

    /**
     * Insert a new entry depending on the case specified
     * @param uri
     * @param values
     * @return
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        //Instantiate a database adapter object
        DatabaseAdapter adapter1 = new DatabaseAdapter(getContext());
        adapter1.open();

        //Select the table being inserted into
        String table;
        switch (uriMatcher.match(uri)) {
            case 1:
                table = "collections";
                break;
            case 3:
                table = "samples";
                break;
            default:
                return null;
        }

        //Insert the new values into the specified table
        long id = adapter1.db.insert(table, null, values);
        adapter1.db.close();

        //Notify the content resolver of changes to the database
        Uri newUri = ContentUris.withAppendedId(uri, id);
        getContext().getContentResolver().notifyChange(newUri, null);

        return newUri;
    }

    /**
     * Delete from the database depending on the case specified
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        //Instantiate a database adapter object
        DatabaseAdapter adapter1 = new DatabaseAdapter(getContext());
        adapter1.open();

        switch (uriMatcher.match(uri)) {
            case 1:
                //Delete from the collections table
                return adapter1.db.delete("collections", selection, selectionArgs);
            case 3:
                //Delete from the samples table
                return adapter1.db.delete("samples", selection, selectionArgs);
            default:
                return 0;
        }
    }

    /**
     * Update the database depending on the case specified (NOT IMPLEMENTED)
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Not Implemented");
    }
}
