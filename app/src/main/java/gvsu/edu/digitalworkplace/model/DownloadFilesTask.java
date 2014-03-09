package gvsu.edu.digitalworkplace.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import android.content.ContentValues;
import android.os.Environment;
import android.util.Log;

import gvsu.edu.digitalworkplace.R;
import gvsu.edu.digitalworkplace.model.parsehtml;
import gvsu.edu.digitalworkplace.view.ListViewer;

/**
 * Created by Andrew on 2/4/14.
 */

public class DownloadFilesTask extends AsyncTask<Context,Integer, Void> {
    private Context con;
    private ProgressDialog mProgressDialog;
    private parsehtml ph;
    private ArrayList<ArrayList<String>> objs;
    private ArrayList<String> links;
    private ArrayList<String> summaries;
    private ArrayList<String> art;
    private ArrayList<String> titles;
    private ArrayList<String> tags;

    //private ArrayList<String> questions;  //new
    private writexml wx;
    private ListViewer o;

    public DownloadFilesTask(ListViewer lv){
        mProgressDialog = new ProgressDialog(lv.getApplicationContext());
        mProgressDialog.setMessage("Downloading");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        ph = new parsehtml();
        objs = null;
        links = new ArrayList<String>();
        summaries = new ArrayList<String>();
        art = new ArrayList<String>();
        //questions = null;
        wx = new writexml();
        titles = new ArrayList<String>();
        o = lv;
    }

    protected Void doInBackground (Context... conn) {
        this.con = conn[0];
        try{
            String s =  ph.downloadLinks();
            String s2 = s;
            String str = s.substring(0,s.indexOf("<->"));
            String[] hey = str.split(" ");
            parseFile(hey,false);
            s = s2.substring(s2.indexOf("<->")+3);
            hey = s.split(" ");
            parseFile(hey,true);

            //write to xml here
            ArrayList<ArrayList<String>> parts = new ArrayList<ArrayList<String>>();
            parts.add(links);
            parts.add(summaries);
            parts.add(titles);
            parts.add(art);
            parts.add(tags);
            //parts.add(questions);  //new
            Log.w("now","writing");
            wx.write(con, parts);
            updateFile();
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void updateFile(){
        try{
            File stat = new File(Environment.getExternalStorageDirectory()+"/dw/Status.txt");
            PrintWriter writer = new PrintWriter(stat);
            writer.print("");
            writer.close();

            FileWriter w = new FileWriter(stat);
            w.append("updated");
            w.flush();
            w.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress[0]);
        mProgressDialog.setProgress(progress[0]);
    }

    protected void onPostExecute(Void result) {
        mProgressDialog.setProgress(100);
        mProgressDialog.dismiss();
    }

    private void parseFile(String[] str, boolean article){
        try{
        for(int i = 0; i < str.length; i++){
            if(article){
                objs = ph.parseArticle(str[i]);
                for(int j = 0; j < objs.get(0).size(); j++){
                    titles.add(objs.get(0).get(j));
                    art.add(objs.get(1).get(j));
                    tags.add(objs.get(2).get(0));
                }
            }
            else{
               objs = ph.parseNav(str[i]);
                for(int j = 0; j < objs.get(0).size(); j++){
                    links.add(objs.get(0).get(j));
                    summaries.add(objs.get(1).get(j));
                }
            }
        }        } catch(Exception e){
                    e.printStackTrace();
        }
    }
}
