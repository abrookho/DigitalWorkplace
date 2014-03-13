package gvsu.edu.digitalworkplace.model;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Adam on 1/23/14.
 */
public class DataManipulator{
    private String[] titles;
    private String[] sum;
    private Stack<String> stack;
    private XmlPullParserFactory pullParserFactory;
    Document doc;
    String title;
    Element body;
    String siteBody;
    public DataManipulator(){
        stack = new Stack<String>();
        sum = null;
    }

    public String[] getItemFromXML(Activity activity, String tag) throws XmlPullParserException, IOException {
        ArrayList<String> XMLTitles = new ArrayList<String>();
        ArrayList<String> XMLSums = new ArrayList<String>();

        pullParserFactory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = pullParserFactory.newPullParser();
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard,"/dw/dwp.xml");
        parser.setInput( new BufferedReader(new FileReader(file)));

        int eventType = parser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if(name.contains(tag)){
                        parser.next();
                        XMLTitles.add(parser.nextText());
                        parser.next();
                        XMLSums.add(parser.nextText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    break;
            }
            eventType = parser.next();
        }

        titles = new String[XMLTitles.size()];
        sum = new String[XMLSums.size()];
        for(int i = 0; i < titles.length; i++){
            titles[i] = XMLTitles.get(i);
            sum[i] = XMLSums.get(i);
        }
        return titles;
    }

    public String[] getTitles(){

        return titles;
    }

    public String[] getSums(){
        return sum;
    }


    public boolean stackEmpty(){
        return stack.empty();
    }

    public String stackPop(){
        return stack.pop();
    }

    public void stackPush(String tag){
        stack.push(tag);
    }



}
