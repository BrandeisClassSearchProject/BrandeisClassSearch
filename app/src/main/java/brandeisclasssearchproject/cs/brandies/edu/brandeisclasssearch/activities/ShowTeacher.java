package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.R;

public class ShowTeacher extends AppCompatActivity {

    ImageView imageView;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_teacher);
        Intent i =getIntent();
        ArrayList<String> list = i.getExtras().getStringArrayList("list");


        imageView = (ImageView) findViewById(R.id.Teacher_Image);
        operationBG task = new operationBG();
        String url;
        if(list.size() == 9){
            url = list.get(8);
        } else {
            url = "";
        }
        Log.e("URL IS: ",url);
        task.execute(url);




        String content = "";
        TextView tv = (TextView) findViewById(R.id.teacher_show_teacher);
        for (String s : list) {
            content += s+"\n";
        }
        tv.setText(content);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.right_out,R.anim.left_in);
    }

    private class operationBG extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            imageView.setImageBitmap(bitmap);
            Log.e("MESSAGE", "task finished!");
        }
    }

}
