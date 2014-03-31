package gvsu.edu.digitalworkplace.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;

import android.os.Environment;

import gvsu.edu.digitalworkplace.view.ListViewer;

/**
 * Created by Andrew on 2/4/14.
 */

public class DownloadFilesTask extends AsyncTask<Context,Integer, Void> {
    private Context con;
    private ProgressDialog mProgressDialog;
    private ListViewer o;

    public DownloadFilesTask(ListViewer lv){
        mProgressDialog = new ProgressDialog(lv.getApplicationContext());
        mProgressDialog.setMessage("Downloading");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
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

    public void createFile() {
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "/dw/dwp.xml");
        try {
            URL url = new URL("http://gvsu.edu/cms3/assets/2D085406-FC80-AE2E-7233BDF30DCE3642/dwp.xml");

                // get the content in bytes
                String xmlString = getURLContent("http://gvsu.edu/cms3/assets/2D085406-FC80-AE2E-7233BDF30DCE3642/dwp.xml");
                FileWriter w = new FileWriter(file);
                w.append(xmlString);
                w.flush();
                w.close();

            createStatFile();
        } catch (Exception e) {
            e.printStackTrace();
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

    public String getURLContent(String p_sURL)
    {
        URL oURL;
        URLConnection oConnection;
        BufferedReader oReader;
        String sLine;
        StringBuilder sbResponse;
        String sResponse = null;

        try
        {
            oURL = new URL(p_sURL);
            oConnection = oURL.openConnection();
            oReader = new BufferedReader(new InputStreamReader(oConnection.getInputStream()));
            sbResponse = new StringBuilder();

            while((sLine = oReader.readLine()) != null)
            {
                if(sLine.contains("<exp/>")){
                    sLine.replace("<exp/>","<exp></exp>");
                }
                sbResponse.append(sLine);
            }

            sResponse = sbResponse.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return sResponse;
    }
}
