package gvsu.edu.digitalworkplace.model;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import gvsu.edu.digitalworkplace.R;

/**
 * Created by Adam on 1/23/14.
 */
public class DataManipulator{
    private String[] items;
    public DataManipulator(){
        items = null;
    }

    public String[] getItemFromXML(Activity activity, int fileName, String tag) throws XmlPullParserException, IOException {
        ArrayList<String> XMLitems = new ArrayList<String>();
        Resources res = activity.getResources();
        XmlResourceParser xpp = res.getXml(fileName);
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT){
            if (eventType == XmlPullParser.START_TAG){
                if (xpp.getName().equals(tag)){
                    XMLitems.add(xpp.getAttributeValue(null, "ItemNumber").toString());
                }
            }
            eventType = xpp.next();
        }
        items = new String[XMLitems.size()];
        for(int i = 0; i < items.length; i++){
            items[i] = XMLitems.get(i);
        }
        return items;
    }

    public String[] getItems(){

        return items;
    }

    public void updateXML(){
        // get code from Mike to update XM
    }

}
