package gvsu.edu.digitalworkplace.model;

/**
 * Created by Andrew on 2/11/14.
 */
import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import gvsu.edu.digitalworkplace.view.ListViewer;

public class DWExpandableListAdapter extends BaseExpandableListAdapter {

    private final SparseArray<Group> groups;
    public LayoutInflater inflater;
    public ListViewer activity;

    public DWExpandableListAdapter(ListViewer act, SparseArray<Group> groups) {
        activity = act;
        this.groups = groups;
        inflater = act.getLayoutInflater();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Group g = groups.get(groupPosition);
        return g.getChild();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String children = (String) getChild(groupPosition, childPosition);
        TextView text = null;
        if (convertView == null) {
            convertView = inflater.inflate(gvsu.edu.digitalworkplace.R.layout.listrow_details, null);
        }
        text = (TextView) convertView.findViewById(gvsu.edu.digitalworkplace.R.id.textView1);
        text.setText(children);
        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, children,
                        Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Group g = groups.get(groupPosition);
        if (g.getChild().equals("")){
            return 0;
        } else {
            return 1;
        }
    }
    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(gvsu.edu.digitalworkplace.R.layout.listrow_group, null);
        }
        Group group = (Group) getGroup(groupPosition);
        ((CheckedTextView) convertView).setText(group.title);
        final String t = group.title;
        ((CheckedTextView) convertView).setChecked(isExpanded);
        ((ImageButton) convertView).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               activity.btnClicked(t);
            }
        });

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}