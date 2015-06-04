package adamli.omgandroid;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    TextView mainTextView;
    Button mainButton;
    EditText mainEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        //1. Access textview defined in layout XML
        // and then alter its text
//        mainTextView = (TextView) findViewById(R.id.main_textview);
        mainTextView.setText("Set in Java!!");

        //2. Access the button defined in layout xml
        // and then listen for it here
//        mainButton = (Button) findViewById(R.id.main_button);
        mainButton.setOnClickListener(this);    //MainActivity is the intended listener

        //3. Access the edittext defined in layout xml
//        mainEditText = (EditText) findViewById(R.id.main_edittext);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
//        mainTextView.setText("Button Pressed!!!!!! by Adam");
        mainTextView.setText(mainEditText.getText().toString() + " is learning Android" +
                "development!");
    }
}
