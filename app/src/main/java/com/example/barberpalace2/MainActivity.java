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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.barberpalace2.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signUp_btn = findViewById(R.id.signUpLink);
        Button signIn_btn = findViewById(R.id.signIn);
        EditText email = findViewById(R.id.email_login);
        EditText pwd = findViewById(R.id.password_login);

        signIn_btn.setOnClickListener(v ->
        {

            signInUser(email.getText().toString().trim(), pwd.getText().toString().trim());

        });

    }

    public void goToSignUpPage(View view)
    {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void signInUser(String email, String pwd)
    {

        String emailCheck = email;
        String pwdCheck = pwd;
        String type;
        final Boolean[] signInSuccess = {false};

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("User").orderByChild("email").orderByChild("type");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

              for (DataSnapshot singleSnapshot : snapshot.getChildren()) {
                  if (singleSnapshot.child("email").getValue().toString().trim() == email.trim()) {
                      String name = (String) singleSnapshot.child("name").getValue(String.class);
                      //System.out.println("Name: "+name);
                      signInSuccess[0] = true;
                      SharedPreferences settings = getSharedPreferences("isLoggedIn", 0);
                      SharedPreferences.Editor editor = settings.edit();
                      editor.putString("name", name);
                      editor.putString("email", email);
                      editor.putString("pwd", pwd);
                      editor.putBoolean("loggedIn", true);
                      editor.putString("type",singleSnapshot.child("type").getValue().toString());
                      editor.commit();

                      if (singleSnapshot.child("type").getValue().toString().trim() == "Barber"){
                          Intent intent = new Intent(getApplicationContext(), BarberScheduleActivity.class);
                          intent.putExtra(EXTRA_MESSAGE, name);
                          startActivity(intent);
                      }
                      else {


                          Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                          intent.putExtra(EXTRA_MESSAGE, name);
                          startActivity(intent);
                      }

                  }


              }

                 if (signInSuccess[0] == false){
                     TextView error_text = findViewById(R.id.login_error_message);
                     error_text.setText("Incorrect email or password");
                 }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}