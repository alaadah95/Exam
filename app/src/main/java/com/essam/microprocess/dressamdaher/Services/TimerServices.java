package com.essam.microprocess.dressamdaher.Services;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.essam.microprocess.dressamdaher.Enums.DataBase_Refrences;
import com.essam.microprocess.dressamdaher.JsonModel.ExamStartTime_Pojo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by microprocess on 2018-10-17.
 */

public class TimerServices extends Service {
    CountDownTimer TimerCounter ;
    String TableName;


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        TableName =  intent.getStringExtra("TableName");

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference(DataBase_Refrences.STARTEDEXAM.getRef())
                .child(TableName).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    ExamStartTime_Pojo time_pojo = dataSnapshot.getValue(ExamStartTime_Pojo.class);
                    String EndTime   = time_pojo.getEndTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String CurrentTime = sdf.format(new Date());


                    StartThread(ConvertRemiderTimeToMilliSecond(CurrentTime,EndTime));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {

        if(TimerCounter != null){

            TimerCounter.cancel();
        }

        super.onDestroy();
    }
    long ConvertRemiderTimeToMilliSecond(String CurrentTime ,String EndTime){

        //وظيفة ال method   هي جلب الوقت الباقي للمستخدم عند دخول الامتحان و تحويلها إلي ثواني
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date txdate = null;
        Date txdate2 = null;
        try {
            txdate = df.parse(CurrentTime);
            txdate2 = df.parse(EndTime);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = txdate2.getTime() - txdate.getTime();

        return diff ;
    }

    void StartThread(long TimerInMilliSecond){

        TimerCounter =  new CountDownTimer(TimerInMilliSecond, 1000) {
            @Override
            public void onTick(long l) {       // l is Reminder of time


                long timeInSeconds = l / 1000;
                long hours, minutes, seconds;
                hours = timeInSeconds / 3600;
                timeInSeconds = timeInSeconds - (hours * 3600);
                minutes = timeInSeconds / 60;
                timeInSeconds = timeInSeconds - (minutes * 60);
                seconds = timeInSeconds;

                //  Timer.setText(String.valueOf(hours)+" : "+String.valueOf(minutes)+" : "+String.valueOf(seconds));
                Log.d("TAG1",String.valueOf(hours)+ " / "+ String.valueOf(minutes) + " / "+ String.valueOf(seconds) );

                Intent local = new Intent("ServicesTimer");
                local.putExtra("hours",hours);
                local.putExtra("minutes",minutes);
                local.putExtra("seconds",seconds);

                sendBroadcast(local);

            }

            @Override
            public void onFinish() {


            }
        }.start();

    }

}
