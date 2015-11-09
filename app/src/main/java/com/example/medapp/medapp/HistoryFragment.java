package com.example.medapp.medapp;

import android.app.Activity;
import android.app.Fragment;
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



public class HistoryFragment extends Fragment{

    private DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);


        TextView toadyText = (TextView)view.findViewById(R.id.today);
        ListView listView = (ListView)view.findViewById(R.id.listView);

        DateFormat df = new SimpleDateFormat("EEEE, MM/dd/yyyy\nHH:mm:ss a");
        Date today = Calendar.getInstance().getTime();

        String reportDate = df.format(today);
        toadyText.setTypeface(FontManager.getProximaNovaBold(this.getActivity()));
        toadyText.setText(reportDate);


        ArrayAdapter<String> adapter = new ScheduleAdapter(this.getActivity(),R.layout.listhistory,getAllMedication());
        listView.setAdapter(adapter);


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


}
