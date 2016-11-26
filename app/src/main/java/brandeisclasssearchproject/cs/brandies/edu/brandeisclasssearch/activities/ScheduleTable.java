package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.R;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.fragments.FragmentSchedule;

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
        for(String s:list){
            Log.i("ScheduleTable",s);
        }
        Fragment fr = new FragmentSchedule();
        Bundle b = new Bundle();
        b.putStringArrayList("list",list);
        fr.setArguments(b);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.placeHolder, fr);
        fragmentTransaction.commit();


    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.right_out,R.anim.left_in);
    }
}
