package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by rozoa on 11/8/2016.
 */

public class DataLoader extends AsyncTask<Object,Void,Void> {

    private final String filename="saves" ;
    private int countLine=0;
    private FileOutputStream outputStream;
    private FileInputStream inputStream;
    private InputStreamReader isr;
    private HashMap<String,ArrayList<String>> datas;
    private Context c;
    private boolean isDone;


    public interface AsyncResponse{
        void processFinish(HashMap<String,ArrayList<String>>  output);
    }

    public AsyncResponse delegate = null;

    public DataLoader(AsyncResponse d,Context context){
        isDone=false;
        c=context;
        this.delegate=d;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        delegate.processFinish(datas);
    }


    @Override
    protected Void doInBackground(Object... params) {
        //Log.i("DataLoader","Files location: "+c.getFilesDir().toString());
        File file = new File(c.getFilesDir(), filename);
        Log.i("DataLoader","file location  "+c.getFilesDir());
        try{

            if(shouldUpdate(file)){
                Log.i("DataLoader","local file not found, start downloading");
                if(file.exists()){
                    file.delete();
                }
                outputStream = c.openFileOutput(filename,Context.MODE_PRIVATE);
                loadAll();
                outputStream.write("END".getBytes());
                Log.i("DataLoader","done downloading");
                outputStream.close();
            }

            inputStream=c.openFileInput(filename);
            isr= new InputStreamReader(inputStream);
            datas=putInMap(isr);

            inputStream.close();
            Log.w("DataLoader","Done Map Building ");

        }catch(Exception e){
            Log.i("DataLoader","file failed");
            e.printStackTrace();
        }

        isDone=true;
        return null;
    }



    private HashMap<String,ArrayList<String>> putInMap(InputStreamReader isr) throws IOException {
        HashMap<String,ArrayList<String>> hm = new HashMap<>();
        BufferedReader br = new BufferedReader(isr);
        String temp;
        String title=null;
        ArrayList<String> tempArray=new ArrayList<>();
        String updateDate = br.readLine();
        int counter=1;
        while((temp=br.readLine())!=null){
            Log.i("DataLoader",temp);
            if(counter%14==1){
                title=temp;
            }else if(counter%14==0){
                Log.i("DataLoader","map: key: "+title);
                if(!temp.equals(".")){
                    tempArray.add(temp);
                }
                hm.put(title,tempArray);
                tempArray.clear();
            }else{
                if(!temp.equals(".")){
                    tempArray.add(temp);
                }
            }
            counter++;
        }
        return hm;
    }


    private boolean shouldUpdate(File file) {
        return !file.exists();


    }


    private void loadAll() throws IOException {

        URL url;
        InputStream is;
        BufferedReader br;
        String line;
        boolean isStart;
        boolean isDone;
        //boolean isHead;
        long startTime = System.currentTimeMillis();
        int number=0;
        int temp=0;
        int counter=0;
        String days="";
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        outputStream.write((dateFormat.format(date)+"\n").getBytes());
        for(int i=1;i<=20;i++) {
            try {
                url = new URL("http://registrar-prod.unet.brandeis.edu/registrar/schedule/search?strm=1171&view=all&status=&time=time&day=mon&day=tues&day=wed&day=thurs&day=fri&start_time=07%3A00%3A00&end_time=22%3A30%3A00&block=&keywords=&order=class&search=Search&subsequent=1&page="+String.valueOf(i));

                //System.out.println("http://registrar-prod.unet.brandeis.edu/registrar/schedule/search?strm=1171&view=all&status=&time=time&day=mon&day=tues&day=wed&day=thurs&day=fri&start_time=07%3A00%3A00&end_time=22%3A30%3A00&block=&keywords=&order=class&search=Search&subsequent=1&page="+String.valueOf(i)+"\n*****");
                is = url.openStream();  // throws an IOException
                br = new BufferedReader(new InputStreamReader(is));
                isStart=false;
                isDone=false;


                while ((line = br.readLine()) != null &&(!isDone)) {
                    counter++;
                    if(!line.isEmpty()) {
                        if ((!isStart) && line.equals("\t/ Wait")) {
                            isStart = true;
                        }//start processing

                        if (isStart) {
                            line = line.trim();
                            //System.out.println(line);
                            if (isDaysString(line)) {
                                temp = counter;
                                days = line;
                            }//get the day

                            if (counter - temp == 4) {
                                printTimes(days, line);
                            }//get time

                            if (line.equals("<div class=\"paging\">")) {// the end
                                isDone = true;
                            } else {

                                if (line.length() > 10 && line.substring(0, 10).equals("Block&nbsp")) {
                                    outputStream.write(("   BLOCK: " + line.substring(11)+"\n").getBytes());
                                    //System.out.println("   BLOCK: " + line.substring(11));
                                    countLine++;
                                    //find out the block
                                } else {
                                    number = isTitle(line, number);
                                    doSpecificSearch(line);
                                }
                            }
                        }
                    }

                }



            } catch (IOException e) {
                i--;
                e.printStackTrace();
            }
        }
        for (int h = 13 - countLine; h > 0; h--) {
            outputStream.write(".\n".getBytes());
        }//

        Log.i("DataLoader","Generating takes "+(System.currentTimeMillis() - startTime)/1000.0+"s");


    }

    private boolean isDaysString(String line){
        String[] ta= line.split(",");
        for(String t:ta){
            if(!(t.equals("M")||t.equals("W")||t.equals("Th")||t.equals("T")||t.equals("F"))) return false;
        }
        return true;

    }

    private void printTimes(String days, String line) {
        try {
            countLine++;
            String[] s = line.split("&ndash;");
            outputStream.write(("   TIMES: " + days + "  ").getBytes());
            //System.out.print("   TIMES: " + days + "  ");
            outputStream.write((s[0] + " - ").getBytes());
            //System.out.print(s[0] + " - ");
            outputStream.write((s[1]+"\n").getBytes());
            //System.out.println(s[1]);
        }catch(Exception e){
            Log.w("DataLoader","printTimes error"+days+" "+line);
            e.printStackTrace();
        }


    }

    private int isTitle(String line,int n) throws IOException {
        if(line.length()>=21) {
            if (line.substring(0,21).equals("<a class=\"def\" name=\"")) {
                //String[] a = line.substring(21,line.length()).split("\"");
                String classNumb = line.split("\"")[3];
                String[] classNumbID = classNumb.split(" ");
                n++;
                if(countLine!=0) {
                    for (int i = 13 - countLine; i > 0; i--) {
                        outputStream.write(".\n".getBytes());
                    }
                }
                countLine=1;
                try {
                    Log.i("DataLoader",(String.valueOf(n)+"\n"));
                    outputStream.write((classNumb+"\n").getBytes());
                    outputStream.write(("   BOOKS: "+"http://www.bkstr.com/webapp/wcs/stores/servlet/booklookServlet?bookstore_id-1=1391&term_id-1=1171&div-1=&dept-1="
                            +classNumbID[0]+"&course-1="+classNumbID[1]+"&sect-1=1\n").getBytes());

                }catch (Exception e){
                    Log.w("DataLoader","isTitle failed"+line+" "+String.valueOf(n));
                    e.printStackTrace();
                }

                //System.out.println(String.valueOf(n));
                //System.out.println("Title:  "+classNumb);
                //System.out.println("   BOOKS: "+"http://www.bkstr.com/webapp/wcs/stores/servlet/booklookServlet?bookstore_id-1=1391&term_id-1=1171&div-1=&dept-1="+classNumbID[0]+"&course-1="+classNumbID[1]+"&sect-1=1");
                //countLine++;
            }
        }

        return n;
    }




    private boolean doSpecificSearch(String line) throws IOException {
        if(line.length()>13){
          if(line.substring(0,7).equals("<a href")){//found the teacher page
                if(line.substring(line.length()-12).equals("Syllabus</a>")){
                    //System.out.println("   SYLLABUS: "+line.split("\"")[1]);
                    outputStream.write(("   SYLLABUS: "+line.split("\"")[1]+"\n").getBytes());
                    countLine++;
                }else{
                    //System.out.println("   TEACHER: "+line.split("\"")[1]);
                    outputStream.write(("   TEACHER: "+line.split("\"")[1]+"\n").getBytes());
                    countLine++;
                }
              return false;
            }
        }else {
            return false;
        }

        if(line.length()>22){
            if(line.substring(0,23).equals("href=\"javascript:popUp(")){//found the description page
                //System.out.println("   DESCRIPTION: "+"http://registrar-prod.unet.brandeis.edu/registrar/schedule/"+line.split("'")[1]);
                outputStream.write(("   DESCRIPTION: "+"http://registrar-prod.unet.brandeis.edu/registrar/schedule/"+line.split("'")[1]+"\n").getBytes());
                countLine++;
                return false;
            }
        }

        if(line.length()>24){
            if(line.substring(0,24).equals("<a target=\"_blank\" href=")){//found the book page
                //System.out.println("   BOOK: "+line.split("'")[1]);
                outputStream.write(("   BOOK: "+line.split("'")[1]+"\n").getBytes());
                countLine++;
                return true;
            }
        }




        //

        return false;
    }








}
