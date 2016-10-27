package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.enums;

/**
 * ENUM of all seasons possible
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

