package com.example.hanan.usrealestate;


import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.*;
import android.support.v4.view.ViewPager;



import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;



import static android.R.drawable.btn_star_big_on;

public class Main3Activity extends AppCompatActivity {

    private static final String TAG = "Main3Activity";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private AppCompatActivity myact;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Log.d(TAG, "onCreate: Starting. ");

        //ActionBar actionBar = getActionBar();
        //actionBar.setDisplayShowTitleEnabled(false);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //myact.setSupportActionBar(toolbar);
         toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
       // MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_main3, menu);

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
             item.setIcon(android.R.drawable.btn_star_big_on);
            return true;
        }

        if(id == R.id.back2){
            finish();
        }
        return super.onOptionsItemSelected(item);
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
