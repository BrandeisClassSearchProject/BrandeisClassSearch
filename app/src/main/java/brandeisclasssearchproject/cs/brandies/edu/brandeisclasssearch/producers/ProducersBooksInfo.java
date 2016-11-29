package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers;

import android.util.Log;

import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ProducersBooksInfo extends ProducersAbstract{
    private String inputURL;
    private ArrayList<String> results;
    private Document document;

    public ProducersBooksInfo(String URL) {
        Name="Books";
        this.inputURL = URL;
        this.results = new ArrayList<>();
        CalcResult();
    }

    public void CalcResult() {
        try {
            Log.i("ProducersBooksInfo",inputURL);

            String allText;
            document = Jsoup.connect(inputURL).timeout(7000).get();

            if(document==null){
                Log.e("ProducersBooksInfo","the document is null!");
            }

            Element error = document.getElementById("efCourseErrorSection");

            if (error == null) {
                Element e = document.getElementById("material-group-name_REQUIRED_1_1");

                String numberOfBooks = e.text();

                if (numberOfBooks.length() == 24)
                    numberOfBooks = numberOfBooks.substring(numberOfBooks.length()-2, numberOfBooks.length()-1);
                else
                    numberOfBooks = numberOfBooks.substring(numberOfBooks.length()-3, numberOfBooks.length()-1);

                int number = Integer.parseInt(numberOfBooks);
                for(int i = 1; i <= number; i++) {
                    String tmpId = "fldset-adoption_1_1_REQUIRED_" + i;
                    Element information = document.getElementById(tmpId);
                    Elements name = information.getElementsByClass("material-group-title");

                    String tmpString = name.text();
                    tmpString = tmpString.substring(0,tmpString.indexOf("Edition"));

                    String urlTmp = information.getElementsByAttribute("src").get(2).toString();
                    urlTmp = urlTmp.substring(0, urlTmp.indexOf("$")+1);
                    urlTmp = urlTmp.replace("<img src=\"", "");

                    results.add(urlTmp);

                    Elements list = information.getElementsByClass("material-group-edition");
                    allText = "";

                    for (org.jsoup.nodes.Element tmpNode : list){
                        allText = allText + tmpNode.text() + "\n";
                    }

                    StringBuilder builder = new StringBuilder(allText);

                    builder.insert(allText.indexOf("Edition:"), "\n");
                    builder.insert(allText.indexOf("ISBN:")+1, "\n");
                    String check = builder.toString();
                    if (check.contains("Copyright Year:")) {
                        builder.insert(allText.indexOf("Copyright Year:") + 2, "\n");
                        builder.insert(allText.indexOf("Publisher:") + 3, "\n");
                    } else {
                        builder.insert(allText.indexOf("Publisher:") + 2, "\n");
                    }
                    builder.insert(0, tmpString + "\n\n");

                    allText = builder.toString();
                    results.add(allText);
                }
            } else {
                allText = error.toString();
                results.add(allText.substring(allText.indexOf("<b>")+4, allText.indexOf("</b>")-1));
            }
        } catch (Exception e) {
            System.err.println("construction failed");
            e.printStackTrace();
        }
    }

    public String getInput() {
        return inputURL;
    }

    public ArrayList<String> getResult() {
        return results;
    }
}