package com.flame.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by AVAZUHOLDING on 2016/12/12.
 */

public final class GirlData {

    public static final String AUTHORITY = "com.flame.provider.girl";

    private GirlData(){

    }

    public static final class GirlInfo implements BaseColumns {

        public static final String TABLE_NAME = "girls";
        private static final String PATH_GIRLS = "/girls";
        private static final String PATH_GIRLS_ID = "/notes/";

        public static final String COLUMN_COVER_URL = "coverurl";

        public static final String COLUMN_DETAIL_URL = "detailurl";

        private static final String SCHEME = "content://";

        public static final Uri CONTENT_URI =  Uri.parse(SCHEME + AUTHORITY + PATH_GIRLS);

        public static final String DEFAULT_SORT_ORDER = "created DESC";

        /**
         * The content URI base for a single note. Callers must
         * append a numeric note id to this Uri to retrieve a note
         */
        public static final Uri CONTENT_ID_URI_BASE
                = Uri.parse(SCHEME + AUTHORITY + PATH_GIRLS_ID);

        public static final int GIRL_ID_PATH_POSITION=1;

        /**
         * The content URI match pattern for a single note, specified by its ID. Use this to match
         * incoming URIs or to construct an Intent.
         */
        public static final Uri CONTENT_ID_URI_PATTERN
                = Uri.parse(SCHEME + AUTHORITY + PATH_GIRLS_ID + "/#");

        /**
         * Column name of the note content
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_DES = "des";

        /**
         * Column name for the creation timestamp
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String COLUMN_CREATE_DATE = "created";


    }


}
