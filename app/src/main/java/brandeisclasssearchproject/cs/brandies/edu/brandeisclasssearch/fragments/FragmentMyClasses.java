package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.fragments;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.MainActivity;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.R;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities.CourseDetail;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.database.DBOpenHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMyClasses extends Fragment {


    Context context;
    DBOpenHelper dbOpenHelper;
    String id2;
    String courseName2;
    SQLiteDatabase db;

    public FragmentMyClasses() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_classes, container, false);

        /*
        String[] str = {"dosomething", "and", "try", "real"};
        ListView ls = (ListView) v.findViewById(R.id.classList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,str);

        ls.setAdapter(adapter);
        */

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
        testConflict();
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
                Intent i = new Intent(getActivity(), CourseDetail.class);
                i.putExtra("courseName", courseName2);
                startActivity(i);

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

    public void testConflict(){
        String timeRowTmp = "";
        ArrayList<String> timeList = new ArrayList<>();
        dbOpenHelper = new DBOpenHelper(context);
        db = dbOpenHelper.getReadableDatabase();
        Cursor testCursor = dbOpenHelper.getCourse(db);
        //int rowCount = testCursor.getCount();
        //Log.e("NUMBER OF ENTRIES","" + rowCount);
        while(true){
            timeRowTmp = testCursor.getString(testCursor.getColumnIndex("courseTime"));
            String[] listTmp = timeRowTmp.split("\\|");
            for (int i = 1; i < listTmp.length; i++) {
                timeList.add(listTmp[i].trim());
            }
            if(testCursor.isLast()){
                break;
            }
            testCursor.moveToNext();
        }

        timeRowTmp = "";
        for (String str : timeList) {
            timeRowTmp = timeRowTmp + str;
        }
        Log.e("THE FIRST ENTRY",timeRowTmp);

    }
}
