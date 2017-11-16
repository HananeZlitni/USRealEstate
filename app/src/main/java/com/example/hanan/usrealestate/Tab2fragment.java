package com.example.hanan.usrealestate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by norahmd on 12 نوف، 2017 م.
 */

public class Tab2fragment extends Fragment {

    private static final String TAG = "Tab2Fragment";
    public TextView t2;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment,container,false);
        t2 = view.findViewById(R.id.textTab2);
       // String id = Main3Activity.MyChart;
        String cId = ((Main3Activity) getActivity()).resultID();
        HTTPRequestClass2 httpRequest2 = new HTTPRequestClass2();
         httpRequest2.execute(cId);
        Log.d("myyyyyyyyy id from Act",cId);
        //t2.setText(cId);
       // String ChartId = ((Main3Activity) getActivity()).resultID();
       // t2.setText(ChartId);


        return view ;
    }

    public void myF (String s){
        t2.setText(s);
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
                    t2.setText(MyString1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

