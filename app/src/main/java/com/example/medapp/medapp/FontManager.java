package com.example.medapp.medapp;

import android.content.Context;
import android.graphics.Typeface;


public class FontManager {

    public static Typeface getProximaNovaBold(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/ProximaNovaBold.otf");
    }

    public static Typeface getProximaNova(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/ProximaNovaRegular.otf");
    }
}
