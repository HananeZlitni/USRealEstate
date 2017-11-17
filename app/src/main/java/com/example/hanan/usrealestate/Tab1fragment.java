package com.example.hanan.usrealestate;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;


/**
 * Created by norahmd on 12 نوف، 2017 م.
 */

public class Tab1fragment extends Fragment {

    private static final String TAG = "Tab1Fragment";
    public TextView t;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.tab1_fragment,container,false);
        t = view.findViewById(R.id.textTab1);

        //*******LOCATION*******
        String detailsText = ("View the Location of \n"+((Main3Activity) getActivity()).Pstreet()+", "+((Main3Activity) getActivity()).Pcity()+", "+((Main3Activity) getActivity()).Pstate());
        t.setText(detailsText);

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+((Main3Activity) getActivity()).Pstreet()+", "+((Main3Activity) getActivity()).Pcity()+", "+((Main3Activity) getActivity()).Pstate()); //+((Main3Activity) getActivity()).Platitude()+","+((Main3Activity) getActivity()).Plongitude()+"?z="+18
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        //*******TABLE*******
        TableLayout table = (TableLayout) view.findViewById(R.id.table);

        TableRow tHead = new TableRow(getActivity());
        tHead.setBackgroundColor(Color.GRAY);
        tHead.setWeightSum(1);

        TableRow tRow = new TableRow(getActivity());
        tRow.setBackgroundResource(R.drawable.row_border);
        tRow.setWeightSum(1);

        TextView fullAddrs = new TextView(getActivity());
        fullAddrs.setGravity(1);
        fullAddrs.setTextColor(Color.WHITE);
        fullAddrs.setTextSize(18);
        fullAddrs.setWidth(100);
        fullAddrs.setText("Full Address");
        tHead.addView(fullAddrs);

        TextView price = new TextView(getActivity());
        price.setGravity(1);
        price.setTextColor(Color.WHITE);
        price.setTextSize(18);
        price.setWidth(100);
        price.setText("     Price");
        tHead.addView(price);

        table.addView(tHead);

        TextView propertyAddr = new TextView(getActivity());
        propertyAddr.setGravity(1);
        propertyAddr.setTextColor(Color.BLACK);
        propertyAddr.setTextSize(16);
        propertyAddr.setWidth(150);
        propertyAddr.setText(((Main3Activity) getActivity()).Pstreet()+", "+((Main3Activity) getActivity()).Pcity()+", "+((Main3Activity) getActivity()).Pstate()+", "+((Main3Activity) getActivity()).Pzipcode());
        tRow.addView(propertyAddr);
        //table.addView(tr);

        TextView propertyPrice = new TextView(getActivity());
        propertyPrice.setGravity(1);
        propertyPrice.setTextColor(Color.BLACK);
        propertyPrice.setTextSize(16);
        propertyAddr.setWidth(100);
        propertyPrice.setText("   $"+((Main3Activity) getActivity()).Pprice());
        tRow.addView(propertyPrice);

        table.addView(tRow);

        return view ;
    }



    public void MYMETHOD (String result9){
       // TextView t = (TextView) getView().findViewById(R.id.textTab1);
        Log.d("MY MSG", result9);
        //t.setText(result9);
       // Log.d("this is msg", result9);


    }


}

