package com.readex.tissuesampleapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
