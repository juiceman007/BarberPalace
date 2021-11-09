package com.example.barberpalace2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class SignUp extends AppCompatActivity {


    public static final String NAME = "com.example.barberpalace2.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        TextView error = findViewById(R.id.SignUpError);
        EditText name_signUp = findViewById(R.id.name_signUp);
        EditText email_signUp = findViewById(R.id.email_signUp);
        EditText password_signUp = findViewById(R.id.password_signUp);
        Button signUpBtn = findViewById(R.id.signUpBtn);
        DAOCustomer dao = new DAOCustomer();
        signUpBtn.setOnClickListener(v->
        {
            if(checkEmailExists(email_signUp.getText().toString()))
            {
                error.setText("An account with this email already exists");
            }
            else {

                User user = new User(name_signUp.getText().toString(), email_signUp.getText().toString(), password_signUp.getText().toString());
                dao.add(user).addOnSuccessListener(suc ->
                {


                    Toast.makeText(this, "You have Successfully been Registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, HomeActivity.class);
                    String name = name_signUp.getText().toString();
                    String email = email_signUp.getText().toString();
                    String pwd = password_signUp.getText().toString();

                    SharedPreferences settings = getSharedPreferences("isLoggedIn", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("name", name);
                    editor.putString("email", email);
                    editor.putString("pwd", pwd);
                    editor.putBoolean("loggedIn", true);
                    editor.commit();
                    startActivity(intent);

                }).addOnFailureListener(er ->
                {
                    Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });

            }
        });


    }

    public Boolean checkEmailExists(String email){

        final Boolean[] emailExists = {false};

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("User").orderByChild("email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for (DataSnapshot singleSnapshot : snapshot.getChildren()) {

                    if(singleSnapshot.child("email").getValue(String.class) == email) {
                       emailExists[0] = true;
                    }

                }



            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        return emailExists[0];

    }

}