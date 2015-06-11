package com.example.adam2392.myfirstapp;

/**
 * Created by adam2392 on 6/5/15.
    Custom ListView.
 */

import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/* Create a custom Adapter for a custom ListView */
public class ApplicationAdapter extends ArrayAdapter<ApplicationInfo> {
    private List<ApplicationInfo> appsList = null;
    private Context context;
    private PackageManager packageManager;

    /* Constructor
     * Inputs:
     *  - context: the context
     *  - textViewResourceId: the id of a text view
     *  - appsList: the list of applications with their meta data
     */
    public ApplicationAdapter(Context context, int textViewResourceId,
                              List<ApplicationInfo> appsList) {
        super(context, textViewResourceId, appsList);
        this.context = context;
        this.appsList = appsList;
        packageManager = context.getPackageManager();
    }


    //get the size of the appslist
    @Override
    public int getCount() {
        return ((null != appsList) ? appsList.size() : 0);
    }

    @Override
    public ApplicationInfo getItem(int position) {
        return ((null != appsList) ? appsList.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // get the view and connect snippet with listapps.xml
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (null == view) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.snippet_list_row, null);
        }

        ApplicationInfo data = appsList.get(position);
        if (null != data) {
            //fill in the snippet_list_row.xml's elements
            TextView appName = (TextView) view.findViewById(R.id.app_name); //name
            ImageView iconview = (ImageView) view.findViewById(R.id.app_icon); //the icon

            appName.setText(data.loadLabel(packageManager));
            iconview.setImageDrawable(data.loadIcon(packageManager));
        }
        return view;
    }
};