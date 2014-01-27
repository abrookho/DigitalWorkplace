package gvsu.edu.digitalworkplace.view;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import gvsu.edu.digitalworkplace.R;
import gvsu.edu.digitalworkplace.model.DataManipulator;

/**
 * Created by Adam on 1/23/14.
 */
public class ListViewer extends ListActivity{
    private String[] items;
    private DataManipulator dm;
    private int expandLay;
    private int listLay;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lv);

        items = null;
        dm = new DataManipulator();
        expandLay = android.R.layout.simple_expandable_list_item_1;
        listLay = android.R.layout.simple_list_item_1;
        listview = (ListView) findViewById(R.id.listview);

        // update XML
        updateXML();

        // first display: tag = title
        updateList("title", false);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                String clicked = (String) parent.getItemAtPosition(position);
                String newTag = clicked.replaceAll("\\s+","").toLowerCase();
                if(newTag.length() >= 5){
                    newTag = newTag.substring(0,5);
                }
                Boolean expand = false;
                if(false){
                    // have to figure out a way when to have an expanded view
                    expand = true;
                }
                updateList(newTag, expand);

            }
        });
    }

    public void setItems(String[] items){
        this.items = items;
    }

    public String[] getItems(){
        return this.items;
    }

    public void setExpandLay(int i){
        expandLay = i;
    }

    public int getExpandLay(){
        return expandLay;
    }

    public void setListLay(int i){
        listLay = i;
    }

    public int getListLay(){
        return listLay;
    }

    public void getData(String tag){
        //pick file based off tag
        int f = R.xml.digitalworkplace;
        try{
        setItems(dm.getItemFromXML(this, f, tag));
        } catch (Exception e){
            // do something
        }
    }

    public void updateXML(){
        dm.updateXML();
    }

    public void updateList(String tag, Boolean expand){
        getData(tag);
        if (expand){
            listview.setAdapter(new ArrayAdapter<String>(this, getExpandLay(), getItems()));
        }
        else{
            listview.setAdapter(new ArrayAdapter<String>(this, getListLay(), getItems()));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}


