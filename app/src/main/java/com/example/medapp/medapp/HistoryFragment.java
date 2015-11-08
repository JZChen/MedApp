package com.example.medapp.medapp;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;



public class HistoryFragment extends Fragment implements TabListener {

    private DBHelper dbHelper;
    private ActionBar actionBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        dbHelper = new DBHelper(this.getActivity());
        Tab tab1 = actionBar
                .newTab()
                .setText("Current Med")
                .setTabListener(this);

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        Tab tab2 = actionBar
                .newTab()
                .setText("Past Med")
                .setTabListener(this);

        actionBar.addTab(tab2);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);





        TextView toadyText = (TextView)view.findViewById(R.id.today);
        ListView listView = (ListView)view.findViewById(R.id.listView);

        DateFormat df = new SimpleDateFormat("EEEE \n MM/dd/yyyy HH:mm:ss a");
        Date today = Calendar.getInstance().getTime();

        String reportDate = df.format(today);
        toadyText.setText(reportDate);


        ArrayAdapter<String> adapter = new ScheduleAdapter(this.getActivity(),R.layout.listhistory,getAllMedication());
        listView.setAdapter(adapter);

        actionBar.show();
        return view;
    }

    public ArrayList<String> getAllMedication(){
        return new ArrayList<String>(Arrays.asList("Medication1 Taken9:00AM",
                "Medication2 Taken10:00AM",
                "Medication1 Nottaken"));


    }


    public class ScheduleAdapter extends ArrayAdapter<String>{

        Context context;
        int layoutResourceId;
        ArrayList<String> data = null;

        public ScheduleAdapter(Context context, int resource,ArrayList<String> data) {
            super(context, resource, data);
            this.layoutResourceId = resource;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            if(row == null)
            {
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);


                TextView medication = (TextView)row.findViewById(R.id.medication);
                TextView time = (TextView)row.findViewById(R.id.time);
                String str = this.data.get(position);

                medication.setText( str.split(" ")[0] );
                time.setText( str.split(" ")[1] );

            }

            return row;
        }


    }

    @Override
    public void onPause(){
        super.onPause();
        actionBar.removeAllTabs();
        actionBar.hide();
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // TODO Auto-generated method stub

    }



    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        // TODO Auto-generated method stub

    }



    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        // TODO Auto-generated method stub

    }

}
