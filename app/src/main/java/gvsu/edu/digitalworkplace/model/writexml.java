package gvsu.edu.digitalworkplace.model;

import android.content.Context;
import android.os.Environment;
import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import gvsu.edu.digitalworkplace.R;
import gvsu.edu.digitalworkplace.view.ListViewer;

public class writexml {

    public void write(Context con, ArrayList<ArrayList<String>> parts){
        try {
          /**  File sdCard = Environment.getExternalStorageDirectory();
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
                //ArrayList<String> quest = new ArrayList<String>();
                title = parts.get(count);
                count++;
                exp = parts.get(count);
                count++;
                //quest = parts.get(count);
                //count++;
                for(int j = 0; j < title.size(); j++){  //was >
                    xmlSerializer.startTag("entry");
                    xmlSerializer.startTag(null,title.get(i).replaceAll("\\s+","").toLowerCase());
                    xmlSerializer.startTag(null,"title");
                    xmlSerializer.text(title.get(i));
                    xmlSerializer.endTag(null,"title");
                    xmlSerializer.startTag(null,"exp");
                    xmlSerializer.text(exp.get(i));
                    //xmlSerializer.startTag(null,"questions");
                    //xmlSerializer.text(quest.get(i));
                    //xmlSerializer.endTag(null,"questions");
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
            f.close(); */


            // i am going to try a different way to write the file
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // create root
            Element rootElement = doc.createElement("root");
            doc.appendChild(rootElement);

            // create elements

            // count of how many arraylists we have pulled from parts
            int count = 0;

            // transverse parts and get arraylists
            for(int i = 0; i < parts.size()/2; i++){
                ArrayList<String> title = new ArrayList<String>();
                ArrayList<String> exp = new ArrayList<String>();
                title = parts.get(count);
                count++;
                exp = parts.get(count);
                count++;
                for(int j = 0; j < title.size(); j++){

                    // entry element
                    Element entry = doc.createElement("entry");
                    rootElement.appendChild((entry));

                    // custom element
                    String s = title.get(j);
                    s = s.replaceAll(" ", "");
                    s = s.toLowerCase();
                    s = s.replaceAll(":","");
                    Element cus = doc.createElement(s);
                        entry.appendChild(cus);

                        // title element for entry
                        Element titleE = doc.createElement("title");
                        titleE.appendChild(doc.createTextNode(title.get(j)));
                        cus.appendChild(titleE);

                        // exp element for entry
                        Element expE = doc.createElement("exp");
                        expE.appendChild(doc.createTextNode(exp.get(j)));
                        cus.appendChild(expE);
                }

                // write the content to an xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);

                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File (sdCard.getAbsolutePath() + "/dw");
                dir.mkdirs();
                File file = new File(dir, "dwp.xml");
                StreamResult result = new StreamResult(file);

                transformer.transform(source,result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 }

