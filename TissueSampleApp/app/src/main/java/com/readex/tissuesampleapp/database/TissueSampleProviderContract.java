package com.readex.tissuesampleapp.database;

import android.net.Uri;

/**
 * Content provider contract, specifying the URI for all tables and their fields
 */
public class TissueSampleProviderContract {

    public static final String AUTHORITY = "com.readex.tissuesampleapp.database.TissueSampleProvider";

    public static final Uri COLLECTIONS_URI = Uri.parse("content://" + AUTHORITY + "/collections");
    public static final Uri SAMPLES_URI = Uri.parse("content://" + AUTHORITY + "/samples");
    public static final Uri ALL_URI = Uri.parse("content://" + AUTHORITY + "/");

    public static final String _ID = "_id";
    public static final String DISEASE_TERM = "disease_term";
    public static final String TITLE = "title";

    public static final String COLLECTION_ID = "collection_id";
    public static final String DONOR_COUNT = "donor_count";
    public static final String MATERIAL_TYPE = "material_type";
    public static final String LAST_UPDATED = "last_updated";
}
