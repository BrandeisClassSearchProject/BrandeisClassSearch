package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Larry on 2016/11/24.
 *
 * stealed from my hw3, haven't modified.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "myDatabase.db1";
    public static final String DATABASE_TABLE = "entry1";
    public static final String description = "description";
    public static final String note = "note";
    public static final String date = "date";
    public static final String id = "_id";
    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "CREATE TABLE "
            + DATABASE_TABLE
            + " (" + id
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + description + " TEXT NOT NULL,"
            + note + " TEXT,"
            + date + " TEXT);";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void addLog(String des, String note, String date, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(this.description,des);
        contentValues.put(this.note,note);
        contentValues.put(this.date,date);
        db.insert(DATABASE_TABLE,null,contentValues);
    }

    public Cursor getLog(SQLiteDatabase db) {
        Cursor cursor;

        String[] projections = {this.id, this.description, this.note, this.date};
        cursor = db.query(this.DATABASE_TABLE, projections, null, null, null, null, null);
        if (cursor != null) {

            cursor.moveToFirst();

        }
        return cursor;
    }

    public void deleteLog(long id, SQLiteDatabase db) {
        db.delete(this.DATABASE_TABLE, this.id + "=" + id, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE);
        onCreate(db);
    }
}

