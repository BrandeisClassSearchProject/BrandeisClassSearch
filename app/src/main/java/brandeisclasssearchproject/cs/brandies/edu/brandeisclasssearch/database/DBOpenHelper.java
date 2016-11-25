package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Larry on 2016/11/24.
 *
 * stolen from my hw3.
 *
 * database table should have columns:  course_name (string)
 *                                      course_time (string)
 *
 * Still need clear entire table method.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "coursesForSchedule.db";

    // table names
    public static final String TABLE_COURSE_SELECTION = "tableForCourseSelection";

    // column names
    public static final String KEY_COURSE_NAME = "courseName";
    public static final String KEY_COURSE_TIME = "courseTime";
    public static final String KEY_ID = "_id";
    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_COURSE_SELECTION_CREATE = "CREATE TABLE "
            + TABLE_COURSE_SELECTION
            + " (" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_COURSE_NAME + " TEXT NOT NULL,"
            + KEY_COURSE_TIME + " TEXT);";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(TABLE_COURSE_SELECTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_COURSE_SELECTION);

        // create new tables
        onCreate(db);
    }

    public void addLog(String courseName, String courseTime, String table, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(this.KEY_COURSE_NAME,courseName);
        contentValues.put(this.KEY_COURSE_TIME,courseTime);
        db.insert(table,null,contentValues);
    }

    public Cursor getLog(String table, SQLiteDatabase db) {
        Cursor cursor;

        String[] projections = {this.KEY_ID, this.KEY_COURSE_NAME, this.KEY_COURSE_TIME,};
        cursor = db.query(table, projections, null, null, null, null, null);
        if (cursor != null) {

            cursor.moveToFirst();

        }
        return cursor;
    }

    public void deleteLog(long id, String table, SQLiteDatabase db) {
        db.delete(table, this.KEY_ID + "=" + id, null);
    }
}

