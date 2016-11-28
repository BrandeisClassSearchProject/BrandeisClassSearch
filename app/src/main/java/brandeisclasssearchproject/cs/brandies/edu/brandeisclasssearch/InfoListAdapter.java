package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

    ImageView imageview ;
    Bitmap bitmap;
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
        teacherHolder holder = new teacherHolder();
        holder.name = (TextView) temp.findViewById(R.id.Layout_Teacher_name);
        holder.department = (TextView) temp.findViewById(R.id.Layout_Teacher_depts);
        holder.degrees = (TextView) temp.findViewById(R.id.Layout_Teacher_degrees);
        imageview = (ImageView) temp.findViewById(R.id.Layout_Teacher_Image_Preview);
        if (p.getResult().size() == 9) {
            holder.name.setText("\n" + p.getResult().get(0));
            holder.department.setText("Department: " + p.getResult().get(1));
            holder.degrees.setText("Expertise: " + p.getResult().get(3) + "\nclick for more");
            operationBG task = new operationBG();
            String url;
            if(p.getResult().size() == 9)
                url = p.getResult().get(8);
            else
                url = "";
            task.execute(url);
            holder.picPreview = imageview;
            temp.setTag(holder);
        }
        else {
            if(p != null) {
                holder.name.setText("\n" + p.getResult().get(0) + "\n");
            } else {
                holder.name.setText("\nThe faculty member you requested cannot be found");
            }
            holder.department.setText("Yes, pharah is my daughter.\n");
            holder.degrees.setText("--- Reinhardt");
            temp.setTag(holder);
        }
        return temp;
    }

    private class operationBG extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                return null;
            }
        }
        @Override
        protected void onPostExecute(Void result) {
            imageview.setImageBitmap(bitmap);
            Log.e("MESSAGE", "task finished!");
        }
    }



    private View getViewSchedule(Producers p, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View temp = inflater.inflate(R.layout.entry_schedule, parent, false);
        if (p.getResult().size()>0) {
            String tempS=p.getResult().get(0);
            for(int i=1;i<p.getResult().size();i++){
                tempS=tempS+"\n"+p.getResult().get(i);
            }
            defaultHolder holder = new defaultHolder();
            holder.entry = (TextView) temp.findViewById(R.id.Layout_schedule_entry1);
            holder.entry.setText(tempS);
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
            String s;
            if (al.get(0).length() > 300) {
                s = al.get(0).substring(0, 300);
                int k = s.lastIndexOf(" ");
                s.substring(0, k);
                s = s + "..." + "\n\nclick for more";
            } else {
                s = al.get(0) + "\n\nclick for more";
            }
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
        ImageView picPreview;
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
