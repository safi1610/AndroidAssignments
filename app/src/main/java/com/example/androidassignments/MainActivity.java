package com.example.androidassignments;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity {
    protected static final String ACTIVITY_NAME = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "onCreate()");
        setContentView(R.layout.activity_main);

        Button iAmAButt = findViewById(R.id.button);
        iAmAButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startAcitivity2 = new Intent(MainActivity.this, ListItemsActivity.class);
                startActivityForResult(startAcitivity2, 10);
            }


        });

        Button startChat = findViewById(R.id.chatButton);

        startChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked start chat");
                Intent enterChar = new Intent(MainActivity.this, ChatWindow.class);
                startActivity(enterChar);
            }
        });

        Button toolBar = findViewById(R.id.testToolBar);

        toolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent accessToolBar = new Intent(MainActivity.this, TestToolbar.class);
                startActivity(accessToolBar);
            }
        });

        Button weather = findViewById(R.id.weatherButton);

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent accessForecast = new Intent(MainActivity.this, WeatherForcast.class);
                startActivity(accessForecast);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            String messagePassed = data.getStringExtra("Response");
            Toast.makeText(MainActivity.this, messagePassed, Toast.LENGTH_LONG).show();
            Log.i(ACTIVITY_NAME, "Returned to MainActivity.onActivityResult");
        }
    }

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