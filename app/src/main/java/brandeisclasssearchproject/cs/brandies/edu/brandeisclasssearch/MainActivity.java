package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;


import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities.ScheduleTable;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities.ShowBooks;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities.ShowDescription;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities.ShowSchedule;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities.ShowSyllabus;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities.ShowTeacher;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.fragments.FragmentBlank;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.fragments.FragmentMyClasses;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ExtructionURLs;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.Producers;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersBasic;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersBooksInfo;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersClassDescription;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersClassSchdule;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersSyllabus;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersTearcherInfo;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.inpInterpreter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ClassSearchingTask CST;
    HashMap<String, ArrayList<String>> datas;
    ArrayList<HashMap<String,ArrayList<String>>> datasMap;
    AsyncTask dataLoader;
    ArrayList<Producers> producersList = new ArrayList<Producers>();
    InfoListAdapter adapter;
    ProgressBar pb;
    ListView lv;
    SearchView sv;
    MenuItem mi;
    private SimpleCursorAdapter mAdapter;
    LinkedList<String> sortedClasses;
    Toolbar toolbar;
    Fragment fr;
    String currentClassName;


    final int[] terms=new int[]{1171,1163,1162,1161,1152,1151,1153} ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentClassName=null;
        pb=(ProgressBar) findViewById(R.id.theProgressBar);
        pb.setVisibility(View.INVISIBLE);

//        dataLoader=new DataLoader(new DataLoader.AsyncResponse() {
//            @Override
//            public void processFinish(HashMap<String, ArrayList<String>> output) {
//                datas=output;//set the hashmap for use in the main thread;
//            }
//        },getApplicationContext());
//        Log.i("Main","dataLoader.execute()");
//        //dataLoader.execute();
        new LoadingData().execute();//not ready yet

        mAdapter = new SimpleCursorAdapter(this,
                R.layout.suggestion_entry,
                null,
                new String[] {"className"},
                new int[] {R.id.suggestion_entry_text},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //declare the fab buttom, which is the round button floating on the screen

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentClassName==null){
                    Snackbar.make(view, "Click the search icon to find your class~", Snackbar.LENGTH_SHORT)
                            .setAction("√", new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    Log.i("Main","no currentClassName yet");
                                }
                            }).show();
                }else{
                    Snackbar.make(view, "Save "+currentClassName+"?", Snackbar.LENGTH_SHORT)
                            .setAction("√ SAVE", new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    //implement add from db here
                                    Toast.makeText(MainActivity.this,"Nothing yet",Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                }


            }
        });//set the on Click of fab buttom

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Find the drawer
        //which is the entire layout we can see

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //default generated code.


        //The list view is the one we put all the infomations
        lv = (ListView) findViewById(R.id.theContentList);
        //SearchView sv = (SearchView) findViewById(R.id.searchClass);
        //SearchView sv = (SearchView) findViewById(R.id.action_settings);
        //adapter = new InfoListAdapter(producersList);
        //lv.setAdapter(adapter);




    }

    private void setSV(){

        sv.setSuggestionsAdapter(mAdapter);
        sv.setOnSuggestionListener(new SearchView.OnSuggestionListener(){

            public boolean onSuggestionSelect(int position) {
                Toast.makeText(getApplicationContext(), "onSugggestionSelect", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Toast.makeText(getApplicationContext(), "onSugggestionClick", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener(){


            @Override
            public boolean onQueryTextSubmit(String query) {
                if(datas!=null){
                    //if(dataLoader.getStatus() == AsyncTask.Status.FINISHED){
                    //lv.setVisibility(View.INVISIBLE);
                    CST= new ClassSearchingTask(query);
                    CST.execute();
                    return true;
                }
                else {
                    pb.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Loading not Finished yet, please wait few more seconds~", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }


            //implement suggestion here!
            @Override
            public boolean onQueryTextChange(String newText) {
                if(sortedClasses==null||newText.isEmpty()){
                    Log.wtf("Main.onQueryTextChange","the list is null!");
                    return false;
                }
                polulateAdapter(newText);
                return true;
            }
        });
    }

    //http://stackoverflow.com/questions/23658567/android-actionbar-searchview-suggestions-with-a-simple-string-array
    private void polulateAdapter(String query) {
        Log.i("Main.polulateAdapter","new query is "+query);
        MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "className" });
        int i=0;
        for (String s:sortedClasses) {
            if (s.startsWith(query.toUpperCase())) {
                Log.wtf("Main.polulateAdapter",s+"  "+query.toUpperCase());
                c.addRow(new Object[] {i, s});
                i++;
            }

            if(i==8){
                break;
            }
        }
        Log.i("Main.polulateAdapter",String.valueOf(i));
        mAdapter.changeCursor(c);



    }

    @Override
    public void onBackPressed() {
        Log.i("mylog","onBackPressed");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //Toast.makeText(getApplicationContext(), "close", Toast.LENGTH_SHORT).show();
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        mi = menu.findItem(R.id.action_settings);
        sv = (SearchView) MenuItemCompat.getActionView(mi);
        setSV();
        //
        //sv=(SearchView)findViewById(R.id.action_settings);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            toolbar.setTitle("Brandeis Class Search");
            fr = new FragmentBlank();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_main, fr);
            fragmentTransaction.commit();
            lv.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_my) {
            lv.setVisibility(View.INVISIBLE);
            Log.i("onNavigationItemS","my class selected");
            toolbar.setTitle("My Classes");
            fr = new FragmentMyClasses();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_main, fr);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_time) {
            toolbar.setTitle("My Schedule");
        }  else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }else if (id==R.id.nav_maj){
            toolbar.setTitle("Majors");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            pb.setVisibility(View.INVISIBLE);
            lv.setVisibility(View.VISIBLE);
            //update the list
            if(producersList==null){
                Toast.makeText(getApplicationContext(), "We cannot find relevant information, maybe the class ID is wrong?", Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(getApplicationContext(), "Showing", Toast.LENGTH_SHORT).show();
            currentClassName=producersList.get(0).getResult().get(0);
            ListView lv = (ListView) findViewById(R.id.theContentList);
            adapter = new InfoListAdapter(producersList);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
            while (datas==null){Log.i("ClassSearchTask","waiting for map");}
            if (classInfos != null) {
                Log.i("ClassSearchTask", "array list classInfos is OK. Initialize extractionURLs");
                //extractionUrls = new ExtructionURLs(classInfos, AcademicSeason.FALL, AcademicYear._2016, datas);
                producersList = new ExtructionURLs(classId,datas).getProducers();
                if (producersList==null){
                    //isDone=true;
                    Log.i("ClassSearchTask", "Class not found");
                    return null;

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

            AssetManager am = getApplicationContext().getAssets();
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
            pb.setVisibility(View.INVISIBLE);
            datasMap=dataMap;
            LinkedList<String> l= new LinkedList<>();
            l.addAll(tempClasses);
            sortedClasses=l;
//            for(String s:l){
//                Log.i("Main.onPostExecute",s);
//            }

            Log.i("Main.LoadingData","the list is "+sortedClasses==null?"NULL":String.valueOf(sortedClasses.size()));
            Log.i("Main.LoadingData","Done.\nTakes "+String.valueOf((System.currentTimeMillis()-startTime)/1000.0)+"s.");
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            pb.setVisibility(View.INVISIBLE);
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
                            if (datas==null){
                                datas=hm;
                                publishProgress();
                            }
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
