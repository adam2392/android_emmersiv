package com.example.adam2392.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

//This is the main activity class
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                openSearch();           //open up searching capabilities if we need
                return true;
            case R.id.action_settings:
                openSettings();         //open up a settings menu
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Called when user clicks list apps button */
    public void listApps(View view) {
        Intent intent = new Intent(this, ListApps.class);   //open up the ListApps class

        startActivity(intent);  //starts that intent
    }

    /* Open up search settings */
    private void openSearch() {
        Toast.makeText(this, "Search button pressed", Toast.LENGTH_SHORT).show();
    }

    /* Open up settings */
    private void openSettings() {
        Toast.makeText(this, "Settings button pressed", Toast.LENGTH_SHORT).show();
    }
}
