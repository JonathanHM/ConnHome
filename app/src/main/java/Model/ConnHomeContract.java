package Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Jonathan on 23-05-2017.
 */

public final class ConnHomeContract {
    // To prevent someone from acidentally instantiating the contract class.
    private ConnHomeContract() {}

    public static class DeviceEntry implements BaseColumns {
        public static final String TABLE_NAME = "device";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_TYPE_ID = "type_id";
        public static final String COLUMN_NAME_PIN_NUMBER = "pin_number";
    }

    public static class ClientDeviceEntry implements BaseColumns {
        public static final String TABLE_NAME = "clientdevicebinding";
        public static final String COLUMN_NAME_CLIENT_ID = "client_id";
        public static final String COLUMN_NAME_DEVICE_ID = "device_id";
        public static final String COLUMN_NAME_PINNUMBER = "pinnumber";
    }

    public static class TypeEntry implements BaseColumns {
        public static final String TABLE_NAME = "type";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_ICON_URL = "icon_url";
    }

    public static class ClientEntry implements BaseColumns {
        public static final String TABLE_NAME = "client";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_IP_ADDRESS = "ipaddress";

    }

    private static final String SQL_CREATE_DEVICE_ENTRIES =
            "CREATE TABLE " + DeviceEntry.TABLE_NAME + " (" +
                    DeviceEntry._ID + " INTEGER PRIMARY KEY," +
                    DeviceEntry.COLUMN_NAME_TITLE + " TEXT," +
                    DeviceEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    DeviceEntry.COLUMN_NAME_PIN_NUMBER + " INTEGER," +
                    DeviceEntry.COLUMN_NAME_TYPE_ID + " INTEGER)";


    private static final String SQL_CREATE_TYPE_ENTRIES =
            "CREATE TABLE " + TypeEntry.TABLE_NAME + " (" +
                    TypeEntry._ID + " INTEGER PRIMARY KEY," +
                    TypeEntry.COLUMN_NAME_NAME + " TEXT," +
                    TypeEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    TypeEntry.COLUMN_NAME_ICON_URL + " TEXT)";

    private static final String SQL_DELETE_DEVICE_ENTRIES =
            "DROP TABLE IF EXISTS " + DeviceEntry.TABLE_NAME;

    private static final String SQL_DELETE_TYPE_ENTRIES =
            "DROP TABLE IF EXISTS " + TypeEntry.TABLE_NAME;

    public class ConnHomeDbHelper extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "ConnHome.db";

        public ConnHomeDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_DEVICE_ENTRIES);
            db.execSQL(SQL_CREATE_TYPE_ENTRIES);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_DEVICE_ENTRIES);
            db.execSQL(SQL_DELETE_TYPE_ENTRIES);
            onCreate(db);
        }
    }
}
