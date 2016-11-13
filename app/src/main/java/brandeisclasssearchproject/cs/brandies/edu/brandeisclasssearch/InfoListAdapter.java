package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.Producers;
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
            return getViewSchdule(p,parent);
        }else if(p instanceof ProducersTearcherInfo){
            return getViewTeacher(p,parent);
        }else{
            Log.wtf("InfoListAdapter","WTF??!!");
            return getViewDefault(p,parent);
        }
    }

    private View getViewTeacher(Producers p, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View temp = inflater.inflate(R.layout.entry_teacher, parent, false);
        //do something!
        if (p.getResult().size()==8) {
            teacherHolder holder = new teacherHolder();
            holder.name = (TextView) temp.findViewById(R.id.TextView_description_entry);
            holder.department = (TextView) temp.findViewById(R.id.TextView_description_entry);
            holder.degrees = (TextView) temp.findViewById(R.id.TextView_description_entry);
            holder.expertise = (TextView) temp.findViewById(R.id.TextView_description_entry);
            holder.profile = (TextView) temp.findViewById(R.id.TextView_description_entry);
            holder.courses= (TextView) temp.findViewById(R.id.TextView_description_entry);
            holder.awards = (TextView) temp.findViewById(R.id.TextView_description_entry);
            holder.scholarship = (TextView) temp.findViewById(R.id.TextView_description_entry);

            holder.name.setText(p.getResult().get(0));
            holder.department.setText(p.getResult().get(1));
            holder.degrees.setText(p.getResult().get(2));
            holder.expertise.setText(p.getResult().get(3));
            holder.profile.setText(p.getResult().get(4));
            holder.courses.setText(p.getResult().get(5));
            holder.awards.setText(p.getResult().get(6));
            holder.scholarship.setText(p.getResult().get(7));

            temp.setTag(holder);
        }
        return temp;
    }


    private View getViewSchdule(Producers p, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View temp = inflater.inflate(R.layout.entry_schedule, parent, false);
        //do something!
        if (p.getResult().size()>0) {
            defaultHolder holder = new defaultHolder();
            holder.entry = (TextView) temp.findViewById(R.id.TextView_description_entry);
            holder.entry.setText(p.getResult().get(0));
            temp.setTag(holder);
        }
        return temp;
    }


    private View getViewBooks(Producers p, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View temp = inflater.inflate(R.layout.entry_books, parent, false);
        //do something!
        return temp;
    }

    private View getViewDescription(Producers p,ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View temp = inflater.inflate(R.layout.entry_description, parent, false);
        //do something!
        if (p.getResult().size()>0) {
            defaultHolder holder = new defaultHolder();
            holder.entry = (TextView) temp.findViewById(R.id.TextView_description_entry);
            holder.entry.setText(p.getResult().get(0));
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
        TextView expertise;
        TextView profile;
        TextView courses;
        TextView awards;
        TextView scholarship;
    }

}
