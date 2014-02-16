package gvsu.edu.digitalworkplace.model;

import android.content.Context;
import android.os.Environment;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;

import gvsu.edu.digitalworkplace.R;
import gvsu.edu.digitalworkplace.view.ListViewer;

public class writexml {

    public void write(Context con, ArrayList<ArrayList<String>> parts){
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File (sdCard.getAbsolutePath() + "/dw");
            dir.mkdirs();
            File file = new File(dir, "dwp.xml");
            FileOutputStream f = new FileOutputStream(file);
            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag(null, "root");
            int count = 0;
            for(int i = 0; i < parts.size()/2; i++){
                ArrayList<String> title = new ArrayList<String>();
                ArrayList<String> exp = new ArrayList<String>();
                title = parts.get(count);
                count++;
                exp = parts.get(count);
                count++;
                for(int j = 0; j > title.size(); j++){
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
            }
            xmlSerializer.endTag(null,"root");
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            String dataWrite=writer.toString();
            f.write(dataWrite.getBytes());
            f.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 }

