package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers;

/**
 * Created by rozoa on 10/27/2016.
 */

import java.io.IOException;
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
}
