package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers;
//package com.ZexiJin.JsoupGetHTMLTest.Test1;

        import android.util.Log;

        import java.io.IOException;
        import java.util.ArrayList;
        import org.jsoup.Jsoup;
        import org.jsoup.nodes.Document;
        import org.jsoup.select.Elements;

public class ProducersClassDescription extends ProducersAbstract{
    private Document document;
    protected ArrayList<String> Results;
    protected String inputURL;
    org.jsoup.nodes.Element content;
    Elements list;

    public ProducersClassDescription(String URL) {
        Name = "Description";
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
            content = document.getElementById("coursepage");
            String tmpString = content.html();
            tmpString = tmpString.replaceAll("<h2>", "");
            tmpString = tmpString.replaceAll("</h2> \\[", "\n");
            tmpString = tmpString.replaceAll("<span class=\"requirement\" title=", "");
            tmpString = tmpString.replaceAll(" </span> ", "");
            tmpString = tmpString.replaceAll("\\]", "");
            tmpString = tmpString.replaceAll("<p>", "\n");
            tmpString = tmpString.replaceAll("</p>", "");
            tmpString = tmpString.replaceAll("<br>", "\n");
            tmpString = tmpString.replaceAll(">", "");
            Results.add(tmpString);
        } catch (IOException e) {
            Log.w("ProducersClassDes","construction failed");
            e.printStackTrace();
        }
    }
}
