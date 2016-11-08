package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers;
//package com.ZexiJin.JsoupGetHTMLTest.Test1;

/**
 * Created by Zexi Jin on 2016/11/6.
 *
 * INCOMPLETE!!!
 */

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ProducersTearcherInfo extends ProducersAbstract {
    private Document document;
    protected ArrayList<String> Results;
    protected String inputURL;
    org.jsoup.nodes.Element content;
    Elements list;

    public ProducersTearcherInfo(String URL) {
        this.inputURL = URL;
        this.Results = new ArrayList<String>();
        CalcResult();
    }

    @Override
    public ArrayList<String> getResult() {
        return Results;
    }

    @Override
    public String getInput() {
        return inputURL;
    }

    public void CalcResult(){
        try {
            this.document = Jsoup.connect(inputURL).get();

            //get the name
            content = document.getElementById("content");
            list = content.getElementsByTag("a");
            org.jsoup.nodes.Element node = list.get(0);
            Results.add(node.text() + "\n");

            //get the depts
            content = document.getElementById("depts");
            list = content.getElementsByTag("a");
            for (org.jsoup.nodes.Element tmpNode : list)
                Results.add(tmpNode.text() + "\n");

            //get the degrees
            content = document.getElementById("degrees");
            Results.add(content.text().substring(8) + "\n");

            //get the expertise
            content = document.getElementById("expertise");
            Results.add(content.text().substring(10) + "\n");

            //get the profile
            content = document.getElementById("profile");
            Results.add(content.text().substring(8) + "\n");

            //get the courses
            content = document.getElementById("courses");
            list = content.getElementsByTag("tr");
            String courseString = "";
            for (org.jsoup.nodes.Element tmpNode : list){
                courseString = courseString + tmpNode.text() + "\n";
            }
            Results.add(courseString);

            //get the awards
            content = document.getElementById("awards");
            list = content.getElementsByTag("p");
            String awardString = "";
            for (org.jsoup.nodes.Element tmpNode : list){
                awardString = awardString + tmpNode.text() + "\n";
            }
            Results.add(awardString.substring(18));

            //get the scholarships
            content = document.getElementById("scholarship");
            list = content.getElementsByTag("p");
            String scholarString = "";
            for (org.jsoup.nodes.Element tmpNode : list){
                scholarString = scholarString + tmpNode.text() + "\n";
            }
            Results.add(scholarString.substring(15));
        } catch (IOException e) {
            Log.w("ProducersTearcherInfo","construction failed");
            e.printStackTrace();
        }
    }
}
