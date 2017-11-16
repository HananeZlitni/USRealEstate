package com.example.hanan.usrealestate;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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


import static android.R.drawable.btn_star_big_on;
import static android.R.drawable.star_big_off;

public class Main3Activity extends AppCompatActivity {

    private static final String TAG = "Main3Activity";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private AppCompatActivity myact;
    private String ID="norah";
    public static final String PREFS_NAME = "MyPrefsFile";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Log.d(TAG, "onCreate: Starting. ");




        //final Drawable upArrow = getResources().getDrawable(android.R.drawable.ic_menu_revert);

         toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Property Details");
        //setToolbar();

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
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
                editor.putBoolean(ID, true);
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
