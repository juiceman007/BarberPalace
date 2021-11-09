package com.example.barberpalace2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AppointmentActivity extends AppCompatActivity {

    public Context context = this;
    public SlotListAdapter slotAdapter;
    public float price = 5;
    public int duration = 0;
    public String calDate;
    public int calDay;
    public int calMonth;
    public int calYear;
    public String time;
    public int slot_num;
    public String haircut;
    public String Barber = "Matthew Bayley";
    public String Name;
    public String nickname = "";
    public String email;
    public boolean repeat = false;
    public String repeat_frequency = "";
    public Date currentdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        CalendarView calendar = findViewById(R.id.bookingCalendar);
        ExpandableHeightGridView timeSlotGrid = (ExpandableHeightGridView) findViewById(R.id.slotGrid);
        timeSlotGrid.setExpanded(true);
        CheckBox haircut = findViewById(R.id.checkbox_haircut);
        CheckBox marks = findViewById(R.id.checkbox_marks);
        CheckBox design = findViewById(R.id.checkbox_design);

        Button make_appointment = findViewById(R.id.continue_btn);
        boolean itemClicked  = false;



       make_appointment.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               AlertDialog.Builder builder = new AlertDialog.Builder(context);
               builder.setTitle("Confirm");
               builder.setMessage("Are you sure you want to make this appointment?");
               builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                     Appointment appointment = getAppointmentDetails();
                     DAOAppointment dao = new DAOAppointment();
                     dao.add(appointment);
                       Toast.makeText(context, "Your appointment has been made successfully", Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(context, HomeActivity.class);
                       startActivity(intent);

                   }
               });
               builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                   }
               });
               builder.show();
           }
       });

        ArrayList<TimeSlot> slots = new ArrayList<TimeSlot>();

        for (int i = 0; i <= 20; i++)
        {
            slots.add(new TimeSlot(true, Common.convertTimeSlotToString(i), i));
        }

         loadSlots(slots, timeSlotGrid);

        timeSlotGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //change all other items background color back to white
                    for (int i = 0; i < parent.getChildCount(); i++){
                        View c = parent.getChildAt(i);

                        c.setBackgroundColor(Color.WHITE);


                    }
                    //change current selected item background
                        // TextView text = parent.getChildAt(position).findViewById(R.id.time);
                       // text.setTextColor(Color.WHITE);
                        slot_num = slots.get(position).slotNum;
                        time = slots.get(position).time;
                        view.setBackgroundColor(Color.LTGRAY);

            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int date) {
                currentdate = Calendar.getInstance().getTime();
                calDate = date + "-" + month + "-" + year;
                calDay = date;
                calMonth = month;
                calYear = year;
                //refresh grid so that nothing is selected
                String dateFormat = date + "-" + month + "-" + year;

                    ArrayList<Integer> unavailable = getAppointmentsByDate(dateFormat);
                   // slots.clear();
                    //for (int i=0; i<=20; i++)
                   // {


                        for (int j=0; j < unavailable.size(); j++) {

                                slots.get(unavailable.get(j)).setAvailable(false);

                        }
                  //  }

                    loadSlots(slots, timeSlotGrid);


                //change the data of slots  according to date
                //slots = newData; and call
                slotAdapter.notifyDataSetChanged();

            }
        });



    }

    public ArrayList<Integer> getAppointmentsByDate(String date){

        ArrayList<Appointment> appointments;
        ArrayList<Integer> closedSlots = new ArrayList<Integer>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Appointment").orderByChild("date").equalTo(date);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {


                for (DataSnapshot singleSnapshot : snapshot.getChildren()) {
                   // String name = (String) singleSnapshot.child("name").getValue(String.class);
                    //System.out.println("Name: "+name);
                   // Appointment appt = new Appointment();
                   // appt.setDay(date);
                  //  appt.setDay(singleSnapshot.child("day").getValue(String.class));
                  //  appt.setMonth(singleSnapshot.child("month").getValue(String.class));
                  //  appt.setYear(singleSnapshot.child("year").getValue(String.class));
                  //  appt.setBarber(singleSnapshot.child("barber").getValue(String.class));
                  //  appt.setEmail(singleSnapshot.child("email").getValue(String.class));
                  //  appt.setHaircut(singleSnapshot.child("haircut").getValue(String.class));
                  //  appt.setName(singleSnapshot.child("name").getValue(String.class));
                  //  appt.setNickname(singleSnapshot.child("nickname").getValue(String.class));
                  //  appt.setPrice(singleSnapshot.child("price").getValue(String.class));
                  //  appt.setRepeat(singleSnapshot.child("repeat").getValue(String.class));
                  //  appt.setRepeat_frequency(singleSnapshot.child("repeat_frequency").getValue(String.class));
                  //  appt.setSlot_num(singleSnapshot.child("slot_num").getValue(String.class));
                  //  appt.setTime(singleSnapshot.child("time").getValue(String.class));

                  //  appointments.add(appt);
                    closedSlots.add(singleSnapshot.child("slot_num").getValue(Integer.class));
                    System.out.println(closedSlots);
                }



            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        return closedSlots;
    }

    public void loadSlots (ArrayList<TimeSlot> slots, ExpandableHeightGridView gridView)
    {
        slotAdapter = new SlotListAdapter(this, slots);

        gridView.setAdapter(slotAdapter);
    }

    public void onCheckBoxClick(View view){
         TextView price_text = findViewById(R.id.price_text);
        TextView apptDuration = findViewById(R.id.duration);

        if (((CheckBox) view).isChecked() == true) {
            switch (view.getId()){
                case R.id.checkbox_haircut: price = price + 20; price_text.setText(((int) price) + ".00"); duration = duration + 30; apptDuration.setText(new StringBuilder().append("Duration: ").append(duration).toString());  break; //set price text
                case R.id.checkbox_marks: price = price + 5; price_text.setText(((int) price) + ".00");duration = duration + 15; apptDuration.setText(new StringBuilder().append("Duration: ").append(duration).toString());  break; //set price text
                case R.id.checkbox_design: price = price + 10; price_text.setText(((int) price) + ".00"); duration = duration + 10; apptDuration.setText(new StringBuilder().append("Duration: ").append(duration).toString());  break; //set price text
            }
        }
        else {
            switch (view.getId()){
                case R.id.checkbox_haircut: price = price - 20; price_text.setText(((int) price) + ".00"); duration = duration - 30; apptDuration.setText(new StringBuilder().append("Duration: ").append(duration).toString());  break; //set price text
                case R.id.checkbox_marks: price = price - 5; price_text.setText(((int) price) + ".00"); duration = duration - 15; apptDuration.setText(new StringBuilder().append("Duration: ").append(duration).toString());  break; //set price text
                case R.id.checkbox_design: price = price - 10; price_text.setText(((int) price) + ".00"); duration = duration - 10; apptDuration.setText(new StringBuilder().append("Duration: ").append(duration).toString());  break; //set price text
            }
        }
    }

    public void returnToHome(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public Appointment getAppointmentDetails(){
        CheckBox haircut_check = findViewById(R.id.checkbox_haircut);
        CheckBox marks_check = findViewById(R.id.checkbox_marks);
        CheckBox design_check = findViewById(R.id.checkbox_design);


        SharedPreferences sharedPref = getSharedPreferences("isLoggedIn", 0);
        Name = sharedPref.getString("name", "");
        email = sharedPref.getString("email", "");
        String str = "";
        if(haircut_check.isChecked()){
            str= str + "Haircut";
            str= str + "|";
        }
        if(marks_check.isChecked()){
            str= str + "Marks";
            str= str + "|";
        }
        if(design_check.isChecked()){
            str= str + "Design";
            str= str + "|";
        }
        haircut = str.toString();

        Appointment newAppointment = new Appointment(calDate,  calDay,  calMonth,  calYear,  time,  slot_num,  haircut,  price,  Barber,  Name,  nickname,  email,  repeat, repeat_frequency);

        return newAppointment;
    }

    public Date stringToDate(String date, String format) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.parse(date);
    }

    public ArrayList<String> createSlots (int duration){
        Time startTime =
    }
}