package gvsu.edu.digitalworkplace;

import android.app.Activity;
import android.app.ListActivity;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends ListActivity {

    String item;
    String items[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toast.makeText(this, "Testing", Toast.LENGTH_LONG).show();
        try {
            items = getItemFromXML(this);
        }catch (XmlPullParserException e){
            Toast.makeText(this, (CharSequence) e, Toast.LENGTH_LONG).show();
        }catch (IOException e){
            Toast.makeText(this, (CharSequence) e, Toast.LENGTH_LONG).show();
        }
        //items.toString();
        //String[] items;

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public String[] getItemFromXML(Activity activity) throws XmlPullParserException, IOException {
        StringBuffer stringBuffer = new StringBuffer();
        Resources res = activity.getResources();
        String[] items2;
        items2 = new String[2];
        Integer index;
        index = 0;
        XmlResourceParser xpp = res.getXml(R.xml.digitalworkplace);
        //xpp.next();
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT){
            if (eventType == XmlPullParser.START_TAG){
                if (xpp.getName().equals("title")){
                    //stringBuffer.append(xpp.getAttributeValue(null, "ItemNumber") + "\n");
                    items2[index] = xpp.getAttributeValue(null, "ItemNumber").toString();
                    //items2[index] = xpp.getText();
                    //items2[index]="test";
                    index = index+1;
                }
            }
            eventType = xpp.next();
        }
        return items2;
    }

}
