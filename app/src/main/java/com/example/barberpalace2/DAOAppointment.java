package com.example.barberpalace2;


import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOAppointment {

    private DatabaseReference databaseReference;
    public DAOAppointment()
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Appointment.class.getSimpleName());
    }

    public Task<Void> add(Appointment appointment)
    {

        return databaseReference.push().setValue(appointment);
    }

    public Task<Void>update(String key, HashMap<String, Object> hashMap)
    {
        return databaseReference.child(key).updateChildren(hashMap);
    }

}
