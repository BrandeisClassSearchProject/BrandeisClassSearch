package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSchedule extends Fragment {

    ArrayList<String> classes;
    ArrayList<Integer> colors;



    public FragmentSchedule() {
        classes=new ArrayList<>();
        colors=new ArrayList<>();
        String[] colors = new String[]{"255 101 101","255 108 0","0 102 0","0 204 204","0 128 255","0 0 204","255 0 255","255 102 255","96 96 96"};
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_schedule, container, false);
        ArrayList<String> al = getArguments().getStringArrayList("list");
        processInput(al);




        return v;
    }

    private void processInput(ArrayList<String> al) {
        boolean isClass=false;
        int i=0;
        int current=-1;
        for(String s:al){
            if (s.equals("CLASS")){
                isClass=true;
            } else if(isClass){
                classes.add(s);
                colors.add(i);
                current=i;
                i++;
                isClass=false;
            }else if(!s.startsWith("Block")){
                try {
                    setColor(s,current);
                } catch (Exception e) {
                    Log.i("FragmentSchedule","something wrong");
                }
            }




        }
    }

    private void setColor(String s, int current) throws Exception{
        if(current==-1){
            Log.wtf("FragmentSchedule","the current is -1");
            return;
        }

        boolean counter=true;
        int ind = 0;
        char c='z';
        while((c = s.charAt(0))!=' '){
            ind++;
        }
        String[] days = s.substring(0,ind+1).split(",");
        String[] times = s.substring(ind+2).split(" - ");
        if(times.length!=2){
            Log.wtf("FragmentSchedule","the times length is "+times.length);
            return;
        }

        for (String day :days){
            String startpoint = findStart(times[0].trim());
            String endpoint = findEnd(times[1].trim());

        }



    }

    private String findEnd(String trim) {

        return null;

    }

    private String findStart(String trim) {
        String ap = trim.split(" ")[1];


        String[] ts = trim.split(":");
        if(ts.length!=2){
            Log.wtf("FragmentSchedule","the ts length is "+ts.length);
            return null;
        }


        return null;

    }

}
