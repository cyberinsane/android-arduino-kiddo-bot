package com.example.achordia.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.achordia.moviesapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isUserLoggedIn = this.isUserCredentialStored();

        if(isUserLoggedIn) {
            showMovieListData();
            return;
        }

        Button continueButton = (Button)findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = ((EditText)findViewById(R.id.username_field)).getText().toString().trim();
                final String password = ((EditText)findViewById(R.id.password_field)).getText().toString().trim();

                if (username.length() > 0 && password.length() > 0) {
                    SharedPreferences prefs = getApplicationContext().getSharedPreferences("Movies_preferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.commit();

                    // Go to next screen
                    showMovieListData();

                }
                else {
                    // Reset username and password
                    ((EditText)findViewById(R.id.username_field)).setText("");
                    ((EditText)findViewById(R.id.password_field)).setText("");
                }
            }
        });

    }

    private  void showMovieListData() {

        Intent intent = new Intent(MainActivity.this, com.example.achordia.moviesapp.MovieListActivity.class);
        startActivity(intent);
    }


    private boolean isUserCredentialStored() {

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("Movies_preferences", Context.MODE_PRIVATE);
        String username = prefs.getString("username", null);
        String password = prefs.getString("password", null);

        if (username != null && password != null) {
            return true;
        }

        return false;

    }
}
