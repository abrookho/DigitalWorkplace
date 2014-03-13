package gvsu.edu.digitalworkplace.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;

import android.os.Environment;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import gvsu.edu.digitalworkplace.old.writexml;
import gvsu.edu.digitalworkplace.view.ListViewer;

/**
 * Created by Andrew on 2/4/14.
 */

public class DownloadFilesTask extends AsyncTask<Context,Integer, Void> {
    private Context con;
    private ProgressDialog mProgressDialog;
    Document doc;
    String title;
    Element body;
    String siteBody;

    //private ArrayList<String> questions;  //new
    private writexml wx;
    private ListViewer o;

    public DownloadFilesTask(ListViewer lv){
        mProgressDialog = new ProgressDialog(lv.getApplicationContext());
        mProgressDialog.setMessage("Downloading");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        doc = null;
        title = "";
        body = null;
        siteBody = "";
    }

    protected Void doInBackground (Context... conn) {
        this.con = conn[0];
        try{
         createFile();
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

    public void createFile(){
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard,"/dw/dwp.xml");
        try {
            setJSOUP("http://gvsu.edu/cms3/assets/2D085406-FC80-AE2E-7233BDF30DCE3642/dwp.xml");

            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();

            FileWriter w = new FileWriter(file);
            w.append(siteBody);
            w.flush();
            w.close();

            createStatFile();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setJSOUP(String url) throws IOException {
        try{
            doc = Jsoup.connect(url).get();
            title = doc.title();
            body = doc.body();
            siteBody = body.toString();
        }
        catch(Exception e){
            e.printStackTrace();
            Log.w("url:", url);
        }
    }

    public void createStatFile(){
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
}
