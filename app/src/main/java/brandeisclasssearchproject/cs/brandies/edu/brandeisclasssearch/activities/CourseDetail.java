package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.InfoListAdapter;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.R;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.fragments.FragmentBlank;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.fragments.FragmentSchedule;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ExtructionURLs;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.Producers;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersBooksInfo;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersClassDescription;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersClassSchdule;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersSyllabus;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersTearcherInfo;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.inpInterpreter;

/**
 * Created by hangyanjiang on 2016/11/27.
 */

public class CourseDetail extends AppCompatActivity {

    ListView lv = (ListView) findViewById(R.id.courseDetail_listView);
    ArrayList<Producers> producersList = new ArrayList<>();
    ArrayList<Producers> producersList_copy ;
    String currentClassName;
    InfoListAdapter adapter;
    HashMap<String, ArrayList<String>> datas;
    ArrayList<HashMap<String,ArrayList<String>>> datasMap;
    String courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        Intent i = getIntent();
        courseName = i.getStringExtra("courseName");
        //producersList.add(courseName);

        Log.i("courseName", courseName);


        ClassSearchingTask CST = new ClassSearchingTask(courseName);
        CST.execute();

    }

    private class ClassSearchingTask extends AsyncTask<Object,Void,Void> {
        private ArrayList<String> classInfos;
        private String classId;
        //private Boolean isDone;

        public ClassSearchingTask(String s) {
            classId=s.toUpperCase();
            classInfos=new inpInterpreter(classId).getClassInfos();
        }



        @Override
        protected void onPreExecute() {
            lv.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //update the list
            if(producersList==null){
                Toast.makeText(getApplicationContext(), "We cannot find relevant information, maybe the class ID is wrong?", Toast.LENGTH_LONG).show();
                producersList=producersList_copy;
                return;
            }
            producersList_copy=producersList;
            Toast.makeText(getApplicationContext(), "Showing", Toast.LENGTH_SHORT).show();
            currentClassName=producersList.get(0).getResult().get(0);
            ListView lv = (ListView) findViewById(R.id.theContentList);
            adapter = new InfoListAdapter(producersList);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    Producers p = producersList.get(position);
                    ArrayList<String> al = new ArrayList<>();
                    boolean isSchedule=false;
                    //Toast.makeText(getApplicationContext(), String.valueOf(position)+" "+p.getName(), Toast.LENGTH_SHORT).show();//debug purpose only
                    Intent i = null;
                    if(p instanceof ProducersClassDescription){
                        i = new Intent(getApplicationContext(),ShowDescription.class);
                    }else if(p instanceof ProducersTearcherInfo){
                        i = new Intent(getApplicationContext(),ShowTeacher.class);
                    }else if(p instanceof ProducersBooksInfo){
                        i = new Intent(getApplicationContext(),ShowBooks.class);
                    }else if(p instanceof ProducersSyllabus){
                        i = new Intent(getApplicationContext(),ShowSyllabus.class);
                    }else if(p instanceof ProducersClassSchdule){
                        al = new ArrayList<>();
                        al.add("CLASS");
                        al.add(currentClassName);
                        for(String temp:p.getResult()){
                            al.add(temp);
                        }
                        i = new Intent(getApplicationContext(),ScheduleTable.class);
                        for(String s:al){
                            Log.i("Main",s);
                        }
                        isSchedule=true;


                    }
                    if(i != null){
                        if(isSchedule){
                            i.putExtra("list",al);
                        }else{
                            i.putExtra("list",p.getResult());
                        }

                        startActivity(i);
                        overridePendingTransition(R.anim.right_in,R.anim.left_out);
                    }

                }
            });


            //adapter.notifyDataSetChanged();

        }

        @Override
        protected void onProgressUpdate(Void... params) {
            Log.i("Main","Search on progress update, thread sleep 400ms");
        }

        @Override
        protected Void doInBackground(Object... params) {
            while (datas==null){Log.i("ClassSearchTask","waiting for map");}
            if (classInfos != null) {
                Log.i("ClassSearchTask", "array list classInfos is OK. Initialize extractionURLs");
                //extractionUrls = new ExtructionURLs(classInfos, AcademicSeason.FALL, AcademicYear._2016, datas);
                producersList = new ExtructionURLs(classId,datas).getProducers();
                if (producersList==null){
                    while (datasMap==null){
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            Log.i("Main.search","interruptedException");
                        }
                    }
                    producersList = new ExtructionURLs(classId,datasMap).getProducers();

                    if(producersList==null ) {
                        Log.i("ClassSearchTask", "Class not found");
                        return null;
                    }

                }
                Log.i("ClassSearchTask", "found it ");
                for (Producers p : producersList) {
                    ArrayList<String> al = p.getResult();
                    if (p instanceof ProducersTearcherInfo) {
                        for (String s : al) {
                            Log.i("teacher", s);
                        }
                    } else if (p instanceof ProducersBooksInfo) {
                        for (String s : al) {
                            Log.i("books", s);
                        }
                    } else if (p instanceof ProducersClassDescription) {
                        for (String s : al) {
                            Log.i("class description", s);
                        }
                    }
                    //isDone = true;
                }
            }return null;
        }
    }
}
