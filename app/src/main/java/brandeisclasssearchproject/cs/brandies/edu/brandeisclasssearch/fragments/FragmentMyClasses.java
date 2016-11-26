package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.fragments;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.List;

import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.R;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.database.DBOpenHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMyClasses extends Fragment {


    Context context;

    public FragmentMyClasses() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_classes, container, false);
        context = getActivity();
        ListView ls = (ListView) v.findViewById(R.id.classList);


        DBOpenHelper dbOpenHelper = new DBOpenHelper(context);

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();


        Cursor cursor = dbOpenHelper.getLog(db);
        String[] columns = new String[] {DBOpenHelper.KEY_ID, DBOpenHelper.KEY_COURSE_NAME, DBOpenHelper.KEY_COURSE_TIME};
        int[] views = new int[] {R.id.myClass_id, R.id.myClass_courseName, R.id.myClass_courseTime};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(container.getContext(), R.layout.fragment_my_classes, cursor, columns, views, 0);

        adapter.notifyDataSetChanged();
        ls.setAdapter(adapter);


        return v;
    }

}
