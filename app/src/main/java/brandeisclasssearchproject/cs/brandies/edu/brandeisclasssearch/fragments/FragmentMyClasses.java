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


        return v;
    }

}
