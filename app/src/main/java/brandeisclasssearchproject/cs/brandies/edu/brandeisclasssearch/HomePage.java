package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;

import java.util.Date;

/**
 * Created by hangyanjiang on 2016/11/24.
 */

public class HomePage extends AppCompatActivity {

    Button track;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        track = (Button) findViewById(R.id.button_track);
        edit = (Button) findViewById(R.id.button_edit);

        // go to track part (time mangement)
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                startActivity(i);
            }
        });

        // go to edit part (select course)
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                startActivity(i);
            }
        });
    }
}
