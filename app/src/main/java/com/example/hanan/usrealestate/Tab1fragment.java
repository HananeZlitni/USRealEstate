package com.example.hanan.usrealestate;

import android.content.Intent;
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
import android.view.View;


/**
 * Created by norahmd on 12 نوف، 2017 م.
 */

public class Tab1fragment extends Fragment {

    private static final String TAG = "Tab1Fragment";




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.tab1_fragment,container,false);


        ///////// ##### ///////
        String detailsText = ((Main3Activity) getActivity()).Pstreet();

        return view ;
    }



    public void MYMETHOD (String result9){
       // TextView t = (TextView) getView().findViewById(R.id.textTab1);
        Log.d("MY MSG", result9);
        //t.setText(result9);
       // Log.d("this is msg", result9);


    }


}

