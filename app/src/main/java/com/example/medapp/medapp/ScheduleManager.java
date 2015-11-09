package com.example.medapp.medapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Reminders;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.Calendar;


public class ScheduleManager {

    public ScheduleManager(){


    }

    public static Notification getNotification(String content,Context context) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.addbutton);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);

        return builder.build();
    }

    public static void addAlarm(Context context,long time){

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra("Message", "Med1");
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, getNotification("Get you med",context));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Calendar calendar = Calendar.getInstance();
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        //alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()+2*60*1000,pendingIntent);
    }

    public static void addEvent(Context context,long time,String title){

        ContentResolver cr= context.getContentResolver();

        Calendar beginTime= Calendar.getInstance();

        long startTime=beginTime.getTimeInMillis();
        Calendar endTime=Calendar.getInstance();

        long end1=endTime.getTimeInMillis();
        ContentValues calEvent = new ContentValues();
        calEvent.put(CalendarContract.Events.CALENDAR_ID, 1); // XXX pick)
        calEvent.put(CalendarContract.Events.TITLE, title);
        calEvent.put(CalendarContract.Events.DTSTART, time);
        calEvent.put(CalendarContract.Events.DTEND, time+60*60*1000);
        calEvent.put(CalendarContract.Events.HAS_ALARM, 1);
        calEvent.put(CalendarContract.Events.EVENT_TIMEZONE, CalendarContract.Calendars.CALENDAR_TIME_ZONE);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.READ_CONTACTS}, 0);
        }

            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, calEvent);

        // The returned Uri contains the content-retriever URI for
        // the newly-inserted event, including its id
        int id = Integer.parseInt(uri.getLastPathSegment());
        //Toast.makeText(context, "Created Calendar Event " + id, Toast.LENGTH_SHORT).show();

        // String reminderUriString = "content://com.android.calendar/reminders";

        ContentValues reminders = new ContentValues();
        reminders.put(Reminders.EVENT_ID,id);
        reminders.put(Reminders.METHOD, Reminders.METHOD_ALERT);
        reminders.put(Reminders.MINUTES, 1);


        Uri uri2 = cr.insert(Reminders.CONTENT_URI, reminders);

        //Toast.makeText(context, "Reminder have been saved succes fully", Toast.LENGTH_SHORT).show();

    }
}
