package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.database.DBOpenHelper;

/**
 * Created by hangyanjiang on 2016/11/24.
 *
 * not done!
 *
 * this class should show the courses selected by reading from the database
 *
 * need generate a schedule by reading database.
 */

public class Schedule extends AppCompatActivity {

    SQLiteDatabase db;
    DBOpenHelper dbOpenHelper;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i =getIntent();

        String databaseName = i.getExtras().getString("databaseName");
        String tableName = i.getExtras().getString("tableName");

        dbOpenHelper = new DBOpenHelper(getApplicationContext());
        db = dbOpenHelper.getReadableDatabase();

        String[] columns = new String[]{DBOpenHelper.KEY_ID, DBOpenHelper.KEY_COURSE_NAME, DBOpenHelper.KEY_COURSE_TIME};

    }
}
