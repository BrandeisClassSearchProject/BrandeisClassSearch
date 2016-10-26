package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch;

/**
 * Created by rozoa on 10/25/2016.
 */

public enum AcademicYear {
    _2017("/2017"),
    _2016("/2016"),
    _2015("/2015"),
    _2014("/2014"),
    _2013("/2013"); //ONLY supports year after 2013 to 2017

    private String year;

    AcademicYear(String y){
        year=y;
    }

    public String getYear() {
        return year;
    }
}
