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
        titles = null;
    }

    protected Void doInBackground (String... texts) {

        try{
            File f = new File("R.files.nav");
            parseFile(f,false);
            f = new File("R.files.article");
            parseFile(f,true);
            //write to xml here

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

    private void parseFile(File f, Boolean article){
        try{
        Scanner s = new Scanner(f);
        while(s.hasNext()){
            if(article){
                objs = ph.parseArticle(s.next());
                titles = objs.get(0);
                art = objs.get(1);
            }
            else{
               objs = ph.parseNav(s.next());
               links = objs.get(0);
               summaries = objs.get(1);
            }
        }        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
