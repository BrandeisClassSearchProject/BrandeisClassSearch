package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMyClasses extends Fragment {


    public FragmentMyClasses() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_classes, container, false);
        ListView lv= (ListView)getView().findViewById(R.id.classList);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //delete the current entry
                //by get the id of this item
                //delete in database
                return false;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });



        return v;
    }

}
