package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.enums.AcademicSeason;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.enums.AcademicYear;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ExtructionURLs;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.Producers;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersBooksInfo;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersClassDescription;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.ProducersTearcherInfo;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers.inpInterpreter;

/**
 * The main runs this task in onQueryTextSubmit
 * This is the process that do all the works
 * pulling information from internet
 * it has to be in a separated thread, android does not allow connection to internet in main thread
 * AsynTash is the thread in Android
 * Android handles the threads pool for us
 *
 * This class will take in
 */

public class ClassSearchTask {
    private ExtructionURLs extractionUrls;
    private ArrayList<String> classInfos;
    private AsyncTask task;
    private Boolean isDone;
    private HashMap<String, ArrayList<String>> datas;
    //private Boolean isDone;//indicates if the process is still running
    private Boolean isFailed;//indicates if the process is done, but failed
    private ArrayList<Producers> producerList;
    private String classId;


    public ClassSearchTask(String s,HashMap<String, ArrayList<String>> data) {
        classId=s;
        datas=data;
        isDone=false;
        classInfos=new inpInterpreter(s).getClassInfos();
    }

    public void execute(){
        task=new searchTask();
        task.execute();
    }

    public Boolean isDone() {
        return this.isDone;
    }

    private class searchTask extends AsyncTask<Object,Void,Void>{



        @Override
        protected void onPostExecute(Void aVoid) {

        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(Object... params) {
            if (classInfos != null) {
                Log.i("ClassSearchTask", "array list classInfos is OK. Initialize extractionURLs");
                //extractionUrls = new ExtructionURLs(classInfos, AcademicSeason.FALL, AcademicYear._2016, datas);
                extractionUrls = new ExtructionURLs(classId,datas);
                producerList = extractionUrls.getProducers();
                if (producerList==null){
                    isDone=true;
                    Log.i("ClassSearchTask", "Class not found");
                    return null;

                }
                Log.i("ClassSearchTask", "found it ");
                for (Producers p : producerList) {
                    ArrayList<String> al = p.getResult();
                    if (p instanceof ProducersTearcherInfo) {
                        for (String s : al) {
                            Log.i("teacher", s);
                        }
                    } else if (p instanceof ProducersBooksInfo) {
                        for (String s : al) {
                            Log.i("books", s);
                        }
                    } else if (p instanceof ProducersClassDescription) {
                        for (String s : al) {
                            Log.i("class description", s);
                        }
                    }

                    //ArrayList<String> ab= new ProducersTearcherInfo(extractionUrls.getTeacherURL()).getResult();
                    //if(ab==null){
                    //    Log.w("Task","the teacher info is null");
                    //}else {
                    //
                    //}
                    //ArrayList<String> a= new ProducersBooksInfo(extractionUrls.getBookURL()).getResult();
                    //for(String s:a){
                    //   Log.i("book info",s);
                    // }
                    //a= new ProducersClassDescription(extractionUrls.getClassDescription()).getResult();
                    //for(String s:a){
                    //   Log.i("class Des",s);
                    //}


                    isDone = true;

                }
            }return null;
        }
}
}
