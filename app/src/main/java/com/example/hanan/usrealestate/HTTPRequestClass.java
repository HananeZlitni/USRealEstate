package com.example.hanan.usrealestate;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by waad on 12/11/2017.
 */

public class HTTPRequestClass extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground (String... params){
        try{
            URL url = new URL("http://www.zillow.com/webservice/GetDeepSearchResults.htm");//zws-id=X1-ZWz1g43lmml3wr_2hwdv
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            // read result
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null ){
                sb.append(line);
                break;
            }
            br.close();
            return sb.toString();
        } //end try
        catch (Exception e){
            System.out.println(e);
        }
        return "";
    }

    @Override
    protected void onPostExecute(String result){
        if (result!=null) {

            try {
                JSONArray jsonArray = new JSONArray(result);
                String symbol, name, desc;
                for (int i=0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                }
            }

            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}