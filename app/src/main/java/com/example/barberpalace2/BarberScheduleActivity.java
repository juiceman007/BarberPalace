package com.example.barberpalace2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BarberScheduleActivity extends AppCompatActivity {

    Date displayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_schedule);

        TextView currentDate = findViewById(R.id.display_date);

        currentDate.setText(dateToString(Calendar.getInstance().getTime(), "ddd MMM yyyy"));
    }

    public Date stringToDate(String date, String format) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.parse(date);
    }

    public String dateToString(Date date, String format) {

        return DateFormat.format(format, date.getTime()).toString();

        //"ddd MMM yyyy"
        //"dd-mm-yyyy"
    }

    public ArrayList<Appointment> getAppointmentsByDate(Date date){
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Appointment").orderByChild("date").equalTo(dateToString(date, "dd-mm-yyyy"));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                SharedPreferences sharedPref = getSharedPreferences("isLoggedIn", 0);
                String barberName = sharedPref.getString("name", "");

                for (DataSnapshot singleSnapshot : snapshot.getChildren()) {

                    if(singleSnapshot.child("barber").getValue(String.class) == barberName) {
                        String name = (String) singleSnapshot.child("name").getValue(String.class);
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

                        appointments.add(appt);
                    }

                }



            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        return appointments;
    }
}