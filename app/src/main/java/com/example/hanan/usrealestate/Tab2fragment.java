package com.example.hanan.usrealestate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
  //  public TextView t2;
    public String URL1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment,container,false);
       // t2 = view.findViewById(R.id.textTab2);

        // use this id in second HTTP request
        String cId = ((Main3Activity) getActivity()).resultID();

        HTTPRequestClass2 httpRequest2 = new HTTPRequestClass2();
         httpRequest2.execute(cId);






        //Uri myuri = Uri.parse("https://yt3.ggpht.com/-v0soe-ievYE/AAAAAAAAAAI/AAAAAAAAAAA/OixOH_h84Po/s900-c-k-no-mo-rj-c0xffffff/photo.jpg");

       /* WebView picView = (WebView)view.findViewById(R.id.pic_view);
        picView.setBackgroundColor(0);
        picView.loadUrl("https://yt3.ggpht.com/-v0soe-ievYE/AAAAAAAAAAI/AAAAAAAAAAA/OixOH_h84Po/s900-c-k-no-mo-rj-c0xffffff/photo.jpg");
            */

        //set Chart
        PhotoView photoView = (PhotoView)view.findViewById(R.id.photo_view);
        Picasso.with(getContext()).load("https://www.zillow.com:443/app?chartDuration=1year&chartType=partner&height=100&page=webservice%2FGetChart&service=chart&showPercent=true&width=200&zpid=2093613455"
).fit().into(photoView);


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

    public void myF (String s){
      //  t2.setText(s);
    }

    class HTTPRequestClass2 extends AsyncTask<String, Void, String> {
        //private final Context context;

       /* public HTTPRequestClass() {
            this.context = context;
        } */

        @Override
        protected String doInBackground(String... params) {
            try {
                String zid;
                zid = params[0];


                URL url = new URL("http://www.zillow.com/webservice/GetChart.htm?zws-id=X1-ZWz1g43lmml3wr_2hwdv&zpid="+zid+"&unit-type=percent&width=200&height=100");
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
                    Log.d("JJJJJJJSSSSSOOONNNNN", jsonObj.toString());
                    String MyString1 = jsonObj.getJSONObject("Chart:chart").getJSONObject("response").getString("url");
                    Log.d("MY UUUUURRRRRRLLLLL",MyString1);
                    //t2.setText(MyString1);
                    URL1 = MyString1;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }




}




