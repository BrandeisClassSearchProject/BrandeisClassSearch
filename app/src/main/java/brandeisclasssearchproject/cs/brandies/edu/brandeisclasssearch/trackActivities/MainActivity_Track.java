package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.trackActivities;

import android.os.Bundle;

import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.R;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.database.DBOpenHelper;

/**
 * Created by hangyanjiang on 2016/11/24.
 */

public class MainActivity_Track {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    String databaseName =i.getExtras().getString("databaseName");
    String tableName = i.getExtras().getString("tableName");

    dbOpenHelper = new DBOpenHelper(getApplicationContext());
    db = dbOpenHelper.getReadableDatabase();

    String[] columns = new String[] {DBOpenHelper.KEY_ID, DBOpenHelper.KEY_COURSE_NAME, DBOpenHelper.KEY_COURSE_TIME};

}

    public String[] readDatabase(String tableName) {

        String selectQuery = "SELECT  * FROM " + tableName;
        cursor = dbOpenHelper.getLog(tableName, db);
        String[] data      = null;
        if (cursor.moveToFirst()) {
            do {
                data = data + {}
            } while (cursor.moveToNext());
        }
        cursor.close();
        return data;
}
