package gvsu.edu.digitalworkplace.view;

import android.app.ListActivity;
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
    private static String start = "whatisdigitalworkplacedatastoragedatatheftmonitoringemployeeslegalissues";

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
        File stat = new File(Environment.getExternalStorageDirectory()+"/dw/Status.txt");
        if(true){//stat.exists()== false){
            createStatusFile();
            updateXML();
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
                    e.printStackTrace();
                }
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
        updateList(start, true);  //use to be nav
        currentTag = start;
    }

    private boolean isXMLIdentifier(char c){
        return (((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c == '_') || (c >= '0' && c <= '9') || (c == '-') || (c == '.')) && c != ':');
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
        String[] child = dm.getSums();
        for(int i = 0; i < it.length; i++){
            Group g = new Group(it[i],child[i]);
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
            updateList(tag, true);
        }
    }

    public void btnClicked(String clicked){
        dm.stackPush(currentTag);
        String newTag = clicked;
        newTag = newTag.replaceAll(" ", "");
        String s = newTag;
        String b = "";
        for (int a = 0; a < s.length(); a++){
            if (isXMLIdentifier(s.charAt(a)))
                b += s.charAt(a);
        }
        s = b;
        s = s.toLowerCase();
        updateList(s, true);
        currentTag = newTag;
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


