package com.example.hanan.usrealestate;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.*;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;



import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.SharedPreferences;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.R.drawable.btn_star_big_on;
import static android.R.drawable.star_big_off;

public class Main3Activity extends AppCompatActivity {

    private static final String TAG = "Main3Activity";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private AppCompatActivity myact;
    private String ID="";
    public static final String PREFS_NAME = "MyPrefsFile";

    SharedPreferences sharedpreferences;



    public static String MyChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Log.d(TAG, "onCreate: Starting. ");




        //final Drawable upArrow = getResources().getDrawable(android.R.drawable.ic_menu_revert);

         toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Property Details");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //String myZPID = getIntent().getStringExtra ("ZPID");






        //setToolbar();

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }
    public String Pstreet (){
        String text = getIntent().getStringExtra ("MyStreet");
        return text;
    }
    public String Pcity (){
        String text = getIntent().getStringExtra ("MyCity");
        return text;
    }

    public String Pstate (){
        String text = getIntent().getStringExtra ("MyState");
        return text;
    }
    public String Pzipcode (){
        String text = getIntent().getStringExtra ("Myzipcode");
        return text;
    }
    public String Pprice (){
        String text = getIntent().getStringExtra ("MyPrice");
        return text;
    }

    public String resultID (){
        String id = getIntent().getStringExtra ("ZPID");
        ID= id;
        return id;

    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1fragment(), "Details");
        adapter.addFragment(new Tab2fragment(),"Chart");
        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main3, menu);

        sharedpreferences = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(ID)) {
            menu.findItem(R.id.favorite_2).setIcon(android.R.drawable.btn_star_big_on);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        int count = 0;

        //noinspection SimplifiableIfStatement
        if (id == R.id.favorite_2) {
            if (item.getIcon().getConstantState().equals(
                    getResources().getDrawable(android.R.drawable.btn_star_big_on).getConstantState()
            )){
                item.setIcon(android.R.drawable.btn_star_big_off);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove(ID);
                editor.apply();
            }else {
                item.setIcon(android.R.drawable.btn_star_big_on);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                //editor.putBoolean(ID, true);
                editor.putString(ID,Pstreet()+", "+Pcity()+", "+Pstate());
                editor.apply();


            }
            return true;
        }

        if(id == R.id.back2){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }










    public void openURLinBrowser(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.viralandroid.com"));
        startActivity(browserIntent);
    }

    /*private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(android.R.drawable.star_big_off);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    } */



}
