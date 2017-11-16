package com.example.hanan.usrealestate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by norahmd on 12 نوف، 2017 م.
 */

public class Tab2fragment extends Fragment {

    private static final String TAG = "Tab2Fragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment,container,false);




        //Uri myuri = Uri.parse("https://yt3.ggpht.com/-v0soe-ievYE/AAAAAAAAAAI/AAAAAAAAAAA/OixOH_h84Po/s900-c-k-no-mo-rj-c0xffffff/photo.jpg");

       /* WebView picView = (WebView)view.findViewById(R.id.pic_view);
        picView.setBackgroundColor(0);
        picView.loadUrl("https://yt3.ggpht.com/-v0soe-ievYE/AAAAAAAAAAI/AAAAAAAAAAA/OixOH_h84Po/s900-c-k-no-mo-rj-c0xffffff/photo.jpg");
            */

        //set Chart
        PhotoView photoView = (PhotoView)view.findViewById(R.id.photo_view);
        Picasso.with(getContext()).load("https://yt3.ggpht.com/-v0soe-ievYE/AAAAAAAAAAI/AAAAAAAAAAA/OixOH_h84Po/s900-c-k-no-mo-rj-c0xffffff/photo.jpg").fit().into(photoView);


        //photoView.setImageDrawable(LoadImageFromWebOperations("https://yt3.ggpht.com/-v0soe-ievYE/AAAAAAAAAAI/AAAAAAAAAAA/OixOH_h84Po/s900-c-k-no-mo-rj-c0xffffff/photo.jpg"));



        //making the text clickable
        TextView textView = (TextView)view.findViewById(R.id.google);
        SpannableString ss = new SpannableString("Hello \nWorld");
        ClickableSpan span1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                // do some thing
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.viralandroid.com"));
                startActivity(browserIntent);
            }
        };
        ss.setSpan(span1, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());






        return view ;
    }



}


 class HTTPRequestClass1 extends AsyncTask<String, Void, String> {

     @Override
     protected String doInBackground(String... params) {
         try {
             String uID, pID, widh;
             widh = params[0];


             //zws-id=X1-ZWz1g43lmml3wr_2hwdv

             URL url = new URL("http://www.zillow.com/webservice/GetChart.htm?zws-id=X1-ZWz1g43lmml3wr_2hwdv&unit-type=percent&zpid=48749425&width=300&height=150");
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
                 JSONArray jsonArray = new JSONArray(result);
                 String symbol, name, desc;
                 for (int i = 0; i < jsonArray.length(); i++) {
                     JSONObject jsonObj = jsonArray.getJSONObject(i);
                 }
             } catch (JSONException e) {
                 e.printStackTrace();
             }
         }

     }
 }

