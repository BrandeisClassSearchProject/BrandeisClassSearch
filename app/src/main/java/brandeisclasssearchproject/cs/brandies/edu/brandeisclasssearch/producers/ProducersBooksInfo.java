package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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


            //InputStream is = new URL(inputURL).openStream();

            //document = Jsoup.parse(is,"UTF-8",inputURL);

            //document = Jsoup.parse(getConnection());
            //Log.i("ProducersBooksInfo",String.valueOf(document.childNodeSize()+" "+document));
            document = Jsoup.connect(inputURL).timeout(7000).get();

            if(document==null){
                Log.e("ProducersBooksInfo","the document is null!");
            }else{
                Log.i("ProducersBooksInfo","the document is not null");
            }

            Element error = document.getElementById("efCourseErrorSection");
            Log.i("ProducersBooksInfo","error done");
            if (error == null) {
                Log.i("ProducersBooksInfo","the error is not null");
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
                Log.i("ProducersBooksInfo","the error is null");
                allText = error.toString();
                results.add(allText.substring(allText.indexOf("<b>")+4, allText.indexOf("</b>")-1));
            }
        } catch (Exception e) {
            System.err.println("construction failed");
            e.printStackTrace();
        }
    }

    private String getConnection() {
        URL url;
        try {
            url = new URL(inputURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //conn.setRequestProperty ("Authorization", encodedCredentials);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));

            String s="";
            StringBuilder sb = new StringBuilder();
            while((s=in.readLine())!=null){
                sb.append(s);
                Log.i("ProducersBooks",s);
            }

            return sb.toString();



        } catch (Exception e) {
            Log.i("ProducersBooks","exception in getConnection");
            e.printStackTrace();
        }


        Log.i("ProducersBooks","Nothing....");
        return "";

    }

    public String getInput() {
        return inputURL;
    }

    public ArrayList<String> getResult() {
        return results;
    }
}