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

    public DownloadFilesTask(Context con){
        this.con = con;
        mProgressDialog = new ProgressDialog(con);
        mProgressDialog.setMessage("Downloading");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        ph = new parsehtml();
    }

    protected Void doInBackground (String... texts) {
        try{
            //URL url = new URL("http://gvsu.edu/e-hr/recent-case-law-49.htm");
            //URLConnection connection = url.openConnection();
            //connection.connect();
            //InputStream input = new BufferedInputStream(url.openStream());
            //OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/dwp/dwp.xml");
            File f = new File("R.files.nav");
            parseFile(f,false,con);
            f = new File("R.files.article");
            parseFile(f,true,con);
            ph.parseNav("http://gvsu.edu/e-hr/recent-case-law-49.htm",con);

            //byte data[] = new byte[1024];
            //long total = 0;
            //int count;
            //while ((count = input.read(data)) != -1) {
             //   total +=count;
             //   publishProgress((int) (total *100));

             //   output.write(data, 0, count);
            //}

            //output.flush();
            //output.close();
            //input.close();
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

    private void parseFile(File f, Boolean article, Context con){
        try{
        Scanner s = new Scanner(f);
        while(s.hasNext()){
            if(article){
                ph.parseArticle(s.next(),con);
            }
            else{
                ph.parseNav(s.next(),con);
            }
        }        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
