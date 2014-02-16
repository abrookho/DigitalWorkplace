package gvsu.edu.digitalworkplace.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
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

import gvsu.edu.digitalworkplace.R;
import gvsu.edu.digitalworkplace.model.parsehtml;

/**
 * Created by Andrew on 2/4/14.
 */

public class DownloadFilesTask extends AsyncTask<String, Integer, Void> {
    private Context con;
    private ProgressDialog mProgressDialog;
    private parsehtml ph;
    private ArrayList<ArrayList<String>> objs;
    private ArrayList<String> links;
    private ArrayList<String> summaries;
    private ArrayList<String> art;
    private ArrayList<String> titles;
    private writexml wx;

    public DownloadFilesTask(Context con){
        this.con = con;
        mProgressDialog = new ProgressDialog(con);
        mProgressDialog.setMessage("Downloading");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        ph = new parsehtml();
        objs = null;
        links = null;
        summaries = null;
        art = null;
        wx = new writexml();
        titles = null;
    }

    protected Void doInBackground (String... texts) {

        try{
            String s =  ph.downloadLinks();
            String str = s.substring(0,s.indexOf("<->"));
            String[] hey = str.split("\n");
            parseFile(hey,false);
            s = s.substring(s.indexOf("<->")+3);
            hey = s.split("\n");
            parseFile(hey,true);
            //write to xml here
            ArrayList<ArrayList<String>> parts = new ArrayList<ArrayList<String>>();
            parts.add(links);
            parts.add(summaries);
            parts.add(titles);
            parts.add(art);
            wx.write(con, parts);

        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress[0]);
        mProgressDialog.setProgress(progress[0]);
    }

    protected void onPostExecute(Void result) {
        mProgressDialog.setProgress(100);
        mProgressDialog.dismiss();
    }

    private void parseFile(String[] str, Boolean article){
        try{
        for(int i = 0; i < str.length; i++){
            if(article){
                objs = ph.parseArticle(str[i]);
                titles = objs.get(0);
                art = objs.get(1);
            }
            else{
               objs = ph.parseNav(str[i]);
               links = objs.get(0);
               summaries = objs.get(1);
            }
        }        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
