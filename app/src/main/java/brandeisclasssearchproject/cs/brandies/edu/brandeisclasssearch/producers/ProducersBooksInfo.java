package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ProducersBooksInfo {
    private Document document;
    protected ArrayList<String> Results;
    protected String inputURL;
    org.jsoup.nodes.Element content;
    Elements list;

    public ProducersBooksInfo(String URL) {
        this.inputURL = URL;
        this.Results = new ArrayList<String>();
        CalcResult();
    }

    public ArrayList<String> getResult() {
        return Results;
    }

    public String getInput() {
        return inputURL;
    }

    public void CalcResult() {
        try {
            this.document = Jsoup.connect(inputURL).get();
            String numberOfBooks = document.getElementById("material-group-name_REQUIRED_1_1").text();
            if (numberOfBooks.length() == 24)
                numberOfBooks = numberOfBooks.substring(numberOfBooks.length()-2, numberOfBooks.length()-1);
            else
                numberOfBooks = numberOfBooks.substring(numberOfBooks.length()-3, numberOfBooks.length()-1);
            int number = Integer.parseInt(numberOfBooks);
            for(int i = 1; i <= number; i++){
                String tmpId = "OrderItemAdd_" + i;
                content = document.getElementById(tmpId);
                String allText = content.text();
                StringBuilder builder = new StringBuilder(allText).insert(allText.indexOf("Edition:"), "\n");
                builder.insert(allText.indexOf("ISBN:") + 1, "\n");
                builder.insert(allText.indexOf("Copyright Year:") + 2, "\n");
                builder.insert(allText.indexOf("Publisher:") + 3, "\n");
                builder.insert(allText.lastIndexOf("Type") + 4, "\n");
                builder.insert(allText.length() + 5, "\n");
                allText = builder.toString();
                System.out.println(allText);
            }

            //System.out.println(content.text());
            //System.out.println();
            //System.out.println(content.html());
        } catch (IOException e) {
            System.err.println("construction failed");
            e.printStackTrace();
        }

    }
}
