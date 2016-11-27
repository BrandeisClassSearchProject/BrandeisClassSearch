package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers;

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
import java.util.HashMap;

import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.enums.AcademicSeason;
import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.enums.AcademicYear;

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
    private ArrayList<Producers>producersList=null;
    private HashMap<String, ArrayList<String>> datas;
    ArrayList<HashMap<String,ArrayList<String>>> datasMap;
    int currentTerm=0;
    final int[] terms=new int[]{1171,1163,1162,1161,1152,1151,1153} ;



    //input need to be guaranteed to have form COSI 131A ,Computer Science, 1400, 131
    public ExtructionURLs(ArrayList<String> classInfo, AcademicSeason s, AcademicYear y,  HashMap<String, ArrayList<String>> d) {
        this.datas=d;

        this.isFound=false;
        this.classID=classInfo.get(0);
        this.subjectID=classInfo.get(2);
        this.season=s.getSeason();
        this.year=y.getYear();

        setOutWithMap(classID);
        //try {
        //    setOutputs(classSearchURL+year+season+subjectID+gra);
        //} catch (IOException e) {
        //    Log.e("ExtructionURLs","ExtructionURLs.setOutputs had an IOException!");
        //}

    }

    public ExtructionURLs(String classID,HashMap<String, ArrayList<String>> d){
        this.datas=d;
        this.isFound=false;
        this.classID=classID;

        setOutWithMap(classID);
    }

    public ExtructionURLs(String id, ArrayList<HashMap<String, ArrayList<String>>> dm) {
        this.datasMap=dm;
        this.isFound=false;
        this.classID=id;

        setOutWithBigMap(classID);

    }

    private String getTerm(int term) {
        String year = String.valueOf((term-1000)/10);
        String semester=null;
        switch (term%10){
            case 1:
                semester="Spring";
                break;
            case 2:
                semester="Summer";
                break;
            case 3:
                semester="Fall";
                break;
            default:
                Log.wtf("ExtructionURLs",String.valueOf(term)+" is Wrong");
                break;
        }
        return "20"+year+" "+semester;
    }

    private void setOutWithBigMap(String subjectID) {
        String term = "";
        Log.i("ExtructionURLs","Search for "+subjectID+"from all years");
        String[] tempS = subjectID.split(" ");
        if(tempS.length!=2){
            isFound=false;
            return;
        }
        String a = tempS[0].trim();
        String b = tempS[1].trim();
        if(b.length()==4){
            b="  "+b;
        }else if(b.length()==3){
            b="   "+b;
        }else if(b.length()==2){
            b="    "+b;
        }
        Log.i("ExtructionURLs","Search for "+a+b+"from all years");
        ArrayList<String> temp=null;
        for(int i=1;i<datasMap.size();i++){
            temp = datasMap.get(i).get(a+b);
            if(temp!=null){
                isFound=true;
                currentTerm=terms[i];
                term=getTerm(currentTerm);
                Log.i("ExtructionURLs","found it "+String.valueOf(terms[i]));
                break;
            }
        }

        if(temp==null){
            isFound=false;
            return;
        }


        producersList = new ArrayList<>();
        producersList.add(new ProducersBasic(term,a+b));
        ProducersClassSchdule timeProducer = new ProducersClassSchdule();
        for(String s : temp){

            String contents=s.substring(14).trim();
            String attr = s.substring(0,13);
            //Log.i("ExtructionURLs",attr);
            switch(attr){
                case "  DESCRIPTION":
                    Log.i("ExtructionURLs","  DESCRIPTION: "+contents);
                    producersList.add(new ProducersClassDescription(contents));
                    break;
                case "      TEACHER":
                    Log.i("ExtructionURLs","      TEACHER: "+contents);
                    producersList.add(new ProducersTearcherInfo(contents));
                    break;
                case "        BOOKS":
                    String template = "http://www.bkstr.com/webapp/wcs/stores/servlet/booklookServlet?bookstore_id-1=1391&term_id-1=1163&div-1=&dept-1=PHYS&course-1=105A&sect-1=1";
                    template=template.replace("1163",String.valueOf(currentTerm));
                    template=template.replace("PHYS",a);
                    template=template.replace("105A",b.trim());
                    Log.i("ExtructionURLs","        BOOKS: "+template);
                    producersList.add(new ProducersBooksInfo(template));
                    break;
                case "        BLOCK":
                    Log.i("ExtructionURLs","       BLOCKS: "+contents);
                    timeProducer.add("Block: "+contents);
                    break;
                case "        TIMES":
                    Log.i("ExtructionURLs","        TIMES: "+contents);
                    timeProducer.add(contents);
                    break;
                default: //Log.i("ExtructionURLs","setOutWithMap String s is"+s);
                    break;

            }
        }
        Producers tempP=producersList.get(1);
        producersList.set(1,timeProducer);
        producersList.add(tempP);





    }


    private void setOutWithMap(String subjectID){
        Log.i("ExtructionURLs","Search for "+subjectID);
        String[] tempS = subjectID.split(" ");
        if(tempS.length!=2){
            isFound=false;
            return;
        }
        String a = tempS[0].trim();
        String b = tempS[1].trim();
        if(b.length()==4){
            b="  "+b;
        }else if(b.length()==3){
            b="   "+b;
        }else if(b.length()==2){
            b="    "+b;
        }
        Log.i("ExtructionURLs","Search for "+a+b);
        ArrayList<String> temp = datas.get(a+b);
        if (temp==null){
            isFound=false;
            return;
        }
        isFound=true;
        producersList = new ArrayList<>();
        producersList.add(new ProducersBasic("2017 Spring",a+b));
        ProducersClassSchdule timeProducer = new ProducersClassSchdule();
        for(String s : temp){

            String contents=s.substring(14).trim();
            String attr = s.substring(0,13);
            //Log.i("ExtructionURLs",attr);
            switch(attr){
                case "  DESCRIPTION":
                    Log.i("ExtructionURLs","  DESCRIPTION: "+contents);
                    producersList.add(new ProducersClassDescription(contents));
                    break;
                case "      TEACHER":
                    Log.i("ExtructionURLs","      TEACHER: "+contents);
                    producersList.add(new ProducersTearcherInfo(contents));
                    break;
                case "        BOOKS":
                    Log.i("ExtructionURLs","        BOOKS: "+contents);
                    producersList.add(new ProducersBooksInfo(contents));
                    break;
                case "        BLOCK":
                    Log.i("ExtructionURLs","       BLOCKS: "+contents);
                    timeProducer.add("Block: "+contents);
                    break;
                case "        TIMES":
                    Log.i("ExtructionURLs","        TIMES: "+contents);
                    timeProducer.add(contents);
                    break;
                default: //Log.i("ExtructionURLs","setOutWithMap String s is"+s);
                    break;

            }
        }
        Producers tempP=producersList.get(1);
        producersList.set(1,timeProducer);
        producersList.add(tempP);


    }

    public ArrayList<Producers> getProducers() {
        if(isFound){
            Log.i("ExtructionURLs",classID+" found return results");
        }else{
            Log.i("ExtructionURLs",classID+" not found, return null");
        }
        return producersList;
    }




//    private void setOutputs(String source) throws IOException {
//        Log.i("ExtructionURLs","The url for "+classID+" is:\n"+source);
//        URL url=null;
//        HttpURLConnection connection=null;
//        try {
//             url = new URL(source);
//            connection = (HttpURLConnection)url.openConnection();
//
//        } catch (MalformedURLException e) {
//            Log.e("ExtructionURLs","MalformedURLException: "+source);//record fails
//        } catch (IOException e) {
//            Log.e("ExtructionURLs","IOException: "+source);//record fails
//        }
//        if (url!=null && connection!=null){//where shits happen
//            long startTime = System.currentTimeMillis();//count time
//            if(connection.getResponseCode()!=HttpURLConnection.HTTP_OK){
//                Log.e("ExtructionURLs",connection.getResponseMessage()+": with "+source);
//            }
//            String searchTarget =  "<a class=\"def\" name=\""+classID+"\"";
//            Log.i("ExtructionURLs","the search target is:  "+searchTarget);
//            InputStream is = connection.getInputStream();
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//            String line;
//            boolean isDone = false;
//            while((line=br.readLine())!=null && !isDone){
//                line=line.trim();
//                if( !line.isEmpty()) {
//                        //Log.d("ExtructionURLs","line:   "+line);
//                    if(line.equals(searchTarget)){
//
//                        this.isFound=true;
//                    }
//                    if(this.isFound){
//                        isDone=doSpecificSearch(line);
//                    }
//                }
//            }
//            if(!this.isFound){
//                Log.w("ExtructionURLs","CLASS ID "+classID+" NOT FOUND!");
//            }
//
//            Log.i("ExtructionURLs","setOutputs takes "+(System.currentTimeMillis() - startTime)+"ms");//count time
//        }else{
//            Log.i("ExtructionURLs","url or connection or both is null, caused by previous exceptions");
//        }
//
//
//    }

//    private boolean doSpecificSearch(String line) {
//        //Log.i("ExtructionURLs","******");//debug
//        //Log.d("ExtructionURLs","line:   "+line);//debug
//        //Log.i("ExtructionURLs","******");//debug
//
//        if(line.length()>8){
//            //Log.d("ExtructionURLs","<a href**vs**"+line.substring(0,7)+"**");//debug
//            if(line.substring(0,7).equals("<a href")){//found the teacher page
//                this.teacherURL=line.split("\"")[1];
//                Log.i("ExtructionURLs","GET TEACHER URLS! : "+this.teacherURL);
//                return false;
//            }
//        }else {
//            return false;
//        }
//
//        if(line.length()>25){
//            //Log.d("ExtructionURLs","a href=\"javascript:popUp(**vs**"+line.substring(0,25)+"**");//debug
//            if(line.substring(0,25).equals("a href=\"javascript:popUp(")){//found the description page
//                this.classDescriptionURL="http://registrar-prod.unet.brandeis.edu/registrar/schedule/"+line.split("'")[1];
//                Log.i("ExtructionURLs","GET DESCRIPTION URLS! : "+this.classDescriptionURL);
//                return false;
//            }
//        }
//
//        if(line.length()>24){
//            //Log.d("ExtructionURLs","<a target=\"_blank\" href=**vs**"+line.substring(0,24)+"**");//debug
//            if(line.substring(0,24).equals("<a target=\"_blank\" href=")){//found the book page
//                this.bookURL=line.split("'")[1];
//                Log.i("ExtructionURLs","GET BOOKS URLS! : "+this.bookURL);
//                return true;
//            }
//        }
//
//
//
//
//        //
//
//        return false;
//    }




}
