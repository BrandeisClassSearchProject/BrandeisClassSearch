package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
        return temp;
    }


    private View getViewSchdule(Producers p, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View temp = inflater.inflate(R.layout.entry_schedule, parent, false);
        //do something!
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
        return temp;
    }

    private View getViewDefault(Producers p, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View temp = inflater.inflate(R.layout.entry_default, parent, false);
        //do something!
        return temp;
    }

}
