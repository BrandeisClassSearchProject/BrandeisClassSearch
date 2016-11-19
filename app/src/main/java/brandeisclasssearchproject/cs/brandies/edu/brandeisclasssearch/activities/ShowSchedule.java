package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.R;

public class ShowSchedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_schedule);

        Intent i =getIntent();
        ArrayList<String> list =i.getExtras().getStringArrayList("list");


        /**for testing purpose, delete after implementation*/
        if(list!=null&&!list.isEmpty()){
            Toast.makeText(getApplicationContext(),list.get(0),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"nothing in the list",Toast.LENGTH_SHORT).show();
        }
        /**for testing purpose, delete after implementation*/
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.right_out,R.anim.left_in);
    }
}
