package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

/**
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
    //private Boolean isDone;//indicates if the process is still running
    private Boolean isFailed;//indicates if the process is done, but failed


    public ClassSearchTask(String s) {
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

    private class searchTask extends AsyncTask<Void,Void,Void>{


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
        protected Void doInBackground(Void... params) {//do all URL connections task here!
            if(classInfos!=null){
                Log.i("ClassSearchTask","array list classInfos is OK. Initialize extractionURLs");
                extractionUrls = new ExtructionURLs(classInfos,AcademicSeason.FALL,AcademicYear._2017);
            }else{
                Log.w("ClassSearchTask","array list classInfos is null.");
            }



            isDone=true;
            return null;
        }
    }
}
