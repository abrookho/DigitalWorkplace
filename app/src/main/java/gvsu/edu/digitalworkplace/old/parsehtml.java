package gvsu.edu.digitalworkplace.old;

       import android.util.Log;

       import java.io.IOException;
       import java.util.ArrayList;
        import org.jsoup.Jsoup;
        import org.jsoup.nodes.Document;
        import org.jsoup.nodes.Element;

       import gvsu.edu.digitalworkplace.old.writexml;

public class parsehtml {

    Document doc;
    String title;
    Element body;
    String siteBody;
    writexml xw;

    public parsehtml(){
        doc = null;
        title = "";
        body = null;
        siteBody = "";
        xw = new writexml();
    }

    public void setJSOUP(String url) throws IOException{
        try{
        doc = Jsoup.connect(url).get();
        title = doc.title();
        body = doc.body();
        siteBody = body.toString();
        }
        catch(Exception e){
            e.printStackTrace();
            Log.w("url:",url);
        }
    }

    public ArrayList<ArrayList<String>> parseArticle(String url) throws IOException{
        setJSOUP(url);
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        ArrayList<String> parts = new ArrayList<String>();
        ArrayList<String> titles = new ArrayList<String>();
        titles.add("Summary");
        titles.add("Questions");
        titles.add("Answers");
        titles.add("Full Text");
        titles.add("Source");

        for (String s : titles) {
            int i = siteBody.indexOf(s);
            if (i != -1) {
                indexes.add(i);
            }
        }

        titles = new ArrayList<String>();

        for (int i = 0; i < indexes.size() - 1; i++) {
            String s = siteBody.substring(indexes.get(i), indexes.get(i + 1));
            s.substring(s.indexOf(":")+1);
            parts.add(s);
        }
        ArrayList<ArrayList<String>> yes = new ArrayList<ArrayList<String>>();
        parts = format(parts);
         for(int i = 0; i < parts.size(); i++){
            String s = parts.get(i);
            if(s.isEmpty()){
                parts.remove(i);
                i--;
            } else {
                s = s.substring(0,s.indexOf(":")+1);
                titles.add(s);
            }
        }
        yes.add(titles);
        yes.add(parts);
        ArrayList<String> t = new ArrayList<String>();
        t.add(title);
        yes.add(formatTag(t));
        return yes;
    }

    public ArrayList<String> parseMenu() throws IOException{
        setJSOUP("http://www.gvsu.edu/e-hr");
        ArrayList<String> menu = new ArrayList<String>();
        siteBody = siteBody.substring(siteBody.indexOf("navigation-menu"),siteBody.indexOf("clear", siteBody.indexOf("navigation-menu")));
        int count = 0;
        int i = 0;
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        String str = "class=\"navparent\" target=\"\">";
        do {
            int k = siteBody.indexOf(str, i + 1);
            indexes.add(k);
            i = k;
            count++;
        } while (indexes.get(count - 1) != siteBody.lastIndexOf(str));

        for (i = 0; i < indexes.size(); i++) {
                int k = indexes.get(i);
                int m = siteBody.indexOf("</a",k);
                String s = siteBody.substring(k,m);
                s = s.substring(s.indexOf(">")+1);
                menu.add(s);

            }
        return menu;
        }


    public ArrayList<ArrayList<String>> parseNav(String url) throws IOException{
        setJSOUP(url);
        ArrayList<String> links = new ArrayList<String>();
        ArrayList<String> summaries = new ArrayList<String>();
        ArrayList<String> full = new ArrayList<String>();
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        try{
        siteBody = siteBody.substring(siteBody.indexOf("<!--start here-->"),
                siteBody.indexOf("<!--end here-->"));
        int count = 0;
        int i = 0;
        do {
            int k = siteBody.indexOf("<a", i + 1);
            indexes.add(k);
            i = k;
            count++;
        } while (indexes.get(count - 1) != siteBody.lastIndexOf("<a"));

        for (i = 0; i < indexes.size(); i++) {
            if (i + 1 == indexes.size()) {
                full.add(siteBody.substring(indexes.get(i)));
            } else {
                full.add(siteBody.substring(indexes.get(i),
                        indexes.get(i + 1)));
            }
        }

        for (String s : full) {
            i = 0;
            if(s.contains("</strong>"))
                 i = s.indexOf("</strong>");
            else if(s.contains("</h2>"))
                i = s.indexOf("</h2>");
            links.add(s.substring(0, i));
            summaries.add(s.substring(i));
        }
        } catch (Exception e){
            Log.w("site:",siteBody);
            Log.w("URL:", url);
            e.printStackTrace();
        }

        ArrayList<ArrayList<String>> yes = new ArrayList<ArrayList<String>>();
        yes.add(format(links));
        yes.add(format(summaries));
        ArrayList<String> t = new ArrayList<String>();
        t.add(title);
        yes.add(formatTag(t));
        return yes;
    }

    private ArrayList<String> formatTag(ArrayList<String> tag){
        ArrayList<String> ret = new ArrayList<String>();
        for (String s : tag) {

            s = s.replaceAll(" ", "");
            String b = "";
            for (int a = 0; a < s.length(); a++){
                if (isXMLIdentifier(s.charAt(a)))
                    b += s.charAt(a);
            }
            s = b;
            int index = s.indexOf("-");
            if(index != -1){
                s = s.substring(0,index);
            }
            s = s.toLowerCase();
            ret.add(s);
        }
        return ret;
    }

    private boolean isXMLIdentifier(char c){
        return (((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c == '_') || (c >= '0' && c <= '9') || (c == '-') || (c == '.')) && c != ':');
    }

    public ArrayList<String> format(ArrayList<String> strs){
        ArrayList<String> ret = new ArrayList<String>();
        for (String s : strs) {

            s = s.replaceAll("&[^;]*;","").replaceAll("<[^>]*>", "").trim();
            ret.add(s);
        }
        return ret;
    }

    public String downloadLinks() throws IOException{
        setJSOUP("http://gvsu.edu/cms3/assets/2D085406-FC80-AE2E-7233BDF30DCE3642/links.txt");
        String nav = siteBody.substring(siteBody.indexOf("http"),siteBody.indexOf("Art")).trim();
        String art = siteBody.substring(siteBody.indexOf("Article:"),siteBody.indexOf("Separate Answer Sheets:")).trim();
        art = art.substring(art.indexOf("http"));
        return nav + "<->" + art;
    }
}

