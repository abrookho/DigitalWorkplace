package gvsu.edu.digitalworkplace.model;

import android.content.Context;
import android.os.Environment;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.OutputStream;
import java.io.StringWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;

import gvsu.edu.digitalworkplace.R;
import gvsu.edu.digitalworkplace.view.ListViewer;

public class writexml {

    public void write(Context con, ArrayList<String> title, ArrayList<String> exp){
        try {
            //FileOutputStream fileos = con.openFileOutput("R.xml.digitalworkplace", Context.MODE_PRIVATE);
            OutputStream fileos = new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/dwp.xml");
            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag(null, "root");
            for(int i = 0; i < title.size(); i++){
                xmlSerializer.startTag(null,"entry");
                xmlSerializer.startTag(null,title.get(i).replaceAll("\\s+","").toLowerCase());
                xmlSerializer.startTag(null,"title");
                xmlSerializer.text(title.get(i));
                xmlSerializer.endTag(null,"title");
                xmlSerializer.startTag(null,"exp");
                xmlSerializer.text(exp.get(i));
                xmlSerializer.endTag(null,"exp");
                xmlSerializer.endTag(null, title.get(i).replaceAll("\\s+", "").toLowerCase());
                xmlSerializer.endTag(null, "entry");
            }
            xmlSerializer.endTag(null,"root");
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            String dataWrite=writer.toString();
            fileos.write(dataWrite.getBytes());
            fileos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(Context con, ArrayList<String> parts){
        try {
            //FileOutputStream fileos = con.openFileOutput("R.xml.digitalworkplace", Context.MODE_PRIVATE);
            OutputStream fileos = new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/dwp.xml");
            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag(null, "root");
            for(int i = 0; i < parts.size(); i++){
                // write xml for article
            }
            xmlSerializer.endTag(null,"root");
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            String dataWrite=writer.toString();
            fileos.write(dataWrite.getBytes());
            fileos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 }

