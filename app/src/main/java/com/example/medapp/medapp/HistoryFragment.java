package com.example.medapp.medapp;

import android.app.Fragment;
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


public class HistoryFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_history, container, false);


        TextView toadyText = (TextView)view.findViewById(R.id.today);
        ListView listView = (ListView)view.findViewById(R.id.listView);

        DateFormat df = new SimpleDateFormat("EEEE \n MM/dd/yyyy HH:mm:ss a");
        Date today = Calendar.getInstance().getTime();

        String reportDate = df.format(today);
        toadyText.setText(reportDate);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,getAllMedication());
        listView.setAdapter(adapter);


        return view;
    }

    public ArrayList<String> getAllMedication(){
        return new ArrayList<String>(Arrays.asList("This","is","a","test","list"));
    }


}
