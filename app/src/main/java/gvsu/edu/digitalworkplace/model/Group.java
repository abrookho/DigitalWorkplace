package gvsu.edu.digitalworkplace.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 2/11/14.
 */
public class Group {
    public String title;
    public String child;

    public Group(String t, String c) {
        this.title = t;
        this.child = c;
    }

    public String getTitle(){
        return this.title;
    }

    public String getChild(){
        return this.child;
    }
}
