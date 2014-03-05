package gvsu.edu.digitalworkplace.view;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import gvsu.edu.digitalworkplace.R;
import gvsu.edu.digitalworkplace.model.*;

/**
 * Created by Adam on 1/23/14.
 */
public class ListViewer extends ListActivity{
    private String[] items;
    private DataManipulator dm;
    private int expandLay;
    private int listLay;
    private ExpandableListView listview;
    private String currentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        Log.w("now", "starting");
        items = null;
        dm = new DataManipulator();
        expandLay = android.R.layout.simple_expandable_list_item_1;
        listLay = android.R.layout.simple_list_item_1;
        listview = (ExpandableListView) findViewById(android.R.id.list);
        createStatusFile();
        updateXML();
        File stat = new File(Environment.getExternalStorageDirectory()+"/dw/Status.txt");
        while(true){
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(stat));
                String line;
                line = br.readLine();
                text.append(line);
                if(line.equals("updated")){
                    Log.w("now","updating");
                    break;
                }
                else{
                    Log.w("now","reading");
                    Thread.sleep(1000);
                }
            }
            catch (Exception e) {
                //You'll need to add proper error handling here
            }
        }
        taskDone();
    }

    public void createStatusFile(){
        try
        {
            File root = new File(Environment.getExternalStorageDirectory(), "dw");
            if (!root.exists()) {
                root.mkdirs();
            }
            File stat = new File(root, "Status.txt");
                FileWriter writer = new FileWriter(stat);
                writer.append("not updated");
                writer.flush();
                writer.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void taskDone(){
        // first display: tag = title
        updateList("title", true);  //use to be nav
        currentTag = "title";
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                dm.stackPush(currentTag);
                String clicked = (String) parent.getItemAtPosition(position);
                String newTag = clicked.replaceAll("\\s+","").toLowerCase();
                if(newTag.length() >= 5){
                    newTag = newTag.substring(0,5);
                }
                updateList(newTag, true);
                currentTag = newTag;

            }
        });
    }

    public void setItems(String[] items){
        this.items = items;
    }

    public String[] getItems(){
        return this.items;
    }

    public SparseArray<Group> getSAItem(){
        SparseArray<Group> sa = new SparseArray<Group>();
        String[] it = getItems();
        for(int i = 0; i < it.length; i++){
            Group g = new Group(it[i]);
            sa.append(i, g);
        }
        return sa;
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
        try{
        setItems(dm.getItemFromXML(this, tag));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateXML(){
        try{
        new DownloadFilesTask(this).execute(this.getApplicationContext());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateList(String tag, Boolean expand){
        getData(tag);
        if (expand){
            listview.setAdapter(new DWExpandableListAdapter(this, getSAItem()));
        }
        else{
            listview.setAdapter(new ArrayAdapter<String>(this, getListLay(), getItems()));
        }
        Log.w("now","updated");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(dm.stackEmpty()){
            super.onBackPressed();
        }
        else{
            String tag = dm.stackPop();
            Boolean expand = false; // again need to figure this out
            updateList(tag, expand);
        }
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


