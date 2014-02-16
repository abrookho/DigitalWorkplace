package gvsu.edu.digitalworkplace.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 2/11/14.
 */
public class Group {
    public String string;
    public final List<String> children = new ArrayList<String>();

    public Group(String string) {
        this.string = string;
    }
}
