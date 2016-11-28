package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLinks extends Fragment {



    private final String[] linksArray=new String[]{"http://www.brandeis.edu/","http://www.brandeis.edu/registrar/bulletin/provisional/courses/subjects/index.html",
    "https://login.brandeis.edu/services.html","https://www.brandeis.edu/sage/","https://www.amazon.com/New-Used-Textbooks-Books/b/ref=sv_b_5?ie=UTF8&node=465600",
    };


    private final int[] layouts=new int[]{R.layout.fragment_links_list_entry2,
    R.layout.fragment_links_list_entry4,R.layout.fragment_links_list_entry,
    R.layout.fragment_links_list_entry3,R.layout.fragment_links_list_entry5};


    public FragmentLinks() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View v = inflater.inflate(R.layout.fragment_links, container, false);
        ListView lv= (ListView)v.findViewById(R.id.fragment_links_list);
        lv.setAdapter(new ItemsAdapter());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Activity a = getActivity();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linksArray[position]));
                startActivity(browserIntent);
                a.overridePendingTransition(R.anim.right_in,R.anim.left_out);
            }

            });



        return v;
    }


    private class ItemsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return linksArray.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView!=null){
//                ImageView iv = (ImageView)convertView.findViewById(R.id.imageView3);
//                iv.setImageResource(drawables[position]);
//                v=convertView;
                return convertView;
            }else{

                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View v = inflater.inflate(layouts[position], parent, false);

                return v;
            }


        }
    }

}
