package com.example.webscraper;

import java.io.IOException;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

class ScrapeHyperlinks {

    public static void main(String[] args) {
        try {
            // fetch the document over HTTP
            Document doc = Jsoup.connect("https://www.imra.ie/runners/").get();

            // selecting all <div> HTML elements
            Elements divs = doc.getElementsByTag("table");
            // getting the ".quote" HTML element
            Elements quotes = doc.getElementsByClass("listing table");

            Elements runners = doc.getElementsByTag("tr");
            for ( Element runner : runners ) {
                Elements link = runner.select("a[href]");
                String linkString = link.attr("href");
                String webId = linkString.replace("/runners/view/id/","");

                // name from link element
                String name = link.text().toString();
                Elements r_info = runner.getAllElements();
                Elements category = r_info.get(3).getElementsByTag("td");
                Elements raceNo = r_info.get(4).getElementsByTag("td");
                Elements noOfRaces = null;
                try {
                    // Column 5 does not exist in header row
                    if (r_info.get(5) != null) {
                        noOfRaces = r_info.get(5).getElementsByTag("td");
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("webId: " + webId +name + raceNo.text() + category.text());
//                System.out.println("name: " + name);
//                System.out.println("Race No.: " + raceNo.text());
//                System.out.println("Category: " + category.text());
                if ( noOfRaces != null) {
                    System.out.println("No. Of Races: " + noOfRaces.text());
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}