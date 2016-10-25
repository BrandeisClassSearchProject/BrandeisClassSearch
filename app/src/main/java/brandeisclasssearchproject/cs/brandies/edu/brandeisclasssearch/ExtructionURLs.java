package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch;

/*
This class takes in a CORRECT Class number
 */

import java.util.ArrayList;

public class ExtructionURLs {
    private String bookURL;
    private String teacherURL;
    private String classDescription;
    private final String classSearchURL = "http://registrar-prod.unet.brandeis.edu/registrar/schedule/classes";
    private String season; //for example "/Fall"
    private String year; // for example "/2017"
    private String gra;//for example "/UGRD"
    private String subjectID; // for example "/100"



    //input need to be guaranteed to have form COSI 131A ,Computer Science, 1400, 131
    public ExtructionURLs(inpInterpreter IIP) {
        subjectID=IIP.getClassInfos().get(2);
        this.season="/Fall";
        this.year="/2017";
        this.gra="/UGRD";


    }

    public void setBookURL(String bookURL) {
        this.bookURL = bookURL;
    }

    public void setTeacherURL(String teacherURL) {
        this.teacherURL = teacherURL;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public String getBookURL() {
        return bookURL;
    }

    public String getTeacherURL() {
        return teacherURL;
    }

}
