package com.example.adam2392.myfirstapp;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.*;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;


/* Class to control the listing of apps pages within the shell (MainActivity)
 * Layouts:
 *  - activity_list_apps.xml (to display a ListView of different app's icons, descriptions)
 *  - snippet_list_row.xml (to display each row in activity list)
 *
 * Classes:
 *  - ApplicationAdapter is used to get all the apps information
 *
 *  */
public class ListApps extends ListActivity {
    public static String PACKAGE_NAME;  // to keep track of the shell's package name
    public static int progress; //to keep track of the seek bar

    private SeekBar seekBar;
    private TextView showText;
    private TextView showTime;
    private PackageManager packageManager = null;
    private List<ApplicationInfo> appList = null;
    private ApplicationAdapter listadapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_apps);    //set the content view to the xml file in "layout"
        PACKAGE_NAME = getApplicationContext().getPackageName();    //store the local package name

        intializeVariables();   //intialize xml variables for seekBar
        showText.setText("Time: ");    //set seekBar's textVieW
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                Toast.makeText(getApplicationContext(), "Changing time allowed to play!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                showText.setText("Time: ")
                showTime.setText(Integer.toString(progress));
                Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_LONG).show();
            }
        });

        showTime.setText("0");
        //perform an async task to get list of app details... long process
        packageManager = getPackageManager();
        new LoadApplications().execute();
    }

    //private method to help us initialize variables in xml
    private void intializeVariables() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        showText = (TextView) findViewById(R.id.showText);
        showTime = (TextView) findViewById(R.id.showTime);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list_apps, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        boolean result = true;      // default return statement

        switch (item.getItemId()) {
            case R.id.menu_about: { //open up an about menu tab
                displayAboutDiaglog();
                break;
            }
            default: {
                result = super.onOptionsItemSelected(item);
                break;
            }
        }
        return result;
    }

    /* Function to handle what happens when a list item gets clicked */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ApplicationInfo app = appList.get(position);
        Timer timer = new Timer();                          // initialize a timer object

        try {
            // get the package info for that app selected
            Intent intent = packageManager.getLaunchIntentForPackage(app.packageName);
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);         //exclude this from the recent history
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                    //clear this from the top

            if(intent != null) {
                startActivity(intent);      // start the activity that was clicked
                timer.schedule(             //start a schedule
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                Intent intent = packageManager.getLaunchIntentForPackage(PACKAGE_NAME);
                                startActivity(intent);

                                killApp();  // closes the app, and reopens the shell
                            }
                        },
                        progress * 1000            // the amount of time before execution
                );
            }
        } catch (ActivityNotFoundException e) { // when no activity
            Toast.makeText(ListApps.this, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception e) { //other exceptions
            Toast.makeText(ListApps.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /* Function for displaying dialog when user clicks 'about' */
    private void displayAboutDiaglog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.about_title));  //set title
        builder.setMessage(getString(R.string.about_desc)); //set description

        builder.setPositiveButton("Know More", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://westhealth.org"));
                startActivity(browserIntent);
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No Thanks!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    /* Check an app to see if there is a launch intent */
    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    applist.add(info);      //add info to the app list
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return applist; //returns the list of apps and their info
    }

    /* Asynchronous method for loading the applciations */
    private class LoadApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {     //perform task in the background
            //intialize the app list and their corresponding meta data
            appList = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));

            //create a custom list adapter
                /* Constructor
                 * Inputs:
                 *  - context: the context
                 *  - textViewResourceId: the id of a text view
                 *  - appsList: the list of applications with their meta data
                 */
            listadapter = new ApplicationAdapter(ListApps.this,
                    R.layout.snippet_list_row, appList);

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {
            setListAdapter(listadapter);
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(ListApps.this, null,
                    "Loading application info...");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    /* Method to return to shell */
    public void killApp() {
        // get the package info for the shell
        Intent intent = packageManager.getLaunchIntentForPackage(PACKAGE_NAME);

        startActivity(intent);
        finish();
    }
}
