package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.R;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.database.DBOpenHelper;

/**
 * Larry's proposal:
 *      this class receives an arrayList<String> list
 *      then creates a schedule table.
 *
 *      list has strings in following manager:
 *      {course1, time1, course2, time2, ...}
 */

public class ScheduleTable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_table);

        Intent i =getIntent();
        ArrayList<String> list =i.getExtras().getStringArrayList("list");

        // parse arrayList and generate a schedule

    }
}
