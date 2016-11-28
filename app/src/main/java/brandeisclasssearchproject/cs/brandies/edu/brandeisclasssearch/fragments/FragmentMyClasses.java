package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.fragments;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.MainActivity;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.R;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities.CourseDetail;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.database.DBOpenHelper;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ExtructionURLs;
/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMyClasses extends Fragment {


    Context context;
    DBOpenHelper dbOpenHelper;
    String id2;
    String courseName2;
    SQLiteDatabase db;
    ListView lv;
    String currentClassName;
    ArrayList<HashMap<String,ArrayList<String>>> datasMap;
    final int[] terms=new int[]{1171,1163,1162,1161,1152,1151,1153} ;
    Snackbar sb;

    public FragmentMyClasses() {
        //new LoadingData().execute();
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

        Boolean testResult = dbOpenHelper.testConflict(db);
        //Toast.makeText(getActivity(),testResult,Toast.LENGTH_LONG).show();
        sb=Snackbar.make(container, "You have class time conflict, the schedule might not be shown correctly.  "+testResult, Snackbar.LENGTH_INDEFINITE)
                .setAction("ok", new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Log.i("Frag","class conflict");
                    }
                });
        sb.show();

        Boolean testResult2 = dbOpenHelper.testConflict(db);

        if (testResult2) {
            Toast.makeText(getActivity(),"WARNING, COURSE CONFLICTS EXIST!",Toast.LENGTH_LONG).show();
        }

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
            private Activity a;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(MainActivity.datasMap!=null) {
                    a=getActivity();
                    TextView t_courseName = (TextView) view.findViewById(R.id.myClass_courseName);
                    TextView t_courseTerm = (TextView) view.findViewById(R.id.myClass_courseSeason);

                    courseName2 = t_courseName.getText().toString();
                    String[] sst = courseName2.split(" ");
                    String cn2 = sst[0]+" "+sst[sst.length-1];
                    Intent i = new Intent(context, CourseDetail.class);
                    ExtructionURLs eurls=new ExtructionURLs(cn2, MainActivity.datasMap,true);
                    i.putExtra("term",eurls.getCurrentTerm());
                    i.putExtra("className",cn2);
                    i.putExtra("classTerm",t_courseTerm.getText().toString());
                    i.putExtra("list", eurls.getSearchResults());
                    currentClassName = courseName2;
                    startActivity(i);
                    a.overridePendingTransition(R.anim.right_in,R.anim.left_out);

                }

            }
        });

        return v;
    }

    private class LoadingData extends AsyncTask<Object,Void,Void>{
        private TreeSet<String> tempClasses;
        long startTime;
        //ProgressDialog pDialog;
        ArrayList<HashMap<String,ArrayList<String>>> dataMap;
        private String filename = "DATA.txt";

        public LoadingData() {
            startTime = System.currentTimeMillis();
            //pDialog=new ProgressDialog(MainActivity.this);
            this.dataMap = null;
        }



        @Override
        protected Void doInBackground(Object... params) {

            AssetManager am = getActivity().getApplicationContext().getAssets();
            try {
                InputStream is = am.open(filename);
                InputStreamReader isr= new InputStreamReader(is);
                dataMap=putInMap(isr);
            } catch (IOException e) {
                Log.wtf("Main.LoadingData","DATA.txt not found");
            }



            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {

            datasMap=dataMap;
            LinkedList<String> l= new LinkedList<>();
            l.addAll(tempClasses);

//            for(String s:l){
//                Log.i("Main.onPostExecute",s);
//            }

            Log.i("Main.LoadingData","Done.\nTakes "+String.valueOf((System.currentTimeMillis()-startTime)/1000.0)+"s.");
        }



        private ArrayList<HashMap<String,ArrayList<String>>> putInMap(InputStreamReader isr) throws IOException {
            tempClasses=new TreeSet<>();
            ArrayList<HashMap<String,ArrayList<String>>> data = new ArrayList<>(terms.length);
            HashMap<String,ArrayList<String>> hm = new HashMap<>();
            BufferedReader br = new BufferedReader(isr);
            String temp;
            String title=null;
            ArrayList<String> tempArray=new ArrayList<>();
            String updateDate = br.readLine().split(" ")[0];
            int counter=1;
            while((temp=br.readLine())!=null ){
                //Log.i("DataLoader",temp);
                if(counter%14==1){
                    title=temp;

                    if(title.length()>updateDate.length()){
                        if(title.substring(0,updateDate.length()).equals(updateDate)){
                            counter--;

                            data.add(hm);
                            hm = new HashMap<>();
                        }else{
                            String[] tempS=title.split(" ");
                            tempClasses.add(tempS[0]+" "+tempS[tempS.length-1]);
                        }
                    }else{
                        String[] tempS=title.split(" ");
                        tempClasses.add(tempS[0]+" "+tempS[tempS.length-1]);
                    }
                }else if(counter%14==0){
                    Log.i("DataLoader",String.valueOf(hm.size())+" "+title);
                    if(!temp.equals(".")){
                        tempArray.add(temp);
                    }
                    ArrayList<String> tt =new ArrayList<String>();
                    tt.addAll(tempArray);
                    hm.put(title,tt);

                    tempArray.clear();
                    Log.i("DataLoader","key: "+title);
                    Log.i("DataLoader","list size "+String.valueOf(tt.size()));
                }else{
                    if(!temp.equals(".")){
                        tempArray.add(temp);
                    }
                }
                counter++;
            }
            Log.i("DataLoader","The size of the map is "+ hm.size());

            br.close();
            return data;
        }
    }

}
