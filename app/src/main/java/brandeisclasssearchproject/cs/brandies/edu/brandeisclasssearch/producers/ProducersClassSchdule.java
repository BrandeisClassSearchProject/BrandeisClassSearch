package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers;

/**
 * Created by rozoa on 10/27/2016.
 *
 * in current state, the result is in a form like:
 * "Block L M,W 3:30 PM–4:50 PM Lown Cntr for JudaicStudies203"
 *
 * and it does not locate the proper course
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


    public ProducersClassSchdule() {
        Name="Schdule";
        this.Results = new ArrayList<String>();
        this.inputURL=null;

    }

    public void add(String s){
        Results.add(s);
    }

    public ProducersClassSchdule(String URL) {
        this.inputURL = URL;
        this.Results = new ArrayList<String>();
        CalcResult();
    }

    public void CalcResult(){
        try {
            //get the schedule
            Document doc = Jsoup.connect(this.inputURL).get();

            for (org.jsoup.nodes.Element table : doc.select("table#classes-list")) {
                for (org.jsoup.nodes.Element row : table.select("tr")) {
                    Elements tds = row.select("td");
                    if (tds.size() > 6) {
                        System.out.println(tds.get(3).text());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("construction failed");
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> getResult() {
        return Results;
    }

    @Override
    public String getInput() {
        return inputURL;
    }
}
