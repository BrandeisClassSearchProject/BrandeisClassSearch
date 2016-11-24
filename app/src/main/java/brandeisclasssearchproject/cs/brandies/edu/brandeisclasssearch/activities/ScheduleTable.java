package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.R;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.database.DBOpenHelper;

/**
 * Larry's proposal:
 *      this class receives a string as database name
 *      and, reading from that database, creates a schedule
 *      table.
 */

public class ScheduleTable extends AppCompatActivity {

    SQLiteDatabase db;
    DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_table);
    }

    Intent i =getIntent();
    String databaseName =i.getExtras().getString("databaseName");


}
