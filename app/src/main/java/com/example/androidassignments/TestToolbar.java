package com.example.androidassignments;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.androidassignments.databinding.ActivityTestToolbarBinding;

public class TestToolbar extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityTestToolbarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTestToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_toolbar);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This is useless", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_toolbar);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public boolean onCreateOptionsMenu(Menu m) {

        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }
    public boolean onOptionItemSelected(MenuItem mi){
        int id = mi.getItemId();

        switch (id){
            case R.id.action_one:
                Log.d("Toolbar", "Option 1 Selected");
                break;
            case R.id.action_two:
                Log.d("Toolbar", "Option 2 Selected");
                AlertDialog.Builder op2Selec = new AlertDialog.Builder(this);
                op2Selec.setTitle("Do you want to complete this action");

                op2Selec.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(TestToolbar.this, "Action Complete", Toast.LENGTH_LONG).show();

                    }


                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(TestToolbar.this, "Action not complete", Toast.LENGTH_SHORT).show();
                    }
                }).show();

                break;
            case R.id.action_three:
                Log.d("Toolbar", "Option 3 Selected");
                break;
            case R.id.action_four:
                Toast.makeText(TestToolbar.this, "Mohammad Safi", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}