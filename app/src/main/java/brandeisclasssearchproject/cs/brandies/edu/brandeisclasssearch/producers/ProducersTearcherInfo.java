/**
 * Created by Zexi Jin on 2016/11/6.
 *
 * INCOMPLETE!!!
 */
package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers;
//package com.ZexiJin.JsoupGetHTMLTest.Test1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ProducersTearcherInfo extends ProducersAbstract {
    private ArrayList<String> scholarships = new ArrayList<String>();
    private ArrayList<String> degrees = new ArrayList<String>();
    private ArrayList<String> courses = new ArrayList<String>();
    private ArrayList<String> awards = new ArrayList<String>();
    private String profile = null;
    private String expertise = null;
    private String depts = null;
    private String deptURL = null;
    private Document document;

    public ProducersTearcherInfo(String url) {
        try {
            document = Jsoup.connect(url).get();
            this.profile = SetUpProfile();
            this.expertise = SetUpExpertise();
            this.depts = SetUpdepts();
            SetUpScholarships();
            SetUpAwards();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.err.println("construction failed");
            e.printStackTrace();
        }
    }

    private String SetUpProfile(){
        org.jsoup.nodes.Element content = getContent("profile");
        String profileTmp = content.text().substring(8);
        return profileTmp;
    }

    private String SetUpExpertise(){
        org.jsoup.nodes.Element content = getContent("expertise");
        String expertiseTmp = content.text().substring(10);
        return expertiseTmp;
    }

    private String SetUpdepts(){
        org.jsoup.nodes.Element content = getContent("depts");
        String deptsTmp = content.text().substring(21);
        return deptsTmp;
    }

    private void SetUpScholarships(){
        org.jsoup.nodes.Element content = getContent("scholarship");
        Elements tmpList = content.getElementsByTag("p");
        for (org.jsoup.nodes.Element nod : tmpList){
            scholarships.add(nod.text());
        }
    }

    private void SetUpAwards(){
        org.jsoup.nodes.Element content = getContent("awards");
        Elements tmpList = content.getElementsByTag("p");
        for (org.jsoup.nodes.Element nod : tmpList){
            awards.add(nod.text());
        }
    }

    private org.jsoup.nodes.Element getContent(String IdName) {
        return document.getElementById(IdName);
    }

    public String getProfile() {
        return profile;
    }

    public String getDeptURL() {
        return deptURL;
    }

    public String getExpertise() {
        return expertise;
    }

    public String getDepts() {
        return depts;
    }

}
