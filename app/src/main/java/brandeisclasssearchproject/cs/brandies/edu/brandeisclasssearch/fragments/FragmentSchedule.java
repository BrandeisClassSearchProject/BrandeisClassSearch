package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.fragments;


import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSchedule extends Fragment {

    ArrayList<String> classes;
    ArrayList<Integer> colors;
    final String[] COLORS =  new String[]{"255 101 101","255 108 0","0 102 0","0 204 204","0 128 255","0 0 204","255 0 255","255 102 255","96 96 96"};
    View v;
    ListView classColors;


    public FragmentSchedule() {
        classes=new ArrayList<>();
        classes.add("");
        colors=new ArrayList<>();
        colors.add(-1);
        // Required empty public constructor
    }


    private class colorListAdapter extends BaseAdapter{

        public colorListAdapter() {
        }

        @Override
        public int getCount() {
            return classes.size();
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
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            if(position==0){
                return inflater.inflate(R.layout.mr_empty, parent, false);
            }
            View temp = inflater.inflate(R.layout.fragment_schedule_list_entry, parent, false);
            TextView tv =(TextView)temp.findViewById(R.id.EntryText);
            TextView tvc = (TextView)temp.findViewById(R.id.EntryColor);
            tv.setText(classes.get(position));
            String[] rgb = COLORS[colors.get(position)].split(" ");
            tvc.setBackgroundColor(Color.rgb(Integer.parseInt(rgb[0]),Integer.parseInt(rgb[1]),Integer.parseInt(rgb[2])));
            return temp;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.fragment_schedule, container, false);
        classColors=(ListView)v.findViewById(R.id.classColor);

        ArrayList<String> al = getArguments().getStringArrayList("list");
        for(String s:al){
            Log.i("FragmentSchedule",s);
        }
        processInput(al);
        BaseAdapter ba = new colorListAdapter();
        classColors.setAdapter(ba);



        return v;
    }

    private void processInput(ArrayList<String> al) {
        Log.i("FragmentSchedule","processinput");
        boolean isClass=false;
        int i=0;
        int current=-1;
        for(String s:al){
            if(classes.size()>(COLORS.length+1)){
                break;
            }

            Log.i("FragmentSchedule",s);
            if (s.equals("CLASS")){
                isClass=true;
            } else if(isClass){
                classes.add(s);
                colors.add(i);
                current=i;
                i++;
                isClass=false;
            }else if(!s.startsWith("Block")){
//                try {
                    setColor(s,current);
//                } catch (Exception e) {
//                    Log.i("FragmentSchedule","setColor something wrong");
//                }
            }




        }
    }

    private void setColor(String s, int current) {//throws exception

        if(current==-1){
            Log.wtf("FragmentSchedule","the current is -1");
            return;
        }

        int ind = 0;
        char c='z';
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)==' '){
                break;
            }
            ind++;
        }
        Log.i("Fragment",String.valueOf(ind));
        String[] days = s.substring(0,ind+1).split(",");
        String[] times = s.substring(ind+2).split(" - ");
        for(String tp:times){
            Log.i("Fragment",tp);
        }
        if(times.length!=2){
            Log.wtf("FragmentSchedule","the times length is "+times.length);
            return;
        }

        for (String day :days){
            Log.i("Fragment",day);
            colorAll(times[0].trim(),times[1].trim(),COLORS[current%(COLORS.length)],day);
        }



    }

    private void colorAll(String start, String end, String RGBcolor,String day) {
        Resources r =getResources();
        String name = getActivity().getPackageName();

        ArrayList<String> alltimes = new ArrayList<>();
        int s = findStart(start);
        int e = findEnd(end);

        while(s<=e){
            Log.i("Fragment.colorAll",String.valueOf(s)+" "+String.valueOf(e));
            String ss = String.valueOf(s);
            if(ss.length()==2){
                ss="t0"+ss+getDay(day);
            }else if(ss.length()==3){
                ss="t"+ss+getDay(day);
            }
            int resourceID = r.getIdentifier(ss, "id", name);
            TextView tv = (TextView) v.findViewById(resourceID);
            String[] rgb = RGBcolor.split(" ");
            Log.i("FragmentSchedule"," set ["+ss+ "] R G B : "+rgb[0]+" "+rgb[1]+" "+rgb[2]);
            tv.setBackgroundColor(Color.rgb(Integer.parseInt(rgb[0]),Integer.parseInt(rgb[1]),Integer.parseInt(rgb[2])));
            if(s%10==3){
                s=s+7;
            }else if(s%10==0){
                s=s+3;
            }
        }



    }

    private String getDay(String day) {
        String temp="";
        switch(day.trim()){
            case "M":
                temp="_1";
                break;
            case "W":
                temp="_3";
                break;
            case "T":
                temp="_2";
                break;
            case "Th":
                temp="_4";
                break;
            case "F":
                temp="_5";
                break;
            default:
                Log.wtf("FragmentSchedule","WTF?! The day is "+day);
                break;
        }

        return temp;



    }


    private int findEnd(String trim) {
        String[] tempArray = trim.split(" ");
        String ap = tempArray[1];
        String[] ts = tempArray[0].split(":");
        if(ts.length!=2){
            Log.wtf("FragmentSchedule","the ts length is "+ts.length);
            return -1;
        }

        int first = Integer.parseInt(ts[0]);
        int second = Integer.parseInt(ts[1]);

        if(ap.equals("PM")&&first!=12){
            first=first+12;
        }

        if (second==20){
            second=0;
        }else if(second==50){
            second=3;
        }else if(second==30){
            second=0;
        }else if(second==0){
            second=3;
            first--;
        }

        String temp = String.valueOf(first);
        temp=temp+String.valueOf(second);





        Log.i("FragmentSchedule","end time is "+Integer.parseInt(temp));
        return Integer.parseInt(temp);

    }

    private int findStart(String trim) {
        String[] tempArray = trim.split(" ");
        String ap = tempArray[1];
        String[] ts = tempArray[0].split(":");
        if(ts.length!=2){
            Log.wtf("FragmentSchedule","the ts length is "+ts.length);
            return -1;
        }

        int first = Integer.parseInt(ts[0]);
        int second = Integer.parseInt(ts[1]);

        if(ap.equals("PM")&&first!=12){
            first=first+12;
        }

        if (second==20){
            second=3;
        }else if(second==50){
            first++;
            second=0;
        }else if(second==30){
            second=second/10;
        }

        String temp = String.valueOf(first);
        temp=temp+String.valueOf(second);

        Log.i("FragmentSchedule","start time is "+Integer.parseInt(temp));
        return Integer.parseInt(temp);

    }

}
