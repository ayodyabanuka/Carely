package com.ab.carely;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MobileLoginActivity extends AppCompatActivity {

    EditText inputMobile;
    Button buttonGetOTP;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_login);

        inputMobile = (EditText)findViewById(R.id.mobileNumber);
        buttonGetOTP = (Button)findViewById(R.id.getOTP);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();

        buttonGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputMobile.getText().toString().isEmpty()){
                    Toast.makeText(MobileLoginActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                buttonGetOTP.setVisibility(View.GONE);


                String phoneNumber = inputMobile.getText().toString();
                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(mAuth)
                                .setPhoneNumber("+94"+phoneNumber)       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(MobileLoginActivity.this)                 // Activity (for callback binding)
                                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        buttonGetOTP.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        buttonGetOTP.setVisibility(View.VISIBLE);
                                        Toast.makeText(MobileLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        buttonGetOTP.setVisibility(View.VISIBLE);

                                        Intent intent = new Intent(MobileLoginActivity.this,OTPManagerActivity.class);
                                        intent.putExtra("mobile",inputMobile.getText().toString());
                                        intent.putExtra("verificationId",verificationId);
                                        startActivity(intent);
                                        finish();

                                    }
                                })          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);

            }
        });
    }
}