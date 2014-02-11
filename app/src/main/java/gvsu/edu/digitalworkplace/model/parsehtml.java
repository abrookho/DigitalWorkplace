package gvsu.edu.digitalworkplace.model;

       import java.io.IOException;
        import java.util.ArrayList;
        import org.jsoup.Jsoup;
        import org.jsoup.nodes.Document;
        import org.jsoup.nodes.Element;

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
        doc = Jsoup.connect(url).get();
        title = doc.title();
        body = doc.body();
        siteBody = body.toString();
    }

    public ArrayList<ArrayList<String>> parseArticle(String url) throws IOException{
        setJSOUP(url);
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        ArrayList<String> parts = new ArrayList<String>();
        ArrayList<String> titles = new ArrayList<String>();
        titles.add("Summary:");
        titles.add("Questions:");
        titles.add("Answers:");
        titles.add("Full Text:");
        titles.add("Source");

        for (String s : titles) {
            int i = siteBody.indexOf(s);
            if (i != -1) {
                indexes.add(i);
            }
        }

        for (int i = 0; i < indexes.size() - 1; i++) {
            parts.add(siteBody.substring(indexes.get(i), indexes.get(i + 1)));
        }
        ArrayList<ArrayList<String>> yes = new ArrayList<ArrayList<String>>();
        yes.add(titles);
        yes.add(format(parts));
        return yes;
    }


    public ArrayList<ArrayList<String>> parseNav(String url) throws IOException{
        setJSOUP(url);
        ArrayList<String> links = new ArrayList<String>();
        ArrayList<String> summaries = new ArrayList<String>();
        ArrayList<String> full = new ArrayList<String>();
        ArrayList<Integer> indexes = new ArrayList<Integer>();

        siteBody = siteBody.substring(siteBody.indexOf("start here"),
                siteBody.indexOf("<!--end here"));
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
            i = s.indexOf("</strong>");
            links.add(s.substring(0, i));
            summaries.add(s.substring(i));
        }
        ArrayList<ArrayList<String>> yes = new ArrayList<ArrayList<String>>();
        yes.add(format(links));
        yes.add(format(summaries));
        return yes;
    }

    public ArrayList<String> format(ArrayList<String> strs){
        for (String s : strs) {
            s = s.replaceAll("&[^;]*;","").replaceAll("<[^>]*>", "").trim();;
        }
        return strs;
    }
}

