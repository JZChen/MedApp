package com.example.medapp.medapp;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class QRCodeFragment extends Fragment implements ZXingScannerView.ResultHandler{


    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


/*
        DBHelper db = new DBHelper(this.getActivity().getApplicationContext());

        Calendar c = Calendar.getInstance();
        int seconds = c.get(Calendar.SECOND);
        ArrayList<Integer> schedule = new ArrayList<>();
        for(int i=0;i<10;i++){
            schedule.add(seconds);
            seconds += 86400;
        }
        boolean result = db.insertMedication("med1",100,schedule);
        if( !result ){
            Toast.makeText(this.getActivity(), "Fail to insert", Toast.LENGTH_LONG).show();
        }
        int i = 1;
        while(!result){
            String med = "med";
            result = db.insertMedication(med+String.valueOf(i),100,schedule);
            i++;
        }
*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle state) {
        // Inflate the layout for this fragment
        mScannerView = new ZXingScannerView(getActivity());
        View view = inflater.inflate(R.layout.fragment_qrcode, container, false);
        FrameLayout layout = (FrameLayout) view.findViewById(R.id.scanner);
        layout.addView(mScannerView);


        EditText et = (EditText) view.findViewById(R.id.nid);



        //parent.indexOfChild()
        /*
        if(state != null) {
            mFlash = state.getBoolean(FLASH_STATE, false);
            mAutoFocus = state.getBoolean(AUTO_FOCUS_STATE, true);
            mSelectedIndices = state.getIntegerArrayList(SELECTED_FORMATS);
            mCameraId = state.getInt(CAMERA_ID, -1);
        } else {
            mFlash = false;
            mAutoFocus = true;
            mSelectedIndices = null;
            mCameraId = -1;
        }
        setupFormats();
*/
  //      return inflater.inflate(R.layout.fragment_qrcode, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v("QR", rawResult.getText()); // Prints scan results
        Log.v("QR", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
    }


}
