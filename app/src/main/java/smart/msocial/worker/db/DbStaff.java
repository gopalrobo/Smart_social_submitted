package smart.msocial.worker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DbStaff extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "staffManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "staff";

    // Contacts Table Columns names
    private static final String KEY_NAME = "name";
    private static final String KEY_DATA = "data";
    private static final String KEY_PASSWORD = "password";

    public DbStaff(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_NAME + " TEXT,"
                + KEY_DATA + " TEXT,"
                + KEY_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new shgName
    public void addData(String name, String data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // ShgName Name
        values.put(KEY_DATA, data); // ShgName Phone
        values.put(KEY_PASSWORD, ""); // ShgName Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public ArrayList<String> getDataByname(String name) {

        SQLiteDatabase db = this.getReadableDatabase();

        final Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_NAME,
                        KEY_DATA,KEY_PASSWORD}, KEY_NAME+ "=?",
                new String[]{name}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return new ArrayList<String>() {{
                add(cursor.getString(0));
                add(cursor.getString(1));
                if(cursor.getString(2)!=null){
                    add(cursor.getString(2));
                }
            }};
        }
        return null;
    }


    public List<ArrayList<String>> getAllData() {
        List<ArrayList<String>> dataList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        final Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ArrayList<String> list = new ArrayList<>();
                list.add(cursor.getString(0));
                list.add(cursor.getString(1));
                if (cursor.getString(2) != null) {
                    list.add(cursor.getString(2));
                }
                dataList.add(list);
            } while (cursor.moveToNext());
        }
        return dataList;
    }

    // Updating single shgName
    public int updatedataByname(String name, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // ShgName Name
        values.put(KEY_DATA, data); // ShgName Phone
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_NAME + " = ?",
                new String[]{name});
    }

    public int updatePassByname(String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD, password); // ShgName Phone
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_NAME + " = ?",
                new String[]{name});
    }

    // Deleting single shgName
    public void deletename(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_NAME + " = ?",
                new String[]{name});
        db.close();
    }

    // Deleting single shgName
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, null,
                null);
        db.close();
    }

    public int getCountByname(String name) {
        int result;
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS + " WHERE " + KEY_NAME + "='" + name + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        result = cursor.getCount();
        cursor.close();
        // return count
        return result;
    }

}
