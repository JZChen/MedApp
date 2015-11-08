package com.example.medapp.medapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
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
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {
    private DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView toadyText = (TextView)view.findViewById(R.id.todayTextView);
        ListView listView = (ListView)view.findViewById(R.id.listView);

        DateFormat df = new SimpleDateFormat("EEEE \n MM/dd/yyyy HH:mm:ss a");
        Date today = Calendar.getInstance().getTime();

        String reportDate = df.format(today);
        toadyText.setText(reportDate);


        ArrayAdapter<String> adapter = new ScheduleAdapter(this.getActivity(),R.layout.listhistory,getAllMedication());
        listView.setAdapter(adapter);


        return view;
    }

    private ArrayList<String> getAllMedication(){
        ArrayList<String> list = new ArrayList<String>();
        Cursor c = dbHelper.getAllData();
        while (c.moveToNext()) {
            Cursor cc = dbHelper.getSchedule( c.getString(1) );

            String date = "";
            while(cc.moveToNext()){
                Integer i = cc.getInt(1);
                date += String.valueOf(i) + "\n";
                System.out.println(date);

            }

            list.add(c.getString(0)+" "+c.getString(1)+" "+date);
        }

        return list;
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

                medication.setText( str.split(" ")[1] );
                time.setText( str.split(" ")[2] );

            }

            return row;
        }


    }

}
