package com.example.medapp.medapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

    ImageView tab1,tab2,tab3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tab1 = (ImageView)findViewById(R.id.tab1);
        tab2 = (ImageView)findViewById(R.id.tab2);
        tab3 = (ImageView)findViewById(R.id.tab3);


        FragmentManager fragMgr = getFragmentManager();
        final HomeFragment mainFrag = new HomeFragment();
        fragMgr.beginTransaction().add(R.id.frameLayout, mainFrag).commit();

        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragMgr = getFragmentManager();
                FragmentTransaction transaction = fragMgr.beginTransaction();
                HistoryFragment historyFrag = new HistoryFragment();
                transaction.replace(R.id.frameLayout, historyFrag);
                transaction.commit();

            }
        });

        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragMgr = getFragmentManager();
                FragmentTransaction transaction = fragMgr.beginTransaction();
                transaction.replace(R.id.frameLayout, mainFrag);
                transaction.commit();
            }
        });

        // Interact
        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragMgr = getFragmentManager();
                FragmentTransaction transaction = fragMgr.beginTransaction();
                QRCodeFragment qefrag = new QRCodeFragment();
                transaction.replace(R.id.frameLayout, qefrag);
                transaction.commit();

            }
        });

    }
}
