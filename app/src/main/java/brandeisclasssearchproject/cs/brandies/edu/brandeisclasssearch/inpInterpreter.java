package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/*the only purpose of this class is to convert the user's input
  have not implemented yet
  */
public class inpInterpreter {

    ArrayList<String> classInfos;

    public ArrayList<String> getClassInfos() {



        //default for debug only
        classInfos = new ArrayList<>();
        classInfos.add("COSI 131A");
        classInfos.add("Computer Science");
        classInfos.add("/1400");
        classInfos.add("131");
        //default for debug only
        return classInfos;
    }

    public inpInterpreter(String rowInput) {
        this.classInfos = phraseRowInput(rowInput);
    }

    //use this method to phrase the row input
    public static ArrayList<String> phraseRowInput(String input){

        return null;
    }

    //This method is called in phraseRowInout
    //it will convert a user input to a legit class number
    //e.g from "cs 131a" to "COSI 131A"
    private String makeCorrect(String rowInput){

        return rowInput;
    }

    /*The following xml is copied from the class search
    The phraseRowInput have to be able to take in
    for example "COSI 131A"
    and return a list of Strings COSI 131A ,Computer Science, 1400, 131
    a constant time search is preferable
    maybe implemented with a hashMap
    or take advantages of android XmlPullParser and have done the searched on web
    In this way, it's also guaranteed to be undated
    /*
    <option value="100" selected>
              African and Afro-American Studies
            </option>

            <option value="200" >
              American Studies
            </option>

            <option value="300" >
              Anthropology
            </option>

            <option value="400" >
              Arabic Language and Literature
            </option>

            <option value="425" >
              Architectural Studies
            </option>

            <option value="450" >
              Art History
            </option>

            <option value="500" >
              Biochemistry
            </option>

            <option value="510" >
              Biochemistry and Biophysics
            </option>

            <option value="600" >
              Biological Physics
            </option>

            <option value="700" >
              Biology
            </option>

            <option value="850" >
              Biotechnology
            </option>

            <option value="900" >
              Business
            </option>

            <option value="1000" >
              Chemistry
            </option>

            <option value="1100" >
              Chinese
            </option>

            <option value="1200" >
              Classical Studies
            </option>

            <option value="1250" >
              Comparative Humanities
            </option>

            <option value="1300" >
              Comparative Literature and Culture
            </option>

            <option value="1400" >
              Computer Science
            </option>

            <option value="1425" >
              Creative Writing
            </option>

            <option value="1475" >
              Creativity, the Arts, and Social Transformation
            </option>

            <option value="1500" >
              East Asian Studies
            </option>

            <option value="1600" >
              Economics
            </option>

            <option value="1700" >
              Education
            </option>

            <option value="1800" >
              English
            </option>

            <option value="1850" >
              English as a Second Language
            </option>

            <option value="1900" >
              Environmental Studies
            </option>

            <option value="2000" >
              European Cultural Studies
            </option>

            <option value="2050" >
              Experiential Learning
            </option>

            <option value="2100" >
              Film, Television and Interactive Media
            </option>

            <option value="2300" >
              Fine Arts
            </option>

            <option value="8000" >
              First Year Seminars (FYS)
            </option>

            <option value="2400" >
              French and Francophone Studies
            </option>

            <option value="2450" >
              Genetic Counseling
            </option>

            <option value="2500" >
              German Studies
            </option>

            <option value="2525" >
              German, Russian, and Asian Languages and Literature
            </option>

            <option value="2535" >
              Global Studies
            </option>

            <option value="2550" >
              Greek
            </option>

            <option value="2700" >
              Health: Science, Society, and Policy
            </option>

            <option value="2800" >
              Hebrew
            </option>

            <option value="2900" >
              Heller School for Social Policy and Management
            </option>

            <option value="2950" >
              Hindi
            </option>

            <option value="6600" >
              Hispanic Studies
            </option>

            <option value="3000" >
              History
            </option>

            <option value="3100" >
              History of Ideas
            </option>

            <option value="3200" >
              Hornstein Jewish Professional Leadership Program
            </option>

            <option value="3300" >
              Independent Interdisciplinary Major
            </option>

            <option value="3400" >
              International and Global Studies
            </option>

            <option value="3600" >
              International Business School
            </option>

            <option value="3700" >
              Internship
            </option>

            <option value="3900" >
              Islamic and Middle Eastern Studies
            </option>

            <option value="4000" >
              Italian Studies
            </option>

            <option value="4100" >
              Japanese
            </option>

            <option value="4200" >
              Journalism
            </option>

            <option value="4225" >
              Justice Brandeis Semester
            </option>

            <option value="4235" >
              Korean
            </option>

            <option value="4600" >
              Language and Linguistics
            </option>

            <option value="4250" >
              Latin
            </option>

            <option value="4300" >
              Latin American and Latino Studies
            </option>

            <option value="4400" >
              Legal Studies
            </option>

            <option value="4700" >
              Mathematics
            </option>

            <option value="4800" >
              Medieval and Renaissance Studies
            </option>

            <option value="4900" >
              Molecular and Cell Biology
            </option>

            <option value="5000" >
              Music
            </option>

            <option value="5100" >
              Near Eastern and Judaic Studies
            </option>

            <option value="5200" >
              Neuroscience
            </option>

            <option value="5300" >
              Peace, Conflict, and Coexistence Studies
            </option>

            <option value="5400" >
              Philosophy
            </option>

            <option value="5500" >
              Physical Education
            </option>

            <option value="5600" >
              Physics
            </option>

            <option value="5700" >
              Politics
            </option>

            <option value="5750" >
              Portuguese
            </option>

            <option value="5800" >
              Postbaccalaureate Premedical Studies
            </option>

            <option value="5900" >
              Psychology
            </option>

            <option value="5950" >
              Quantitative Biology
            </option>

            <option value="6000" >
              Religious Studies
            </option>

            <option value="6100" >
              Romance Studies
            </option>

            <option value="6300" >
              Russian Studies
            </option>

            <option value="6325" >
              Sculpture
            </option>

            <option value="6350" >
              Sexuality and Queer Studies
            </option>

            <option value="6400" >
              Social Justice and Social Policy
            </option>

            <option value="6500" >
              Sociology
            </option>

            <option value="6550" >
              South Asian Studies
            </option>

            <option value="6625" >
              Spanish Language and Literature
            </option>

            <option value="6675" >
              Studio Art
            </option>

            <option value="6700" >
              Theater Arts
            </option>

            <option value="7050" >
              University Writing (COMP and UWS)
            </option>

            <option value="6900" >
              Women's, Gender, and Sexuality Studies
            </option>

            <option value="7000" >
              Yiddish
            </option>
     */
}
