package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities;

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
import android.widget.ProgressBar;

import java.util.ArrayList;

import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.InfoListAdapter;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.R;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.Producers;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersBasic;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersBooksInfo;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersClassDescription;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersClassSchdule;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersSyllabus;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersTearcherInfo;

/**
 * Created by hangyanjiang on 2016/11/27.
 */

public class CourseDetail extends AppCompatActivity {

    ListView lv;
    ArrayList<Producers> producersList = new ArrayList<>();
    ArrayList<String> searchResult ;
    String currentClassName;
    InfoListAdapter adapter;
    ProgressBar pb ;

    String term;
    int intTerm;
    String courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        lv = (ListView) findViewById(R.id.courseDetail_listView);
        pb = (ProgressBar)findViewById(R.id.thePB) ;
        Intent i = getIntent();
        intTerm=i.getIntExtra("term",1171);
        courseName=i.getStringExtra("className");
        term=i.getStringExtra("classTerm");
        searchResult = i.getStringArrayListExtra("list");
        ClassSearchingTask CST = new ClassSearchingTask();
        CST.execute();

    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.right_out,R.anim.left_in);
    }

    private class ClassSearchingTask extends AsyncTask<Object,Void,Void> {
        private ArrayList<String> classInfos;
        private String classId;
        //private Boolean isDone;

        public ClassSearchingTask() {
        }



        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
            lv.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //update the list
            lv.setVisibility(View.VISIBLE);
            pb.setVisibility(View.INVISIBLE);
            currentClassName=producersList.get(0).getResult().get(0);
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
        protected Void doInBackground(Object... params) {

            if (searchResult != null) {
                Log.i("SmallClassSearchTask", "ready, go");

                producersList = new ArrayList<>();
                producersList.add(new ProducersBasic( term,courseName));
                ProducersClassSchdule timeProducer = new ProducersClassSchdule();
                for(String s : searchResult){

                    String contents=s.substring(14).trim();
                    String attr = s.substring(0,13);
                    //Log.i("ExtructionURLs",attr);
                    switch(attr){
                        case "  DESCRIPTION":
                            Log.i("ExtructionURLs","  DESCRIPTION: "+contents);
                            producersList.add(new ProducersClassDescription(contents));
                            break;
                        case "      TEACHER":
                            Log.i("ExtructionURLs","      TEACHER: "+contents);
                            producersList.add(new ProducersTearcherInfo(contents));
                            break;
                        case "        BOOKS":
                            String template = "http://www.bkstr.com/webapp/wcs/stores/servlet/booklookServlet?bookstore_id-1=1391&term_id-1=1163&div-1=&dept-1=PHYS&course-1=105A&sect-1=1";
                            template=template.replace("1163",String.valueOf(intTerm));
                            String[] ssss = courseName.split(" ");
                            template=template.replace("PHYS",ssss[0]);
                            template=template.replace("105A",ssss[ssss.length-1]);
                            Log.i("ExtructionURLs","        BOOKS: "+template);
                            producersList.add(new ProducersBooksInfo(template));
                            break;
                        case "        BLOCK":
                            Log.i("ExtructionURLs","       BLOCKS: "+contents);
                            timeProducer.add("Block: "+contents);
                            break;
                        case "        TIMES":
                            Log.i("ExtructionURLs","        TIMES: "+contents);
                            timeProducer.add(contents);
                            break;
                        default: //Log.i("ExtructionURLs","setOutWithMap String s is"+s);
                            break;

                    }
                }
                Producers tempP=producersList.get(1);
                producersList.set(1,timeProducer);
                producersList.add(tempP);






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
