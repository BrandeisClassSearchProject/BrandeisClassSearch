package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

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

    public static final String DATABASE_NAME = "coursesForSchedule1.db";

    // table names
    public static final String TABLE_COURSE_SELECTION = "tableForCourseSelection";

    // column names
    public static final String KEY_COURSE_NAME = "courseName";
    public static final String KEY_COURSE_TIME = "courseTime";
    public static final String KEY_COURSE_SEASON = "courseSeason";
    public static final String KEY_ID = "_id";
    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_COURSE_SELECTION_CREATE = "CREATE TABLE "
            + TABLE_COURSE_SELECTION
            + " (" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_COURSE_NAME + " TEXT NOT NULL,"
            + KEY_COURSE_SEASON + " TEXT,"
            + KEY_COURSE_TIME + " TEXT);";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(DATABASE_COURSE_SELECTION_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_COURSE_SELECTION);

        // create new tables
        onCreate(db);
    }

    public void addCourse(String courseName, String courseSeason, String courseTime, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(this.KEY_COURSE_NAME,courseName);
        contentValues.put(this.KEY_COURSE_SEASON,courseSeason);
        contentValues.put(this.KEY_COURSE_TIME,courseTime);
        db.insert(TABLE_COURSE_SELECTION,null,contentValues);
    }

    public Cursor getCourse(SQLiteDatabase db) {
        Cursor cursor;

        String[] projections = {this.KEY_ID, this.KEY_COURSE_NAME, this.KEY_COURSE_SEASON, this.KEY_COURSE_TIME,};
        cursor = db.query(TABLE_COURSE_SELECTION, projections, null, null, null, null, null);
        if (cursor != null) {

            cursor.moveToFirst();

        }
        return cursor;
    }

    public void deleteCourse(long id, SQLiteDatabase db) {
        db.delete(TABLE_COURSE_SELECTION, this.KEY_ID + "=" + id, null);
    }

    public Boolean testConflict(SQLiteDatabase db){
        String timeRowTmp = "";
        ArrayList<String> timeList = new ArrayList<>();
        Cursor testCursor = getCourse(db);
        //int rowCount = testCursor.getCount();
        //Log.e("NUMBER OF ENTRIES","" + rowCount);
        while(true){
            timeRowTmp = testCursor.getString(testCursor.getColumnIndex("courseTime"));
            String[] listTmp = timeRowTmp.split("\\|");
            if (listTmp[0].indexOf("Block")==-1) {
                timeList.add(listTmp[0].trim());
            }
            for (int i = 1; i < listTmp.length; i++) {
                if (!listTmp[i].trim().equals("")) {
                    timeList.add(listTmp[i].trim());
                }
            }
            if(testCursor.isLast()){
                break;
            }
            testCursor.moveToNext();
        }

        timeRowTmp = "";
        for (String str : timeList) {
            timeRowTmp = timeRowTmp + str + "\n";
        }
        Log.e("Processed schedule:", timeRowTmp);

        // compare times in ArrayList<String>
        ArrayList<ArrayList<Period>> timeTable = new ArrayList<ArrayList<Period>>();
        for (int i=0; i<5; i++) {
            timeTable.add(new ArrayList<Period>());
        }

        for (String s : timeList) {
            String[] sep = s.split(" ");
            String[] days = sep[0].split(",");     // eg. {M, W, Th}
            Period p = new Period(convertToMin(sep[2]+" "+sep[3]), convertToMin(sep[5]+" "+sep[6]));
            for (String d : days) {
                if (d.equals("M")) {
                    timeTable.get(0).add(p);
                } else if (d.equals(("T"))) {
                    timeTable.get(1).add(p);
                } else if (d.equals("W")) {
                    timeTable.get(2).add(p);
                } else if (d.equals("Th")) {
                    timeTable.get(3).add(p);
                } else if (d.equals("F")) {
                    timeTable.get(4).add(p);
                }
            }
        }

        Boolean checkResult = false;
        for (ArrayList<Period> ls : timeTable) {
            for (int i=0; i<ls.size(); i++) {
                for (int j=i+1; j<ls.size(); j++) {
                    if (ls.get(i).checkConflict(ls.get(j))) {
                        checkResult = true;
                    }
                }
            }
        }
        Log.i("has conflict?", checkResult+"");
        return checkResult;
    }

    public int convertToMin(String time) {
        String[] temp = time.split(" ");
        int hour = Integer.parseInt(temp[0].split(":")[0]);
        int min = Integer.parseInt(temp[0].split(":")[1]);
        if (temp[1].equals("PM")) {
            hour += 12;
        }
        return 60 * hour + min;
    }

    private class Period {
        private int startMin;
        private int endMin;

        public Period(int s, int e) {
            this.startMin = s;
            this.endMin = e;
        }

        public int getStartMin() {
            return startMin;
        }

        public int getEndMin() {
            return endMin;
        }

        public boolean checkConflict(Period p) {
            return (p.getStartMin()>=startMin && p.getStartMin()<=endMin)
                    || (p.getEndMin()>=startMin && p.getEndMin()<=endMin)
                    || (p.getStartMin()<=startMin && p.getEndMin()>=endMin);
        }
    }
}