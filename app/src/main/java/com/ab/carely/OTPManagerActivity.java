package com.ab.carely;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OTPManagerActivity extends AppCompatActivity {


    EditText inputCode;
    String verificationId;

    ProgressBar progressBar;
    TextView textMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_manager);

        inputCode = (EditText)findViewById(R.id.inputCode);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        verificationId = getIntent().getStringExtra("verificationId");
    }
    public void validateOTP(View view) {
        String verification_code = inputCode.getText().toString();
        if (!verification_code.isEmpty()){
            PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId,verification_code);
            FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(OTPManagerActivity.this, "Verification code invalid", Toast.LENGTH_SHORT).show();
                }

                }
            });
        }else{
            Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show();
        }
    }


}
