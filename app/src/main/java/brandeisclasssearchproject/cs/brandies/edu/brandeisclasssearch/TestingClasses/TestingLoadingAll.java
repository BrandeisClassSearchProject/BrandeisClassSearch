package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.TestingClasses;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by rozoa on 11/6/2016.
 */

public class TestingLoadingAll {




    public static void loadAll(){
        URL url;
        InputStream is;
        BufferedReader br;
        String line;
        boolean isStart;
        boolean isDone;
        long startTime = System.currentTimeMillis();
        int number=0;
        for(int i=1;i<=14;i++) {
            try {
                url = new URL("http://registrar-prod.unet.brandeis.edu/registrar/schedule/search?strm=1171&view=all&status=&time=time&day=mon&day=tues&day=wed&day=thurs&day=fri&start_time=07%3A00%3A00&end_time=22%3A30%3A00&block=&keywords=&order=class&search=Search&subsequent=1&page="+String.valueOf(i));
                System.out.println("*****\nhttp://registrar-prod.unet.brandeis.edu/registrar/schedule/search?strm=1171&view=all&status=&time=time&day=mon&day=tues&day=wed&day=thurs&day=fri&start_time=07%3A00%3A00&end_time=22%3A30%3A00&block=&keywords=&order=class&search=Search&subsequent=1&page="+String.valueOf(i)+"\n*****");
                is = url.openStream();  // throws an IOException
                br = new BufferedReader(new InputStreamReader(is));
                //System.out.println("print web...");
                isStart=false;
                isDone=false;
                int counter=0;
                while ((line = br.readLine()) != null &&(!isDone)) {
                    counter++;


                    if((!isStart)&&line.equals("\t/ Wait")){
                        isStart=true;
                    }
                    if(isStart) {

                        line = line.trim();
                        if(line.equals("<div class=\"paging\">")){
                            isDone=true;
                        }else {
                            number=isTitle(line,number);
                            doSpecificSearch(line);
                        }
                    }

                    // }

                }

            } catch (Exception e) {
                i--;
            }
        }
        long endTime   = System.currentTimeMillis();
        System.out.println("Generating takes "+(endTime - startTime)/1000.0+"s");

    }

    private static int isTitle(String line,int n) {
        if(line.length()>=21) {
            if (line.substring(0,21).equals("<a class=\"def\" name=\"")) {
                //String[] a = line.substring(21,line.length()).split("\"");
                String classNumb = line.split("\"")[3];
                String[] classNumbID = classNumb.split(" ");
                n++;
                System.out.println(String.valueOf(n));
                System.out.println("Title:  "+classNumb);
                System.out.println("   BOOKS: "+"http://www.bkstr.com/webapp/wcs/stores/servlet/booklookServlet?bookstore_id-1=1391&term_id-1=1171&div-1=&dept-1="+classNumbID[0]+"&course-1="+classNumbID[1]+"&sect-1=1");
            }
        }

        return n;
    }


    private static boolean doSpecificSearch(String line) {
        //Log.i("ExtructionURLs","******");//debug
        //Log.d("ExtructionURLs","line:   "+line);//debug
        //Log.i("ExtructionURLs","******");//debug

        if(line.length()>13){
            //line=line.trim();
            //Log.d("ExtructionURLs","<a href**vs**"+line.substring(0,7)+"**");//debug
            if(line.substring(0,7).equals("<a href")){//found the teacher page
                if(line.substring(line.length()-12).equals("Syllabus</a>")){
                    System.out.println("   SYLLABUS: "+line.split("\"")[1]);
                }else{
                    System.out.println("   TEACHER: "+line.split("\"")[1]);
                }


                //Log.i("ExtructionURLs","GET TEACHER URLS! : "+this.teacherURL);
                return false;
            }
        }else {
            return false;
        }

        if(line.length()>22){
            //Log.d("ExtructionURLs","a href=\"javascript:popUp(**vs**"+line.substring(0,25)+"**");//debug
            if(line.substring(0,23).equals("href=\"javascript:popUp(")){//found the description page
                System.out.println("   DESCRIPTION: "+"http://registrar-prod.unet.brandeis.edu/registrar/schedule/"+line.split("'")[1]);
                //Log.i("ExtructionURLs","GET DESCRIPTION URLS! : "+this.classDescriptionURL);
                return false;
            }
        }

        if(line.length()>24){
            //Log.d("ExtructionURLs","<a target=\"_blank\" href=**vs**"+line.substring(0,24)+"**");//debug
            if(line.substring(0,24).equals("<a target=\"_blank\" href=")){//found the book page
                System.out.println("   BOOK: "+line.split("'")[1]);
                //Log.i("ExtructionURLs","GET BOOKS URLS! : "+this.bookURL);
                return true;
            }
        }




        //

        return false;
    }

}
