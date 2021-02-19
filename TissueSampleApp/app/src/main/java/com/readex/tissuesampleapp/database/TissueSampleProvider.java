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

    private DatabaseAdapter adapter = null;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(TissueSampleProviderContract.AUTHORITY, "collections", 1);
        uriMatcher.addURI(TissueSampleProviderContract.AUTHORITY, "collections/#", 2);
        uriMatcher.addURI(TissueSampleProviderContract.AUTHORITY, "samples", 3);
        uriMatcher.addURI(TissueSampleProviderContract.AUTHORITY, "samples/#", 4);
        uriMatcher.addURI(TissueSampleProviderContract.AUTHORITY, "*", 7);
    }

    @Override
    public boolean onCreate() {
        this.adapter = new DatabaseAdapter(this.getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        DatabaseAdapter adapter1 = new DatabaseAdapter(getContext());
        adapter1.open();

        switch (uriMatcher.match(uri)) {
            case 1:
                return adapter1.db.query("collections", projection, selection, selectionArgs, null, null, sortOrder);
            case 2:
            case 4:
                selection = "_id = " + uri.getLastPathSegment();
            case 3:
                return adapter1.db.query("samples", projection, selection, selectionArgs, null, null, sortOrder);
            default:
                return null;
        }
    }

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

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        DatabaseAdapter adapter1 = new DatabaseAdapter(getContext());
        adapter1.open();

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

        long id = adapter1.db.insert(table, null, values);
        adapter1.db.close();

        Uri newUri = ContentUris.withAppendedId(uri, id);
        getContext().getContentResolver().notifyChange(newUri, null);

        return newUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        DatabaseAdapter adapter1 = new DatabaseAdapter(getContext());
        adapter1.open();

        switch (uriMatcher.match(uri)) {
            case 1:
                return adapter1.db.delete("collections", selection, selectionArgs);
            case 3:
                return adapter1.db.delete("samples", selection, selectionArgs);
            default:
                return 0;
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
