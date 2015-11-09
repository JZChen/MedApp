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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {
    private DBHelper dbHelper;
    private Context context = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this.getActivity());
        context = this.getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView toadyText = (TextView)view.findViewById(R.id.today);
        ListView listView = (ListView)view.findViewById(R.id.listView);
        ImageButton addButton = (ImageButton)view.findViewById(R.id.addbutton);



        DateFormat df = new SimpleDateFormat("EEEE\nMM/dd/yyyy\nHH:mm:ss a");
        Date today = Calendar.getInstance().getTime();

        String reportDate = df.format(today);
        toadyText.setText(reportDate);
        toadyText.setTypeface(FontManager.getProximaNovaBold(context));


        final ArrayAdapter<String> adapter = new ScheduleAdapter(this.getActivity(),R.layout.listhistory,getAllMedication());
        listView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // temporary add mock-up data
                addMockData();
                adapter.clear();
                adapter.addAll(getAllMedication());
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    private ArrayList<String> getAllMedication(){
        ArrayList<String> list = new ArrayList<String>();
        Cursor c = dbHelper.getAllData();
        while (c.moveToNext()) {
            Cursor cc = dbHelper.getSchedule( c.getString(1) );

            String date = "";
            while(cc.moveToNext()){
                date += cc.getString(1) + " ";
                System.out.println(date);

            }

            //list.add(c.getString(0)+" "+c.getString(1)+" "+date);
            // Get today's 00:00

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            long unixTimeStamp = calendar.getTimeInMillis();

            String str = c.getString(0)+" "+c.getString(1)+" "+date;
            String[] med = str.split(" ");
            for(int i=2;i<med.length;i++){
                Long epochDate = Long.valueOf(med[i]);
                if(unixTimeStamp < epochDate && unixTimeStamp+86400000 > epochDate ){
                    DateFormat df = new SimpleDateFormat("HH:mm:ss a");
                    String reportDate = df.format(epochDate);
                    list.add(med[1]+" "+reportDate);
                }
            }

        }

        return list;
    }


    private void addMockData(){


        DBHelper db = new DBHelper(this.getActivity().getApplicationContext());
        Calendar c = Calendar.getInstance();
        long currentTime = c.getTimeInMillis();
        // 3*60*1000
        currentTime -= 86400*1000 ;
        ArrayList<String> schedule = new ArrayList<String>();
        for(int i=0;i<3;i++){
            schedule.add(String.valueOf(currentTime));
            currentTime += 86400*1000;

            ScheduleManager.addAlarm(context,currentTime);
            ScheduleManager.addEvent(context, currentTime, "med1");

        }

        boolean result = db.insertMedication("med1",100,schedule);
        if( !result ){
           // Toast.makeText(this.getActivity(), "Fail to insert", Toast.LENGTH_LONG).show();
        }

        //Toast.makeText(context, "Reminder clicked", Toast.LENGTH_SHORT).show();

        int i = 1;
        while(!result){
            String med = "med";
            result = db.insertMedication(med+String.valueOf(i),100,schedule);
            i++;
        }


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
                System.out.println(str);
                medication.setText( str.split(" ")[0] );
                time.setText( str.split(" ")[1] );

            }

            return row;
        }


    }

}
