package gvsu.edu.digitalworkplace.model;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import gvsu.edu.digitalworkplace.R;

/**
 * Created by Adam on 1/23/14.
 */
public class DataManipulator{
    private String[] titles;
    private Stack<String> stack;
    private parsehtml ph;
    public DataManipulator(){
        titles = null;
        stack = new Stack<String>();
        ph = new parsehtml();
    }

    public String[] getItemFromXML(Activity activity, String tag) throws XmlPullParserException, IOException {
        ArrayList<String> XMLTitles = new ArrayList<String>();
        ArrayList<String> XMLSums = new ArrayList<String>();
        Resources res = activity.getResources();
        XmlResourceParser xpp = res.getXml(R.xml.digitalworkplace);
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT){
            if (eventType == XmlPullParser.START_TAG){
                if (xpp.getName().equals(tag)){
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
                        XMLSums.add(xpp.getText().trim());
                }
            }
            eventType = xpp.next();
        }
        titles = new String[XMLTitles.size()];
        for(int i = 0; i < titles.length; i++){
            titles[i] = XMLTitles.get(i);
        }
        String[] sum = new String[XMLSums.size()];
        for(int i = 0; i < sum.length; i++){
            sum[i] = XMLSums.get(i);
        }

        return titles;
    }

    public String[] getItems(){

        return titles;
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
