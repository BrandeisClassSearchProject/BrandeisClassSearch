package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch;

/*
This class takes in a CORRECT Class number
 */

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ExtructionURLs {
    private String bookURL;
    private String teacherURL;
    //private String sourceURL;
    private Boolean isFound;
    private String classDescriptionURL;
    private String schedules;//not implemented yet
    private final String classSearchURL = "http://registrar-prod.unet.brandeis.edu/registrar/schedule/classes";
    private String season; //for example "/Fall"
    private String year; // for example "/2017"
    private String gra="/UGRD";//for example "/UGRD"
    private String subjectID; // for example "/100"
    private String classID;




    //input need to be guaranteed to have form COSI 131A ,Computer Science, 1400, 131
    public ExtructionURLs(ArrayList<String> classInfo, AcademicSeason s, AcademicYear y) {
        this.isFound=false;
        this.classID=classInfo.get(0);
        this.subjectID=classInfo.get(2);
        this.season=s.getSeason();
        this.year=y.getYear();
        try {
            setOutputs(classSearchURL+year+season+subjectID+gra);
        } catch (IOException e) {
            Log.e("ExtructionURLs","ExtructionURLs.setOutputs had an IOException!");
        }

    }

    private void setOutputs(String source) throws IOException {
        Log.i("ExtructionURLs","The url for "+classID+" is:\n"+source);
        URL url=null;
        HttpURLConnection connection=null;
        try {
             url = new URL(source);
            connection = (HttpURLConnection)url.openConnection();

        } catch (MalformedURLException e) {
            Log.e("ExtructionURLs","MalformedURLException: "+source);//record fails
        } catch (IOException e) {
            Log.e("ExtructionURLs","IOException: "+source);//record fails
        }
        if (url!=null && connection!=null){//where shits happen
            long startTime = System.currentTimeMillis();//count time
            if(connection.getResponseCode()!=HttpURLConnection.HTTP_OK){
                Log.e("ExtructionURLs",connection.getResponseMessage()+": with "+source);
            }
            String searchTarget =  "<a class=\"def\" name=\""+classID+"\"";
            Log.i("ExtructionURLs","the search target is:  "+searchTarget);
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            boolean isDone = false;
            while((line=br.readLine())!=null && !isDone){
                line=line.trim();
                if( !line.isEmpty()) {
                        //Log.d("ExtructionURLs","line:   "+line);
                    if(line.equals(searchTarget)){

                        this.isFound=true;
                    }
                    if(this.isFound){
                        isDone=doSpecificSearch(line);
                    }
                }
            }
            if(!this.isFound){
                Log.w("ExtructionURLs","CLASS ID "+classID+" NOT FOUND!");
            }

            Log.i("ExtructionURLs","setOutputs takes "+(System.currentTimeMillis() - startTime)+"ms");//count time
        }else{
            Log.i("ExtructionURLs","url or connection or both is null, caused by previous exceptions");
        }


    }

    private boolean doSpecificSearch(String line) {
        //Log.i("ExtructionURLs","******");//debug
        //Log.d("ExtructionURLs","line:   "+line);//debug
        //Log.i("ExtructionURLs","******");//debug

        if(line.length()>8){
            //Log.d("ExtructionURLs","<a href**vs**"+line.substring(0,7)+"**");//debug
            if(line.substring(0,7).equals("<a href")){//found the teacher page
                this.teacherURL=line.split("\"")[1];
                Log.i("ExtructionURLs","GET TEACHER URLS! : "+this.teacherURL);
                return false;
            }
        }else {
            return false;
        }

        if(line.length()>25){
            //Log.d("ExtructionURLs","a href=\"javascript:popUp(**vs**"+line.substring(0,25)+"**");//debug
            if(line.substring(0,25).equals("a href=\"javascript:popUp(")){//found the teacher page
                this.classDescriptionURL="http://registrar-prod.unet.brandeis.edu/registrar/schedule/"+line.split("'")[1];
                Log.i("ExtructionURLs","GET DESCRIPTION URLS! : "+this.classDescriptionURL);
                return false;
            }
        }

        if(line.length()>24){
            //Log.d("ExtructionURLs","<a target=\"_blank\" href=**vs**"+line.substring(0,24)+"**");//debug
            if(line.substring(0,24).equals("<a target=\"_blank\" href=")){//found the teacher page
                this.bookURL=line.split("'")[1];
                Log.i("ExtructionURLs","GET BOOKS URLS! : "+this.bookURL);
                return true;
            }
        }




        //

        return false;
    }


    public String getClassDescription() {
        return classDescriptionURL;
    }

    public String getBookURL() {
        return bookURL;
    }

    public String getTeacherURL() {
        return teacherURL;
    }


}
