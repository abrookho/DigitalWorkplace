package gvsu.edu.digitalworkplace.model;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Environment;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Stack;

import gvsu.edu.digitalworkplace.R;

/**
 * Created by Adam on 1/23/14.
 */
public class DataManipulator{
    private String[] titles;
    private String[] sum;
    private Stack<String> stack;
    private parsehtml ph;
    private XmlPullParserFactory pullParserFactory;
    public DataManipulator(){
        titles = null;
        stack = new Stack<String>();
        ph = new parsehtml();
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
                    if(name.equalsIgnoreCase(tag)){
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

      /* XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        // this needs to load the actual file
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard,"/dw/dwp.xml");
        xpp.setInput( new BufferedReader(new FileReader(file)));
        while (xpp.getEventType() != XmlPullParser.END_DOCUMENT){
            if (xpp.getEventType() == XmlPullParser.START_TAG){
                if (xpp.getName().equals(tag)){
                   *//** while(xpp.getEventType() != XmlPullParser.START_TAG && xpp.getName().equals("title")){
                        xpp.next();
                    }
                    while(xpp.getEventType() != XmlPullParser.TEXT){
                        xpp.next();
                    }

                    XMLTitles.add(xpp.getText().trim());
                    Boolean broken = false;
                    while(xpp.getEventType() != XmlPullParser.TEXT){
                        xpp.next();
                        if(xpp.getEventType() == XmlPullParser.END_TAG && xpp.getName().equals("entry)")){
                            broken = true;
                            break;
                        }
                    }
                    if(!broken)
                        XMLSums.add(xpp.getText().trim()); *//*
                    while(xpp.getEventType() != XmlPullParser.TEXT){
                        xpp.next();
                    }
                    XMLTitles.add(xpp.getText());
                    xpp.next();
                    while(xpp.getEventType() != XmlPullParser.TEXT){
                        xpp.next();
                    }
                    XMLSums.add(xpp.getText());
                }
            } else {
               xpp.next();
            }
        }
        titles = new String[XMLTitles.size()];
        sum = new String[XMLSums.size()];
        for(int i = 0; i < titles.length; i++){
            titles[i] = XMLTitles.get(i);
            sum[i] = XMLSums.get(i);
        }*/
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
