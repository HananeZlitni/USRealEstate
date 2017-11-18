package com.example.hanan.usrealestate;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import org.json.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.annotation.SuppressLint;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import android.widget.TableLayout;
import android.widget.TableRow;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import static android.graphics.Color.RED;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.graphics.Color.RED;

public class MainActivity extends AppCompatActivity {

    final String[] usStates = new String[] {
            "State                      *", "CA","NY","AL","AK","AZ","AR","CO","CT","DE","FL",
            "GA","HI","ID","IL","IN","IA","KS","KY","LA","ME",
            "MD","MA","MI","MN","MS","MO","MT","NE","NV","NH",
            "NJ","NM","NC","ND","OH","OK","OR","PA","RI","SC",
            "SD","TN","TX","UT","VT","VA","WA","WV","WI","WY",
    };


    @Override
    protected void onRestart() {

        TextView addressField = (TextView)findViewById(R.id.addressField);
        TextView cityField =(TextView)findViewById(R.id.cityField);
        Spinner stateField = (Spinner)findViewById(R.id.stateField);

        super.onRestart();
        addressField.setText("");
        cityField.setText("");
        stateField.setSelection(0);
        recreate();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences prefs = getSharedPreferences("MyPrefsFile",MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.remove("21822 68th Ave, Bayside, NY");
        prefsEdit.commit();

       getSupportActionBar().setIcon(R.drawable.ic_action_name);




        PhotoView photoView2 = (PhotoView) findViewById(R.id.photo_view3);
        Picasso.with(getBaseContext()).load("https://www.zillowstatic.com/vstatic/64dd1c9/static/logos/Zillowlogo_200x50.gif").fit().into(photoView2);

        //making the text clickable
        TextView textView = (TextView) findViewById(R.id.google3);
        SpannableString ss = new SpannableString("© Zillow, Inc., 2006-2016. \nGoogle Maps. (2017).");
        ClickableSpan span1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                // do some thing
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.zillow.com/"));
                startActivity(browserIntent);
            }
        };
        ss.setSpan(span1, 2, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        ClickableSpan span2 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                // do some thing
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://developers.google.com/maps/"));
                startActivity(browserIntent);
            }
        };
        ss.setSpan(span2, 28, 39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());





        //********** SET TEXT FIELDS ************
        setTextFields();

        //********** SPINNER ************
        final Spinner spinner = (Spinner) findViewById(R.id.stateField);

        final List<String> stateArr = new ArrayList<>(Arrays.asList(usStates));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,stateArr){

            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
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

        //********** CLEAR BUTTON ************
        Button clearBtn = (Button)findViewById(R.id.clear);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView addressField = (TextView)findViewById(R.id.addressField);
                TextView cityField =(TextView)findViewById(R.id.cityField);
                Spinner stateField = (Spinner)findViewById(R.id.stateField);
                addressField.setText("");
                cityField.setText("");
                stateField.setSelection(0);
            }
        });

        //******** ADD FAVORITE PROPERTIES TO TABLE ***********
        int counter = 0;
        int rowID= 100;
        int txtViewID= 200;
        final SharedPreferences sharedPrefs = getSharedPreferences("MyPrefsFile",MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPrefs.getAll();

        //********** SCREEN 1 TABLE ************
        final TableLayout table1 = (TableLayout)findViewById(R.id.table1);
        final HTTPRequestClass httpClass = new HTTPRequestClass();

        for (final Map.Entry<String, ?> entry : allEntries.entrySet()) {
            //TableLayout firstTable = (TableLayout) findViewById(R.id.table1);

            final TableRow tr = new TableRow(this);
            /*if (counter % 2 != 0)
                tr.setBackgroundColor(Color.RED);*/
            tr.setId(Integer.parseInt(entry.getKey()));
            tr.setBackgroundResource(R.drawable.row_border);
            tr.setWeightSum(1);
            tr.setPadding(13,30,13,40);
            final TextView property = new TextView(this);
            //property.setId(Integer.parseInt(entry.getKey()));
            property.setGravity(1);
            property.setTextColor(Color.BLACK);
            property.setTextSize(18);
            property.setHeight(40);
            property.setText((CharSequence) entry.getValue());
            tr.addView(property);
            table1.addView(tr);
            property.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sequence = ((CharSequence) entry.getValue()).toString();
                    String street= sequence.substring(0, sequence.indexOf(","));
                    String city= sequence.substring(sequence.indexOf(",")+2, sequence.lastIndexOf(","));
                    String state= sequence.substring(sequence.lastIndexOf(",")+2);


                    String[] arr = {street,city,state};
                    httpClass.execute(arr);
                }
            });

            tr.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public void onClick(View v) {
                    tr.setOnTouchListener(new SwipeDismissTouchListener(
                            tr,
                            null,
                            new SwipeDismissTouchListener.DismissCallbacks() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    return false;
                                }

                                @Override
                                public boolean canDismiss(Object token) {
                                    return true;
                                }

                                @Override
                                public void onDismiss(View view, Object token) {
                                    deleteAlert("Are You Sure You Want to Delete this Property?");
                                }

                                @Override
                                public boolean performClick() {
                                    return true;
                                }

                                public void deleteAlert(String msg) {
                                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                    alertDialog.setMessage(msg);
                                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                    SharedPreferences.Editor editPref = sharedPrefs.edit();
                                                    editPref.remove(tr.getId()+"");
                                                    table1.removeView(tr);
                                                    dialog.dismiss();
                                                    editPref.apply();
                                                    recreate();

                                                }
                                            });
                                    alertDialog.show();
                                }
                            }));
                }
            });
        }
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

    public void setTextFields() {
        TextView addr = (TextView)findViewById(R.id.addressField);
        TextView city = (TextView)findViewById(R.id.cityField);
        Spinner state = (Spinner)findViewById(R.id.stateField);

        //****** HINT 1*****
        String hint1 = "Street Address                            ";
        String asterisk = "*";
        SpannableStringBuilder builder1 = new SpannableStringBuilder();
        builder1.append(hint1);
        int start = builder1.length();
        builder1.append(asterisk);
        int end = builder1.length();
        builder1.setSpan(new ForegroundColorSpan(RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        addr.setHint(builder1);

        //****** HINT 2*****
        String hint2 = "City                                  ";
        SpannableStringBuilder builder2 = new SpannableStringBuilder();
        builder2.append(hint2);
        int start2 = builder2.length();
        builder2.append(asterisk);
        int end2 = builder2.length();
        builder2.setSpan(new ForegroundColorSpan(RED), start2, end2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        city.setHint(builder2);
    }

    public void goToActivity2(View view) {
        TextView addressField = (TextView)findViewById(R.id.addressField);
        TextView cityField = (TextView)findViewById(R.id.cityField);
        Spinner stateField = (Spinner)findViewById(R.id.stateField);

        if (addressField.getText().toString().matches("") || cityField.getText().toString().matches("") || stateField.getSelectedItemPosition()==0) {
            alert("Please Fill All Fields");
        }

        else {
            String[] arr = {addressField.getText().toString(), cityField.getText().toString(), stateField.getSelectedItem().toString()};

            HTTPRequestClass httpRequest = new HTTPRequestClass();
            httpRequest.execute(arr);

        }
    }

    public void alert (String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
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
            //alert("Please Enter Valid Address"); Request successfully processed
            if (result != null) {

                try {

                    JSONObject jsonObj = null;
                    jsonObj = XML.toJSONObject(result);

                    if (!jsonObj.getJSONObject("SearchResults:searchresults").getJSONObject("message").getString("text").equals("Request successfully processed")){
                        alert("Please Enter Valid Address");
                        this.cancel(true);
                     }





                    Log.d("JJJJJJJSSSSSSSOOOOONNNN", jsonObj.toString());
                   String MyStreet = jsonObj.getJSONObject("SearchResults:searchresults").getJSONObject("response").getJSONObject("results").getJSONObject("result").getJSONObject("address").getString("street");
                    String Myzipcode = jsonObj.getJSONObject("SearchResults:searchresults").getJSONObject("response").getJSONObject("results").getJSONObject("result").getJSONObject("address").getString("zipcode");
                    String MyCity = jsonObj.getJSONObject("SearchResults:searchresults").getJSONObject("response").getJSONObject("results").getJSONObject("result").getJSONObject("address").getString("city");
                    String MyState = jsonObj.getJSONObject("SearchResults:searchresults").getJSONObject("response").getJSONObject("results").getJSONObject("result").getJSONObject("address").getString("state");
                    String MyString = MyStreet+", "+MyCity+", "+MyState+", "+Myzipcode;
                    String ZPID = jsonObj.getJSONObject("SearchResults:searchresults").getJSONObject("response").getJSONObject("results").getJSONObject("result").getString("zpid");
                    String MyPrice = jsonObj.getJSONObject("SearchResults:searchresults").getJSONObject("response").getJSONObject("results").getJSONObject("result").getJSONObject("zestimate").getJSONObject("amount").getString("content");

                    Log.d("CCCUUURREENCCCY",MyPrice);

                    //Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                    //   startActivity(intent);

                    Intent i1 = new Intent(MainActivity.this, Main3Activity.class);
                    i1.putExtra("MyStreet", MyStreet);
                    i1.putExtra("Myzipcode",Myzipcode);
                    i1.putExtra("MyCity",MyCity);
                    i1.putExtra("MyState",MyState);
                    i1.putExtra("ZPID",ZPID);
                    i1.putExtra("MyPrice",MyPrice);
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