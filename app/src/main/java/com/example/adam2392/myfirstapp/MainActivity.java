package com.example.adam2392.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//This is the main activity class
public class MainActivity extends Activity {

    public final static String EXTRA_MESSAGE = "com.AdamLi.myFirstApp";

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
                openSearch();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Called when user clicks the 'Send' button */
    public void sendMessage(View view) {
        //do something after button clicked
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);

        String message = editText.getText().toString(); //gather the text from the text panel
        intent.putExtra(EXTRA_MESSAGE, message);    //input the message
        startActivity(intent);      //start an instance of the intent
    }

    /* Called when user clicks list apps button */
    public void listApps(View view) {
        Intent intent = new Intent(this, ListApps.class);

        startActivity(intent);
    }

    private void openSearch() {
        Toast.makeText(this, "Search button pressed", Toast.LENGTH_SHORT).show();
    }

    private void openSettings() {

    }
}
