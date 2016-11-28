package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.fragments;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.InfoListAdapter;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.MainActivity;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.R;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities.CourseDetail;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities.ScheduleTable;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities.ShowBooks;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities.ShowDescription;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities.ShowSyllabus;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities.ShowTeacher;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.database.DBOpenHelper;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ExtructionURLs;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.Producers;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersBooksInfo;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersClassDescription;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersClassSchdule;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersSyllabus;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersTearcherInfo;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.inpInterpreter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMyClasses extends Fragment {


    Context context;
    DBOpenHelper dbOpenHelper;
    String id2;
    String courseName2;
    SQLiteDatabase db;
    Fragment fr;
    ListView lv;
    InfoListAdapter adapter;
    String currentClassName;

    public FragmentMyClasses() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_classes, container, false);

        context = getActivity();
        ListView ls = (ListView) v.findViewById(R.id.classList);
        dbOpenHelper = new DBOpenHelper(context);
        db = dbOpenHelper.getReadableDatabase();

        Cursor cursor = dbOpenHelper.getCourse(db);
        String[] columns = new String[] {DBOpenHelper.KEY_ID, DBOpenHelper.KEY_COURSE_NAME, DBOpenHelper.KEY_COURSE_SEASON, DBOpenHelper.KEY_COURSE_TIME};
        int[] views = new int[] {R.id.myClass_id, R.id.myClass_courseName, R.id.myClass_courseSeason, R.id.myClass_courseTime};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(container.getContext(), R.layout.fragment_my_classes_listentry, cursor, columns, views, 0);

        adapter.notifyDataSetChanged();
        ls.setAdapter(adapter);

        // long click listener
        ls.setLongClickable(true);
        ls.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                TextView t_id = (TextView) arg1.findViewById(R.id.myClass_id);
                id2 = t_id.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Do you really want to delete this Course?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dbOpenHelper.deleteCourse(Long.parseLong(id2), db);

                                // refresh fragment
                                Fragment fr = new FragmentMyClasses();
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.content_main, fr);
                                fragmentTransaction.commit();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                return true;
            }
        });

        // short click listener
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView t_courseName = (TextView) view.findViewById(R.id.myClass_courseName);
                courseName2 = t_courseName.getText().toString();

                lv = (ListView) getActivity().findViewById(R.id.theContentList);
                currentClassName = courseName2;

                /*
                Intent i = new Intent(getActivity(), CourseDetail.class);
                i.putExtra("courseName", courseName2);
                startActivity(i);
                */

                /*
                Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                toolbar.setTitle("Brandeis Class Search");
                Fragment fr = new FragmentBlank();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_main, fr);
                fragmentTransaction.commit();
                MainActivity.lv.setVisibility(View.VISIBLE);
                */
            }
        });

        return v;
    }

    /*
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

            fr = new FragmentBlank();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_main, fr);
            fragmentTransaction.commit();
            lv.setVisibility(View.VISIBLE);

            //update the list
            if(producersList==null){
                //Toast.makeText(getApplicationContext(), "We cannot find relevant information, maybe the class ID is wrong?", Toast.LENGTH_LONG).show();
                producersList=producersList_copy;
                return;
            }
            producersList_copy=producersList;
            //Toast.makeText(getApplicationContext(), "Showing", Toast.LENGTH_SHORT).show();
            currentClassName=producersList.get(0).getResult().get(0);
            ListView lv = (ListView) getActivity().findViewById(R.id.theContentList);
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
                        i = new Intent(getActivity(),ShowDescription.class);
                    }else if(p instanceof ProducersTearcherInfo){
                        i = new Intent(getActivity(),ShowTeacher.class);
                    }else if(p instanceof ProducersBooksInfo){
                        i = new Intent(getActivity(),ShowBooks.class);
                    }else if(p instanceof ProducersSyllabus){
                        i = new Intent(getActivity(),ShowSyllabus.class);
                    }else if(p instanceof ProducersClassSchdule){
                        al = new ArrayList<>();
                        al.add("CLASS");
                        al.add(currentClassName);
                        for(String temp:p.getResult()){
                            al.add(temp);
                        }
                        i = new Intent(getActivity(),ScheduleTable.class);
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
                    while (MainActivity.datasMap==null){
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            Log.i("Main.search","interruptedException");
                        }
                    }
                    producersList = new ExtructionURLs(classId,MainActivity.datasMap).getProducers();

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
    */

}
