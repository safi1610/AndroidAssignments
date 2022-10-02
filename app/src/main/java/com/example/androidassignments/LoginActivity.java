package com.example.androidassignments;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Log.i(ACTIVITY_NAME, "onCreate()");

        EditText email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        EditText password = (EditText) findViewById(R.id.editTextTextPassword);
        SharedPreferences pref = getSharedPreferences("DefaultEmail", MODE_PRIVATE);
        String myPref = pref.getString("DefaultEmail", "email@domain.com");

        email.setText(myPref);



        Button submit = (Button) findViewById(R.id.button2);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginEmail = email.getText().toString();

                SharedPreferences.Editor edit = pref.edit();
                edit.putString("LoginEmail", loginEmail);
                edit.commit();
                Toast.makeText(LoginActivity.this, "Info Saved", Toast.LENGTH_LONG).show();

                String pass = password.getText().toString();
                Intent startNewAcitivity = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(startNewAcitivity);
            }
        });
    }

    /*@Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String myPref = pref.getString("editTextTextEmailAddress", "email@domain.com");

        Log.i(ACTIVITY_NAME, "onCreate()");
        EditText email = findViewById(R.id.editTextTextEmailAddress);
        EditText password = findViewById(R.id.editTextTextPassword);

        Button submit = findViewById(R.id.button2);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString();
                String pass = password.getText().toString();
                Intent startNewAcitivity = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(startNewAcitivity);
            }
        });
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "onResume()");


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "onStart()");


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "onPause()");


    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "onStop()");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "onDestroy()");



    }
}