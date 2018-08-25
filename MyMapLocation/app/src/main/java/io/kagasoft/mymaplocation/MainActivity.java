package io.kagasoft.mymaplocation;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends LifeCycleLoggingActivity {
/*
***An Activity that uses intent to map the location entered.
***Location can be opened either on google maps or in Browser.
 */
    EditText addr;
    Button addrbtn;
    private  String searchTerm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Method to initialize all the layout components
        initializeView();
    }

/************************* Method to initialize all the layout components ************/
      private void initializeView() {
        addr = (EditText) findViewById(R.id.addr);
        addrbtn = (Button) findViewById(R.id.addrbtn);


          // Create an intent for the map by calling method myMapIntent

    }
/********************************************************************
 * If user selects the button for google search then find the address in Google Maps
 * */
    public void googleMapLocation(View view){
        searchTerm = addr.getText().toString();
        /*
           Check if the user has entered the search term.
           If not entered prompt the user to enter the search term
         */
        Intent mapintent = makeMapIntent(searchTerm);
        if(searchTerm.equals(null)){
            myToast("Please Enter the address to find");
        }

        else {

            //Check to see if maps app exist and able to solve intent
            if(mapintent.resolveActivity(getPackageManager()) != (null)) {
                startActivity(mapintent);
            }
            //There is an issue with opening the maps app
            else{
                myToast("Address cannot be foud by Google Maps. Try using Browser");

            }
        }

    }

    /********************************************************************
     * If user selects the button for Browser search then find the address in the Browser
     * */
    public void browserMapLocation(View view){

        if(searchTerm.equals(null)){
            myToast("Please Enter the address to find");
        }

        else {
                startActivity(makeBrowserIntent(searchTerm));

        }

    }

/*
Factory method that returns the intent for the Browser app
*/
    @NonNull
    private Intent makeBrowserIntent(final String address) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://maps.google.com/?q="
                        + Uri.encode(address)));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       return intent;


    }
    /*
    Factory method that returns the intent for the map app
    */
    @NonNull
    private Intent makeMapIntent(final String address) {
       return new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + Uri.encode(address)));
    }

/*
General method to create a toast
 */
    private void myToast(final String toastText) {
        Toast.makeText(this,toastText, Toast.LENGTH_LONG).show();
    }
}
