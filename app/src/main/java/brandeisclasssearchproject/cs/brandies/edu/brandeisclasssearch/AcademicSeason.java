package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch;

/**
 * Created by rozoa on 10/25/2016.
 */

public enum AcademicSeason {
    SPRING("/Spring"), SUMMER("/Summer"), FALL("/Fall");

    private String season;

    AcademicSeason(String s) {
        season=s;
    }

    public String getSeason() {
        return season;
    }
}

