package gvsu.edu.digitalworkplace.old;

import android.content.Context;
import android.os.Environment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class writexml {

    public void write(Context con, ArrayList<ArrayList<String>> parts){
        try {
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
            for(int i = 0; i < 3; i++) {
                if (i == 2) {
                    parsehtml ph = new parsehtml();
                    ArrayList<String> menu = ph.parseMenu();
                    for(int k = 0; i < menu.size(); k++) {
                        Element entry = doc.createElement("entry");
                        rootElement.appendChild((entry));
                        Element cus;

                        // custom element
                        String s = menu.get(k);
                        s = s.replaceAll(" ", "");
                        String b = "";
                        for (int a = 0; a < s.length(); a++) {
                            if (isXMLIdentifier(s.charAt(a)))
                                b += s.charAt(a);
                        }
                        s = b;

                        if (s.isEmpty() == false)
                            cus = doc.createElement(s);
                        else {
                            cus = doc.createElement("other");
                        }
                        entry.appendChild(cus);

                        // title element for entry
                        Element titleE;
                        titleE = doc.createElement("title");
                        titleE.appendChild(doc.createTextNode(menu.get(k)));
                        cus.appendChild(titleE);
                    }
                } else {
                    ArrayList<String> title = new ArrayList<String>();
                    ArrayList<String> exp = new ArrayList<String>();
                    ArrayList<String> tags = new ArrayList<String>();
                    title = parts.get(count);
                    count++;
                    exp = parts.get(count);
                    count++;
                    tags = parts.get(count);
                    count++;
                    for (int j = 0; j < title.size(); j++) {

                        // entry element
                        Element entry = doc.createElement("entry");
                        rootElement.appendChild((entry));
                        Element cus;

                        // custom element
                        String s = tags.get(j);
                        s = s.replaceAll(" ", "");
                        String b = "";
                        for (int a = 0; a < s.length(); a++) {
                            if (isXMLIdentifier(s.charAt(a)))
                                b += s.charAt(a);
                        }
                        s = b;

                        if (s.isEmpty() == false)
                            cus = doc.createElement(s);
                        else {
                            cus = doc.createElement("other");
                        }
                        entry.appendChild(cus);

                        // title element for entry
                        Element titleE;
                        titleE = doc.createElement("title");
                        titleE.appendChild(doc.createTextNode(title.get(j)));
                        cus.appendChild(titleE);

                        // exp element for entry
                        Element expE = doc.createElement("exp");
                        String str = exp.get(j);
                        int index = str.indexOf("var _gaq");
                        if (index == -1) {
                            expE.appendChild(doc.createTextNode(exp.get(j)));
                        } else {
                            str = str.substring(0, index).trim();
                            expE.appendChild(doc.createTextNode(str));
                        }
                        cus.appendChild(expE);
                    }
                }
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isXMLIdentifier(char c){
        return (((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c == '_') || (c >= '0' && c <= '9') || (c == '-') || (c == '.')) && c != ':');
    }
 }

