package com.example.hanan.usrealestate;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.ViewGroup;
import android.graphics.Color;
import android.widget.AdapterView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.style.*;


import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        //********** SCREEN 1 TABLE ************
        final TableRow tableRow = (TableRow)findViewById(R.id.table1row);
        final TableLayout table1 = (TableLayout)findViewById(R.id.table1);
        tableRow.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View v) {
                tableRow.setOnTouchListener(new SwipeDismissTouchListener(
                        tableRow,
                        null,
                        new SwipeDismissTouchListener.DismissCallbacks() {
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
                                                table1.removeView(tableRow);
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }
                        }));
            }
        });
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
            Intent intent = new Intent(MainActivity.this, Main3Activity.class);
            startActivity(intent);
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
}
