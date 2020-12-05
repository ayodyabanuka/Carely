package com.ab.carely;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    Button logout;
    private FirebaseAuth logAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout);
        logAuth = FirebaseAuth.getInstance();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logAuth.signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = logAuth.getCurrentUser();
        if (currentUser == null){
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }
    }
}