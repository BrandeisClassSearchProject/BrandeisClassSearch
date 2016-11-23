package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.Producers;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersBasic;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersBooksInfo;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersClassDescription;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersClassSchdule;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersTearcherInfo;

/**
 * list adapter for the list view
 *
 * Need to implement getView methods!
 *
 *
 */

public class InfoListAdapter extends BaseAdapter {

    ArrayList<Producers> producerList;

    public InfoListAdapter(ArrayList<Producers> ap) {
        this.producerList=ap;
    }

    @Override
    public int getCount() {
        return producerList.size();
    }

    @Override
    public Object getItem(int position) {
        return producerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Producers p = producerList.get(position);
        if(p instanceof ProducersClassDescription){
            return getViewDescription(p,parent);
        }else if(p instanceof ProducersBooksInfo){
            return getViewBooks(p,parent);
        }else if(p instanceof ProducersClassSchdule){
            return getViewSchedule(p,parent);
        }else if(p instanceof ProducersTearcherInfo){
            return getViewTeacher(p,parent);
        }else if(p instanceof ProducersBasic){
            return getViewBasic(p,parent);
        }else{
            Log.wtf("InfoListAdapter","WTF??!!");
            return getViewDefault(p,parent);
        }
    }

    private View getViewBasic(Producers p, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View temp = inflater.inflate(R.layout.entry_basic, parent, false);
        TextView year = (TextView) temp.findViewById(R.id.basic_Year);
        year.setText(p.getResult().get(1));
        TextView className = (TextView) temp.findViewById(R.id.basic_Class);
        className.setText(p.getResult().get(0));
        return temp;
    }

    private View getViewTeacher(Producers p, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View temp = inflater.inflate(R.layout.entry_teacher, parent, false);
        //do something!
        if (p.getResult().size()==8) {
            teacherHolder holder = new teacherHolder();
            holder.name = (TextView) temp.findViewById(R.id.Layout_Teacher_name);
            holder.department = (TextView) temp.findViewById(R.id.Layout_Teacher_depts);
            holder.degrees = (TextView) temp.findViewById(R.id.Layout_Teacher_degrees);

            holder.name.setText(p.getResult().get(0));
            holder.department.setText(p.getResult().get(1));
            holder.degrees.setText(p.getResult().get(2));

            temp.setTag(holder);
        }
        else {
            teacherHolder holder = new teacherHolder();
            holder.name = (TextView) temp.findViewById(R.id.Layout_Teacher_name);
            holder.department = (TextView) temp.findViewById(R.id.Layout_Teacher_depts);
            holder.degrees = (TextView) temp.findViewById(R.id.Layout_Teacher_degrees);
            holder.name.setText(p.getResult().get(0));
            holder.department.setText("");
            holder.degrees.setText("");

            temp.setTag(holder);

        }
        return temp;
    }


    private View getViewSchedule(Producers p, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View temp = inflater.inflate(R.layout.entry_schedule, parent, false);
        //do something!
        if (p.getResult().size()>0) {
            defaultHolder holder = new defaultHolder();
            holder.entry = (TextView) temp.findViewById(R.id.Layout_schedule_entryOne);
            holder.entry.setText(p.getResult().get(0));
            temp.setTag(holder);
        }
        return temp;
    }


    private View getViewBooks(Producers p, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View temp = inflater.inflate(R.layout.entry_books, parent, false);

        ArrayList<String> al = p.getResult();
        if (al.size()>0) {
            defaultHolder holder = new defaultHolder();
            holder.entry = (TextView) temp.findViewById(R.id.Layout_Book_entryOne);

            String s = al.get(0);
            if (al.size()>1) {
                s = s + "\n" + "and more...";
            }
            holder.entry.setText(s);
            temp.setTag(holder);
        }
        return temp;
    }

    private View getViewDescription(Producers p,ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View temp = inflater.inflate(R.layout.entry_description, parent, false);
        //do something!
        ArrayList<String> al = p.getResult();
        if (al.size()>0) {
            defaultHolder holder = new defaultHolder();
            holder.entry = (TextView) temp.findViewById(R.id.Layout_Description_entryOne);
            String s = al.get(0).substring(0, 300) + "\nclick for more";
            holder.entry.setText(s);
            temp.setTag(holder);
        }
        return temp;
    }

    private View getViewDefault(Producers p, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View temp = inflater.inflate(R.layout.entry_default, parent, false);
        //do something!
        return temp;
    }

    static class defaultHolder
    {
        TextView entry;
    }

    static class teacherHolder
    {
        TextView name;
        TextView department;
        TextView degrees;
    }

    static class bookHolder
    {
        TextView name;
        TextView department;
        TextView degrees;
        TextView expertise;
        TextView profile;
        TextView courses;
    }

}
