package com.example.hanan.usrealestate;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.ViewGroup;
import android.graphics.Color;
import android.widget.AdapterView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

 import android.content.Context;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final Spinner spinner = (Spinner) findViewById(R.id.stateField);

        String[] usStates = new String[]{
                "State", "CA", "NY", "AL", "AK", "AZ", "AR", "CO", "CT", "DE", "FL",
                "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME",
                "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH",
                "NJ", "NM", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
                "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY",
        };

        final List<String> usStatesList = new ArrayList<>(Arrays.asList(usStates));

        // Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, usStatesList) {

            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button clearBtn = (Button) findViewById(R.id.clear);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView addressField = (TextView) findViewById(R.id.addressField);
                TextView cityField = (TextView) findViewById(R.id.cityField);
                Spinner stateField = (Spinner) findViewById(R.id.stateField);
                addressField.setText("");
                cityField.setText("");
                stateField.setSelection(0);
            }
        });

        /*Button searchBtn = (Button)findViewById(R.id.search);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HTTPRequestClass httpRequest = new HTTPRequestClass();
                httpRequest.execute();
                Intent intent = new Intent(MainActivity.this, Main3Activity.class);

                startActivity(intent);
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToActivity2(View view) {
        TextView addressField = (TextView) findViewById(R.id.addressField);
        TextView cityField = (TextView) findViewById(R.id.cityField);
        Spinner stateField = (Spinner) findViewById(R.id.stateField);
        String[] arr = {addressField.getText().toString(), cityField.getText().toString(), stateField.getSelectedItem().toString()};

        //  Intent intent = new Intent(MainActivity.this, Main3Activity.class);
        //  startActivity(intent);
        HTTPRequestClass httpRequest = new HTTPRequestClass();
        httpRequest.execute(arr);
    }


    class HTTPRequestClass extends AsyncTask<String, Void, String> {
        //private final Context context;

       /* public HTTPRequestClass() {
            this.context = context;
        } */

        @Override
        protected String doInBackground(String... params) {
            try {
                String addressInput, cityInput, stateInput;
                addressInput = params[0];
                cityInput = params[1];
                stateInput = params[2];

                addressInput = addressInput.replaceAll(" ", "+");
                cityInput = cityInput.replaceAll(" ", "+");

                URL url = new URL("http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id=X1-ZWz1g43lmml3wr_2hwdv&address="+addressInput+"&citystatezip="+cityInput+"+"+stateInput);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();



                // read result
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null) {

                    sb.append(line);
                    break;
                }
                br.close();
                // Log.d("RESUUUULT",sb.toString());
                return sb.toString();
            } //end try
            catch (Exception e) {
                System.out.println(e);
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {

                try {

                    JSONObject jsonObj = null;
                    jsonObj = XML.toJSONObject(result);
                    Log.d("JJJJJJJSSSSSSSOOOOONNNNN", jsonObj.toString());
                    Log.d("MMY ADDRESSS", jsonObj.getJSONObject("SearchResults:searchresults").getJSONObject("request").getString("address"));
                    String MyString1 = jsonObj.getJSONObject("SearchResults:searchresults").getJSONObject("request").getString("address");
                    String MyString2 = jsonObj.getJSONObject("SearchResults:searchresults").getJSONObject("request").getString("citystatezip");
                    String MyString = MyString1+", "+MyString2;
                    String ZPID = jsonObj.getJSONObject("SearchResults:searchresults").getJSONObject("response").getJSONObject("results").getJSONObject("result").getString("zpid");
                    //Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                    //   startActivity(intent);

                    Intent i1 = new Intent(MainActivity.this, Main3Activity.class);
                    i1.putExtra("MyData", MyString);
                    i1.putExtra("MyID",ZPID);
                    startActivity(i1);

                    // Tab1fragment mm = new Tab1fragment();
                    // mm.MYMETHOD("hhhhh");
                    // JSONArray jsonArray = new JSONArray(result);
               /* String aa, bb, cc;
               for (int i=0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    aa = jsonObj.getString("address");
                   Log.d("WWWWWwwwwwwwwwwwwwWW",aa);
                } */
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}