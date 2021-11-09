package com.example.barberpalace2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    //private static final String EXTRA_MESSAGE = ;
    String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        checkUserLoggedIn();
        //Intent intent = getIntent();
        //String getName = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView name = findViewById(R.id.name);
        TextView appointment = findViewById(R.id.appointment);
        loadAppointment(appointment);
        Button appointment_btn = findViewById(R.id.make_appointment);
        appointment_btn.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, AppointmentActivity.class);
            startActivity(intent);
        });

        name.setText(userName);



    }

    public void checkUserLoggedIn() {
        SharedPreferences sharedPref = getSharedPreferences("isLoggedIn", 0);
        String name = sharedPref.getString("name", "");
        String password = sharedPref.getString("pwd", "");
        Boolean isUserLoggedIn = sharedPref.getBoolean("loggedIn", false);
        String type = sharedPref.getString("type", "");

        if (isUserLoggedIn) {
            userName = name;
            if(type == "Barber")
            {
                Intent intent = new Intent(getApplicationContext(), BarberScheduleActivity.class);
                //intent.putExtra(EXTRA_MESSAGE, name);
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    public void loadAppointment(TextView textView) {
        SharedPreferences sharedPref = getSharedPreferences("isLoggedIn", 0);
        String email = sharedPref.getString("email", "");
        String nextAppointment;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Appointment").orderByChild("email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String appointment = "";

                for (DataSnapshot singleSnapshot : snapshot.getChildren()) {
                    String name = (String) singleSnapshot.child("name").getValue(String.class);
                    //System.out.println("Name: "+name);
                    Appointment appt = new Appointment();
                    appt.setDate(singleSnapshot.child("date").getValue(String.class));
                    appt.setDay(singleSnapshot.child("day").getValue(Integer.class));
                    appt.setMonth(singleSnapshot.child("month").getValue(Integer.class));
                    appt.setYear(singleSnapshot.child("year").getValue(Integer.class));
                    appt.setBarber(singleSnapshot.child("barber").getValue(String.class));
                    appt.setEmail(singleSnapshot.child("email").getValue(String.class));
                    appt.setHaircut(singleSnapshot.child("haircut").getValue(String.class));
                    appt.setName(singleSnapshot.child("name").getValue(String.class));
                    appt.setNickname(singleSnapshot.child("nickname").getValue(String.class));
                    appt.setPrice(singleSnapshot.child("price").getValue(Float.class));
                    appt.setRepeat(singleSnapshot.child("repeat").getValue(Boolean.class));
                    appt.setRepeat_frequency(singleSnapshot.child("repeat_frequency").getValue(String.class));
                    appt.setSlot_num(singleSnapshot.child("slot_num").getValue(Integer.class));
                    appt.setTime(singleSnapshot.child("time").getValue(String.class));

                    try {
                         appointment = formatAppointment(appt);
                         textView.setText(appointment);


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public String formatAppointment(Appointment appointment) throws ParseException {
        String str;
        Date date = stringToDate(appointment.getDate(), "dd-MM-yyyy");
        String dayName = (String) android.text.format.DateFormat.format("EEEE", date);
        String monthName = (String) android.text.format.DateFormat.format("MMM", date);
        str = "Next appointment: \n" + dayName + " " + monthName + " " + appointment.getYear() + "\n" + appointment.getTime() + "\n" + appointment.getPrice();

        return str;
    }

    public Date stringToDate(String date, String format) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.parse(date);
    }
};

