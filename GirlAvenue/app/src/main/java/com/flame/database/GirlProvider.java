package com.flame.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AVAZUHOLDING on 2016/12/12.
 */

public class GirlProvider extends ContentProvider {

    private static final String TAG= "GirlProvider";
    private static final String DATABASE_NAME = "girl.db";
    private DatabaseHelper mOpenHelper;
    private static final UriMatcher sUriMatcher;
    private static final Map<String,String> sProjectionMap;
    private static final int GIRLS = 1;
    private static final int GIRL_ID= 2;
    private static final String[] GIRL_PROJECTION = new String[] {
            GirlData.GirlInfo._ID,             // Projection position 0, the note's id
            GirlData.GirlInfo.COLUMN_DES,  // Projection position 1, the note's content
            GirlData.GirlInfo.COLUMN_COVER_URL,// Projection position 2, the note's title
            GirlData.GirlInfo.COLUMN_DETAIL_URL
    };
    private static final int READ_NOTE_NOTE_INDEX = 1;
    private static final int READ_NOTE_TITLE_INDEX = 2;

    static {
        /*
         * Creates and initializes the URI matcher
         */
        // Create a new instance
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Add a pattern that routes URIs terminated with "notes" to a NOTES operation
        sUriMatcher.addURI(GirlData.AUTHORITY, "girls", GIRLS);

        // Add a pattern that routes URIs terminated with "notes" plus an integer
        // to a note ID operation
        sUriMatcher.addURI(GirlData.AUTHORITY, "girls/#", GIRL_ID);
        /*
         * Creates and initializes a projection map that returns all columns
         */
        // Creates a new projection map instance. The map returns a column name
        // given a string. The two are usually equal.
        sProjectionMap = new HashMap<String, String>();
        // Maps the string "_ID" to the column name "_ID"
        sProjectionMap.put(GirlData.GirlInfo._ID,  GirlData.GirlInfo._ID);
        // Maps "title" to "title"
        sProjectionMap.put(GirlData.GirlInfo.COLUMN_DES, GirlData.GirlInfo.COLUMN_DES);
        // Maps "note" to "note"
        sProjectionMap.put( GirlData.GirlInfo.COLUMN_COVER_URL,  GirlData.GirlInfo.COLUMN_COVER_URL);
        sProjectionMap.put( GirlData.GirlInfo.COLUMN_DETAIL_URL,  GirlData.GirlInfo.COLUMN_DETAIL_URL);
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Constructs a new query builder and sets its table name
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(GirlData.GirlInfo.TABLE_NAME);

        /**
         * Choose the projection and adjust the "where" clause based on URI pattern-matching.
         */
        switch (sUriMatcher.match(uri)) {
            // If the incoming URI is for notes, chooses the Notes projection
            case GIRLS:
                qb.setProjectionMap(sProjectionMap);
                break;

           /* If the incoming URI is for a single note identified by its ID, chooses the
            * note ID projection, and appends "_ID = <noteID>" to the where clause, so that
            * it selects that single note
            */
            case GIRL_ID:
                qb.setProjectionMap(sProjectionMap);
                qb.appendWhere(
                        GirlData.GirlInfo._ID +    // the name of the ID column
                                "=" +
                                // the position of the note ID itself in the incoming URI
                                uri.getPathSegments().get(GirlData.GirlInfo.GIRL_ID_PATH_POSITION));
                break;

            default:
                // If the URI doesn't match any of the known patterns, throw an exception.
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        String orderBy;
        // If no sort order is specified, uses the default
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = GirlData.GirlInfo.DEFAULT_SORT_ORDER;
        } else {
            // otherwise, uses the incoming sort order
            orderBy = sortOrder;
        }

        // Opens the database object in "read" mode, since no writes need to be done.
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();

       /*
        * Performs the query. If no problems occur trying to read the database, then a Cursor
        * object is returned; otherwise, the cursor variable contains null. If no records were
        * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
        */
        Cursor c = qb.query(
                db,            // The database to query
                projection,    // The columns to return from the query
                selection,     // The columns for the where clause
                selectionArgs, // The values for the where clause
                null,          // don't group the rows
                null,          // don't filter by row groups
                orderBy        // The sort order
        );

        // Tells the Cursor what URI to watch, so it knows when its source data changes
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }


    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        if (sUriMatcher.match(uri) != GIRLS) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        ContentValues values;
        // If the incoming values map is not null, uses it for the new values.
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            // Otherwise, create a new value map
            values = new ContentValues();
        }

        Long now = Long.valueOf(System.currentTimeMillis());
        if (!values.containsKey(GirlData.GirlInfo.COLUMN_CREATE_DATE)) {
            values.put(GirlData.GirlInfo.COLUMN_CREATE_DATE, now);
        }

        // Opens the database object in "write" mode.
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        // Performs the insert and returns the ID of the new note.
        long rowId = db.insert(
                GirlData.GirlInfo.TABLE_NAME,        // The table to insert into.
                GirlData.GirlInfo.COLUMN_CREATE_DATE,  // A hack, SQLite sets this column value to null
                // if values is empty.
                values                           // A map of column names, and the values to insert
                // into the columns.
        );

        // If the insert succeeded, the row ID exists.
        if (rowId > 0) {
            // Creates a URI with the note ID pattern and the new row ID appended to it.
            Uri noteUri = ContentUris.withAppendedId(GirlData.GirlInfo.CONTENT_ID_URI_BASE, rowId);

            // Notifies observers registered against this provider that the data changed.
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }
        // If the insert didn't succeed, then the rowID is <= 0. Throws an exception.
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Opens the database object in "write" mode.
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        String finalWhere;

        int count;

        // Does the delete based on the incoming URI pattern.
        switch (sUriMatcher.match(uri)) {

            // If the incoming pattern matches the general pattern for notes, does a delete
            // based on the incoming "where" columns and arguments.
            case GIRLS:
                count = db.delete(
                        GirlData.GirlInfo.TABLE_NAME,  // The database table name
                        selection,                     // The incoming where clause column names
                        selectionArgs                  // The incoming where clause values
                );
                break;

            // If the incoming URI matches a single note ID, does the delete based on the
            // incoming data, but modifies the where clause to restrict it to the
            // particular note ID.
            case GIRL_ID:
                /*
                 * Starts a final WHERE clause by restricting it to the
                 * desired note ID.
                 */
                finalWhere =
                        GirlData.GirlInfo._ID +                              // The ID column name
                                " = " +                                          // test for equality
                                uri.getPathSegments().                           // the incoming note ID
                                        get(GirlData.GirlInfo.GIRL_ID_PATH_POSITION)
                ;

                // If there were additional selection criteria, append them to the final
                // WHERE clause
                if (selection != null) {
                    finalWhere = finalWhere + " AND " + selection;
                }

                // Performs the delete.
                count = db.delete(
                        GirlData.GirlInfo.TABLE_NAME,  // The database table name.
                        finalWhere,                // The final WHERE clause
                        selectionArgs                  // The incoming where clause values.
                );
                break;

            // If the incoming pattern is invalid, throws an exception.
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        /*Gets a handle to the content resolver object for the current context, and notifies it
         * that the incoming URI changed. The object passes this along to the resolver framework,
         * and observers that have registered themselves for the provider are notified.
         */
        getContext().getContentResolver().notifyChange(uri, null);

        // Returns the number of rows deleted.
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context){
            super(context,DATABASE_NAME,null,2);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + GirlData.GirlInfo.TABLE_NAME + " ("
                    + GirlData.GirlInfo._ID + " INTEGER PRIMARY KEY,"
                    + GirlData.GirlInfo.COLUMN_DES + " TEXT,"
                    + GirlData.GirlInfo.COLUMN_COVER_URL + " TEXT,"
                    + GirlData.GirlInfo.COLUMN_DETAIL_URL + " TEXT,"
                    + GirlData.GirlInfo.COLUMN_CREATE_DATE + " INTEGER"
                    + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Logs that the database is being upgraded
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");

            // Kills the table and existing data
            db.execSQL("DROP TABLE IF EXISTS notes");

            // Recreates the database with a new version
            onCreate(db);

        }
    }


}
