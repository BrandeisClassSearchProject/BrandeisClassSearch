package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers;

/**
 * Created by rozoa on 10/27/2016.
 */

import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ProducersClassSchdule extends ProducersAbstract {
    private Document document;
    protected ArrayList<String> Results;
    protected String inputURL;
    org.jsoup.nodes.Element content;
    Elements list;

    public ProducersClassSchdule(String URL) {
        this.inputURL = URL;
        this.Results = new ArrayList<String>();
        CalcResult();
    }

    public void CalcResult(){
        /**
        try {
         //get the courses
            content = document.getElementById("courses");
            list = content.getElementsByTag("tr");
            String courseString = "";
            for (org.jsoup.nodes.Element tmpNode : list){
                courseString = courseString + tmpNode.text() + "\n";
             }
            Results.add(courseString);
        } catch (IOException e) {
            System.err.println("construction failed");
            e.printStackTrace();
        }
         */
        try {
            //get the schedule
            content = document.getElementById("contentText");
            list = content.getElementsByTag("Table");
            Document doc = Jsoup.connect(inputURL).get();

            for (org.jsoup.nodes.Element table : doc.select("table.classes-list")) {
                for (org.jsoup.nodes.Element row : table.select("tr")) {
                    Elements tds = row.select("td");
                    if (tds.size() > 6) {
                        System.out.println(tds.get(0).text() + ":" + tds.get(1).text());
                    }
                }
            }
        } catch (IOException e) {
            Log.w("ProducersClassSchdule","construction failed");

            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> getResult() {
        return null;
    }

    @Override
    public String getInput() {
        return null;
    }
}
